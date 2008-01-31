package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class TransacaoFechamentoX extends Transacao{

	private String codigoUsuarioAutorizador;
	private String codigoUsuarioOperador;


	public TransacaoFechamentoX(TransacaoPK pk, int tipoTransacao, String codigoUsuarioAutorizador, String codigoUsuarioOperador) {
		super(pk,tipoTransacao);
		this.codigoUsuarioAutorizador = codigoUsuarioAutorizador;
		this.codigoUsuarioOperador = codigoUsuarioOperador;
	}

	public TransacaoFechamentoX(){

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

}
