package com.infinity.datamarket.comum.produto;

import java.io.Serializable;
import java.math.BigDecimal;

public class Composicao implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8104857903481444287L;
	
	private ComposicaoPK pk;
	private BigDecimal quantidade;
	private String acao;
	
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	public ComposicaoPK getPk() {
		return pk;
	}
	public void setPk(ComposicaoPK pk) {
		this.pk = pk;
	}
}
