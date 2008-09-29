package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;

public class TransacaoPagamentoCartaoProprio extends TransacaoPagamento{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7371750038229819699L;
	
	private String CPFCNPJ;
	
	private String nome;
	
	
	public TransacaoPagamentoCartaoProprio(){
		
	}
	
	public TransacaoPagamentoCartaoProprio(TransacaoPK pk, int tipoTransacao, String codigoUsuarioOperador, Date dataHoraInicio, Date dataHoraFim, BigDecimal valor, BigDecimal desconto, BigDecimal acressimo, String situacao, String CPFCNPJ, String nome){ 
		super(pk, tipoTransacao, codigoUsuarioOperador, dataHoraInicio, dataHoraFim, valor, desconto, acressimo, situacao);
		this.setCPFCNPJ(CPFCNPJ);
		this.setNome(nome);
	}


	public String getCPFCNPJ() {
		return CPFCNPJ;
	}


	public void setCPFCNPJ(String cpfcnpj) {
		CPFCNPJ = cpfcnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
