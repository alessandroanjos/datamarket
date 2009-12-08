package com.infinity.datamarket.comum.operacao;

import java.math.BigDecimal;
import java.util.Date;

public class EventoOperacaoItemRegistrado extends EventoOperacao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5992287490965394698L;
	
	private ProdutoOperacaoItemRegistrado produtoOperacaoItemRegistrado;
	private BigDecimal preco;
	private BigDecimal quantidade;
	private BigDecimal desconto;
	private BigDecimal lucro;
	
	private String acao;
	public static final String ITEM_NAO_ALTERADO = "N";
	public static final String ITEM_INSERIDO = "I";
	public static final String ITEM_EXCLUIDO = "E";
	
	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public EventoOperacaoItemRegistrado(EventoOperacaoPK pk, int tipoEvento, Date dataHoraEvento,BigDecimal preco, BigDecimal quantidade, BigDecimal desconto, BigDecimal lucro, ProdutoOperacaoItemRegistrado produtoOperacaoItemRegistrado){
		super(pk,tipoEvento,dataHoraEvento);
		this.preco = preco;
		this.quantidade = quantidade;
		this.desconto = desconto;
		this.lucro = lucro;
		this.produtoOperacaoItemRegistrado = produtoOperacaoItemRegistrado;
	}
	
	public EventoOperacaoItemRegistrado(){
		
	}
	
	
	public BigDecimal getDesconto() {
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	public BigDecimal getLucro() {
		return lucro;
	}
	public void setLucro(BigDecimal lucro) {
		this.lucro = lucro;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public ProdutoOperacaoItemRegistrado getProdutoOperacaoItemRegistrado() {
		return produtoOperacaoItemRegistrado;
	}

	public void setProdutoOperacaoItemRegistrado(
			ProdutoOperacaoItemRegistrado produtoOperacaoItemRegistrado) {
		this.produtoOperacaoItemRegistrado = produtoOperacaoItemRegistrado;
	}

}
