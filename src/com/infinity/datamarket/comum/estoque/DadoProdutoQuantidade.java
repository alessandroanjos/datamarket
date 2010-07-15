package com.infinity.datamarket.comum.estoque;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.produto.Produto;

public class DadoProdutoQuantidade {
	private Produto produto;
	private BigDecimal quantidade;
	private Date vencimento;
	
	public DadoProdutoQuantidade(Produto produto, BigDecimal quantidade, Date vencimento){
		this.produto = produto;
		this.quantidade = quantidade;
		this.vencimento = vencimento;
	}
	
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
}
