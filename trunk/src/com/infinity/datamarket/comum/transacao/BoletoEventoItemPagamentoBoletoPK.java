package com.infinity.datamarket.comum.transacao;

import java.util.Date;

import com.infinity.datamarket.comum.boleto.Boleto;

public class BoletoEventoItemPagamentoBoletoPK extends EventoTransacaoPK{

	private static final long serialVersionUID = -364623423492019716L;

	private Boleto boleto;
	
	public BoletoEventoItemPagamentoBoletoPK(int loja, int componente, int numeroTransacao, Date dataTransacao, int numeroEvento, Boleto boleto) {
		super(loja,componente,numeroTransacao,dataTransacao,numeroEvento);
		this.boleto = boleto;
	}

	public BoletoEventoItemPagamentoBoletoPK(){

	}

	public boolean equals(Object obj){
		if (obj instanceof BoletoEventoItemPagamentoBoletoPK){
			BoletoEventoItemPagamentoBoletoPK t = (BoletoEventoItemPagamentoBoletoPK) obj;
			return (this.boleto.equals(t.boleto) && super.equals(t));
		}
		return false;

	}

	public Boleto getBoleto() {
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}
}