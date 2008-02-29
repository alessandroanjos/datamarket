package com.infinity.datamarket.comum.pagamento;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.hibernate.proxy.HibernateProxyHelper;

import com.infinity.datamarket.comum.util.Persistente;

public class FormaRecebimento extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4859446635232194912L;
	String id;
	String descricao;
	String recebimentoImpressora;
	String abrirGaveta;
	BigDecimal valorLimiteSangria;
	Date dataInicioValidade;
	Date dataFimValidade;
	BigDecimal valorMaxTroco;
	FormaRecebimento formaTroco;
	Collection planos;

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
