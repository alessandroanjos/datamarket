package com.infinity.datamarket.comum.totalizadores;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.util.Persistente;

public class TotalizadorNaoFiscal extends Persistente{

	private BigDecimal valor;
	private int contador;
	private String impressora;


	public String getImpressora() {
		return impressora;
	}
	public void setImpressora(String impressora) {
		this.impressora = impressora;
	}
	public int getContador() {
		return contador;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
