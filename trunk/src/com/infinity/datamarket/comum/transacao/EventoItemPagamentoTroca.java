package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class EventoItemPagamentoTroca extends EventoItemPagamento{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -398192688585384654L;

	public EventoItemPagamentoTroca(EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento,int codigoForma, int codigoPlano, String formaImpressora, BigDecimal valorBruto, BigDecimal valorDesconto, BigDecimal valorAcrescimo,
			String operacao){
		super(pk,tipoEvento,dataHoraEvento,codigoForma,codigoPlano,formaImpressora,valorBruto,valorDesconto,valorAcrescimo);
		this.operacao = operacao;
	}
	
	public EventoItemPagamentoTroca(){
		
	}
	
	private String operacao;

	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	
}
