package com.infinity.datamarket.pdv.op;

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

public class OpSolicitaDadosConsultaOperacaoPedido extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
		String operacao = null;
		
		try{
			while(operacao == null || "".equals(operacao)){
				gerenciadorPerifericos.getDisplay().setMensagem("N�mero do Pedido");
				EntradaDisplay entrada3 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 6);
				if (entrada3.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					operacao = entrada3.getDado();
					if (!"".equals(operacao)){	
						OperacaoPedido pedido = null; 
						try{
							gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
							//Pegar Pedido no Servidor
							Operacao op = null;
							if (op.getTipo() == ConstantesOperacao.OPERACAO_PEDIDO){
								pedido = (OperacaoPedido) op;
								if (pedido.getStatus() == ConstantesOperacao.ABERTO){
									pedido.setStatus(ConstantesOperacao.EM_PROCESSAMENTO);
									gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_PEDIDO,pedido);
									//Alterar estatos para em processamento									
									return ALTERNATIVA_1;									
								}else{
									gerenciadorPerifericos.getDisplay().setMensagem("Opera��o Inv�lida");
									gerenciadorPerifericos.esperaVolta();
									return ALTERNATIVA_2;
								}
							}else{
								gerenciadorPerifericos.getDisplay().setMensagem("Opera��o Inv�lida");
								gerenciadorPerifericos.esperaVolta();
								return ALTERNATIVA_2;
							}
						}catch(Exception e){
							e.printStackTrace();
							gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunica��o");
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
