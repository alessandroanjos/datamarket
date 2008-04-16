package com.infinity.datamarket.comum.estoque;

import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.Persistente;

public class EstoquePK extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4072775940872166998L;
	
	private Loja loja;

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}
}
