package com.infinity.datamarket.pdv.maquinaestados;

import com.infinity.datamarket.comum.util.Persistente;

public class MicroOperacao extends Persistente{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2445151310530394212L;
	private String classe;

	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
}
