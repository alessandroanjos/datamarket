package com.infinity.datamarket.pdv.maquinaestados;

import java.util.ArrayList;
import java.util.Collection;

import com.infinity.datamarket.comum.util.Persistente;

public class MicroOperacaoAssociada extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5359714767961446097L;
	private MicroOperacao microOperacao;
	private Collection saidas;
	public MicroOperacao getMicroOperacao() {
		return microOperacao;
	}
	public void setMicroOperacao(MicroOperacao microOperacao) {
		this.microOperacao = microOperacao;
	}
	public Collection getSaidas() {
		if (saidas == null) {
			saidas = new ArrayList();
		}
		return saidas;
	}
	public void setSaidas(Collection saidas) {
		this.saidas = saidas;
	}

}
