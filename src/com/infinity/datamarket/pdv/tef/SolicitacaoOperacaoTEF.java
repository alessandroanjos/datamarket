package com.infinity.datamarket.pdv.tef;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SolicitacaoOperacaoTEF  implements Serializable{

	
	private Long identificacao;
	
	private Long numeroCOO;
	
	private BigDecimal valorOperacao;
	
	private Byte moeda;
	
	private String cmc7;
	
	private String tipoPessoa;
	
	private Long documentoPessoa;
	
	private Date dataCheque;
	
	private String nomeRede;
	
	private Long nsuTEF;
	
	private Date dataHoraTransacao;
	
	private String chaveFinalizacao;
	
	private String banco;
	
	private String agencia;
	
	private String digitoAgencia;
	
	private String contaCorrente;
	
	private String digitoContaCorrente;
	
	private String numeroCheque;
	
	private String digitoCheque;

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

	public String getChaveFinalizacao() {
		return chaveFinalizacao;
	}

	public void setChaveFinalizacao(String chaveFinalizacao) {
		this.chaveFinalizacao = chaveFinalizacao;
	}

	public String getCmc7() {
		return cmc7;
	}

	public void setCmc7(String cmc7) {
		this.cmc7 = cmc7;
	}

	public String getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public Date getDataCheque() {
		return dataCheque;
	}

	public void setDataCheque(Date dataCheque) {
		this.dataCheque = dataCheque;
	}

	public Date getDataHoraTransacao() {
		return dataHoraTransacao;
	}

	public void setDataHoraTransacao(Date dataHoraTransacao) {
		this.dataHoraTransacao = dataHoraTransacao;
	}

	public String getDigitoAgencia() {
		return digitoAgencia;
	}

	public void setDigitoAgencia(String digitoAgencia) {
		this.digitoAgencia = digitoAgencia;
	}

	public String getDigitoCheque() {
		return digitoCheque;
	}

	public void setDigitoCheque(String digitoCheque) {
		this.digitoCheque = digitoCheque;
	}

	public String getDigitoContaCorrente() {
		return digitoContaCorrente;
	}

	public void setDigitoContaCorrente(String digitoContaCorrente) {
		this.digitoContaCorrente = digitoContaCorrente;
	}

	public Long getDocumentoPessoa() {
		return documentoPessoa;
	}

	public void setDocumentoPessoa(Long documentoPessoa) {
		this.documentoPessoa = documentoPessoa;
	}

	public Long getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(Long identificacao) {
		this.identificacao = identificacao;
	}

	public Byte getMoeda() {
		return moeda;
	}

	public void setMoeda(Byte moeda) {
		this.moeda = moeda;
	}

	public String getNomeRede() {
		return nomeRede;
	}

	public void setNomeRede(String nomeRede) {
		this.nomeRede = nomeRede;
	}

	public Long getNsuTEF() {
		return nsuTEF;
	}

	public void setNsuTEF(Long nsuTEF) {
		this.nsuTEF = nsuTEF;
	}

	public String getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public Long getNumeroCOO() {
		return numeroCOO;
	}

	public void setNumeroCOO(Long numeroCOO) {
		this.numeroCOO = numeroCOO;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public BigDecimal getValorOperacao() {
		return valorOperacao;
	}

	public void setValorOperacao(BigDecimal valorOperacao) {
		this.valorOperacao = valorOperacao;
	}	
}
