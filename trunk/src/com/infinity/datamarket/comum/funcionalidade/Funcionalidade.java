package com.infinity.datamarket.comum.funcionalidade;

import java.util.Collection;
import java.util.Set;

import com.infinity.datamarket.comum.util.Persistente;

public class Funcionalidade extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3210257304789828052L;
	
	private String descricao;
	private Funcionalidade funcionalidadeSuperior;
	private String situacao;
	private String url;
	private Set perfis;
	
	private Collection funcionalidades;
	
	/**
	 * @return the funcionalidades
	 */
	public Collection getFuncionalidades() {
		return funcionalidades;
	}

	/**
	 * @param perfis the perfis to set
	 */
	public void setFuncionalidades(Collection funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set getPerfis() {
		return perfis;
	}

	public void setPerfis(Set perfis) {
		this.perfis = perfis;
	}

	public Funcionalidade getFuncionalidadeSuperior() {
		return funcionalidadeSuperior;
	}

	public void setFuncionalidadeSuperior(Funcionalidade funcionalidadeSuperior) {
		this.funcionalidadeSuperior = funcionalidadeSuperior;
	}

	/**
	 * @return the situacao
	 */
	public String getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
