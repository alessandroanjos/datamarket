package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProdutoEntradaProduto implements Serializable{

	public ProdutoEntradaProduto produtoEntradaProduto;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4456383943889461011L;

	private ProdutoEntradaProdutoPK pk;
	private BigDecimal precoUnitario;
	private BigDecimal quantidade;
	private BigDecimal desconto;
	private BigDecimal icms;
	private BigDecimal ipi;
	private BigDecimal total;
	private Date vencimento;
	
	
	public Date getVencimento() {
		return vencimento;
	}
	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
	public BigDecimal getDesconto() {
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	
	public BigDecimal getIcms() {
		return icms;
	}
	public void setIcms(BigDecimal icms) {
		this.icms = icms;
	}
	public BigDecimal getIpi() {
		return ipi;
	}
	public void setIpi(BigDecimal ipi) {
		this.ipi = ipi;
	}
	public ProdutoEntradaProdutoPK getPk() {
		return pk;
	}
	public void setPk(ProdutoEntradaProdutoPK pk) {
		this.pk = pk;
	}
	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}
	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public ProdutoEntradaProduto getProdutoEntradaProduto() {
		return produtoEntradaProduto;
	}
	public void setProdutoEntradaProduto(ProdutoEntradaProduto produtoEntradaProduto) {
		this.produtoEntradaProduto = produtoEntradaProduto;
	}
	
}
