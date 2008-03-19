package com.infinity.datamarket.comum.transacao;

import java.io.Serializable;
import java.util.Date;

public class EventoTransacao implements Serializable{
	private EventoTransacaoPK pk;
	private int tipoEvento;
	private Date dataHoraEvento;



	public EventoTransacao(EventoTransacaoPK pk, int tipoEvento, Date dataHoraEvento){
		this.pk = pk;
		this.tipoEvento = tipoEvento;
		this.dataHoraEvento = dataHoraEvento;
	}

	public EventoTransacao(){

	}

	public Date getDataHoraEvento() {
		return dataHoraEvento;
	}
	public void setDataHoraEvento(Date dataHoraEvento) {
		this.dataHoraEvento = dataHoraEvento;
	}
	public EventoTransacaoPK getPk() {
		return pk;
	}
	public void setPk(EventoTransacaoPK pk) {
		this.pk = pk;
	}
	public int getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(int tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

}
