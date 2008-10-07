package com.infinity.datamarket.comum.operacao;

import java.io.Serializable;
import java.util.Date;

public class Operacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6759506297676784374L;

	private OperacaoPK pk;
	private Date data;
	private int tipo;
	private int status;
	
	public Operacao(OperacaoPK pk, Date data, int tipo, int status){
		this.pk = pk;
		this.data = data;
		this.tipo = tipo;
		this.status = status;
	}
	
	public Operacao(){
		
	}
	
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public OperacaoPK getPk() {
		return pk;
	}
	public void setPk(OperacaoPK pk) {
		this.pk = pk;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
}
