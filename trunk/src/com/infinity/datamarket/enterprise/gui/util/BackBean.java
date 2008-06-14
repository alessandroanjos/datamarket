package com.infinity.datamarket.enterprise.gui.util;

import java.io.IOException;
import java.util.Properties;

import com.infinity.datamarket.comum.Fachada;

public class BackBean {
	
	public static final String INSERIR = "I";
	public static final String ALTERAR = "A";
	public static final String CONSULTAR = "C";
	
	public static Properties mensagens;
	
	public Fachada getFachada(){
		return Fachada.getInstancia();
	}
	
	public static String retornaTituloPagina(String chave){
		String valor = "";
		
		try {
			if(mensagens == null){
				mensagens.load(BackBean.class.getResourceAsStream("/resources/mensagens.properties"));
			}
			valor = mensagens.getProperty(chave);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valor;
	}	
}