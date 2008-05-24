package com.infinity.datamarket.comum.pagamento;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.util.Constantes;

public class DadosChequePredatado extends DadosCheque implements Comparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2833652866041770343L;

	private BigDecimal valor;
	private Date data;
	private String entrada = Constantes.NAO;
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getEntrada() {
		return entrada;
	}
	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public int compareTo(Object o) {
		if (o instanceof DadosChequePredatado){
			DadosChequePredatado dado = (DadosChequePredatado) o;
			if (this.data.after(dado.data)){
				return -1;
			}else{
				return 1;
			}
		}
		return 0;
	}
	
		
}
