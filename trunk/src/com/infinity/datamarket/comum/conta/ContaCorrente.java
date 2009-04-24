package com.infinity.datamarket.comum.conta;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.banco.Banco;
import com.infinity.datamarket.comum.util.Persistente;

public class ContaCorrente extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6474664301311452427L;
	/**
	 * 
	 */

	private String idAgencia;
	private String numero;
	private String nome;
	private BigDecimal saldo;
	private String situacao;
	private Banco banco;
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	public String getIdAgencia() {
		return idAgencia;
	}
	public void setIdAgencia(String idAgencia) {
		this.idAgencia = idAgencia;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
