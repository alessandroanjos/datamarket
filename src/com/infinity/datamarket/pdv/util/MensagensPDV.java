package com.infinity.datamarket.pdv.util;

import java.util.ResourceBundle;

import com.infinity.datamarket.pdv.maquinaestados.Mic;

public class MensagensPDV {

	private static ResourceBundle rb; 

	protected static ResourceBundle getResourceBundle(){
		if (rb ==null) {
			rb = ResourceBundle.getBundle("MensagensPDV");	
		}
		return rb;
	}

	private static String getMensagem(String key) {
		String mensagem = null;
		
		try {
			mensagem = getResourceBundle().getString(key);
			
			
		} catch (Exception e) {}

		return mensagem;
	}
	
	private static String retirarEspaco(String key){
		String msn = key;
		int posicao = msn.indexOf(" ");
		while (posicao != -1) {
			msn = msn.substring(0,posicao) + "_" + msn.substring(posicao +1,msn.length() );
			posicao = msn.indexOf(" ");
		}
		return msn;
	}

	public static String getMensagem(Mic mic, String key) {
		String mensagem = null;
		
		String keyMic = mic.getClass().getSimpleName() + "."+key;
		
		mensagem = getMensagem(keyMic);

		if (mensagem == null) {
			
			mensagem = getMensagem(retirarEspaco(keyMic));
		}

		if (mensagem == null) {
			
			mensagem = getMensagem(key);
		}


		if (mensagem == null) {
			
			mensagem = getMensagem(retirarEspaco(key));
		}

		if (mensagem == null) {
			mensagem = (key);
		}
		
		return mensagem;
	}
}