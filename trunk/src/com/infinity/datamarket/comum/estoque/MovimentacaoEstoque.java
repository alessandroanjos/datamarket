package com.infinity.datamarket.comum.estoque;

import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.util.Persistente;

public class MovimentacaoEstoque extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2975176905789035870L;

	private int codigoUsuario;
	private Date dataMovimentacao;
	private Estoque estoqueSaida;
	private Estoque estoqueEntrada;
	
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCodigoUsuario() {
		return codigoUsuario;
	}
	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	public Date getDataMovimentacao() {
		return dataMovimentacao;
	}
	public void setDataMovimentacao(Date dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}
	public Estoque getEstoqueEntrada() {
		return estoqueEntrada;
	}
	public void setEstoqueEntrada(Estoque estoqueEntrada) {
		this.estoqueEntrada = estoqueEntrada;
	}
	public Estoque getEstoqueSaida() {
		return estoqueSaida;
	}
	public void setEstoqueSaida(Estoque estoqueSaida) {
		this.estoqueSaida = estoqueSaida;
	}

	private Collection produtosMovimentacao;
	
	public Collection getProdutosMovimentacao() {
		return produtosMovimentacao;
	}
	public void setProdutosMovimentacao(Collection produtosMovimentacao) {
		this.produtosMovimentacao = produtosMovimentacao;
	}

}
