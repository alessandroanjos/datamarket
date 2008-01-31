package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class TransacaoEntradaOperador extends Transacao{

	private String codigoUsuarioAutorizador;
	private String codigoUsuarioOperador;
	private BigDecimal fundoTroco;

	public TransacaoEntradaOperador(TransacaoPK pk, int tipoTransacao, String codigoUsuarioAutorizador, String codigoUsuarioOperador, BigDecimal fundoTroco) {
		super(pk,tipoTransacao);
		this.codigoUsuarioAutorizador = codigoUsuarioAutorizador;
		this.codigoUsuarioOperador = codigoUsuarioOperador;
		this.fundoTroco = fundoTroco;
	}

	public TransacaoEntradaOperador(){

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

	public BigDecimal getFundoTroco() {
		return fundoTroco;
	}

	public void setFundoTroco(BigDecimal fundoTroco) {
		this.fundoTroco = fundoTroco;
	}

}
