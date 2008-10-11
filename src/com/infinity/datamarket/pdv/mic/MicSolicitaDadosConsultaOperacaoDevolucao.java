package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.infinity.datamarket.autorizador.AutorizacaoException;
import com.infinity.datamarket.autorizador.AutorizadorServerRemote;
import com.infinity.datamarket.autorizador.DadosAutorizacaoCartaoProprio;
import com.infinity.datamarket.autorizador.DadosConsultaCartaoProprio;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoDevolucao;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.pagamento.DadosCartaoProprio;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.SistemaException;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.operacao.OperacaoServerRemote;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.TelaConsultaCartaoProprio;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.util.ServerConfig;
import com.infinity.datamarket.pdv.util.ServiceLocator;

public class MicSolicitaDadosConsultaOperacaoDevolucao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
		String operacao = null;
		
		try{
			while(operacao == null || "".equals(operacao)){
				gerenciadorPerifericos.getDisplay().setMensagem("Numero da Devolução");
				EntradaDisplay entrada3 = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 6);
				if (entrada3.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					operacao = entrada3.getDado();
					if (!"".equals(operacao)){	
						OperacaoDevolucao devolucao = null; 
						try{
							gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
							//OperacaoServerRemote remote = (OperacaoServerRemote) ServiceLocator.getJNDIObject(ServerConfig.OPERACAO_SERVER_JNDI);
//							if (remote == null){
//								gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
//								gerenciadorPerifericos.esperaVolta();
//								return ALTERNATIVA_2;
//							}
							OperacaoPK pk = new OperacaoPK(gerenciadorPerifericos.getCodigoLoja(),Integer.parseInt(operacao));
							Operacao op = null;
							try{
								//op = remote.consultarOperacaoPorID(pk);
								op = getFachadaPDV().consultarOperacaoPorPK(pk);
							}catch(ObjectNotFoundException e){								
								gerenciadorPerifericos.getDisplay().setMensagem("Operação Inválida");
								gerenciadorPerifericos.esperaVolta();
								return ALTERNATIVA_2;
							}
							if (op.getTipo() == ConstantesOperacao.OPERACAO_DEVOLUCAO){
								devolucao = (OperacaoDevolucao) op;
								if (devolucao.getStatus() == ConstantesOperacao.ABERTO){
									gerenciadorPerifericos.getCmos().gravar(CMOS.VALOR_PAGAMENTO_ATUAL,devolucao.getValor());
									gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_DEVOLUCAO,devolucao);
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
