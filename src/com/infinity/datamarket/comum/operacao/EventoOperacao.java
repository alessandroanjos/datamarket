package com.infinity.datamarket.comum.operacao;

import java.io.Serializable;
import java.util.Date;

public class EventoOperacao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3428441771620806848L;
	private EventoOperacaoPK pk;
	private int tipoEvento;
	private Date dataHoraEvento;



	public EventoOperacao(EventoOperacaoPK pk, int tipoEvento, Date dataHoraEvento){
		this.pk = pk;
		this.tipoEvento = tipoEvento;
		this.dataHoraEvento = dataHoraEvento;
	}

	public EventoOperacao(){

	}

	public Date getDataHoraEvento() {
		return dataHoraEvento;
	}
	public void setDataHoraEvento(Date dataHoraEvento) {
		this.dataHoraEvento = dataHoraEvento;
	}
	public EventoOperacaoPK getPk() {
		return pk;
	}
	public void setPk(EventoOperacaoPK pk) {
		this.pk = pk;
	}
	public int getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(int tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	
	
}
