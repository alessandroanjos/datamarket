package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;
import java.util.Date;

import com.infinity.datamarket.comum.produto.Produto;

public class EstoqueProdutoPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6209805049920310856L;
	
	private Estoque estoque;
	
	private Produto produto;

	
	public EstoqueProdutoPK(){
		
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
	
}
