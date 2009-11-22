package com.infinity.datamarket.pdv.op;

import java.util.Collection;
import java.util.Iterator;

import br.com.kobi.JBoleto;
import br.com.kobi.bancos.BancoBrasil;
import br.com.kobi.control.GeraPDF;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.boleto.Boleto;
import com.infinity.datamarket.comum.transacao.BoletoEventoItemPagamentoBoleto;
import com.infinity.datamarket.comum.transacao.EventoItemPagamentoBoleto;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpImprimeBoleto extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
		int idLoja = Integer.parseInt(ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOJA).getValor());
//		Loja loja = Fachada.getInstancia().consultarLojaPorId(new Long(idLoja));
		int componente =  Integer.parseInt(ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.COMPONENTE).getValor());
		Usuario usuario = (Usuario)gerenciadorPerifericos.getCmos().ler(CMOS.USUARIO_ATUAL);

		TransacaoVenda trans = (TransacaoVenda) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);

		Collection col = trans.getEventosTransacao();
		if (col != null && col.size() > 0){
			Iterator i = col.iterator();
			while(i.hasNext()){
				EventoTransacao evt = (EventoTransacao) i.next();
				if (evt instanceof EventoItemPagamentoBoleto){
					EventoItemPagamentoBoleto ev = (EventoItemPagamentoBoleto) evt;
					if (ev.getBoletos() != null) {
						Collection boletos = ev.getBoletos();
						Iterator it = boletos.iterator();
						while (it.hasNext() ) {
							BoletoEventoItemPagamentoBoleto boletoEIPB = (BoletoEventoItemPagamentoBoleto)it.next();
							Boleto boleto = (Boleto) boletoEIPB.getPk().getBoleto();


							try {

								String  dataProcessamento  = Util.retornaDataFormatoDDMMYYYY(boleto.getDataProcessamento());
								String dataVencimento= Util.retornaDataFormatoDDMMYYYY(boleto.getDataVencimento());
								String cedente = boleto.getCedente(); // empresa que vai receber do sacado
								String nossoNumero = boleto.getNossoNumero();
								String instrucao1 = boleto.getInstrucao1();
								if (instrucao1 == null) instrucao1 = "";
								String instrucao2 = boleto.getInstrucao2();
								if (instrucao2 == null) instrucao2 = "";
								String instrucao3 = boleto.getInstrucao3();
								if (instrucao3 == null) instrucao3 = "";
								String instrucao4 = boleto.getInstrucao4();
								if (instrucao4 == null) instrucao4 = "";
								String agencia = boleto.getAgencia();
								if (agencia.length() > 4) {
									agencia = agencia.substring(0, 4);
								}
								
								agencia = Util.completaString(agencia, "0", 4, true);

								String carteira = boleto.getCarteira();
								if (carteira.length() > 2) {
									carteira = carteira.substring(0, 2);
								}
								
								carteira = Util.completaString(carteira, "0", 2, true);

								String codigoBanco = boleto.getTipoBanco() + "";
								if (codigoBanco.length() > 3) {
									codigoBanco = codigoBanco.substring(0, 3);
								}
								
								codigoBanco = Util.completaString(codigoBanco, "0", 3, true);

								String digitoContaCorrente = boleto.getDigitoContaCorrente();
								if (digitoContaCorrente.length() >= 1) {
									digitoContaCorrente = digitoContaCorrente.substring(0, 1);
								}else if (digitoContaCorrente.length() == 0) {
									digitoContaCorrente = "0";
								}
								
								String contaCorrente = boleto.getNumeroContaCorrente() + digitoContaCorrente; 

								if (contaCorrente.length() > 8) {
									contaCorrente = contaCorrente.substring(0, 8);
								}

								contaCorrente = Util.completaString(contaCorrente, "0", 8, true);
								
								String valor = boleto.getValor().toString();
	
								String nomeCliente = boleto.getNomeCliente();
								if (nomeCliente == null) nomeCliente = "";
								
								String enderecoCliente = boleto.getEnderecoCliente();
								if (enderecoCliente == null) enderecoCliente= "";
								
								String bairroCliente = boleto.getBairroCliente();
								if (bairroCliente == null) bairroCliente = "";
								
								String cidadeCliente = boleto.getCidadeCliente();
								if (cidadeCliente == null) cidadeCliente = "";
								
								String UFCliente = boleto.getUFCliente();
								if (UFCliente == null) UFCliente = "";
								
								String cpfCnpj = boleto.getCpfCnpj();
								if (cpfCnpj == null) cpfCnpj = "";
								
								String cepCliente = boleto.getCepCliente();
								if (cepCliente == null) cepCliente = "";

							 
								JBoleto jBoletoBean = new JBoleto();
								
								jBoletoBean.setDataDocumento(dataVencimento);
								jBoletoBean.setDataProcessamento(dataProcessamento);
					
								jBoletoBean.setCedente(cedente);
					
								jBoletoBean.setNomeSacado(nomeCliente);
								jBoletoBean.setEnderecoSacado(enderecoCliente);
								jBoletoBean.setBairroSacado(bairroCliente);
								jBoletoBean.setCidadeSacado(cidadeCliente);
								jBoletoBean.setUfSacado(UFCliente);
								jBoletoBean.setCepSacado(cepCliente);
								jBoletoBean.setCpfSacado(cpfCnpj);
					
//								jBoletoBean.setImagemMarketing("original_template_logo.gif");
					
								jBoletoBean.setDataVencimento(dataVencimento);
								jBoletoBean.setInstrucao1(instrucao1);
								jBoletoBean.setInstrucao2(instrucao2);
								jBoletoBean.setInstrucao3(instrucao3);
								jBoletoBean.setInstrucao4(instrucao4);
					
								jBoletoBean.setCarteira(carteira);
								jBoletoBean.setAgencia(agencia);
								jBoletoBean.setContaCorrente(contaCorrente);
								jBoletoBean.setLocalPagamento("");
								jBoletoBean.setLocalPagamento2("");
								
//S								jBoletoBean.setDvContaCorrente(digitoContaCorrente);
					
								if (boleto.getTipoBanco().intValue() == Boleto.TIPO_BANCO_BANCO_DO_BRASIL) {
									jBoletoBean.setNossoNumero("000"+nossoNumero, 11);
										
								} else if (boleto.getTipoBanco().intValue() == Boleto.TIPO_BANCO_ITAU) {
									jBoletoBean.setNossoNumero(nossoNumero, 8);
										
								} 
//								jBoletoBean.setNoDocumento(nossoNumero);
								jBoletoBean.setValorBoleto(valor);
								jBoletoBean.setBanco(codigoBanco);
								jBoletoBean.setMoeda("9");
						        
								String arquivoBoleto =  idLoja + "_" + componente + "_" +  usuario.getId() + "_" +   System.currentTimeMillis() + "_" + "boleto.pdf"; // o ideal eh colocar loja + pdv + codigoOperador + timestemp;

//								String caminho = Fachada.getInstancia().consultarParametro("DIR_PADRAO_RECIBOS").getValor() + arquivoBoleto; // "c:\\pdv\\temp\\";
								String caminho = Util.getDirCorrente();

						        BancoBrasil bbb = new BancoBrasil(jBoletoBean);
						        jBoletoBean.setLinhaDigitavel(bbb.getLinhaDigitavel());
						        jBoletoBean.setCodigoBarras(bbb.getCodigoBarras());
						            
						        jBoletoBean.setCaminho(caminho  + "/" + arquivoBoleto);
						        
						        GeraPDF pdf = new GeraPDF(jBoletoBean);

						        pdf.addBoleto();
						        pdf.geraBoleto();		        

						        if (false) {
						        	
						        }
								Runtime.getRuntime().exec("cmd /c" + caminho + "/" + arquivoBoleto);
							} catch (Exception e) {
								e.printStackTrace();

								return ALTERNATIVA_2;
								
							}
						}
					}	
				}
			}
		}	

		return ALTERNATIVA_1;
	}

}
