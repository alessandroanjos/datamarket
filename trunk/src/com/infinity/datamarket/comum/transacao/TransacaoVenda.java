package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;

public class TransacaoVenda extends Transacao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7371750038229819699L;
	public static final String ATIVO = "A";
	public static final String CANCELADO = "C";

	
	private String codigoUsuarioVendedor;
	private String codigoUsuarioOperador;
	private Date dataHoraInicio;
	private Date dataHoraFim;
	private BigDecimal valorTroco;
	private String numeroCupom;
	private BigDecimal valorCupom;
	private BigDecimal descontoCupom;
	private BigDecimal comissaoUsuarioVendedor;
	private String situacao;
	private Collection eventosTransacao;
	private FormaRecebimento formaTroco;

	public Collection getEventosTransacao() {
		return eventosTransacao;
	}

	public void setEventosTransacao(Collection eventosTransacao) {
		this.eventosTransacao = eventosTransacao;
	}

	public TransacaoVenda(TransacaoPK pk, int tipoTransacao, String codigoUsuarioVendedor, String codigoUsuarioOperador, Date dataHoraInicio, Date dataHoraFim, BigDecimal valorTroco, String numeroCupom, BigDecimal valorCupom, BigDecimal descontoCupom, String situacao) {
		super(pk,tipoTransacao);
		this.codigoUsuarioVendedor = codigoUsuarioVendedor;
		this.codigoUsuarioOperador = codigoUsuarioOperador;
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.valorTroco = valorTroco;
		this.numeroCupom = numeroCupom;
		this.valorCupom = valorCupom;
		this.descontoCupom = descontoCupom;
		this.situacao = situacao;
	}

	public TransacaoVenda() {

	}

	public String getCodigoUsuarioOperador() {
		return codigoUsuarioOperador;
	}

	public void setCodigoUsuarioOperador(String codigoUsuarioOperador) {
		this.codigoUsuarioOperador = codigoUsuarioOperador;
	}

	public String getCodigoUsuarioVendedor() {
		return codigoUsuarioVendedor;
	}

	public void setCodigoUsuarioVendedor(String codigoUsuarioVendedor) {
		this.codigoUsuarioVendedor = codigoUsuarioVendedor;
	}

	public Date getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public String getNumeroCupom() {
		return numeroCupom;
	}

	public void setNumeroCupom(String numeroCupom) {
		this.numeroCupom = numeroCupom;
	}

	public BigDecimal getValorCupom() {
		return valorCupom;
	}

	public void setValorCupom(BigDecimal valorCupom) {
		this.valorCupom = valorCupom;
	}

	public BigDecimal getValorTroco() {
		return valorTroco;
	}

	public void setValorTroco(BigDecimal valorTroco) {
		this.valorTroco = valorTroco;
	}

	public BigDecimal getDescontoCupom() {
		return descontoCupom;
	}

	public void setDescontoCupom(BigDecimal descontoCupom) {
		this.descontoCupom = descontoCupom;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public BigDecimal getComissaoUsuarioVendedor() {
		return comissaoUsuarioVendedor;
	}

	public void setComissaoUsuarioVendedor(BigDecimal comissaoUsuarioVendedor) {
		this.comissaoUsuarioVendedor = comissaoUsuarioVendedor;
	}

	public FormaRecebimento getFormaTroco() {
		return formaTroco;
	}

	public void setFormaTroco(FormaRecebimento formaTroco) {
		this.formaTroco = formaTroco;
	}
}
