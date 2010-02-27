package com.infinity.datamarket.av.op;

import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;

import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.MensagensAV;
import com.infinity.datamarket.pdv.util.ServerConfig;


public class OpAVCancelarSeparacaoServidor extends Mic {

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			
			OperacaoPedido pedido = (OperacaoPedido)gerenciadorPerifericos.getCmos().ler(CMOS.OPERACAO_PEDIDO);

			if (pedido == null || pedido.getPk() == null || pedido.getPk().getLoja() == 0 || pedido.getPk().getId() == 0) {
				return ALTERNATIVA_1;
			}
			try{
			URL urlCon = new URL("http://" +
					ServerConfig.HOST_SERVIDOR_ES +
					":" +
					ServerConfig.PORTA_SERVIDOR_ES +
					"/" +
					ServerConfig.CONTEXTO_SERVIDOR_ES +
					"/" +
					ServerConfig.CANCELAR_OPERACAO_EM_SEPARACAO + "?idLoja=" + pedido.getPk().getLoja() + "&idPedido="+pedido.getPk().getId());
			URLConnection huc1 = urlCon.openConnection();

			huc1.setAllowUserInteraction(true);						

			ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
			Object obj = input.readObject();
			if (obj instanceof String && "OK".equalsIgnoreCase(((String)obj))) {

			} else  if (obj instanceof Exception){
				((Exception)obj).printStackTrace();
				
				gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Erro de Comunicação"));
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;		
			}
		}catch(Exception e){
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Erro de Comunicação"));
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}

			return ALTERNATIVA_1;
			
		}catch(Exception e){
			gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Erro"));
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}
	}

	
}