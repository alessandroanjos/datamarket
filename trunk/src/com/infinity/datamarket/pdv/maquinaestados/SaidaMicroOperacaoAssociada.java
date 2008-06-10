package com.infinity.datamarket.pdv.maquinaestados;

import com.infinity.datamarket.comum.util.Persistente;

public class SaidaMicroOperacaoAssociada extends Persistente{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7384950519346168687L;
	private MicroOperacaoAssociada microOperacaoAssociada;
	private int saida;
	public MicroOperacaoAssociada getMicroOperacaoAssociada() {
		return microOperacaoAssociada;
	}
	public void setMicroOperacaoAssociada(
			MicroOperacaoAssociada microOperacaoAssociada) {
		this.microOperacaoAssociada = microOperacaoAssociada;
	}
	public int getSaida() {
		return saida;
	}
	public void setSaida(int saida) {
		this.saida = saida;
	}
}