package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Date;

public class EventoItemRegistrado extends EventoTransacao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4762680977171871132L;
	public static final String ATIVO = "A";
	public static final String CANCELADO = "C";

	private BigDecimal quantidade;
	private BigDecimal desconto;
	private BigDecimal preco;
	private String situacao = ATIVO;
	private ProdutoItemRegistrado produtoItemRegistrado;
	//atributo que indica se o produto do evento item registrado foi incluído ("I"), nao alterado ("N") ou excluído ("E")
	private String acao;
	public static final String ITEM_NAO_ALTERADO = "N";
	public static final String ITEM_INSERIDO = "I";
	public static final String ITEM_EXCLUIDO = "E";

	public EventoItemRegistrado(){

	}

	public EventoItemRegistrado(EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento, BigDecimal quantidade, BigDecimal desconto, BigDecimal preco, ProdutoItemRegistrado produtoItemRegistrado){
		super(pk,tipoEvento,dataHoraEvento);
		this.quantidade = quantidade;
		this.desconto = desconto;
		this.preco = preco;
		this.produtoItemRegistrado = produtoItemRegistrado;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}
	public ProdutoItemRegistrado getProdutoItemRegistrado() {
		return produtoItemRegistrado;
	}
	public void setProdutoItemRegistrado(ProdutoItemRegistrado produtoItemRegistrado) {
		this.produtoItemRegistrado = produtoItemRegistrado;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
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

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}
}
