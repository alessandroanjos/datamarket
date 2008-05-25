package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.util.Persistente;

public class AjusteEstoque extends Persistente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9165973049134016452L;

	private Estoque estoque;
	private Produto produto;
	private BigDecimal quantidadeAntes;
	private BigDecimal quantidadeDepois;
	private Date data;
	private int codigoUsuario;
	public int getCodigoUsuario() {
		return codigoUsuario;
	}
	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public BigDecimal getQuantidadeAntes() {
		return quantidadeAntes;
	}
	public void setQuantidadeAntes(BigDecimal quantidadeAntes) {
		this.quantidadeAntes = quantidadeAntes;
	}
	public BigDecimal getQuantidadeDepois() {
		return quantidadeDepois;
	}
	public void setQuantidadeDepois(BigDecimal quantidadeDepois) {
		this.quantidadeDepois = quantidadeDepois;
	}
	
}
