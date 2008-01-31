package com.infinity.datamarket.comum.usuario;

import com.infinity.datamarket.comum.util.Persistente;

public class Loja extends Persistente{
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
