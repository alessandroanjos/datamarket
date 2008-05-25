package com.infinity.datamarket.cliente;

import java.io.Serializable;
import java.math.BigDecimal;

public class DadosCliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5973713505221057137L;

	private String nome;
	private BigDecimal saldoDisponivel;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getSaldoDisponivel() {
		return saldoDisponivel;
	}
	public void setSaldoDisponivel(BigDecimal saldoDisponivel) {
		this.saldoDisponivel = saldoDisponivel;
	}
	
}
