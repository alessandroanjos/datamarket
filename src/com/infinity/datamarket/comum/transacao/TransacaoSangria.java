package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;

public class TransacaoSangria extends Transacao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8627339626787054277L;
	private String codigoUsuarioAutorizador;
	private String codigoUsuarioOperador;
	private BigDecimal valor;
	private String numeroCupom;

	public TransacaoSangria(TransacaoPK pk, int tipoTransacao, String codigoUsuarioAutorizador, String codigoUsuarioOperador, BigDecimal valor, String numeroCupom) {
		super(pk,tipoTransacao);
		this.codigoUsuarioAutorizador = codigoUsuarioAutorizador;
		this.codigoUsuarioOperador = codigoUsuarioOperador;
		this.valor= valor;
		this.numeroCupom = numeroCupom;
	}

	public TransacaoSangria() {

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
