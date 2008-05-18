package com.infinity.datamarket.comum.pagamento;

import java.io.Serializable;
import java.math.BigDecimal;

public class ParcelaPlanoPagamentoChequePredatado implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6237638829985692672L;
	private ParcelaPlanoPagamentoChequePredatadoPK pk;
	
	private BigDecimal percentagemParcela;
	private int quantidadeDias;
	public BigDecimal getPercentagemParcela() {
		return percentagemParcela;
	}
	public void setPercentagemParcela(BigDecimal percentagemParcela) {
		this.percentagemParcela = percentagemParcela;
	}
	public ParcelaPlanoPagamentoChequePredatadoPK getPk() {
		return pk;
	}
	public void setPk(ParcelaPlanoPagamentoChequePredatadoPK pk) {
		this.pk = pk;
	}
	public int getQuantidadeDias() {
		return quantidadeDias;
	}
	public void setQuantidadeDias(int quantidadeDias) {
		this.quantidadeDias = quantidadeDias;
	}
	
}
