/**
 * 
 */
package com.infinity.datamarket.comum.util;

import com.infinity.datamarket.comum.util.Persistente;

/**
 * @author alessandro
 *
 */
public class Controle extends Persistente {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String chave;
	
	int valor;
	
/**
 * @return the chave
 */
public String getChave() {
	return chave;
}
/**
 * @param chave the chave to set
 */
public void setChave(String chave) {
	this.chave = chave;
}
/**
 * @return the valor
 */
public int getValor() {
	return valor;
}
/**
 * @param valor the valor to set
 */
public void setValor(int valor) {
	this.valor = valor;
}
}
