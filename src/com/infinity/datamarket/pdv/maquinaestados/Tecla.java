package com.infinity.datamarket.pdv.maquinaestados;

import com.infinity.datamarket.comum.util.Persistente;

public class Tecla extends Persistente{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4381406580564543091L;
	public static final int CODIGO_ENTER = 10;
	public static final int CODIGO_VOLTA = 27;

	private int codigoASCI;
	private String descricao;

	public int getCodigoASCI() {
		return codigoASCI;
	}

	public void setCodigoASCI(int codigoASCI) {
		this.codigoASCI = codigoASCI;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean equals(Object o){
		if (o instanceof Tecla){
			Tecla tecla = (Tecla) o;
			return (tecla.getId().equals(this.getId()) && codigoASCI == this.codigoASCI);
		}else{
			return false;
		}
	}
	
	public String toString() {
		return descricao;
	}
}
