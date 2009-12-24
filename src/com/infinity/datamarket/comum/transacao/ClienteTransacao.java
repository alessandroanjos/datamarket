package com.infinity.datamarket.comum.transacao;

import java.io.Serializable;
import java.util.Date;

public class ClienteTransacao implements Serializable {
	
	public static final String PESSOA_FISICA = "F";
	public static final String PESSOA_JURIDICA = "J";
	/**
	 * 
	 */
	private static final long serialVersionUID = 139511304874487777L;

	private String nomeCliente;
	private String tipoPessoa;
	private String cpfCnpj;
	private String razaoSocial;
	
	private String inscricaoEstadual;
	private String inscricaoMunicipal;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	private String fone;
	private String email;
	private String celular;
	private String pessoaContato;
	private String referenciaBancaria;
	private Date dataCadastro;
	
	private String enderecoConcatenado;
	
	public String getEnderecoConcatenado() {
		StringBuffer endTmp = new StringBuffer();
		if(this.getLogradouro() != null){
			endTmp.append(this.getLogradouro().trim() + "|");	
		}else{
			endTmp.append(" |");
		}
		
		endTmp.append(this.getNumero().trim() + "|");
		endTmp.append(this.getComplemento().trim() + "|");
		endTmp.append(this.getBairro().trim() + "|");
		endTmp.append(this.getCidade().trim() + "|");
		endTmp.append(this.getEstado().trim() + "|");
		endTmp.append(this.getCep().trim());
		return endTmp.toString();
	}
	public void setEnderecoConcatenado(String enderecoConcatenado) {
		this.enderecoConcatenado = enderecoConcatenado;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
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
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}
	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getPessoaContato() {
		return pessoaContato;
	}
	public void setPessoaContato(String pessoaContato) {
		this.pessoaContato = pessoaContato;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getReferenciaBancaria() {
		return referenciaBancaria;
	}
	public void setReferenciaBancaria(String referenciaBancaria) {
		this.referenciaBancaria = referenciaBancaria;
	}
	public String getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}