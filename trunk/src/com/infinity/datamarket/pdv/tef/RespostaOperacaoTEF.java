package com.infinity.datamarket.pdv.tef;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class RespostaOperacaoTEF implements Serializable {
	
	private long identificacao;
	
	private long numeroCOO;
	
	private BigDecimal valorOperacao;
	
	private byte moeda;
	
	private String cmc7;
	
	private String tipoPessoa;
	
	private long documentoPessoa;
	
	private Date dataCheque;
	
	private String statusTransacao;
	
	private String nomeRede;
	
	private int tipoTransacao;
	
	private long nsuTEF;
	
	private int codigoAutorizacao;
	
	private long numeroLoteTransacao;
	
	private Date dataHoraTransacaoHost;
	
	private Date dataHoraTransacaoLocal;
	
	private byte tipoParcelamento;
	
	private int quantidadeParcelas;
	
	private Collection<ParcelaTEF> conjuntoParcelas = new ArrayList<ParcelaTEF>();
	
	private Date dataHoraTransacao;
	
	private Date dataCartaoPreDatado;
	
	private long nsuTransacaoCancelada;
	
	private Date dataHoraTransacaoCancelada;
	
	private String chaveFinalizacao;
	
	private String[] linhasComprovantePrincipal;
	
	private String[] linhasCupomReduzidoCli;
	
	private String[] linhasComprovanteCli;
	
	private String[] linhasComprovanteEstabl;
	
	private String textoEspecialOperador;
	
	private String textoEspecialCliente;
	
	private String numeroAutenticacaoCheque;
	
	private String banco;
	
	private String agencia;
	
	private String digitoAgencia;
	
	private String contaCorrente;
	
	private String digitoContaCorrente;
	
	private String numeroCheque;
	
	private String digitoCheque;
	
	private String nomeAutorizadora;
	
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

	public int getCodigoAutorizacao() {
		return codigoAutorizacao;
	}

	public void setCodigoAutorizacao(int codigoAutorizacao) {
		this.codigoAutorizacao = codigoAutorizacao;
	}

	public Collection<ParcelaTEF> getConjuntoParcelas() {
		return conjuntoParcelas;
	}

	public void setConjuntoParcelas(Collection<ParcelaTEF> conjuntoParcelas) {
		this.conjuntoParcelas = conjuntoParcelas;
	}

	public String getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public Date getDataCartaoPreDatado() {
		return dataCartaoPreDatado;
	}

	public void setDataCartaoPreDatado(Date dataCartaoPreDatado) {
		this.dataCartaoPreDatado = dataCartaoPreDatado;
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

	public Date getDataHoraTransacaoCancelada() {
		return dataHoraTransacaoCancelada;
	}

	public void setDataHoraTransacaoCancelada(Date dataHoraTransacaoCancelada) {
		this.dataHoraTransacaoCancelada = dataHoraTransacaoCancelada;
	}

	public Date getDataHoraTransacaoHost() {
		return dataHoraTransacaoHost;
	}

	public void setDataHoraTransacaoHost(Date dataHoraTransacaoHost) {
		this.dataHoraTransacaoHost = dataHoraTransacaoHost;
	}

	public Date getDataHoraTransacaoLocal() {
		return dataHoraTransacaoLocal;
	}

	public void setDataHoraTransacaoLocal(Date dataHoraTransacaoLocal) {
		this.dataHoraTransacaoLocal = dataHoraTransacaoLocal;
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

	public long getDocumentoPessoa() {
		return documentoPessoa;
	}

	public void setDocumentoPessoa(long documentoPessoa) {
		this.documentoPessoa = documentoPessoa;
	}

	public long getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(long identificacao) {
		this.identificacao = identificacao;
	}

	public String[] getLinhasComprovantePrincipal() {
		return linhasComprovantePrincipal;
	}

	public void setLinhasComprovantePrincipal(String[] linhasComprovantePrincipal) {
		this.linhasComprovantePrincipal = linhasComprovantePrincipal;
	}

	public byte getMoeda() {
		return moeda;
	}

	public void setMoeda(byte moeda) {
		this.moeda = moeda;
	}

	public String getNomeAutorizadora() {
		return nomeAutorizadora;
	}

	public void setNomeAutorizadora(String nomeAutorizadora) {
		this.nomeAutorizadora = nomeAutorizadora;
	}

	public String getNomeRede() {
		return nomeRede;
	}

	public void setNomeRede(String nomeRede) {
		this.nomeRede = nomeRede;
	}

	public long getNsuTEF() {
		return nsuTEF;
	}

	public void setNsuTEF(long nsuTEF) {
		this.nsuTEF = nsuTEF;
	}

	public long getNsuTransacaoCancelada() {
		return nsuTransacaoCancelada;
	}

	public void setNsuTransacaoCancelada(long nsuTransacaoCancelada) {
		this.nsuTransacaoCancelada = nsuTransacaoCancelada;
	}

	public String getNumeroAutenticacaoCheque() {
		return numeroAutenticacaoCheque;
	}

	public void setNumeroAutenticacaoCheque(String numeroAutenticacaoCheque) {
		this.numeroAutenticacaoCheque = numeroAutenticacaoCheque;
	}

	public String getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public long getNumeroCOO() {
		return numeroCOO;
	}

	public void setNumeroCOO(long numeroCOO) {
		this.numeroCOO = numeroCOO;
	}

	public long getNumeroLoteTransacao() {
		return numeroLoteTransacao;
	}

	public void setNumeroLoteTransacao(long numeroLoteTransacao) {
		this.numeroLoteTransacao = numeroLoteTransacao;
	}

	public int getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(int quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

	public String getStatusTransacao() {
		return statusTransacao;
	}

	public void setStatusTransacao(String statusTransacao) {
		this.statusTransacao = statusTransacao;
	}

	public String getTextoEspecialCliente() {
		return textoEspecialCliente;
	}

	public void setTextoEspecialCliente(String textoEspecialCliente) {
		this.textoEspecialCliente = textoEspecialCliente;
	}

	public String getTextoEspecialOperador() {
		return textoEspecialOperador;
	}

	public void setTextoEspecialOperador(String textoEspecialOperador) {
		this.textoEspecialOperador = textoEspecialOperador;
	}

	public byte getTipoParcelamento() {
		return tipoParcelamento;
	}

	public void setTipoParcelamento(byte tipoParcelamento) {
		this.tipoParcelamento = tipoParcelamento;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	public int getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(int tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public BigDecimal getValorOperacao() {
		return valorOperacao;
	}

	public void setValorOperacao(BigDecimal valorOperacao) {
		this.valorOperacao = valorOperacao;
	}
	
	public ParcelaTEF newParcelaTEF(){
		return new ParcelaTEF();
	}
		
	public class ParcelaTEF{
		
		public ParcelaTEF(){
			
		}
		
		private Date vencimentoParcela;
		
		private BigDecimal valorParcela;
		
		private int nsuParcela;

		public int getNsuParcela() {
			return nsuParcela;
		}

		public void setNsuParcela(int nsuParcela) {
			this.nsuParcela = nsuParcela;
		}

		public BigDecimal getValorParcela() {
			return valorParcela;
		}

		public void setValorParcela(BigDecimal valorParcela) {
			this.valorParcela = valorParcela;
		}

		public Date getVencimentoParcela() {
			return vencimentoParcela;
		}

		public void setVencimentoParcela(Date vencimentoParcela) {
			this.vencimentoParcela = vencimentoParcela;
		}
	}

	public String[] getLinhasComprovanteEstabl() {
		return linhasComprovanteEstabl;
	}

	public void setLinhasComprovanteEstabl(String[] linhasComprovanteEstabl) {
		this.linhasComprovanteEstabl = linhasComprovanteEstabl;
	}

	public String[] getLinhasCupomReduzidoCli() {
		return linhasCupomReduzidoCli;
	}

	public void setLinhasCupomReduzidoCli(String[] linhasCupomReduzidoCli) {
		this.linhasCupomReduzidoCli = linhasCupomReduzidoCli;
	}

	public String[] getLinhasComprovanteCli() {
		return linhasComprovanteCli;
	}

	public void setLinhasComprovanteCli(String[] linhasComprovanteCli) {
		this.linhasComprovanteCli = linhasComprovanteCli;
	}
}
