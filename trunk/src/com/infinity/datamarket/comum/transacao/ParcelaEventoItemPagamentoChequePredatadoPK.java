package com.infinity.datamarket.comum.transacao;

import java.util.Date;

public class ParcelaEventoItemPagamentoChequePredatadoPK extends EventoTransacaoPK{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3646463671992019716L;

	private int numeroParcela;
	
	public ParcelaEventoItemPagamentoChequePredatadoPK(int loja, int componente, int numeroTransacao, Date dataTransacao, int numeroEvento, int numeroParcela) {
		super(loja,componente,numeroTransacao,dataTransacao,numeroEvento);
		this.numeroParcela = numeroParcela;
	}

	public ParcelaEventoItemPagamentoChequePredatadoPK(){

	}

	public boolean equals(Object obj){
		if (obj instanceof ParcelaEventoItemPagamentoChequePredatadoPK){
			ParcelaEventoItemPagamentoChequePredatadoPK t = (ParcelaEventoItemPagamentoChequePredatadoPK) obj;
			return (this.numeroParcela == t.numeroParcela && super.equals(t));
		}
		return false;

	}

	public int getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(int numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	
}
