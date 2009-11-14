package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.infinity.datamarket.comum.boleto.Boleto;

public class EventoItemPagamentoBoleto extends EventoItemPagamento{

	private static final long serialVersionUID = 4993197218465195174L;
	
	public EventoItemPagamentoBoleto(EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento,int codigoForma, int codigoPlano, String formaImpressora, BigDecimal valorBruto, BigDecimal valorDesconto, BigDecimal valorAcrescimo){
		super(pk,tipoEvento,dataHoraEvento,codigoForma,codigoPlano,formaImpressora,valorBruto,valorDesconto,valorAcrescimo);
	}
	
	public EventoItemPagamentoBoleto(){
		
	}
	private Set  boletos;

	public Set getBoletos() {
		return boletos;
	}

	public void setBoletos(Set boletos) {
		this.boletos = boletos;
	}
}
