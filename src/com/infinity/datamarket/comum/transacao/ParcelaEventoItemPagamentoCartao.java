package com.infinity.datamarket.comum.transacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ParcelaEventoItemPagamentoCartao implements Serializable{

	private static final long serialVersionUID = -4926613293470780967L;
	
	private ParcelaEventoItemPagamentoCartaoPK pk;
	
	private BigDecimal valor;
	private Date data;

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	public ParcelaEventoItemPagamentoCartaoPK getPk() {
		return pk;
	}
	public void setPk(ParcelaEventoItemPagamentoCartaoPK pk) {
		this.pk = pk;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}