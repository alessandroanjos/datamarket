package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class EventoItemPagamentoCartaoProprio extends EventoItemPagamento{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -398192688585384654L;

	public EventoItemPagamentoCartaoProprio(EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento,int codigoForma, int codigoPlano, String formaImpressora, BigDecimal valorBruto, BigDecimal valorDesconto, BigDecimal valorAcrescimo,
			String autorizacao, String CPFCNPJ){
		super(pk,tipoEvento,dataHoraEvento,codigoForma,codigoPlano,formaImpressora,valorBruto,valorDesconto,valorAcrescimo);
		this.autorizacao = autorizacao;
		this.CPFCNPJ = CPFCNPJ;
	}
	
	public EventoItemPagamentoCartaoProprio(){
		
	}
	
	private String autorizacao;

	private String CPFCNPJ;
	
	public String getAutorizacao() {
		return autorizacao;
	}
	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}

	public String getCPFCNPJ() {
		return CPFCNPJ;
	}

	public void setCPFCNPJ(String cpfcnpj) {
		CPFCNPJ = cpfcnpj;
	}

	
}
