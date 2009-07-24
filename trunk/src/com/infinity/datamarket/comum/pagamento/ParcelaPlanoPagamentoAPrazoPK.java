package com.infinity.datamarket.comum.pagamento;

import java.io.Serializable;

public class ParcelaPlanoPagamentoAPrazoPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8914372301952165565L;
	private PlanoPagamentoAPrazo plano;
	private int numeroEntrada;

	public int getNumeroEntrada() {
		return numeroEntrada;
	}

	public void setNumeroEntrada(int numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}
	
	public PlanoPagamentoAPrazo getPlano() {
		return plano;
	}
	public void setPlano(PlanoPagamentoAPrazo plano) {
		this.plano = plano;
	}

}
