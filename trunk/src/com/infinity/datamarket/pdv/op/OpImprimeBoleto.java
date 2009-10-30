package com.infinity.datamarket.pdv.op;

import java.util.Collection;
import java.util.Iterator;

import org.jboleto.JBoleto;
import org.jboleto.JBoletoBean;
import org.jboleto.control.Generator;
import org.jboleto.control.PDFGenerator;

import com.infinity.datamarket.comum.boleto.Boleto;
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
					if (ev.getBoleto() != null) {
						Boleto boleto = ev.getBoleto();
						if (boleto != null) {
							try {

								String dataVencimento = Util.retornaDataFormatoDDMMYYYY(boleto.getDataProcessamento());
								String dataProcessamento = Util.retornaDataFormatoDDMMYYYY(boleto.getDataVencimento());
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
								String carteira = boleto.getCarteira();
								String contaCorrente = boleto.getNumeroContaCorrente();
								String digitoContaCorrente = boleto.getDigitoContaCorrente();
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

								int codigoBanco = boleto.getTipoBanco().intValue();
							
								JBoletoBean jBoletoBean = new JBoletoBean();
								
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
								jBoletoBean.setDvContaCorrente(digitoContaCorrente);
					
								jBoletoBean.setNossoNumero(nossoNumero, 8);
								jBoletoBean.setNoDocumento(nossoNumero);
								jBoletoBean.setValorBoleto(valor);
					
								Generator generator = new PDFGenerator(jBoletoBean, codigoBanco);
								JBoleto jBoleto = new JBoleto(generator, jBoletoBean, codigoBanco);

								String arquivoBoleto =  idLoja + "_" + componente + "_" +  usuario.getId() + "_" +   System.currentTimeMillis() + "_" + "boleto.pdf"; // o ideal eh colocar loja + pdv + codigoOperador + timestemp;
								jBoleto.addBoleto();
								jBoleto.closeBoleto(Util.getDirCorrente() + "/"+ arquivoBoleto);

								Runtime.getRuntime().exec("cmd /c"+Util.getDirCorrente() + "/"+ arquivoBoleto);
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
