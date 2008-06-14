package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class EventoItemPagamentoChequePredatado extends EventoItemPagamento{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3072430464035122249L;
	private Collection parcelas;
	
	public Collection getParcelas() {
		return parcelas;
	}

	public void setParcelas(Collection parcelas) {
		this.parcelas = parcelas;
	}

	public EventoItemPagamentoChequePredatado(EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento,int codigoForma, int codigoPlano, String formaImpressora, BigDecimal valorBruto, BigDecimal valorDesconto, BigDecimal valorAcrescimo,Collection parcelas){
		super(pk,tipoEvento,dataHoraEvento,codigoForma,codigoPlano,formaImpressora,valorBruto,valorDesconto,valorAcrescimo);
		this.parcelas = parcelas;
	}
	
	public EventoItemPagamentoChequePredatado(){
		
	}
}
