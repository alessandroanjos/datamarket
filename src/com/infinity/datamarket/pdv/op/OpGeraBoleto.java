package com.infinity.datamarket.pdv.op;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.boleto.Boleto;
import com.infinity.datamarket.comum.conta.ContaCorrente;
import com.infinity.datamarket.comum.pagamento.DadosChequePredatado;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoAPrazo;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoAVista;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class OpGeraBoleto extends Mic{
	
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try {
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,new Locale("pt", "BR"));

			int idLoja = Integer.parseInt(ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOJA).getValor());
			int componente = Integer.parseInt(ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.COMPONENTE).getValor());
			Usuario usu = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.OPERADOR_ATUAL);
			Loja loja = Fachada.getInstancia().consultarLojaPorId(new Long(idLoja));
			ContaCorrente contaCorrente = null;

			try {
				contaCorrente = getFachadaPDV().consultarContaCorrentePorID(loja.getIdContaCorrente() + "");
				if (contaCorrente == null) {
					gerenciadorPerifericos.getDisplay().setMensagem("Loja sem Conta Corrente");
					gerenciadorPerifericos.esperaVolta();
					gerenciadorPerifericos.getDisplay().setMensagem("A receber");
					return ALTERNATIVA_2;
				}
			} catch (Exception e) {
				gerenciadorPerifericos.getDisplay().setMensagem("Loja sem Conta Corrente");
				gerenciadorPerifericos.esperaVolta();
				gerenciadorPerifericos.getDisplay().setMensagem("A receber");
				return ALTERNATIVA_2;
			}
			
