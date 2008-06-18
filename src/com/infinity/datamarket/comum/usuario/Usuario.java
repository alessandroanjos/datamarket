package com.infinity.datamarket.comum.usuario;

import java.util.Collection;
import java.util.Set;

import com.infinity.datamarket.comum.util.Persistente;

public class Usuario extends Persistente{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 243674995309273011L;
	private String nome;
	private Perfil perfil;
	private String senha;
	
	Collection usuarios;
	
	Set lojas;
	
	/**
	 * @return the lojas
	 */
	public Set getLojas() {
		return lojas;
	}

	/**
	 * @param lojas the lojas to set
	 */
	public void setLojas(Set lojas) {
		this.lojas = lojas;
	}

	/**
	 * @return the usuarios
	 */
	public Collection getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(Collection usuarios) {
		this.usuarios = usuarios;
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.getId().toString().hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Usuario other = (Usuario) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}	
}
