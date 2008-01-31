package com.infinity.datamarket.comum.transacao;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProdutoItemRegistrado implements Serializable{
	private EventoTransacaoPK pk;
	private int idProduto;
	private String codigoExterno;
	private String decricaoCompleta;
	private BigDecimal precoPadrao;
	private BigDecimal precoPraticado;

	public ProdutoItemRegistrado(){

	}

	public ProdutoItemRegistrado(EventoTransacaoPK pk, int idProduto, String codigoExterno, String descricaoCompleta, BigDecimal precoPadrao, BigDecimal precoPraticado){
		this.pk = pk;
		this.idProduto = idProduto;
		this.codigoExterno = codigoExterno;
		this.decricaoCompleta = descricaoCompleta;
		this.precoPadrao = precoPadrao;
		this.precoPraticado = precoPraticado;
	}

	public String getCodigoExterno() {
		return codigoExterno;
	}
	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}
	public String getDecricaoCompleta() {
		return decricaoCompleta;
	}
	public void setDecricaoCompleta(String decricaoCompleta) {
		this.decricaoCompleta = decricaoCompleta;
	}
	public int getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	public EventoTransacaoPK getPk() {
		return pk;
	}
	public void setPk(EventoTransacaoPK pk) {
		this.pk = pk;
	}
	public BigDecimal getPrecoPadrao() {
		return precoPadrao;
	}
	public void setPrecoPadrao(BigDecimal precoPadrao) {
		this.precoPadrao = precoPadrao;
	}
	public BigDecimal getPrecoPraticado() {
		return precoPraticado;
	}
	public void setPrecoPraticado(BigDecimal precoPraticado) {
		this.precoPraticado = precoPraticado;
	}
}
