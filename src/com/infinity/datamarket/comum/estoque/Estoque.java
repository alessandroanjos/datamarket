package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;

public class Estoque implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1105657518976458874L;
	
	EstoquePK pk;
	String descricao;
	String estoqueVenda;

	
	public String getEstoqueVenda() {
		return estoqueVenda;
	}
	public void setEstoqueVenda(String estoqueVenda) {
		this.estoqueVenda = estoqueVenda;
	}
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
