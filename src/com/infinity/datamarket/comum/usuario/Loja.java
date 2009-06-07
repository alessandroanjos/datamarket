package com.infinity.datamarket.comum.usuario;

import java.util.Collection;
import java.util.Set;

import com.infinity.datamarket.comum.util.Persistente;

/**
 * @author jonas
 *
 */
public class Loja extends Persistente {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 3232434821743619505L;
	
	private String nome;
	private String numeroIp;
	private String numeroPorta;
	private Long idEstoque;
	private Long idContaCorrente;
	private Collection lojas;
	
	private Set usuarios;
	
	/**
	 * @return the usuarios
	 */
	public Set getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(Set usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the numeroIp
	 */
	public String getNumeroIp() {
		return numeroIp;
	}

	/**
	 * @param numeroIp the numeroIp to set
	 */
	public void setNumeroIp(String numeroIp) {
		this.numeroIp = numeroIp;
	}

	/**
	 * @return the numeroPorta
	 */
	public String getNumeroPorta() {
		return numeroPorta;
	}

	/**
	 * @param numeroPorta the numeroPorta to set
	 */
	public void setNumeroPorta(String numeroPorta) {
		this.numeroPorta = numeroPorta;
	}

	/**
	 * @return the lojas
	 */
	public Collection getLojas() {
		return lojas;
	}

	/**
	 * @param lojas the lojas to set
	 */
	public void setLojas(Collection lojas) {
		this.lojas = lojas;
	}

	public Long getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(Long idEstoque) {
		this.idEstoque = idEstoque;
	}

	public Long getIdContaCorrente() {
		return idContaCorrente;
	}

	public void setIdContaCorrente(Long idContaCorrente) {
		this.idContaCorrente = idContaCorrente;
	}		
}
