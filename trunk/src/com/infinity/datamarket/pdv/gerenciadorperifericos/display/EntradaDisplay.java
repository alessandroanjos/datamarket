package com.infinity.datamarket.pdv.gerenciadorperifericos.display;

public class EntradaDisplay {
	private int teclaFinalizadora;
	private String dado;

	public EntradaDisplay(){

	}

	public EntradaDisplay(String dado, int teclaFinalizadora){
		this.dado = dado;
		this.teclaFinalizadora = teclaFinalizadora;
	}

	public String getDado() {
		return dado;
	}
	public void setDado(String dado) {
		this.dado = dado;
	}
	public int getTeclaFinalizadora() {
		return teclaFinalizadora;
	}
	public void setTeclaFinalizadora(int teclaFinalizadora) {
		this.teclaFinalizadora = teclaFinalizadora;
	}
}
