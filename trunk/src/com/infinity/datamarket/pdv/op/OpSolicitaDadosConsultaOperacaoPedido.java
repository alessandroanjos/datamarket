package com.infinity.datamarket.pdv.op;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.ValidationException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class OpSolicitaDadosConsultaOperacaoPedido extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
		String operacao = null;
		
		try{
			while(operacao == null || "".equals(operacao)){
				gerenciadorPerifericos.getDisplay().setMensagem("Número do Pedido");
				EntradaDisplay entrada3 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 6);
				if (entrada3.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					operacao = entrada3.getDado();
					if (!"".equals(operacao)){	
						OperacaoPedido pedido = null;
						Operacao op = null;
						try{
							gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
							URL urlCon = new URL("http://" +
									ServerConfig.HOST_SERVIDOR_ES +
									":" +
									ServerConfig.PORTA_SERVIDOR_ES +
									"/" +
									ServerConfig.CONTEXTO_SERVIDOR_ES +
									"/" +
									ServerConfig.CONSULTAR_OPERACA_SERVLET);
							URLConnection huc1 = urlCon.openConnection();
	
							huc1.setAllowUserInteraction(true);
							huc1.setDoOutput(true);
	
							ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
							OperacaoPK pk = new OperacaoPK(Integer.parseInt(operacao),gerenciadorPerifericos.getCodigoLoja());
							output.writeObject(pk);
							ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
							Object obj = input.readObject();
							if (obj instanceof Operacao ) {								
								op = (Operacao) obj;
							} else if (obj instanceof ObjectNotFoundException) {
								gerenciadorPerifericos.getDisplay().setMensagem("Operação Inválida");
								gerenciadorPerifericos.esperaVolta();
								return ALTERNATIVA_2;
							} else  if (obj instanceof ValidationException){
								gerenciadorPerifericos.getDisplay().setMensagem("Operação Inválida");
								gerenciadorPerifericos.esperaVolta();
								return ALTERNATIVA_2;
							} else  if (obj instanceof Exception){
								gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
								gerenciadorPerifericos.esperaVolta();
								return ALTERNATIVA_2;
							}
	
							
							if (op.getTipo() == ConstantesOperacao.OPERACAO_PEDIDO){
								pedido = (OperacaoPedido) op;
								if (pedido.getStatus() == ConstantesOperacao.ABERTO){
									pedido.setStatus(ConstantesOperacao.EM_PROCESSAMENTO);
									gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_PEDIDO,pedido);
									//Alterar estatos para em processamento									
									return ALTERNATIVA_1;									
								}else{
									gerenciadorPerifericos.getDisplay().setMensagem("Operação Inválida");
									gerenciadorPerifericos.esperaVolta();
									return ALTERNATIVA_2;
								}
							}else{
								gerenciadorPerifericos.getDisplay().setMensagem("Operação Inválida");
								gerenciadorPerifericos.esperaVolta();
								return ALTERNATIVA_2;
							}
						}catch(Exception e){
							e.printStackTrace();
							gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
							gerenciadorPerifericos.esperaVolta();
							return ALTERNATIVA_2;
						}																
					}
				}else{
					operacao = null;
					return ALTERNATIVA_2;
				}
			}
			return ALTERNATIVA_2;
		}catch(Exception e){
			return ALTERNATIVA_2;
		}
	}
}
