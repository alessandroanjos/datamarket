package com.infinity.datamarket.pdv.op;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.boleto.Boleto;
import com.infinity.datamarket.comum.conta.ContaCorrente;
import com.infinity.datamarket.comum.pagamento.ParcelaPlanoPagamentoAPrazo;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamentoAPrazo;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class OpGeraBoleto extends Mic{
	
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try {

			BigDecimal valorPagamento = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);

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
			
			Date dataVencimento = com.infinity.datamarket.comum.util.Util.adicionarDia(new Date(), 3);
			
			PlanoPagamento plano = (PlanoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.PLANO_PAGAMENTO_ATUAL);
			if (plano  instanceof PlanoPagamentoAPrazo) {
				PlanoPagamentoAPrazo planoAprazo = (PlanoPagamentoAPrazo) plano;
				Collection coll = planoAprazo.getParcelas();
				if (coll == null || !coll.isEmpty()) {
					gerenciadorPerifericos.getDisplay().setMensagem("Forma sem plano associado");
					try{
						gerenciadorPerifericos.esperaVolta();
						return ALTERNATIVA_2;
					}catch(AppException e){

					}
				}
				ParcelaPlanoPagamentoAPrazo parcela = (ParcelaPlanoPagamentoAPrazo) coll.iterator().next();
				if (parcela.getQuantidadeDias() != 0) {
					dataVencimento = com.infinity.datamarket.comum.util.Util.adicionarDia(dataVencimento, parcela.getQuantidadeDias());
				} else if (parcela.getDataProgramada() != null) {
					try {
						dataVencimento = com.infinity.datamarket.comum.util.Util.formatarStringParaData(parcela.getDataProgramada());	
					} catch (Exception e) {
						e.printStackTrace();// TODO: handle exception
						dataVencimento = com.infinity.datamarket.comum.util.Util.adicionarDia(dataVencimento, 3);
					}
				} else {
					dataVencimento = com.infinity.datamarket.comum.util.Util.adicionarDia(dataVencimento, 3);
				}
			}
			
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

//				getFachadaPDV().inserirBoleto(boleto);
				Long id = boleto.getId();
				String nossoNumero = id + "";
				if (nossoNumero.length() > 8) {
					nossoNumero = nossoNumero.substring(0,8);
				}
				
				nossoNumero = Util.completaString(nossoNumero, "0", 8, true);

				boleto.setNossoNumero(nossoNumero);
				boleto.setId(null); //TODO porque seta pra null???
				gerenciadorPerifericos.getCmos().gravar(CMOS.BOLETO,boleto);

			} else if (obj instanceof String) {
				gerenciadorPerifericos.getDisplay().setMensagem(obj.toString());
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			} else if (obj instanceof Exception) {
				gerenciadorPerifericos.getDisplay().setMensagem(((Exception)obj).getMessage());
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			}

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
		}
		return ALTERNATIVA_1;
	}
}