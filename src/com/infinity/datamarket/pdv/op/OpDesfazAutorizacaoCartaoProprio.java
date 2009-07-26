package com.infinity.datamarket.pdv.op;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.autorizador.AutorizacaoException;
import com.infinity.datamarket.autorizador.AutorizadorServerRemote;
import com.infinity.datamarket.autorizador.DadosAutorizacaoCartaoProprio;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.ServerConfig;
import com.infinity.datamarket.pdv.util.ServiceLocator;

public class OpDesfazAutorizacaoCartaoProprio extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
		
		try{
			Collection c = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_AUTORIZACOES_CARTAO_PROPRIO);
			
			if (c != null && c.size() > 0){
				gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
				AutorizadorServerRemote remote = (AutorizadorServerRemote) ServiceLocator.getJNDIObject(ServerConfig.AUTORIZADOR_SERVER_JNDI);
				if (remote == null){
					gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}				
			
				Iterator i = c.iterator();
				while(i.hasNext()){
					DadosAutorizacaoCartaoProprio dados = (DadosAutorizacaoCartaoProprio) i.next();
					remote.desfazTransacaoCartaoProprio(new Long(dados.getAutrizacao()));
				}
			}
			
			gerenciadorPerifericos.getCmos().gravar(CMOS.DADOS_AUTORIZACOES_CARTAO_PROPRIO,new ArrayList());
								
		}catch(AutorizacaoException e){
			gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}catch(Exception e){
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
}
