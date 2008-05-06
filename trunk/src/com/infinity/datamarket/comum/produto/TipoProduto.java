package com.infinity.datamarket.comum.produto;

import com.infinity.datamarket.comum.util.Persistente;

public class TipoProduto extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8544105692519556877L;
	
	public static final Long NORMAL = new Long(1);
	public static final Long UNIDADE_VARIAVEL = new Long(2);
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public boolean equals(Object obj){
		if (obj instanceof TipoProduto){
			TipoProduto tipo = (TipoProduto) obj;
			return super.equals(tipo) && tipo.descricao.equals(this.descricao);
		}else{
			return false;
		}
		
	}
	
}
