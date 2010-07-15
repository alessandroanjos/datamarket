package com.infinity.datamarket.comum.estoque;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.util.Persistente;

public class ParcelaEntradaProdutoPK extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 344724114903177652L;

	private Long idEntradaProduto;

	public Long getIdEntradaProduto() {
		return idEntradaProduto;
	}

	public void setIdEntradaProduto(Long idEntradaProduto) {
		this.idEntradaProduto = idEntradaProduto;
	}
	


	
}
