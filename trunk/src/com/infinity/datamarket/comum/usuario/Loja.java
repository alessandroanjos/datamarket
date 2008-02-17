package com.infinity.datamarket.comum.usuario;

import java.util.Collection;

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
	
	String nome;
	String numeroIp;
	String numeroPorta;
	Collection lojas;
	
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
	
	
}
