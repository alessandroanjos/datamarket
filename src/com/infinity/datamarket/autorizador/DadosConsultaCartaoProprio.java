package com.infinity.datamarket.autorizador;

import java.io.Serializable;
import java.math.BigDecimal;

public class DadosConsultaCartaoProprio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4011337076128287265L;
	
	private String nome;
	
	private BigDecimal valorDebito;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValorDebito() {
		return valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito) {
		this.valorDebito = valorDebito;
	}

}
