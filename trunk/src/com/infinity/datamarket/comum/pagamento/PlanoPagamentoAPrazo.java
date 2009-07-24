package com.infinity.datamarket.comum.pagamento;

import java.math.BigDecimal;
import java.util.Collection;

public class PlanoPagamentoAPrazo extends PlanoPagamento{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1665020148129076387L;
	
	private BigDecimal percentagemEntrada;
	
	private Collection parcelas;

	public Collection getParcelas() {
		return parcelas;
	}

	public void setParcelas(Collection parcelas) {
		this.parcelas = parcelas;
	}

	public BigDecimal getPercentagemEntrada() {
		return percentagemEntrada;
	}

	public void setPercentagemEntrada(BigDecimal percentagemEntrada) {
		this.percentagemEntrada = percentagemEntrada;
	}

}
