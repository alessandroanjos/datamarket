package com.infinity.datamarket.comum.estoque;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.util.Persistente;

public class EntradaProduto extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7938984530697816420L;
	
	private String numeroNota;
	private Date dataEmissaoNota;
	private Date dataEntrada;
	private BigDecimal frete;
	private BigDecimal icms;
	private BigDecimal ipi;
	private BigDecimal desconto;
	private BigDecimal valor;
	private BigDecimal quantidadeTotal;
	private String idFornecedor;
	private Fornecedor fornecedor;
	private Estoque estoque;
	private Collection produtosEntrada;
	
	private Date dataVencimento;	
	
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Collection getProdutosEntrada() {
		return produtosEntrada;
	}
	public void setProdutosEntrada(Collection produtosEntrada) {
		this.produtosEntrada = produtosEntrada;
	}
	public Date getDataEmissaoNota() {
		return dataEmissaoNota;
	}
	public void setDataEmissaoNota(Date dataEmissaoNota) {
		this.dataEmissaoNota = dataEmissaoNota;
	}
	public Date getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public BigDecimal getDesconto() {
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public BigDecimal getFrete() {
		return frete;
	}
	public void setFrete(BigDecimal frete) {
		this.frete = frete;
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
	public String getNumeroNota() {
		return numeroNota;
	}
	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	/**
	 * @return the idFornecedor
	 */
	public String getIdFornecedor() {
		return idFornecedor;
	}
	/**
	 * @param idFornecedor the idFornecedor to set
	 */
	public void setIdFornecedor(String idFornecedor) {
		this.idFornecedor = idFornecedor;
	}
	public BigDecimal getQuantidadeTotal() {
		return quantidadeTotal;
	}
	public void setQuantidadeTotal(BigDecimal quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
}
