package com.infinity.datamarket.comum.pagamento;

import java.io.Serializable;

public class ParcelaPlanoPagamentoChequePredatadoPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8914372301952165565L;
	private PlanoPagamentoChequePredatado plano;
	private int numeroEntrada;

	public int getNumeroEntrada() {
		return numeroEntrada;
	}

	public void setNumeroEntrada(int numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}
	
	public PlanoPagamentoChequePredatado getPlano() {
		return plano;
	}
	public void setPlano(PlanoPagamentoChequePredatado plano) {
		this.plano = plano;
	}

}
