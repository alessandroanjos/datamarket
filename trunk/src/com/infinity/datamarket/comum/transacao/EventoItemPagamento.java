package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class EventoItemPagamento extends EventoTransacao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5059669320701557563L;
	private BigDecimal valorBruto;
	private BigDecimal valorDesconto;
	private BigDecimal valorAcrescimo;
	private String formaImpressora;
	private int codigoForma;
	private int codigoPlano;
	private String descricaoForma;
	
	public String getDescricaoForma() {
		return descricaoForma;
	}

	public void setDescricaoForma(String descricaoForma) {
		this.descricaoForma = descricaoForma;
	}

	public EventoItemPagamento(){

	}

	public EventoItemPagamento(EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento,int codigoForma, int codigoPlano, String formaImpressora, BigDecimal valorBruto, BigDecimal valorDesconto, BigDecimal valorAcrescimo){
		super(pk,tipoEvento,dataHoraEvento);
		this.valorBruto = valorBruto;
		this.valorDesconto = valorDesconto;
		this.valorDesconto = valorDesconto;
		this.formaImpressora = formaImpressora;
		this.codigoForma = codigoForma;
		this.codigoPlano = codigoPlano;
	}

	public int getCodigoForma() {
		return codigoForma;
	}

	public void setCodigoForma(int codigoForma) {
		this.codigoForma = codigoForma;
	}

	public int getCodigoPlano() {
		return codigoPlano;
	}

	public void setCodigoPlano(int codigoPlano) {
		this.codigoPlano = codigoPlano;
	}

	public String getFormaImpressora() {
		return formaImpressora;
	}

	public void setFormaImpressora(String formaImpressora) {
		this.formaImpressora = formaImpressora;
	}

	public BigDecimal getValorAcrescimo() {
		return valorAcrescimo;
	}

	public void setValorAcrescimo(BigDecimal valorAcrescimo) {
		this.valorAcrescimo = valorAcrescimo;
	}

	public BigDecimal getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(BigDecimal valorBruto) {
		this.valorBruto = valorBruto;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

}
