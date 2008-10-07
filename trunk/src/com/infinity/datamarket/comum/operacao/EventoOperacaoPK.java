package com.infinity.datamarket.comum.operacao;


public class EventoOperacaoPK extends OperacaoPK{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4464241339700833249L;
	
	private int numeroEvento;

	public int getNumeroEvento() {
		return numeroEvento;
	}

	public void setNumeroEvento(int numeroEvento) {
		this.numeroEvento = numeroEvento;
	}
	
	public EventoOperacaoPK(int id, int loja, int numeroEvento){
		super(id,loja);
		this.numeroEvento = numeroEvento;
	}
	
	public EventoOperacaoPK(){
		
	}
	
	public boolean equals(Object obj){
		if (obj instanceof EventoOperacaoPK){
			EventoOperacaoPK t = (EventoOperacaoPK) obj;
			return (this.numeroEvento == t.numeroEvento && super.equals(t));
		}
		return false;

	}


}
