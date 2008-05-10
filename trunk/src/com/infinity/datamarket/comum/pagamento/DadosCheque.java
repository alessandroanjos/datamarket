package com.infinity.datamarket.comum.pagamento;

import java.io.Serializable;

public class DadosCheque implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5262321983436492278L;

	private String CPFCNPJ;
	private String numeroChequeLido;
	private String banco;
	private String agencia;
	private String conta;
	private String numeroCheque;
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public String getCPFCNPJ() {
		return CPFCNPJ;
	}
	public void setCPFCNPJ(String cpfcnpj) {
		CPFCNPJ = cpfcnpj;
	}
	public String getNumeroCheque() {
		return numeroCheque;
	}
	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}
	public String getNumeroChequeLido() {
		return numeroChequeLido;
	}
	public void setNumeroChequeLido(String numeroChequeLido) {
		this.numeroChequeLido = numeroChequeLido;
	}
	
}
