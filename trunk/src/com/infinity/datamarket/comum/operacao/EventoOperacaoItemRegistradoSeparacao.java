package com.infinity.datamarket.comum.operacao;

import java.math.BigDecimal;
import java.util.Date;

public class EventoOperacaoItemRegistradoSeparacao extends EventoOperacaoItemRegistrado {

	private EventoOperacaoItemRegistrado eventoOperacaoItemRegistrado;
	private BigDecimal quantidadeSeparada;

	public EventoOperacaoItemRegistradoSeparacao(EventoOperacaoItemRegistrado eventoOperacaoItemRegistrado, BigDecimal quantidadeSeparada) {
		this.eventoOperacaoItemRegistrado = eventoOperacaoItemRegistrado;
		this.quantidadeSeparada = quantidadeSeparada;
	}


	public EventoOperacaoItemRegistrado getEventoOperacaoItemRegistrado() {
		return eventoOperacaoItemRegistrado;
	}
	public void setEventoOperacaoItemRegistrado(
			EventoOperacaoItemRegistrado eventoOperacaoItemRegistrado) {
		this.eventoOperacaoItemRegistrado = eventoOperacaoItemRegistrado;
	}
	public BigDecimal getQuantidadeSeparada() {
		return quantidadeSeparada;
	}
	public void setQuantidadeSeparada(BigDecimal quantidadeSeparada) {
		this.quantidadeSeparada = quantidadeSeparada;
	}
}