package com.infinity.datamarket.comum.util;

import java.io.Serializable;

public class Parametro implements Serializable{
	private String chave;
	private String valor;
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

}
