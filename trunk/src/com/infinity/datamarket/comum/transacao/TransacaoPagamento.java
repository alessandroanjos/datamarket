package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;

public class TransacaoPagamento extends Transacao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7371750038229819699L;
	
	private String codigoUsuarioOperador;
	private Date dataHoraInicio;
	private Date dataHoraFim;
	private BigDecimal valor;
	private BigDecimal desconto;
	private BigDecimal acressimo;
	private String situacao;
	
	public static final String ATIVO = "A";
	public static final String CANCELADO = "C";

	
	private Collection eventosTransacao;	

	
	public Collection getEventosTransacao() {
		return eventosTransacao;
	}

	public void setEventosTransacao(Collection eventosTransacao) {
		this.eventosTransacao = eventosTransacao;
	}

	public TransacaoPagamento(TransacaoPK pk, int tipoTransacao, String codigoUsuarioOperador, Date dataHoraInicio, Date dataHoraFim, BigDecimal valor, BigDecimal desconto, BigDecimal acressimo, String situacao){ 
		super(pk,tipoTransacao);
		this.codigoUsuarioOperador = codigoUsuarioOperador;
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.valor = valor;
		this.desconto = desconto;
		this.acressimo = acressimo;
		this.situacao = situacao;
	}

	public TransacaoPagamento() {

	}

	public BigDecimal getAcressimo() {
		return acressimo;
	}

	public void setAcressimo(BigDecimal acressimo) {
		this.acressimo = acressimo;
	}

	public String getCodigoUsuarioOperador() {
		return codigoUsuarioOperador;
	}

	public void setCodigoUsuarioOperador(String codigoUsuarioOperador) {
		this.codigoUsuarioOperador = codigoUsuarioOperador;
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

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	
}