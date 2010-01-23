package com.infinity.datamarket.pdv.maquinaestados;

import java.util.Set;

import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.util.Persistente;

public class MacroOperacao extends Persistente{


	public static int TIPO_COMPONENTE_PDV = Componente.TIPO_COMPONENTE_PDV;
	public static int TIPO_COMPONENTE_AV = Componente.TIPO_COMPONENTE_AV;

	private static final long serialVersionUID = 3360926495862268571L;
	private String descricao;
	int tipoComponente = TIPO_COMPONENTE_PDV;

	private Tecla tecla;
	private Estado estadoAtual;
	private Estado proximoEstado;
	private MicroOperacaoAssociada microOperacaoInicial;

	public int getTipoComponente() {
		return tipoComponente;
	}
	public void setTipoComponente(int tipoComponente) {
		this.tipoComponente = tipoComponente;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
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
