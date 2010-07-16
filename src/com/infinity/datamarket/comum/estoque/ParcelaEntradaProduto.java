package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ParcelaEntradaProduto implements Serializable, Comparable<ParcelaEntradaProduto>{
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
	
	public int compareTo(ParcelaEntradaProduto o) {
		// TODO Auto-generated method stub
		return getPk().getId().compareTo((Long)o.getPk().getId());
	}
	@Override
	public int hashCode() {
		return getPk().toString().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ParcelaEntradaProduto other = (ParcelaEntradaProduto) obj;
		if (getPk() == null) {
			if (other.getPk() != null)
				return false;
		} else if (!getPk().equals(other.getPk()))
			return false;
		return true;
	}
}
