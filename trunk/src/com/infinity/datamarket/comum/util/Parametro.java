package com.infinity.datamarket.comum.util;

import java.io.Serializable;

public class Parametro implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8560952991356845725L;
	private String chave;
	private String valor;
	
	public Parametro () {
		
	}
	
	public Parametro (String chave, String valor) {
		this.chave = chave;
		this.valor = valor;
	}

	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public int getValorInteiro() {
		try{
			return Integer.parseInt(valor);
		}catch(Exception e){
			return -1;
		}
		
	}
	
	public void setValorInteiro(int valor) {
		this.valor = valor+"";
	}

	public String toString() {
		// TODO Auto-generated method stub
		return getValor();
	}
	
}
