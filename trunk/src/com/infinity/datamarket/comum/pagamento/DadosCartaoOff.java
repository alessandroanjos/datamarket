package com.infinity.datamarket.comum.pagamento;

import java.io.Serializable;

public class DadosCartaoOff implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8662547788446208443L;

	private String numeroCartao;
	private int quantidadeParcelas;
	private String autorizacao;
	private String codigoAutorizadora;
	
	public String getAutorizacao() {
		return autorizacao;
	}
	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}
	public String getCodigoAutorizadora() {
		return codigoAutorizadora;
	}
	public void setCodigoAutorizadora(String codigoAutorizadora) {
		this.codigoAutorizadora = codigoAutorizadora;
	}
	public String getNumeroCartao() {
		return numeroCartao;
	}
	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
	public int getQuantidadeParcelas() {
		return quantidadeParcelas;
	}
	public void setQuantidadeParcelas(int quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}
	
}
