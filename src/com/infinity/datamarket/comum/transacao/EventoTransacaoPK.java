package com.infinity.datamarket.comum.transacao;

import java.io.Serializable;
import java.util.Date;

public class EventoTransacaoPK extends TransacaoPK{
	private int numeroEvento;

	public EventoTransacaoPK(int loja, int componente, int numeroTransacao, Date dataTransacao) {
		super(loja,componente,numeroTransacao,dataTransacao);
	}

	public EventoTransacaoPK(){

	}

	public int getNumeroEvento() {
		return numeroEvento;
	}
	public void setNumeroEvento(int numeroEvento) {
		this.numeroEvento = numeroEvento;
	}

	public boolean equals(Object obj){
		if (obj instanceof EventoTransacaoPK){
			EventoTransacaoPK t = (EventoTransacaoPK) obj;
			return (this.numeroEvento == t.numeroEvento && super.equals(t));
		}
		return false;

	}

}
