package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class EventoItemPagamentoCartaoProprio extends EventoItemPagamento{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -398192688585384654L;

	public EventoItemPagamentoCartaoProprio(EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento,int codigoForma, int codigoPlano, String formaImpressora, BigDecimal valorBruto, BigDecimal valorDesconto, BigDecimal valorAcrescimo,
			String autorizacao){
		super(pk,tipoEvento,dataHoraEvento,codigoForma,codigoPlano,formaImpressora,valorBruto,valorDesconto,valorAcrescimo);
		this.autorizacao = autorizacao;
	}
	
	private String autorizacao;

	public String getAutorizacao() {
		return autorizacao;
	}
	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}
	
}
