package com.infinity.datamarket.comum.transacao;

import java.io.Serializable;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.util.Constantes;

public class Transacao implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3194381389822018403L;
	private TransacaoPK pk;
	private String status;
	private Cliente cliente;
	private int tipoTransacao = Constantes.NUMERO_NAO_INFORMADO;
	
	public static final String PROCESSADO = "PR";
	public static final String NAO_PROCESSADO = "NP";
	
	public boolean equals(Object obj){
		if (obj instanceof Transacao){
			Transacao t = (Transacao) obj;
			return (this.pk.equals(t.pk) && this.tipoTransacao == t.tipoTransacao);
		}
		return false;

	}

	public Transacao(){

	}

	public Transacao(TransacaoPK pk, int tipoTransacao){
		this.pk = pk;
		this.tipoTransacao = tipoTransacao;
		this.status = NAO_PROCESSADO;
	}

	public int getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(int tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public TransacaoPK getPk() {
		return pk;
	}

	public void setPk(TransacaoPK pk) {
		this.pk = pk;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
