package com.infinity.datamarket.comum.usuario;

import com.infinity.datamarket.comum.util.Persistente;

public class Usuario extends Persistente{
	private String nome;
	private Perfil perfil;
	private String senha;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
