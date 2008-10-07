package com.infinity.datamarket.comum.operacao;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProdutoOperacaoItemRegistrado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3587927369616239870L;
	
	private EventoOperacaoPK pk;
	private int idProduto;
	private String codigoExterno;
	private String descricaoCompleta;
	private BigDecimal precoPadrao;
	private BigDecimal precoPraticado;
	private String impostoImpressora;
	private BigDecimal percentual;
	
	public ProdutoOperacaoItemRegistrado(EventoOperacaoPK pk, int idProduto, String codigoExterno, String descricaoCompleta, BigDecimal precoPadrao, BigDecimal precoPraticado, String impostoImpressora, BigDecimal percentual){
		this.pk = pk;
		this.idProduto = idProduto;
		this.codigoExterno = codigoExterno;
		this.descricaoCompleta = descricaoCompleta;
		this.precoPadrao = precoPadrao;
		this.precoPraticado = precoPraticado;
		this.impostoImpressora = impostoImpressora;
		this.percentual = percentual;
	}
	
	public ProdutoOperacaoItemRegistrado(){
	}
	
	
	public String getCodigoExterno() {
		return codigoExterno;
	}
	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}
	public String getDescricaoCompleta() {
		return descricaoCompleta;
	}
	public void setDescricaoCompleta(String descricaoCompleta) {
		this.descricaoCompleta = descricaoCompleta;
	}
	public int getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	public String getImpostoImpressora() {
		return impostoImpressora;
	}
	public void setImpostoImpressora(String impostoImpressora) {
		this.impostoImpressora = impostoImpressora;
	}
	public BigDecimal getPercentual() {
		return percentual;
	}
	public void setPercentual(BigDecimal percentual) {
		this.percentual = percentual;
	}
	public EventoOperacaoPK getPk() {
		return pk;
	}
	public void setPk(EventoOperacaoPK pk) {
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
