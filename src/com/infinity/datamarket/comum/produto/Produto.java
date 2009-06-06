package com.infinity.datamarket.comum.produto;

import java.math.BigDecimal;
import java.util.Collection;

import com.infinity.datamarket.comum.fabricante.Fabricante;
import com.infinity.datamarket.comum.util.Persistente;

public class Produto extends Persistente{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4520193183852821246L;
	
	
	private String codigoExterno;
	private String codigoAutomacao;
	private String descricaoCompleta;
	private String descricaoCompacta;
	private BigDecimal precoPadrao;
	private BigDecimal precoPromocional;
	private BigDecimal precoCompra;
	private TipoProduto tipo;
	private Unidade unidade;
	private Imposto imposto;
	private GrupoProduto grupo;
	private Fabricante fabricante;
	private Collection lojas;
	
	
	public Imposto getImposto() {
		return imposto;
	}
	public void setImposto(Imposto imposto) {
		this.imposto = imposto;
	}
	public String getCodigoExterno() {
		return codigoExterno;
	}
	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}
	public String getDescricaoCompacta() {
		return descricaoCompacta;
	}
	public void setDescricaoCompacta(String descricaoCompacta) {
		this.descricaoCompacta = descricaoCompacta;
	}
	public String getDescricaoCompleta() {
		return descricaoCompleta;
	}
	public void setDescricaoCompleta(String descricaoCompleta) {
		this.descricaoCompleta = descricaoCompleta;
	}
	public BigDecimal getPrecoPadrao() {
		return precoPadrao;
	}
	public void setPrecoPadrao(BigDecimal precoPadrao) {
		this.precoPadrao = precoPadrao;
	}
	public BigDecimal getPrecoPromocional() {
		return precoPromocional;
	}
	public void setPrecoPromocional(BigDecimal precoPromocional) {
		this.precoPromocional = precoPromocional;
	}
	public String toString(){
		return this.descricaoCompleta;
	}
	public TipoProduto getTipo() {
		return tipo;
	}
	public void setTipo(TipoProduto tipo) {
		this.tipo = tipo;
	}
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	public String getCodigoAutomacao() {
		return codigoAutomacao;
	}
	public void setCodigoAutomacao(String codigoAutomacao) {
		this.codigoAutomacao = codigoAutomacao;
	}
	public GrupoProduto getGrupo() {
		return grupo;
	}
	public void setGrupo(GrupoProduto grupo) {
		this.grupo = grupo;
	}
	public Collection getLojas() {
		return lojas;
	}
	public void setLojas(Collection lojas) {
		this.lojas = lojas;
	}
	/**
	 * @return the precoCompra
	 */
	public BigDecimal getPrecoCompra() {
		return precoCompra;
	}
	/**
	 * @param precoCompra the precoCompra to set
	 */
	public void setPrecoCompra(BigDecimal precoCompra) {
		this.precoCompra = precoCompra;
	}
	/**
	 * @return the fabricante
	 */
	public Fabricante getFabricante() {
		return fabricante;
	}
	/**
	 * @param fabricante the fabricante to set
	 */
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

}
