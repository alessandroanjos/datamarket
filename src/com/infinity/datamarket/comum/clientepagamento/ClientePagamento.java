package com.infinity.datamarket.comum.clientepagamento;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.Persistente;

public class ClientePagamento extends Persistente {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8421411464115163116L;

	private Cliente cliente;
	private Date dataPagamento;
	private FormaRecebimento formaRecebimento;
	private BigDecimal valorPagamento;
	
	private Usuario usuario;
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public FormaRecebimento getFormaRecebimento() {
		return formaRecebimento;
	}
	public void setFormaRecebimento(FormaRecebimento formaRecebimento) {
		this.formaRecebimento = formaRecebimento;
	}
	public BigDecimal getValorPagamento() {
		return valorPagamento;
	}
	public void setValorPagamento(BigDecimal valorPagamento) {
		this.valorPagamento = valorPagamento;
	}	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
