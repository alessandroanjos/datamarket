package com.infinity.datamarket.infocomponent;

import java.io.Serializable;
import java.util.Date;

public class InfoComponent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8204037679947279102L;
	private InfoComponentPK pk;
	private String versao;
	private String lote;
	private String estado;
	private Date dataAtualizacao;
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	public InfoComponentPK getPk() {
		return pk;
	}
	public void setPk(InfoComponentPK pk) {
		this.pk = pk;
	}
	
	
}
