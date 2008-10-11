package com.infinity.datamarket.pdv.mic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.autorizador.AutorizacaoException;
import com.infinity.datamarket.autorizador.AutorizadorServerRemote;
import com.infinity.datamarket.autorizador.DadosAutorizacaoCartaoProprio;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.pagamento.DadosCartaoProprio;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.StringUtil;
import com.infinity.datamarket.operacao.OperacaoServerRemote;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.ServerConfig;
import com.infinity.datamarket.pdv.util.ServiceLocator;

public class MicConfirmaOperacao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
		
		try{
			
			Collection c = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.PK_OPERACOES);
			if (c != null && c.size() > 0){
				gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
				OperacaoServerRemote remote = (OperacaoServerRemote) ServiceLocator.getJNDIObject(ServerConfig.OPERACAO_SERVER_JNDI);
				if (remote == null){
					gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}				
			
				Iterator i = c.iterator();
				while(i.hasNext()){
					OperacaoPK pk = (OperacaoPK) i.next();
					remote.alteraStatusOperacao(pk,ConstantesOperacao.FECHADO);
				}
			}
			
			gerenciadorPerifericos.getCmos().gravar(CMOS.PK_OPERACOES,new ArrayList());
								
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
