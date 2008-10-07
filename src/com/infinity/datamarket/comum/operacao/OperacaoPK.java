package com.infinity.datamarket.comum.operacao;

import java.io.Serializable;

import com.infinity.datamarket.comum.util.Constantes;

public class OperacaoPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2448632926844538580L;
	
	private int loja = Constantes.NUMERO_NAO_INFORMADO;
	private int id = Constantes.NUMERO_NAO_INFORMADO;
	
	public OperacaoPK(){
		
	}
	
	public OperacaoPK(int id, int loja){
		this.id = id;
		this.loja = loja;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLoja() {
		return loja;
	}
	public void setLoja(int loja) {
		this.loja = loja;
	}
	
	public boolean equals(Object obj){
		if (obj instanceof OperacaoPK){
			OperacaoPK o = (OperacaoPK) obj;
			return (this.loja == o.loja && this.id == o.id);
		}
		return false;
	}

}
