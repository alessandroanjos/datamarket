package com.infinity.datamarket.comum.pagamento;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.util.Persistente;

public class Autorizadora extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1244373537831421025L;

	private String descricao;
	private BigDecimal desagil;
	private String situacao;
	public BigDecimal getDesagil() {
		return desagil;
	}
	public void setDesagil(BigDecimal desagil) {
		this.desagil = desagil;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
}
