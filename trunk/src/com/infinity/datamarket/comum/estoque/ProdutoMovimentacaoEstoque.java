package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;
import java.math.BigDecimal;

import com.infinity.datamarket.comum.produto.Produto;

public class ProdutoMovimentacaoEstoque implements Serializable{

	public ProdutoMovimentacaoEstoque produtoMovimentacaoEstoque;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2696827327255900982L;
	
	private ProdutoMovimentacaoEstoquePK pk;
	private BigDecimal quantidade;
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

	/**
	 * @return the quantidade
	 */
	public BigDecimal getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public ProdutoMovimentacaoEstoque getProdutoMovimentacaoEstoque() {
		return produtoMovimentacaoEstoque;
	}

	public void setProdutoMovimentacaoEstoque(
			ProdutoMovimentacaoEstoque produtoMovimentacaoEstoque) {
		this.produtoMovimentacaoEstoque = produtoMovimentacaoEstoque;
	}
	
}
