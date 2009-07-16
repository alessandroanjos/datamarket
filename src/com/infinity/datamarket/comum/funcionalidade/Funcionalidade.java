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
	private String largura;
	private String altura;
	
	//private Set perfis;
	
//	private Collection funcionalidades;
	
	private Collection funcionalidadesFilhas;
	
	
	/**
	 * @return the funcionalidadesFilhas
	 */
	public Collection getFuncionalidadesFilhas() {
		return funcionalidadesFilhas;
	}

	/**
	 * @param funcionalidadesFilhas the funcionalidadesFilhas to set
	 */
	public void setFuncionalidadesFilhas(Collection funcionalidadesFilhas) {
		this.funcionalidadesFilhas = funcionalidadesFilhas;
	}

	/**
	 * @return the funcionalidades
	 */
//	public Collection getFuncionalidades() {
//		return funcionalidades;
//	}
//
//	/**
//	 * @param perfis the perfis to set
//	 */
//	public void setFuncionalidades(Collection funcionalidades) {
//		this.funcionalidades = funcionalidades;
//	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

//	public Set getPerfis() {
//		return perfis;
//	}
//
//	public void setPerfis(Set perfis) {
//		this.perfis = perfis;
//	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = PRIME * result + ((funcionalidadeSuperior == null) ? 0 : funcionalidadeSuperior.hashCode());
//		result = PRIME * result + ((funcionalidades == null) ? 0 : funcionalidades.hashCode());
//		result = PRIME * result + ((funcionalidadesFilhas == null) ? 0 : funcionalidadesFilhas.hashCode());
//		result = PRIME * result + ((perfis == null) ? 0 : perfis.hashCode());
		result = PRIME * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = PRIME * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Funcionalidade other = (Funcionalidade) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (funcionalidadeSuperior == null) {
			if (other.funcionalidadeSuperior != null)
				return false;
		} else if (!funcionalidadeSuperior.equals(other.funcionalidadeSuperior))
			return false;
//		if (funcionalidades == null) {
//			if (other.funcionalidades != null)
//				return false;
//		} else if (!funcionalidades.equals(other.funcionalidades))
//			return false;
//		if (funcionalidadesFilhas == null) {
//			if (other.funcionalidadesFilhas != null)
//				return false;
//		} else if (!funcionalidadesFilhas.equals(other.funcionalidadesFilhas))
//			return false;
//		if (perfis == null) {
//			if (other.perfis != null)
//				return false;
//		} else if (!perfis.equals(other.perfis))
//			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getLargura() {
		return largura;
	}

	public void setLargura(String largura) {
		this.largura = largura;
	}

}
