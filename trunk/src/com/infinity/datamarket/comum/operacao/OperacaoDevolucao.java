package com.infinity.datamarket.comum.operacao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.transacao.ClienteTransacao;

public class OperacaoDevolucao extends Operacao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6690526936538124591L;
	
	private BigDecimal valor;
	private String codigoUsuarioOperador;
	private ClienteTransacao cliente;
	private Collection eventosOperacao;
	
	public OperacaoDevolucao(OperacaoPK pk, Date data, int tipo, int status,BigDecimal valor, String codigoUsuarioOperador, ClienteTransacao cliente){
		super(pk,data,tipo,status);
		this.valor = valor;
		this.codigoUsuarioOperador = codigoUsuarioOperador;
		this.cliente = cliente;
	}
	
	public OperacaoDevolucao(){
		
	}
	
	public ClienteTransacao getCliente() {
		return cliente;
	}
	public void setCliente(ClienteTransacao cliente) {
		this.cliente = cliente;
	}
	public String getCodigoUsuarioOperador() {
		return codigoUsuarioOperador;
	}
	public void setCodigoUsuarioOperador(String codigoUsuarioOperador) {
		this.codigoUsuarioOperador = codigoUsuarioOperador;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Collection getEventosOperacao() {
		return eventosOperacao;
	}

	public void setEventosOperacao(Collection eventosOperacao) {
		this.eventosOperacao = eventosOperacao;
	}

}
