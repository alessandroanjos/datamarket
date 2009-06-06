package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;

import com.infinity.datamarket.comum.usuario.Loja;

public class EstoquePK implements Serializable { // extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4072775940872166998L;
	
	private Long id;
	private Loja loja;

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public EstoquePK(){
		
	}
	
	public EstoquePK(Long id, Loja loja){
		this.setId(id);
		this.setLoja(loja);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