//			ArrayList boletos = new ArrayList();
			Collection boletos = new HashSet();
			
			PlanoPagamento plano = (PlanoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.PLANO_PAGAMENTO_ATUAL);
			if (plano  instanceof PlanoPagamentoAPrazo) {
				PlanoPagamentoAPrazo planoAprazo = (PlanoPagamentoAPrazo) plano;
				
				SortedSet parcelas = (SortedSet)gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CHEQUE_PRE);
				Iterator i = parcelas.iterator();
				for(int cont = 1;i.hasNext();cont++){
					DadosChequePredatado dado = (DadosChequePredatado) i.next();

					if (dado.getData() == null) {
						String dataBoleto =null;
						while(dataBoleto == null || "".equals(dataBoleto)){
							gerenciadorPerifericos.getDisplay().setMensagem( cont +"/" + parcelas.size()+" Data Boleto");
							EntradaDisplay entrada7 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_DATA, 0);
							if (entrada7.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
								dataBoleto = entrada7.getDado();
								if (!"".equals(dataBoleto)){
	
									Date data = dateFormat.parse(dataBoleto);
									if (data.compareTo(new Date()) == -1){
										gerenciadorPerifericos.getDisplay().setMensagem("Data Inválida");
										gerenciadorPerifericos.esperaVolta();
										dataBoleto = null;
										continue;
									}
									
									dado.setData(data);
								}else{
									dataBoleto = null;
									continue;
								}
							} else {
								return ALTERNATIVA_2;
							}
						}
					}
						
						
						
					Object obj = gerarBoleto(gerenciadorPerifericos, dado.getValor(), idLoja, componente, usu, loja, contaCorrente, dado.getData());
					if (obj instanceof Boleto) {
						Boleto boleto = (Boleto) obj;
						boletos.add(boleto); 
					} else if (obj instanceof String) {
						gerenciadorPerifericos.getDisplay().setMensagem(obj.toString());
						gerenciadorPerifericos.esperaVolta();
						return ALTERNATIVA_2;
					} else if (obj instanceof Exception) {
						((Exception)obj).printStackTrace();
						gerenciadorPerifericos.getDisplay().setMensagem(((Exception)obj).getMessage());
						gerenciadorPerifericos.esperaVolta();
						return ALTERNATIVA_2;
					}
					
				}	
			} else if (plano  instanceof PlanoPagamentoAVista) {
				
				Date dataVencimento = com.infinity.datamarket.comum.util.Util.adicionarDia(new Date(), 3);
				BigDecimal valorPagamento = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);

				Object obj = gerarBoleto(gerenciadorPerifericos, valorPagamento, idLoja, componente, usu, loja, contaCorrente, dataVencimento);
				
				if (obj instanceof Boleto) {
					Boleto boleto = (Boleto) obj;
					boletos.add(boleto);
				} else if (obj instanceof String) {
					gerenciadorPerifericos.getDisplay().setMensagem(obj.toString());
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				} else if (obj instanceof Exception) {
					gerenciadorPerifericos.getDisplay().setMensagem(((Exception)obj).getMessage());
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}
			}
			gerenciadorPerifericos.getCmos().gravar(CMOS.BOLETO,boletos);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ALTERNATIVA_2;
		} catch (MalformedURLException e) {
			gerenciadorPerifericos.getDisplay().setMensagem("Servidor fora do ar");
			try {
				gerenciadorPerifericos.esperaVolta();	
			} catch (Exception ee) {}
			gerenciadorPerifericos.getDisplay().setMensagem("A receber");
			
			e.printStackTrace();
			return ALTERNATIVA_2;
		} catch (IOException e) {
			gerenciadorPerifericos.getDisplay().setMensagem("Erro na comunicação com servidor");
			try {
				gerenciadorPerifericos.esperaVolta();	
			} catch (Exception ee) {}
			gerenciadorPerifericos.getDisplay().setMensagem("A receber");
			
			e.printStackTrace();
			return ALTERNATIVA_2;
		} catch (ClassNotFoundException e) {
			gerenciadorPerifericos.getDisplay().setMensagem("Erro na comunicação com servidor");
			try {
				gerenciadorPerifericos.esperaVolta();	
			} catch (Exception ee) {}
			gerenciadorPerifericos.getDisplay().setMensagem("A receber");
			
			e.printStackTrace();
			return ALTERNATIVA_2;
		} catch (Exception e) {
			gerenciadorPerifericos.getDisplay().setMensagem("Erro na comunicação com servidor");
			try {
				gerenciadorPerifericos.esperaVolta();	
			} catch (Exception ee) {}
			gerenciadorPerifericos.getDisplay().setMensagem("A receber");
			
			e.printStackTrace();
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}

	private Object gerarBoleto(GerenciadorPerifericos gerenciadorPerifericos, BigDecimal valorPagamento, int idLoja, int componente, Usuario usu, Loja loja, ContaCorrente contaCorrente, Date dataVencimento) throws MalformedURLException, IOException, ClassNotFoundException, AppException {
		
		Date dataProcessamento = new Date();
		String cedente = loja.getNome(); // empresa que vai receber do sacado
		String instrucao1 = contaCorrente.getMensagemBoleto1();//"APOS O VENCIMENTO COBRAR MULTA DE 2%";
		String instrucao2 = contaCorrente.getMensagemBoleto2();//"APOS O VENCIMENTO COBRAR R$ 0,50 POR DIA";
		String instrucao3 = contaCorrente.getMensagemBoleto3();//"";
		String instrucao4 = contaCorrente.getMensagemBoleto4();//"";
		String agencia = contaCorrente.getIdAgencia();
		String carteira = contaCorrente.getCarteira();
		String numeroContaCorrente = contaCorrente.getNumero();
		String digitoContaCorrente = contaCorrente.getDigitoContaCorrente();

		Boleto boleto = new Boleto();
		if (usu != null && usu.getId() != null) 
			boleto.setUsuario(new Integer(usu.getId().intValue()));
		boleto.setComponente(componente);
		boleto.setNossoNumero("00000000");
		boleto.setLoja(idLoja);
		boleto.setContaCorrente(contaCorrente);
		boleto.setDataVencimento(dataVencimento);// = "10/08/2009";
		boleto.setDataProcessamento(dataProcessamento);// = "05/07/2009";
		boleto.setCedente(cedente);// = "JB fraudas"; // empresa que vai receber do sacado
		boleto.setInstrucao1(instrucao1);// = "APOS O VENCIMENTO COBRAR MULTA DE 2%";
		boleto.setInstrucao2(instrucao2);// = "APOS O VENCIMENTO COBRAR R$ 0,50 POR DIA";
		boleto.setInstrucao3(instrucao3);// = "";
		boleto.setInstrucao4(instrucao4);// = "";
		boleto.setAgencia(agencia);// = "2971";
		boleto.setCarteira(carteira);// = "175";
		boleto.setNumeroContaCorrente(numeroContaCorrente);// = "08690";
		boleto.setDigitoContaCorrente(digitoContaCorrente);// = "1";
		boleto.setValor(valorPagamento);// = "0.01";
		String codigoBanco = contaCorrente.getBanco().getId() + "";
		if (codigoBanco.length() > 3) {
			codigoBanco = codigoBanco.substring(0, 3);
		}
		codigoBanco = Util.completaString(codigoBanco, "0", 3, true);
		
		boleto.setTipoBanco(new Integer(codigoBanco));// = "00000000000";

		URL urlCon = new URL("http://" + ServerConfig.HOST_SERVIDOR_ES + ":" + ServerConfig.PORTA_SERVIDOR_ES + "/" +
				ServerConfig.CONTEXTO_SERVIDOR_ES + "/" + ServerConfig.SERVLET_GERADOR_BOLETO);
		URLConnection huc1 = urlCon.openConnection();

		huc1.setAllowUserInteraction(true);
		huc1.setDoOutput(true);

		ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
		output.writeObject(boleto);
		ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
		Object obj = input.readObject();
		if (obj instanceof Boleto) {
			boleto = (Boleto) obj;

			Long id = boleto.getId();
			String nossoNumero = id + "";
			if (nossoNumero.length() > 8) {
				nossoNumero = nossoNumero.substring(0,8);
			}
			
			nossoNumero = Util.completaString(nossoNumero, "0", 8, true);

			boleto.setNossoNumero(nossoNumero);
			boleto.setId(null); 
			
			return boleto;
//			gerenciadorPerifericos.getCmos().gravar(CMOS.BOLETO,boleto);

		} else {
			return obj;
		}
	}
}