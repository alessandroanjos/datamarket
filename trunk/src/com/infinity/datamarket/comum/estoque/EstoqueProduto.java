package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;
import java.math.BigDecimal;

public class EstoqueProduto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5553641702848574308L;

	private EstoqueProdutoPK pk;
	
	private BigDecimal quantidade;

	public EstoqueProdutoPK getPk() {
		return pk;
	}

	public void setPk(EstoqueProdutoPK pk) {
		this.pk = pk;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	
}
