package com.infinity.datamarket.pdv.maquinaestados;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;

public class MaquinaEstado {

	private Collection estados = new ArrayList();
	private Collection teclas = new ArrayList();
	private Collection micOperacaos = new ArrayList();
	private Collection macroOperacoes = new ArrayList();
	
	public MaquinaEstado(Collection estados, Collection teclas, Collection micOperacaos, Collection macroOperacoes) {
		super();
		this.estados = estados;
		this.teclas = teclas;
		this.micOperacaos = micOperacaos;
		this.macroOperacoes = macroOperacoes;
	}

	public MacroOperacao consultaMacroOperacao(IPropertyFilter filtro) throws AppException{

		Long idEstadoAtual = (Long)filtro.getProperties().get("estadoAtual.id");
		Integer teclaFinalizadora = (Integer )filtro.getProperties().get("tecla.codigoASCI");
		
		Iterator  it = macroOperacoes.iterator();
		while(it.hasNext()){
			MacroOperacao  macro = (MacroOperacao)it.next();
			if (macro.getEstadoAtual().getId().equals(idEstadoAtual) && macro.getTecla().getCodigoASCI() == teclaFinalizadora.intValue()) {
				return macro;
			}
		}
		
		return null;
	}
	
	public Tecla consultaTeclaPorId(Long id) throws AppException{
		Iterator  it = teclas.iterator();
		while(it.hasNext()){
			Tecla tecla = (Tecla)it.next();
			if (tecla.getId() == id) {
				return tecla;
			}
		}
		
		return null;
	}
	
	public Collection getEstados() {
		return estados;
	}

	public Collection getMacroOperacoes() {
		return macroOperacoes;
	}

	public Collection getMicOperacaos() {
		return micOperacaos;
	}

	public Collection getTeclas() {
		return teclas;
	}

}
