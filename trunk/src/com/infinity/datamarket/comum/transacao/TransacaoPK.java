package com.infinity.datamarket.comum.transacao;

import java.io.Serializable;
import java.util.Date;

import com.infinity.datamarket.comum.util.Constantes;

public class TransacaoPK implements Serializable{
	private int loja = Constantes.NUMERO_NAO_INFORMADO;
	private int componente = Constantes.NUMERO_NAO_INFORMADO;
	private int numeroTransacao = Constantes.NUMERO_NAO_INFORMADO;
	private Date dataTransacao;
	public TransacaoPK(int loja, int componente, int numeroTransacao, Date dataTransacao) {
		this.loja = loja;
		this.componente = componente;
		this.numeroTransacao = numeroTransacao;
		this.dataTransacao = dataTransacao;
	}

	public TransacaoPK(){

	}

	public int getComponente() {
		return componente;
	}
	public void setComponente(int componente) {
		this.componente = componente;
	}
	public Date getDataTransacao() {
		return dataTransacao;
	}
	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	public int getLoja() {
		return loja;
	}
	public void setLoja(int loja) {
		this.loja = loja;
	}
	public int getNumeroTransacao() {
		return numeroTransacao;
	}
	public void setNumeroTransacao(int numeroTransacao) {
		this.numeroTransacao = numeroTransacao;
	}
	public boolean equals(Object obj){
		if (obj instanceof TransacaoPK){
			TransacaoPK t = (TransacaoPK) obj;
			return (this.loja == t.loja && this.componente == t.componente
						&& this.numeroTransacao == t.numeroTransacao
						&& this.dataTransacao == t.dataTransacao);
		}
		return false;

	}
}
