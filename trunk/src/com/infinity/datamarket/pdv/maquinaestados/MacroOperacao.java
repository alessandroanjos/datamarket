package com.infinity.datamarket.pdv.maquinaestados;

import com.infinity.datamarket.comum.util.Persistente;

public class MacroOperacao extends Persistente{
	private Tecla tecla;
	private Estado estadoAtual;
	private Estado proximoEstado;
	private MicroOperacaoAssociada microOperacaoInicial;
	public Estado getEstadoAtual() {
		return estadoAtual;
	}
	public void setEstadoAtual(Estado estadoAtual) {
		this.estadoAtual = estadoAtual;
	}
	public MicroOperacaoAssociada getMicroOperacaoInicial() {
		return microOperacaoInicial;
	}
	public void setMicroOperacaoInicial(MicroOperacaoAssociada microOperacaoInicial) {
		this.microOperacaoInicial = microOperacaoInicial;
	}
	public Estado getProximoEstado() {
		return proximoEstado;
	}
	public void setProximoEstado(Estado proximoEstado) {
		this.proximoEstado = proximoEstado;
	}
	public Tecla getTecla() {
		return tecla;
	}
	public void setTecla(Tecla tecla) {
		this.tecla = tecla;
	}
}
