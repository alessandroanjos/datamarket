package com.infinity.datamarket.comum.boleto;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.util.Persistente;

public class PagamentoBoleto extends Persistente{
	
	private PagamentoBoletoPK pk;

	private Long idBoleto;
	private BigDecimal valor;
	private BigDecimal valorDesconto;
	private BigDecimal valorMulta;
	private BigDecimal valorMora;
	private Date dataPagamento;
	private Boleto boleto;

	public Long getIdBoleto() {
		return idBoleto;
	}
	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}
	public Boleto getBoleto() {
		return boleto;
	}
	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public PagamentoBoletoPK getPk() {
		return pk;
	}
	public void setPk(PagamentoBoletoPK pk) {
		this.pk = pk;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	public BigDecimal getValorMora() {
		return valorMora;
	}
	public void setValorMora(BigDecimal valorMora) {
		this.valorMora = valorMora;
	}
	public BigDecimal getValorMulta() {
		return valorMulta;
	}
	public void setValorMulta(BigDecimal valorMulta) {
		this.valorMulta = valorMulta;
	}

	public int hashCode() {
		return getPk().hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PagamentoBoleto other = (PagamentoBoleto) obj;
		if (getPk() == null) {
			if (other.getPk() != null)
				return false;
		} else if (!getPk().equals(other.getPk()))
			return false;
		return true;
	}
	
}
