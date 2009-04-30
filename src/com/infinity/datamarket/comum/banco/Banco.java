package com.infinity.datamarket.comum.banco;

import com.infinity.datamarket.comum.util.Persistente;

public class Banco extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4859446635232194912L;
	private String descricao;
	private String situacao;
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	@Override
	public int hashCode() {
		StringBuffer sb = new StringBuffer();
		sb.append(descricao);
		sb.append(situacao);
		return sb.toString().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Banco other = (Banco) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	
	public Banco(){
		
	}
	
	public Banco(String descricao, String situacao){
		setDescricao(descricao);
		setSituacao(situacao);		
	}


}
