package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;

public class Estoque implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1105657518976458874L;
	
	private EstoquePK pk;
	String descricao;

	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public EstoquePK getPk() {
		return pk;
	}
	public void setPk(EstoquePK pk) {
		this.pk = pk;
	}
	
}
