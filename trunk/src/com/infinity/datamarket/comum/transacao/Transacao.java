package com.infinity.datamarket.comum.transacao;

import java.io.Serializable;

import com.infinity.datamarket.comum.util.Constantes;

public class Transacao implements Serializable{
	private TransacaoPK pk;
	private int tipoTransacao = Constantes.NUMERO_NAO_INFORMADO;
	public boolean equals(Object obj){
		if (obj instanceof Transacao){
			Transacao t = (Transacao) obj;
			return (this.pk.equals(t.pk) && this.tipoTransacao == t.tipoTransacao);
		}
		return false;

	}

	public Transacao(){

	}

	public Transacao(TransacaoPK pk, int tipoTransacao){
		this.pk = pk;
		this.tipoTransacao = tipoTransacao;
	}

	public int getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(int tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public TransacaoPK getPk() {
		return pk;
	}

	public void setPk(TransacaoPK pk) {
		this.pk = pk;
	}
}
