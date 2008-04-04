package com.infinity.datamarket.comum.estoque;

import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.Persistente;

public class Estoque extends Persistente {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1105657518976458874L;
	
	String descricao;
	Loja loja;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Loja getLoja() {
		return loja;
	}
	public void setLoja(Loja loja) {
		this.loja = loja;
	}

}
