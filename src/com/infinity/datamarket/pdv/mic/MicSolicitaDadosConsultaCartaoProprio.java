package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.infinity.datamarket.autorizador.AutorizacaoException;
import com.infinity.datamarket.autorizador.AutorizadorServerRemote;
import com.infinity.datamarket.autorizador.DadosAutorizacaoCartaoProprio;
import com.infinity.datamarket.autorizador.DadosConsultaCartaoProprio;
import com.infinity.datamarket.comum.pagamento.DadosCartaoProprio;
import com.infinity.datamarket.comum.util.Util;
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

public class MicSolicitaDadosConsultaCartaoProprio extends Mic{
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
							DadosConsultaCartaoProprio dadosConsultaCartaoProprio = null;
							try{
								gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
								AutorizadorServerRemote remote = (AutorizadorServerRemote) ServiceLocator.getJNDIObject(ServerConfig.AUTORIZADOR_SERVER_JNDI);
								if (remote == null){
									gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
									gerenciadorPerifericos.esperaVolta();
									return ALTERNATIVA_2;
								}
								dadosConsultaCartaoProprio = remote.consultaDebito(CPFCNPJ);
//								dadosConsultaCartaoProprio = new DadosConsultaCartaoProprio();
//								dadosConsultaCartaoProprio.setNome("Wagner de Medeiros Melo");													
//								dadosConsultaCartaoProprio.setValorDebito(new BigDecimal(200.98).setScale(2,BigDecimal.ROUND_UP));
								dadosConsultaCartaoProprio.setCPFCNPJ(CPFCNPJ);
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
							gerenciadorPerifericos.getCmos().gravar(CMOS.DADOS_CONSULTA_CARTAO_PROPRIO,dadosConsultaCartaoProprio);
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
