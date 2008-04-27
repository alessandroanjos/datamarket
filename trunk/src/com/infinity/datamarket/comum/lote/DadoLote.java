package com.infinity.datamarket.comum.lote;

import java.io.Serializable;

public class DadoLote implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8305908432249491664L;
	
	public static final String ALTERAR = "A";
	public static final String INSERIR = "I";
	public static final String EXCLUIR = "E";
	
	private String operacao;
	
	private int lote;
	
	private Serializable dado;

	public Serializable getDado() {
		return dado;
	}

	public void setDado(Serializable dado) {
		this.dado = dado;
	}

	public int getLote() {
		return lote;
	}

	public void setLote(int lote) {
		this.lote = lote;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

}
