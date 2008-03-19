package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class TransacaoCancelamento extends Transacao{

	 
	private int lojaCancelada;
	private int componenteCancelado;
	private int numeroTransacaoCancelada;
	private Date dataTransacaoCancelada;
	private String numeroCupom;
	private String codigoUsuarioAutorizador;
	
	public TransacaoCancelamento(TransacaoPK pk, int tipoTransacao, int lojaCancelada, int componenteCancelado, int numeroTransacaoCancelada, Date dataTransacaoCancelada, String numeroCupom, String codigoUsuarioAutorizador) {
		super(pk,tipoTransacao);
		this.lojaCancelada = lojaCancelada;
		this.componenteCancelado = componenteCancelado;
		this.numeroTransacaoCancelada = numeroTransacaoCancelada;
		this.dataTransacaoCancelada = dataTransacaoCancelada;
		this.codigoUsuarioAutorizador = codigoUsuarioAutorizador;
		this.numeroCupom = numeroCupom;
	}

	public TransacaoCancelamento() {

	}

	public int getLojaCancelada() {
		return lojaCancelada;
	}

	public void setLojaCancelada(int lojaCancelada) {
		this.lojaCancelada = lojaCancelada;
	}

	public int getComponenteCancelado() {
		return componenteCancelado;
	}

	public void setComponenteCancelado(int componenteCancelado) {
		this.componenteCancelado = componenteCancelado;
	}

	public int getNumeroTransacaoCancelada() {
		return numeroTransacaoCancelada;
	}

	public void setNumeroTransacaoCancelada(int numeroTransacaoCancelada) {
		this.numeroTransacaoCancelada = numeroTransacaoCancelada;
	}

	public Date getDataTransacaoCancelada() {
		return dataTransacaoCancelada;
	}

	public void setDataTransacaoCancelada(Date dataTransacaoCancelada) {
		this.dataTransacaoCancelada = dataTransacaoCancelada;
	}

	public String getNumeroCupom() {
		return numeroCupom;
	}

	public void setNumeroCupom(String numeroCupom) {
		this.numeroCupom = numeroCupom;
	}

	public String getCodigoUsuarioAutorizador() {
		return codigoUsuarioAutorizador;
	}

	public void setCodigoUsuarioAutorizador(String codigoUsuarioAutorizador) {
		this.codigoUsuarioAutorizador = codigoUsuarioAutorizador;
	}

}
