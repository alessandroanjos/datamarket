package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class TransacaoFechamentoZ extends Transacao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4036037336418550868L;
	private String codigoUsuarioAutorizador;
	private BigDecimal gtFim;

	public TransacaoFechamentoZ(TransacaoPK pk, int tipoTransacao, String codigoUsuarioAutorizador, BigDecimal gtFim) {
		super(pk,tipoTransacao);
		this.codigoUsuarioAutorizador = codigoUsuarioAutorizador;
		this.gtFim = gtFim;
	}

	public TransacaoFechamentoZ(){

	}

	public String getCodigoUsuarioAutorizador() {
		return codigoUsuarioAutorizador;
	}

	public void setCodigoUsuarioAutorizador(String codigoUsuarioAutorizador) {
		this.codigoUsuarioAutorizador = codigoUsuarioAutorizador;
	}

	public BigDecimal getGtFim() {
		return gtFim;
	}

	public void setGtFim(BigDecimal gtFim) {
		this.gtFim = gtFim;
	}

}
