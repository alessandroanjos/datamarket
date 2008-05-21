package com.infinity.datamarket.comum.transacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.util.Constantes;

public class ParcelaEventoItemPagamentoChequePredatado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4926613293470780967L;
	
	private ParcelaEventoItemPagamentoChequePredatadoPK pk;
	
	private String CPFCNPJ;
	private String numeroChequeLido;
	private String banco;
	private String agencia;
	private String conta;
	private String numeroCheque;
	private BigDecimal valor;
	private Date data;
	private String entrada = Constantes.NAO;
	
	
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
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getEntrada() {
		return entrada;
	}
	public void setEntrada(String entrada) {
		this.entrada = entrada;
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
	public ParcelaEventoItemPagamentoChequePredatadoPK getPk() {
		return pk;
	}
	public void setPk(ParcelaEventoItemPagamentoChequePredatadoPK pk) {
		this.pk = pk;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
