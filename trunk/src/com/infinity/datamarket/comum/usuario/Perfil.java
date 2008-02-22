package com.infinity.datamarket.comum.usuario;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import com.infinity.datamarket.comum.util.Persistente;

public class Perfil extends Persistente{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3958914186578038113L;
	
	private String descricao;
	private BigDecimal percentualDesconto;
	private Perfil perfilSuperior;
	private Set operacoes;
	
	private Collection perfis;
	
	/**
	 * @return the perfis
	 */
	public Collection getPerfis() {
		return perfis;
	}

	/**
	 * @param perfis the perfis to set
	 */
	public void setPerfis(Collection perfis) {
		this.perfis = perfis;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(Set operacoes) {
		this.operacoes = operacoes;
	}

	public Perfil getPerfilSuperior() {
		return perfilSuperior;
	}

	public void setPerfilSuperior(Perfil perfilSuperior) {
		this.perfilSuperior = perfilSuperior;
	}

	public BigDecimal getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(BigDecimal percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}
}
