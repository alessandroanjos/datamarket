package com.infinity.datamarket.comum.produto;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.util.Persistente;

public class Imposto extends Persistente{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3531389031720522863L;
	private String descricao;
	private String impostoImpressora;
	private BigDecimal percentual;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	
}
