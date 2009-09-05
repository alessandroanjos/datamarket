package com.infinity.datamarket.pdv.maquinaestados;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.util.Persistente;

public class Estado extends Persistente{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6159008855909034237L;
	private String descricao;
	private int inputType;
	private int inputSize;
	private Collection finalizadoras;

	public static final Long DISPONIVEL = new Long(3);
	
//	private static final String DELIM = ",";

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean equals(Object o){
		if (o instanceof Estado){
			Estado est = (Estado) o;
			return (this.getId().equals(est.getId()) && this.getDescricao().equals(est.getDescricao()));
		}else{
			return false;
		}

	}

	public int getInputSize() {
		return inputSize;
	}

	public void setInputSize(int inputSize) {
		this.inputSize = inputSize;
	}

	public int getInputType() {
		return inputType;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public Collection getFinalizadoras() {
		if (finalizadoras == null) {
			finalizadoras = new ArrayList();
		}
		return finalizadoras;
	}

	public void setFinalizadoras(Collection finalizadoras) {
		this.finalizadoras = finalizadoras;
	}

	public int[] getFinalizadorasArray(){
		Collection c = getFinalizadoras();
		int[] retorno = new int[c.size()];
		Iterator itera = c.iterator();
		for(int i = 0; itera.hasNext(); i++){
			retorno[i] = ((Tecla) itera.next()).getCodigoASCI();
		}
		return retorno;
	}


}
