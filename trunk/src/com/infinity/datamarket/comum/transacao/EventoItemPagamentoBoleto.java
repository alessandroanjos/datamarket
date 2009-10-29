package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.boleto.Boleto;

public class EventoItemPagamentoBoleto extends EventoItemPagamento{

	private static final long serialVersionUID = 4993197218465195174L;
	
	public EventoItemPagamentoBoleto(EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento,int codigoForma, int codigoPlano, String formaImpressora, BigDecimal valorBruto, BigDecimal valorDesconto, BigDecimal valorAcrescimo, Boleto boleto){
		super(pk,tipoEvento,dataHoraEvento,codigoForma,codigoPlano,formaImpressora,valorBruto,valorDesconto,valorAcrescimo);
		this.boleto = boleto;
	}
	
	public EventoItemPagamentoBoleto(){
		
	}
	private Boleto boleto;


	public Boleto getBoleto() {
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}
}
