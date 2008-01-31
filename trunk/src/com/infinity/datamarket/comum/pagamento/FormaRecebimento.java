package com.infinity.datamarket.comum.pagamento;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.util.Persistente;

public class FormaRecebimento extends Persistente{

	private String descricao;
	private String recebimentoImpressora;
	private String abrirGaveta;
	private BigDecimal valorLimiteSangria;
	private Date dataInicioValidade;
	private Date dataFimValidade;
	private BigDecimal valorMaxTroco;
	private FormaRecebimento formaTroco;
	private Collection planos;

	public Collection getPlanos() {
		return planos;
	}
	public void setPlanos(Collection planos) {
		this.planos = planos;
	}
	public String getAbrirGaveta() {
		return abrirGaveta;
	}
	public void setAbrirGaveta(String abrirGaveta) {
		this.abrirGaveta = abrirGaveta;
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
	public FormaRecebimento getFormaTroco() {
		return formaTroco;
	}
	public void setFormaTroco(FormaRecebimento formaTroco) {
		this.formaTroco = formaTroco;
	}
	public String getRecebimentoImpressora() {
		return recebimentoImpressora;
	}
	public void setRecebimentoImpressora(String recebimentoImpressora) {
		this.recebimentoImpressora = recebimentoImpressora;
	}
	public BigDecimal getValorLimiteSangria() {
		return valorLimiteSangria;
	}
	public void setValorLimiteSangria(BigDecimal valorLimiteSangria) {
		this.valorLimiteSangria = valorLimiteSangria;
	}
	public BigDecimal getValorMaxTroco() {
		return valorMaxTroco;
	}
	public void setValorMaxTroco(BigDecimal valorMaxTroco) {
		this.valorMaxTroco = valorMaxTroco;
	}

}
