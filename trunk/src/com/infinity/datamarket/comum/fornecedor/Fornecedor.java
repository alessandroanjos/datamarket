package com.infinity.datamarket.comum.fornecedor;

import com.infinity.datamarket.comum.util.Persistente;

public class Fornecedor extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7916843563910560609L;
	
	private String cpfcnpj;
	private String rasaoSocial;
	private String nomeFantazia;
	private String inscricaoEstadual;
	private String inscricaoMunincipal;
	private String logradouro;
	private String numero;
	private String cidade;
	private String estado;
	private String pais;
	private String telefone;
	private String contato;
	private String telefoneContato;
	private String cep;
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	public String getCpfcnpj() {
		return cpfcnpj;
	}
	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
	public String getInscricaoMunincipal() {
		return inscricaoMunincipal;
	}
	public void setInscricaoMunincipal(String inscricaoMunincipal) {
		this.inscricaoMunincipal = inscricaoMunincipal;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNomeFantazia() {
		return nomeFantazia;
	}
	public void setNomeFantazia(String nomeFantazia) {
		this.nomeFantazia = nomeFantazia;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getRasaoSocial() {
		return rasaoSocial;
	}
	public void setRasaoSocial(String rasaoSocial) {
		this.rasaoSocial = rasaoSocial;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getTelefoneContato() {
		return telefoneContato;
	}
	public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato = telefoneContato;
	}
	
}
