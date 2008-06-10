package com.infinity.datamarket.autorizador;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DadosAutorizacaoCartaoProprio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4006620586425641199L;

	
	private String autrizacao;
	private String loja;
	private String componente;
	private Date data;
	private BigDecimal valor;
	private String nome;

	public String getComponente() {
		return componente;
	}
	public void setComponente(String componente) {
		this.componente = componente;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getLoja() {
		return loja;
	}
	public void setLoja(String loja) {
		this.loja = loja;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getAutrizacao() {
		return autrizacao;
	}
	public void setAutrizacao(String autrizacao) {
		this.autrizacao = autrizacao;
	}

}
