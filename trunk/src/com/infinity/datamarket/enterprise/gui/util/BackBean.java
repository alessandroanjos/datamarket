package com.infinity.datamarket.enterprise.gui.util;

import com.infinity.datamarket.comum.Fachada;

public class BackBean {
	public Fachada getFachada(){
		return Fachada.getInstancia();
	}
}