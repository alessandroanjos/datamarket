package com.infinity.datamarket.pdv.maquinaestados;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;

public class MaquinaEstado implements ControladorMaquinaEstado, Serializable {

	private Collection estados = new ArrayList();
	private Collection teclas = new ArrayList();
	private Collection micOperacaos = new ArrayList();
	private Collection macroOperacoes = new ArrayList();
	
	public MaquinaEstado(Collection estados, Collection teclas, Collection micOperacaos, Collection macroOperacoes) {
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
	
	public Map<Tecla, MacroOperacao> getDescTeclasDescMacro(Long idEstadoAtual) throws AppException{

		Map<Tecla, MacroOperacao> descTeclasDescMacro = new HashMap<Tecla, MacroOperacao>();
		
		Iterator  it = macroOperacoes.iterator();
		while(it.hasNext()){
			MacroOperacao macro = (MacroOperacao)it.next();
			if (macro.getEstadoAtual().getId().equals(idEstadoAtual)) {
				descTeclasDescMacro.put(macro.getTecla(), macro);
			}
		}
		
		return descTeclasDescMacro;
	}

	public Estado getEstado(Long id) throws AppException{
		Estado retorno = null;
		Iterator it = estados.iterator();
		while(it.hasNext()){
			Estado estado = (Estado)it.next();
			if (estado.getId().equals(id)) {
				retorno = estado;
			}
		}
		return retorno;
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
