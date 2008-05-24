package com.infinity.datamarket.comum.pagamento;

import java.math.BigDecimal;
import java.util.Date;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.util.Persistente;

public class PlanoPagamento extends Persistente implements Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5162497470349833544L;
	String descricao;
	String status;
	BigDecimal valorMinimo;
	BigDecimal valorMaximo;
	BigDecimal percDesconto;
	BigDecimal percAcrescimo;
	Date dataInicioValidade;
	Date dataFimValidade;
	FormaRecebimento forma;

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
	public FormaRecebimento getForma() {
		return forma;
	}
	public void setForma(FormaRecebimento forma) {
		this.forma = forma;
	}
	public int compareTo(Object o) {
		if (o instanceof PlanoPagamento){
			PlanoPagamento plano = (PlanoPagamento) o;
			if (this.getId().intValue() > plano.getId().intValue()){
				return 1;
			}else{
				return -1;
			}
		}
		return 0;
	}

	
}
