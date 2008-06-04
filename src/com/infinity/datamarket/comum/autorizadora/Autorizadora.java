package com.infinity.datamarket.comum.autorizadora;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.util.Persistente;

public class Autorizadora extends Persistente {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8582322953653946189L;
	
	private String descricao;
	private String situacao;
	private BigDecimal desagil;
	/**
	 * @return the desagil
	 */
	public BigDecimal getDesagil() {
		return desagil;
	}
	/**
	 * @param desagil the desagil to set
	 */
	public void setDesagil(BigDecimal desagil) {
		this.desagil = desagil;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the situacao
	 */
	public String getSituacao() {
		return situacao;
	}
	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
}
