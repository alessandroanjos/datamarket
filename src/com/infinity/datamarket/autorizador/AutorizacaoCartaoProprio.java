package com.infinity.datamarket.autorizador;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.util.Persistente;

public class AutorizacaoCartaoProprio extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5749542027431481751L;
	
	public static String STATUS_PENDENTE = "P";
	public static String STATUS_CONCLUIDO = "C";
	public static String STATUS_DESFEITO = "D";
	
	private Date data;
	private BigDecimal valor;
	private Cliente cliente;
	private String status;
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
