package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;

public class TransacaoResuprimento extends Transacao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3853794063310558624L;
	private String codigoUsuarioAutorizador;
	private String codigoUsuarioOperador;
	private BigDecimal valor;
	private String numeroCupom;

	public TransacaoResuprimento(TransacaoPK pk, int tipoTransacao, String codigoUsuarioAutorizador, String codigoUsuarioOperador, BigDecimal valor, String numeroCupom) {
		super(pk,tipoTransacao);
		this.codigoUsuarioAutorizador = codigoUsuarioAutorizador;
		this.codigoUsuarioOperador = codigoUsuarioOperador;
		this.valor= valor;
		this.numeroCupom = numeroCupom;
	}

	public TransacaoResuprimento() {

	}

	public String getCodigoUsuarioAutorizador() {
		return codigoUsuarioAutorizador;
	}

	public void setCodigoUsuarioAutorizador(String codigoUsuarioAutorizador) {
		this.codigoUsuarioAutorizador = codigoUsuarioAutorizador;
	}

	public String getCodigoUsuarioOperador() {
		return codigoUsuarioOperador;
	}

	public void setCodigoUsuarioOperador(String codigoUsuarioOperador) {
		this.codigoUsuarioOperador = codigoUsuarioOperador;
	}

	public String getNumeroCupom() {
		return numeroCupom;
	}

	public void setNumeroCupom(String numeroCupom) {
		this.numeroCupom = numeroCupom;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


}
