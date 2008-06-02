package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class EventoItemPagamentoCartaoOff extends EventoItemPagamento{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4730778721487671453L;

	public EventoItemPagamentoCartaoOff(EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento,int codigoForma, int codigoPlano, String formaImpressora, BigDecimal valorBruto, BigDecimal valorDesconto, BigDecimal valorAcrescimo,
			String numeroCartao, int quantidadeParcelas, String autorizacao, String codigoAutorizadora){
		super(pk,tipoEvento,dataHoraEvento,codigoForma,codigoPlano,formaImpressora,valorBruto,valorDesconto,valorAcrescimo);
		this.numeroCartao = numeroCartao;
		this.codigoAutorizadora = codigoAutorizadora;
		this.quantidadeParcelas = quantidadeParcelas;
		this.autorizacao = autorizacao;
	}
	
	private String numeroCartao;
	private int quantidadeParcelas;
	private String autorizacao;
	private String codigoAutorizadora;

	public String getAutorizacao() {
		return autorizacao;
	}
	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}
	public String getCodigoAutorizadora() {
		return codigoAutorizadora;
	}
	public void setCodigoAutorizadora(String codigoAutorizadora) {
		this.codigoAutorizadora = codigoAutorizadora;
	}
	public String getNumeroCartao() {
		return numeroCartao;
	}
	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
	public int getQuantidadeParcelas() {
		return quantidadeParcelas;
	}
	public void setQuantidadeParcelas(int quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}
	
}
