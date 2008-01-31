package com.infinity.datamarket.comum.pagamento;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.util.Persistente;

public class PlanoPagamento extends Persistente{
	private String descricao;
	private String status;
	private BigDecimal valorMinimo;
	private BigDecimal valorMaximo;
	private BigDecimal percDesconto;
	private BigDecimal percAcrescimo;
	private Date dataInicioValidade;
	private Date dataFimValidade;
	private FormaRecebimento forma;

	public FormaRecebimento getForma() {
		return forma;
	}
	public void setForma(FormaRecebimento forma) {
		this.forma = forma;
	}
	public Date getDataFimValidade() {
		return dataFimValidade;
	}
	public void setDataFimValidade(Date dataFimValidade) {
		this.dataFimValidade = dataFimValidade;
	}
	public Date getDataInicioValidade() {
		return dataInicioValidade;
	}
	public void setDataInicioValidade(Date dataInicioValidade) {
		this.dataInicioValidade = dataInicioValidade;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getPercAcrescimo() {
		return percAcrescimo;
	}
	public void setPercAcrescimo(BigDecimal percAcrescimo) {
		this.percAcrescimo = percAcrescimo;
	}
	public BigDecimal getPercDesconto() {
		return percDesconto;
	}
	public void setPercDesconto(BigDecimal percDesconto) {
		this.percDesconto = percDesconto;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}
	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}
	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
}
