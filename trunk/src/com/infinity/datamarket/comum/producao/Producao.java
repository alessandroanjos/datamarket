
package com.infinity.datamarket.comum.producao;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.Persistente;

public class Producao extends Persistente{
	
	private int lote;
	private Date dataFabricacao;
	private Produto produto;
	private BigDecimal quantidade;
	private Estoque estoque;
	
	
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public Date getDataFabricacao() {
		return dataFabricacao;
	}
	public void setDataFabricacao(Date dataFabricacao) {
		this.dataFabricacao = dataFabricacao;
	}
	public int getLote() {
		return lote;
	}
	public void setLote(int lote) {
		this.lote = lote;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	
	
	
}
