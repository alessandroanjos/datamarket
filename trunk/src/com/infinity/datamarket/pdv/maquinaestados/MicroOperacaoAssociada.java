package com.infinity.datamarket.pdv.maquinaestados;

import java.util.Collection;

import com.infinity.datamarket.comum.util.Persistente;

public class MicroOperacaoAssociada extends Persistente{

	private MicroOperacao microOperacao;
	private Collection saidas;
	public MicroOperacao getMicroOperacao() {
		return microOperacao;
	}
	public void setMicroOperacao(MicroOperacao microOperacao) {
		this.microOperacao = microOperacao;
	}
	public Collection getSaidas() {
		return saidas;
	}
	public void setSaidas(Collection saidas) {
		this.saidas = saidas;
	}

}
