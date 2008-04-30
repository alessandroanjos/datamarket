package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class TransacaoAbertura extends Transacao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9197000863507808331L;
	private String codigoUsuarioAutorizador;
	private BigDecimal gtInicio;

	public TransacaoAbertura(TransacaoPK pk, int tipoTransacao, String codigoUsuarioAutorizador, BigDecimal gtInicio) {
		super(pk,tipoTransacao);
		this.codigoUsuarioAutorizador = codigoUsuarioAutorizador;
		this.gtInicio = gtInicio;
	}

	public TransacaoAbertura(){

	}

	public String getCodigoUsuarioAutorizador() {
		return codigoUsuarioAutorizador;
	}

	public void setCodigoUsuarioAutorizador(String codigoUsuarioAutorizador) {
		this.codigoUsuarioAutorizador = codigoUsuarioAutorizador;
	}

	public BigDecimal getGtInicio() {
		return gtInicio;
	}

	public void setGtInicio(BigDecimal gtInicio) {
		this.gtInicio = gtInicio;
	}

}
