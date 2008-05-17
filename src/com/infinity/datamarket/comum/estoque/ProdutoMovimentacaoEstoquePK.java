package com.infinity.datamarket.comum.estoque;

import com.infinity.datamarket.comum.util.Persistente;

public class ProdutoMovimentacaoEstoquePK extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3855882590609079639L;

	private int numeroEntrada;

	public int getNumeroEntrada() {
		return numeroEntrada;
	}

	public void setNumeroEntrada(int numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}
	
	
}
