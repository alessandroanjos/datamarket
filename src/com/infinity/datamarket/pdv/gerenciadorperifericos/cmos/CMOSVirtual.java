package com.infinity.datamarket.pdv.gerenciadorperifericos.cmos;

import java.io.Serializable;
import java.util.HashMap;

public class CMOSVirtual implements CMOS, Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private HashMap hash;
	
	public CMOSVirtual(){

		hash = new HashMap();
		

	}
	
	public void gravar(String chave, Object valor) {
		hash.put(chave, valor);
		
	}

	public Object ler(String chave) {
		return hash.get(chave);
	}

	public void setArquivo(String arquivo) {
		// TODO Auto-generated method stub
		
	}

}
