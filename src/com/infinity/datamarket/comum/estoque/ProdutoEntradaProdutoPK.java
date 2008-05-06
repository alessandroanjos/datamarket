package com.infinity.datamarket.comum.estoque;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.util.Persistente;

public class ProdutoEntradaProdutoPK extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6439770029246202160L;
	
	private int numeroEntrada;
	private Produto produto;

	public int getNumeroEntrada() {
		return numeroEntrada;
	}

	public void setNumeroEntrada(int numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
}
