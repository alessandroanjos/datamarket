package com.infinity.datamarket.comum.produto;

import com.infinity.datamarket.comum.util.Persistente;

public class Unidade extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1506752627384026564L;
	
	private String descricao;
	private String descricaoDisplay;
	private String abreviacao;
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDescricaoDisplay() {
		return descricaoDisplay;
	}
	public void setDescricaoDisplay(String descricaoDisplay) {
		this.descricaoDisplay = descricaoDisplay;
	}
	public String getAbreviacao() {
		return abreviacao;
	}
	public void setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
	}
	
}
