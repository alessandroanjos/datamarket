package com.infinity.datamarket.comum.conta;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.util.Persistente;

public class MovimentacaoBancaria extends Persistente {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1538424218494596372L;
	  private ContaCorrente conta;
	  private String tipo;
	  private String numero;
	  private Date data;
	  private BigDecimal valor;
	  private FormaRecebimento forma;
	  
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public FormaRecebimento getForma() {
		return forma;
	}
	public void setForma(FormaRecebimento forma) {
		this.forma = forma;
	}
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public ContaCorrente getConta() {
		return conta;
	}
	public void setConta(ContaCorrente conta) {
		this.conta = conta;
	}
}
