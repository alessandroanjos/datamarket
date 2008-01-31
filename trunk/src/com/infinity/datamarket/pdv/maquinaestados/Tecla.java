package com.infinity.datamarket.pdv.maquinaestados;

import com.infinity.datamarket.comum.util.Persistente;

public class Tecla extends Persistente{

	public static final int CODIGO_ENTER = 10;
	public static final int CODIGO_VOLTA = 27;

	private int codigoASCI;

	public int getCodigoASCI() {
		return codigoASCI;
	}

	public void setCodigoASCI(int codigoASCI) {
		this.codigoASCI = codigoASCI;
	}

	public boolean equals(Object o){
		if (o instanceof Tecla){
			Tecla tecla = (Tecla) o;
			return (tecla.getId().equals(this.getId()) && codigoASCI == this.codigoASCI);
		}else{
			return false;
		}
	}
}
