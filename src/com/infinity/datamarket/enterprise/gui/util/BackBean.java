package com.infinity.datamarket.enterprise.gui.util;

import com.infinity.datamarket.comum.Fachada;

public class BackBean {
	
	public static final String INSERIR = "I";
	public static final String ALTERAR = "A";
	
	public Fachada getFachada(){
		return Fachada.getInstancia();
	}
}
