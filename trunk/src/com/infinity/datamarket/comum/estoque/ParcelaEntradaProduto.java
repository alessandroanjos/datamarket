package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ParcelaEntradaProduto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5379894414735608500L;

	private BigDecimal valor;
	
	private Date dataVencimento;

	private ParcelaEntradaProdutoPK pk;

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public ParcelaEntradaProdutoPK getPk() {
		return pk;
	}

	public void setPk(ParcelaEntradaProdutoPK pk) {
		this.pk = pk;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
