package com.infinity.datamarket.comum.pagamento;

import java.io.Serializable;
import java.math.BigDecimal;

public class ParcelaPlanoPagamentoChequePredatado implements Serializable, Comparable<ParcelaPlanoPagamentoChequePredatado>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6237638829985692672L;
	private ParcelaPlanoPagamentoChequePredatadoPK pk;
	
	
	private BigDecimal percentagemParcela;
	private int quantidadeDias;
	private String dataProgramada;
	
	public BigDecimal getPercentagemParcela() {
		return percentagemParcela;
	}
	public void setPercentagemParcela(BigDecimal percentagemParcela) {
		this.percentagemParcela = percentagemParcela;
	}
	public ParcelaPlanoPagamentoChequePredatadoPK getPk() {
		return pk;
	}
	public void setPk(ParcelaPlanoPagamentoChequePredatadoPK pk) {
		this.pk = pk;
	}
	public int getQuantidadeDias() {
		return quantidadeDias;
	}
	public void setQuantidadeDias(int quantidadeDias) {
		this.quantidadeDias = quantidadeDias;
	}
	
	public int compareTo(ParcelaPlanoPagamentoChequePredatado obj) {
		int ret = 0;
		if(this.getPk().getNumeroEntrada() > obj.getPk().getNumeroEntrada()){
			ret = 1;
		}else if(this.getPk().getNumeroEntrada() < obj.getPk().getNumeroEntrada()){
			ret =  -1;
		}else {
			ret = 0;	
		}		
		return ret;
	}
	/**
	 * @return the dataProgramada
	 */
	public String getDataProgramada() {
		return dataProgramada;
	}
	/**
	 * @param dataProgramada the dataProgramada to set
	 */
	public void setDataProgramada(String dataProgramada) {
		this.dataProgramada = dataProgramada;
	}
}
