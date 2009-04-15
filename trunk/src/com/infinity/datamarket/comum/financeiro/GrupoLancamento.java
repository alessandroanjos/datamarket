package com.infinity.datamarket.comum.financeiro;

import com.infinity.datamarket.comum.util.Persistente;

public class GrupoLancamento extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6992015292096878327L;

	private String descricao;
	private String tipoRegistro;
	
	public static final String REGISTRO_SISTEMA = "S";
	public static final String REGISTRO_USUARIO = "U";
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	@Override
	public int hashCode() {
		return descricao.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GrupoLancamento other = (GrupoLancamento) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}
	
	
	
}
