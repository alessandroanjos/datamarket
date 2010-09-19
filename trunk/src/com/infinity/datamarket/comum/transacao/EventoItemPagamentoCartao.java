package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class EventoItemPagamentoCartao extends EventoItemPagamento{

	private static final long serialVersionUID = 4730700123423671453L;

	public EventoItemPagamentoCartao (EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento,int codigoForma, int codigoPlano, String formaImpressora, BigDecimal valorBruto, BigDecimal valorDesconto, BigDecimal valorAcrescimo){
		super(pk,tipoEvento,dataHoraEvento,codigoForma,codigoPlano,formaImpressora,valorBruto,valorDesconto,valorAcrescimo);
	}
	
	public EventoItemPagamentoCartao (){
		
	}
	private Collection parcelas;
	
	public Collection getParcelas() {
		return parcelas;
	}

	public void setParcelas(Collection parcelas) {
		this.parcelas = parcelas;
	}


	private java.lang.Long identificacao;
	private java.lang.Long coo;
	private java.math.BigDecimal valor;
	private java.util.Date dataHoraTransacao;
	private java.util.Date dataHoraTransacaoLocal;
	private java.util.Date dataHoraTransacaoHost;
	private java.util.Date dataCartaoPreDatado;
	private java.lang.Short moeda;
	private java.lang.Short status;
	private String rede;
	private java.lang.Short tipoTransacao;
	private java.lang.Long numeroTransacaoNSU;
	private java.lang.Long codigoAutorizacao;
	private java.lang.Long numeroLoteTransacao;
	private java.lang.Short tipoParcelamento;
	private java.lang.Integer quantidadeParcelamento;
	private String chaveFinalizacao;
	private String nomeAdministrador;

	public java.lang.Long getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(java.lang.Long identificacao) {
		this.identificacao = identificacao;
	}

	public java.lang.Long getCoo() {
		return this.coo;
	}

	public void setCoo(java.lang.Long coo_) {
		this.coo = coo_;
	}

	public java.math.BigDecimal getValor() {
		return valor;
	}

	public void setValor(java.math.BigDecimal valor) {
		this.valor = valor;
	}

	public java.util.Date getDataHoraTransacao() {
		return dataHoraTransacao;
	}

	public void setDataHoraTransacao(java.util.Date dataHoraTransacao) {
		this.dataHoraTransacao = dataHoraTransacao;
	}

	public java.util.Date getDataHoraTransacaoLocal() {
		return dataHoraTransacaoLocal;
	}

	public void setDataHoraTransacaoLocal(java.util.Date dataHoraTransacaoLocal) {
		this.dataHoraTransacaoLocal = dataHoraTransacaoLocal;
	}

	public java.util.Date getDataHoraTransacaoHost() {
		return dataHoraTransacaoHost;
	}

	public void setDataHoraTransacaoHost(java.util.Date dataHoraTransacaoHost) {
		this.dataHoraTransacaoHost = dataHoraTransacaoHost;
	}

	public java.util.Date getDataCartaoPreDatado() {
		return dataCartaoPreDatado;
	}

	public void setDataCartaoPreDatado(java.util.Date dataCartaoPreDatado) {
		this.dataCartaoPreDatado = dataCartaoPreDatado;
	}

	public java.lang.Short getMoeda() {
		return moeda;
	}

	public void setMoeda(java.lang.Short moeda) {
		this.moeda = moeda;
	}

	public java.lang.Short getStatus() {
		return status;
	}

	public void setStatus(java.lang.Short status) {
		this.status = status;
	}

	public String getRede() {
		return rede;
	}

	public void setRede(String rede) {
		this.rede = rede;
	}

	public java.lang.Short getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(java.lang.Short tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public java.lang.Long getNumeroTransacaoNSU() {
		return numeroTransacaoNSU;
	}

	public void setNumeroTransacaoNSU(java.lang.Long numeroTransacaoNSU) {
		this.numeroTransacaoNSU = numeroTransacaoNSU;
	}

	public java.lang.Long getCodigoAutorizacao() {
		return codigoAutorizacao;
	}

	public void setCodigoAutorizacao(java.lang.Long codigoAutorizacao) {
		this.codigoAutorizacao = codigoAutorizacao;
	}

	public java.lang.Long getNumeroLoteTransacao() {
		return numeroLoteTransacao;
	}

	public void setNumeroLoteTransacao(java.lang.Long numeroLoteTransacao) {
		this.numeroLoteTransacao = numeroLoteTransacao;
	}

	public java.lang.Short getTipoParcelamento() {
		return tipoParcelamento;
	}

	public void setTipoParcelamento(java.lang.Short tipoParcelamento) {
		this.tipoParcelamento = tipoParcelamento;
	}

	public java.lang.Integer getQuantidadeParcelamento() {
		return quantidadeParcelamento;
	}

	public void setQuantidadeParcelamento(java.lang.Integer quantidadeParcelamento) {
		this.quantidadeParcelamento = quantidadeParcelamento;
	}

	public String getChaveFinalizacao() {
		return chaveFinalizacao;
	}

	public void setChaveFinalizacao(String chaveFinalizacao) {
		this.chaveFinalizacao = chaveFinalizacao;
	}

	public String getNomeAdministrador() {
		return nomeAdministrador;
	}

	public void setNomeAdministrador(String nomeAdministrador) {
		this.nomeAdministrador = nomeAdministrador;
	}
}