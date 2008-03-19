package com.infinity.datamarket.pdv.maquinaestados;

import com.infinity.datamarket.comum.util.Persistente;

public class MicroOperacao extends Persistente{
	private String classe;

	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
}
