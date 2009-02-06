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
	
}
