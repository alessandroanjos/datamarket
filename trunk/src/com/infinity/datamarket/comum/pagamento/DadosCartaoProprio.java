package com.infinity.datamarket.comum.pagamento;

import java.io.Serializable;

public class DadosCartaoProprio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4718494944907148936L;

	private String autorizacao;
	private String CPFCNPJ;

	
	public String getCPFCNPJ() {
		return CPFCNPJ;
	}
	public void setCPFCNPJ(String cpfcnpj) {
		CPFCNPJ = cpfcnpj;
	}
	public String getAutorizacao() {
		return autorizacao;
	}
	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}
	
}
