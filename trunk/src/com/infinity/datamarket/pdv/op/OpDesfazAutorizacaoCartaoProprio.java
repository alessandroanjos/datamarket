package com.infinity.datamarket.pdv.op;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.autorizador.AutorizacaoException;
import com.infinity.datamarket.autorizador.DadosAutorizacaoCartaoProprio;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class OpDesfazAutorizacaoCartaoProprio extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
		
		try{
			Collection c = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_AUTORIZACOES_CARTAO_PROPRIO);
			
			if (c != null && c.size() > 0){
				gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
			
				Iterator i = c.iterator();
				while(i.hasNext()){
					DadosAutorizacaoCartaoProprio dados = (DadosAutorizacaoCartaoProprio) i.next();
					
					try{
						URL urlCon = new URL("http://" +
								ServerConfig.HOST_SERVIDOR_ES +
								":" +
								ServerConfig.PORTA_SERVIDOR_ES +
								"/" +
								ServerConfig.CONTEXTO_SERVIDOR_ES +
								"/" +
								ServerConfig.DESFAZER_TRANSACAO_CARTAO_PROPRIO_SERVLET +"?id=" + dados.getAutrizacao());
						URLConnection huc1 = urlCon.openConnection();

						huc1.setAllowUserInteraction(true);
						huc1.setDoOutput(true);

						ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
						output.writeObject("OK");
						ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
						Object obj = input.readObject();
						if (obj instanceof String && obj.toString().equals("OK") ) {

						} else if (obj instanceof Exception) {
							gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
							gerenciadorPerifericos.esperaVolta();
							return ALTERNATIVA_2;
						}
					} catch (Exception e) {
						gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
						gerenciadorPerifericos.esperaVolta();
						return ALTERNATIVA_2;
					}
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
