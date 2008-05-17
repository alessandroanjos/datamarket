package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;

import com.infinity.datamarket.comum.produto.Produto;

public class ProdutoMovimentacaoEstoque implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2696827327255900982L;
	
	private ProdutoMovimentacaoEstoquePK pk;
	
	private Produto produto;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public ProdutoMovimentacaoEstoquePK getPk() {
		return pk;
	}

	public void setPk(ProdutoMovimentacaoEstoquePK pk) {
		this.pk = pk;
	}
	
}
