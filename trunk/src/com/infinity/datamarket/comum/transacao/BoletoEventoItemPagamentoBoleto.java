package com.infinity.datamarket.comum.transacao;

import java.util.Date;

import com.infinity.datamarket.comum.boleto.Boleto;


public class BoletoEventoItemPagamentoBoleto extends EventoTransacaoPK{

	private static final long serialVersionUID = -364623423492019716L;

	private BoletoEventoItemPagamentoBoletoPK pk;

	public BoletoEventoItemPagamentoBoletoPK getPk() {
		return pk;
	}

	public void setPk(BoletoEventoItemPagamentoBoletoPK pk) {
		this.pk = pk;
	}

	public boolean equals(Object obj) {
		boolean retono = false;
		if (obj instanceof BoletoEventoItemPagamentoBoleto){
			BoletoEventoItemPagamentoBoleto t = (BoletoEventoItemPagamentoBoleto) obj;
			if (t.getPk() != null && getPk() != null) {
				retono = t.getPk().equals(getPk());
			} else if (t.getPk() == null && getPk() == null) {
					retono = true;
			} else {
				
			}
		}
		return retono;
	}
}