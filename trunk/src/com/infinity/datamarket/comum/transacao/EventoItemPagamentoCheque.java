package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class EventoItemPagamentoCheque extends EventoItemPagamento{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4993197218465195174L;
	
	
	public EventoItemPagamentoCheque(EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento,int codigoForma, int codigoPlano, String formaImpressora, BigDecimal valorBruto, BigDecimal valorDesconto, BigDecimal valorAcrescimo,
			String CPFCNPJ, String numeroChequeLido, String banco, String agencia, String conta,String numeroCheque){
		super(pk,tipoEvento,dataHoraEvento,codigoForma,codigoPlano,formaImpressora,valorBruto,valorDesconto,valorAcrescimo);
		this.CPFCNPJ = CPFCNPJ;
		this.numeroChequeLido = numeroChequeLido;
		this.banco = banco;
		this.agencia = agencia;
		this.conta = conta;
		this.numeroCheque = numeroCheque;
	}
	
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
