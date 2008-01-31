package com.infinity.datamarket.comum.usuario;

import java.math.BigDecimal;
import java.util.Collection;

import com.infinity.datamarket.comum.util.Persistente;

public class Perfil extends Persistente{
	private String descricao;
	private BigDecimal percentualDesconto;
	private Perfil perfilSuperior;
	private Collection operacoes;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Collection getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(Collection operacoes) {
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
