
package com.infinity.datamarket.comum.producao;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.Persistente;

public class Producao extends Persistente{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -381923958554940125L;
	private int lote;
	private Date dataFabricacao;
	private Produto produto;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private Estoque estoque;
	private BigDecimal precoVenda;
	private BigDecimal precoVendaAnterior;
	private BigDecimal markUp;
	private Date vencimento;
	
	
	public Date getVencimento() {
		return vencimento;
	}
	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
	public BigDecimal getMarkUp() {
		return markUp;
	}
	public void setMarkUp(BigDecimal markUp) {
		this.markUp = markUp;
	}
	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}
	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}
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
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public BigDecimal getPrecoVendaAnterior() {
		return precoVendaAnterior;
	}
	public void setPrecoVendaAnterior(BigDecimal precoVendaAnterior) {
		this.precoVendaAnterior = precoVendaAnterior;
	}
	
	
	
}
