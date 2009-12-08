package com.infinity.datamarket.comum.operacao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.usuario.Vendedor;

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
	private Vendedor usuarioVendedor;
	
	private String usaEnderecoEntrega;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	private String pontoReferencia;

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public String getUsaEnderecoEntrega() {
		return usaEnderecoEntrega;
	}

	public void setUsaEnderecoEntrega(String usaEnderecoEntrega) {
		this.usaEnderecoEntrega = usaEnderecoEntrega;
	}

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

	public Vendedor getUsuarioVendedor() {
		return usuarioVendedor;
	}

	public void setUsuarioVendedor(Vendedor usuarioVendedor) {
		this.usuarioVendedor = usuarioVendedor;
	}
	
	public String toString(){
		return this.getPk().getId()+"";
	}

}
