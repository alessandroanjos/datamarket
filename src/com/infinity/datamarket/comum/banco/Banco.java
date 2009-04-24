package com.infinity.datamarket.comum.banco;

import com.infinity.datamarket.comum.util.Persistente;

public class Banco extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4859446635232194912L;
	private String descricao;
	private String situacao;
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	


}
