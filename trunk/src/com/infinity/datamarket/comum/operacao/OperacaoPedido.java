package com.infinity.datamarket.comum.operacao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;

public class OperacaoPedido extends Operacao{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8976638297940671289L;
	
	private BigDecimal valor;
	private BigDecimal desconto;
	private String codigoUsuarioOperador;
	private ClienteTransacao cliente;
	private Collection eventosOperacao;
	private String codigoUsuarioVendedor;
	private TransacaoVenda transacaoVenda;
	
	

	public OperacaoPedido(OperacaoPK pk, Date data, int tipo, int status,BigDecimal valor, String codigoUsuarioOperador, ClienteTransacao cliente){
		super(pk,data,tipo,status);
		this.valor = valor;
		this.codigoUsuarioOperador = codigoUsuarioOperador;
		this.cliente = cliente;
	}
	
	public OperacaoPedido(){
		
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

	public String getCodigoUsuarioVendedor() {
		return codigoUsuarioVendedor;
	}

	public void setCodigoUsuarioVendedor(String codigoUsuarioVendedor) {
		this.codigoUsuarioVendedor = codigoUsuarioVendedor;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public TransacaoVenda getTransacaoVenda() {
		return transacaoVenda;
	}

	public void setTransacaoVenda(TransacaoVenda transacaoVenda) {
		this.transacaoVenda = transacaoVenda;
	}

}
