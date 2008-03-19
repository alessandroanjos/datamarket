package com.infinity.datamarket.comum.util;

import java.io.Serializable;
import java.util.Vector;

import com.infinity.datamarket.pdv.maquinaestados.Evento;

public class ListaEventos implements Serializable{
	private Vector lista;
	private int indice;
	private int prox;
	public ListaEventos(){
		lista = new Vector();
		indice = 0;
		prox = 0;
	}
	
	public void add(Evento e){
		lista.add(indice++, e);
	}
	
	public Evento getEvento(){
		Evento e = null;
		try{
			e = (Evento) lista.get(prox);
			lista.setElementAt(null, prox++);
		}catch(Exception ex){
			
		}
		return e;
	}
	
}
