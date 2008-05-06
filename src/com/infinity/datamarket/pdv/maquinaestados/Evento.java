package com.infinity.datamarket.pdv.maquinaestados;

import java.io.Serializable;

public class Evento implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6823177101174180928L;
	private String param;
	private int tecla;
	
	public Evento(int tecla, String param){
		this.param = param;
		this.tecla = tecla;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public int getTecla() {
		return tecla;
	}
	public void setTecla(int tecla) {
		this.tecla = tecla;
	}
}
