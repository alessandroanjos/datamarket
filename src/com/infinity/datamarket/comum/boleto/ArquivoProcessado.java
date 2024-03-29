package com.infinity.datamarket.comum.boleto;

import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.banco.Banco;
import com.infinity.datamarket.comum.financeiro.BaixaLancamento;
import com.infinity.datamarket.comum.util.Persistente;

public class ArquivoProcessado extends Persistente{
	
	private static final long serialVersionUID = 4520193212224231246L;

	private String nomeArquivo;
	private Date dataHoraInicial;
	private Date dataHoraFinal;
	private Date dataProcessamento = new Date();
	private Banco banco;
	
	private Collection<PagamentoBoleto> pagamentosBoleto;
	
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	public Date getDataHoraFinal() {
		return dataHoraFinal;
	}
	public void setDataHoraFinal(Date dataHoraFinal) {
		this.dataHoraFinal = dataHoraFinal;
	}
	public Date getDataHoraInicial() {
		return dataHoraInicial;
	}
	public void setDataHoraInicial(Date dataHoraInicial) {
		this.dataHoraInicial = dataHoraInicial;
	}
	public Date getDataProcessamento() {
		return dataProcessamento;
	}
	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public Collection<PagamentoBoleto> getPagamentosBoleto() {
		return pagamentosBoleto;
	}
	public void setPagamentosBoleto(Collection<PagamentoBoleto> pagamentosBoleto) {
		this.pagamentosBoleto = pagamentosBoleto;
	}
}