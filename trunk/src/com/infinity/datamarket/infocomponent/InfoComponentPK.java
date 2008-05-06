package com.infinity.datamarket.infocomponent;

import java.io.Serializable;

public class InfoComponentPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5620031029892663257L;
	private String loja;
	private String componente;
	public String getComponente() {
		return componente;
	}
	public void setComponente(String componente) {
		this.componente = componente;
	}
	public String getLoja() {
		return loja;
	}
	public void setLoja(String loja) {
		this.loja = loja;
	}
}
