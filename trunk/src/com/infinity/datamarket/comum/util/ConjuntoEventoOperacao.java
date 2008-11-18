package com.infinity.datamarket.comum.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.infinity.datamarket.comum.operacao.EventoOperacao;

public class ConjuntoEventoOperacao implements Collection, Serializable, Set{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5057841182069647949L;
	
	private Collection list;

	public ConjuntoEventoOperacao(){
		this.list = new ArrayList();
	}

	public boolean add(Object o) {
		if (o instanceof EventoOperacao){
			EventoOperacao ev = (EventoOperacao) o;
			ev.getPk().setNumeroEvento(list.size()+1);
		}
		return list.add(o);
	}

	public boolean addAll(Collection c) {
		return list.addAll(c);
	}

	public void clear() {
		list.clear();

	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public boolean containsAll(Collection c) {
		return list.containsAll(c);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Iterator iterator() {
		return list.iterator();
	}

	public boolean remove(Object o) {
		return list.remove(o);
	}

	public boolean removeAll(Collection c) {
		return list.removeAll(c);
	}

	public boolean retainAll(Collection c) {
		return list.retainAll(c);
	}

	public int size() {
		return list.size();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public Object[] toArray(Object[] a) {
		return list.toArray();
	}

}