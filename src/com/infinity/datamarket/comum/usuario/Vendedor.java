package com.infinity.datamarket.comum.usuario;

import java.math.BigDecimal;

public class Vendedor extends Usuario{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4910579417653846606L;
	
	private BigDecimal comissao;

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

}
