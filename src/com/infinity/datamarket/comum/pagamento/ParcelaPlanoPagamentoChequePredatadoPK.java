package com.infinity.datamarket.comum.pagamento;

import com.infinity.datamarket.comum.util.Persistente;

public class ParcelaPlanoPagamentoChequePredatadoPK extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8914372301952165565L;
	
	private int numeroEntrada;

	public int getNumeroEntrada() {
		return numeroEntrada;
	}

	public void setNumeroEntrada(int numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}

}
