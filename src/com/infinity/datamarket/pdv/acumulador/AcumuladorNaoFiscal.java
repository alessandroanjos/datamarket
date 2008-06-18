package com.infinity.datamarket.pdv.acumulador;

import com.infinity.datamarket.comum.util.Persistente;

public class AcumuladorNaoFiscal extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7622922080658287395L;
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
