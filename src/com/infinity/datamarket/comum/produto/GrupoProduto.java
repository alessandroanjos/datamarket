package com.infinity.datamarket.comum.produto;

import com.infinity.datamarket.comum.util.Persistente;

public class GrupoProduto extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 827425506049106448L;
	/**
	 * 
	 */
	
	
	private String descricao;
	private GrupoProduto grupoSuperior; 	

	public GrupoProduto getGrupoSuperior() {
		return grupoSuperior;
	}

	public void setGrupoSuperior(GrupoProduto grupoSuperior) {
		this.grupoSuperior = grupoSuperior;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public boolean equals(Object obj){
		if (obj instanceof GrupoProduto){
			GrupoProduto tipo = (GrupoProduto) obj;
			return super.equals(tipo) && tipo.descricao.equals(this.descricao) && tipo.grupoSuperior.equals(this.grupoSuperior);
		}else{
			return false;
		}
		
	}
	
}
