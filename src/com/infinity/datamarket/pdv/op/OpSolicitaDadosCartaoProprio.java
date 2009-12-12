package com.infinity.datamarket.pdv.op;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

import com.infinity.datamarket.autorizador.AutorizacaoException;
import com.infinity.datamarket.autorizador.AutorizadorServerRemote;
import com.infinity.datamarket.autorizador.DadosAutorizacaoCartaoProprio;
import com.infinity.datamarket.comum.pagamento.DadosCartaoProprio;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.util.ServerConfig;
import com.infinity.datamarket.pdv.util.ServiceLocator;

public class OpSolicitaDadosCartaoProprio extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
		String CPFCNPJ = null;
		
		try{
			while(CPFCNPJ == null || "".equals(CPFCNPJ)){
				gerenciadorPerifericos.getDisplay().setMensagem("CPF/CNPJ");
				EntradaDisplay entrada3 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 14);
				if (entrada3.getTeclaFinalizadora() == 10){
					CPFCNPJ = entrada3.getDado();
					if (!"".equals(CPFCNPJ)){
						if (Util.validacpf(CPFCNPJ) || Util.validaCnpj(CPFCNPJ)){
							DadosAutorizacaoCartaoProprio dadosAutorizacao = null;
							try{
								gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
//								AutorizadorServerRemote remote = (AutorizadorServerRemote) ServiceLocator.getJNDIObject(ServerConfig.AUTORIZADOR_SERVER_JNDI);
//								if (remote == null){
//									gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
//									gerenciadorPerifericos.esperaVolta();
//									return ALTERNATIVA_2;
//								}
								BigDecimal valorPagamento = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);
								try{
//									URL urlCon = new URL("http://" + loja.getNumeroIp() + ":" + loja.getNumeroPortaServlet() + "/EnterpriseServer/GerarBoletoServlet.servlet");
									URL urlCon = new URL("http://" +
											ServerConfig.HOST_SERVIDOR_ES +
											":" +
											ServerConfig.PORTA_SERVIDOR_ES +
											"/" +
											ServerConfig.CONTEXTO_SERVIDOR_ES +
											"/" +
											ServerConfig.AUTORIZAR_TRANSACAO_CARTAO_PROPRIO_SERVLET +"?cpfCnpj=" + CPFCNPJ + "&valor=" + valorPagamento);
									URLConnection huc1 = urlCon.openConnection();
			
									huc1.setAllowUserInteraction(true);
									huc1.setDoOutput(true);
			
									ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
									output.writeObject("OK");
									ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
									Object obj = input.readObject();
									if (obj instanceof DadosAutorizacaoCartaoProprio ) {
										dadosAutorizacao = (DadosAutorizacaoCartaoProprio) obj;
									} else if (obj instanceof Exception) {
										if(obj instanceof AutorizacaoException){
											gerenciadorPerifericos.getDisplay().setMensagem(((AutorizacaoException)obj).getMessage());
											gerenciadorPerifericos.esperaVolta();
											return ALTERNATIVA_2;
										}else{
											gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
											gerenciadorPerifericos.esperaVolta();
											return ALTERNATIVA_2;
										}
										
									}
								} catch (Exception e) {
									gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
									gerenciadorPerifericos.esperaVolta();
									return ALTERNATIVA_2;
								}
//								dadosAutorizacao = remote.autorizaTransacaoCartaoProprio(CPFCNPJ, valorPagamento);
								gerenciadorPerifericos.getDisplay().setMensagem("Aprovada "+dadosAutorizacao.getAutrizacao());
								gerenciadorPerifericos.esperaEntra();
							}catch(AutorizacaoException e){
								gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
								gerenciadorPerifericos.esperaVolta();
								return ALTERNATIVA_2;
							}catch(Exception e){
								e.printStackTrace();
								gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
								gerenciadorPerifericos.esperaVolta();
								return ALTERNATIVA_2;
							}
							DadosCartaoProprio dados = new DadosCartaoProprio();
							dados.setAutorizacao(dadosAutorizacao.getAutrizacao());
							dados.setCPFCNPJ(CPFCNPJ);
							Collection c = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_AUTORIZACOES_CARTAO_PROPRIO);
							if (c == null){
								c = new ArrayList();
							}
							c.add(dadosAutorizacao);
							gerenciadorPerifericos.getCmos().gravar(CMOS.DADOS_AUTORIZACOES_CARTAO_PROPRIO,c);
							gerenciadorPerifericos.getCmos().gravar(CMOS.DADOS_CARTAO_PROPRIO,dados);
							return ALTERNATIVA_1;
						}else{
							CPFCNPJ = null;
							gerenciadorPerifericos.getDisplay().setMensagem("CPF/CNPJ Inválido");
							gerenciadorPerifericos.esperaVolta();
						}
					}
				}else{
					CPFCNPJ = null;
					return ALTERNATIVA_2;
				}
			}
			return ALTERNATIVA_2;
		}catch(Exception e){
			return ALTERNATIVA_2;
		}
	}
}
