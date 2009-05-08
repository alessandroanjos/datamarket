package com.infinity.datamarket.comum.macrooperacao;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;
import com.infinity.datamarket.pdv.maquinaestados.MacroOperacao;

public class RepositorioMacroOperacao extends Repositorio implements IRepositorioMacroOperacao{

	private static RepositorioMacroOperacao instancia;
	private static Class CLASSE = MacroOperacao.class;
	private RepositorioMacroOperacao(){}
	public static RepositorioMacroOperacao getInstancia(){
		if (instancia == null){
			instancia = new RepositorioMacroOperacao();
		}
		return instancia;
	}

	public MacroOperacao consultarPorId(Long id) throws AppException{
		return (MacroOperacao) findById(CLASSE, id);
	}
	
	public void inserir(MacroOperacao macroOperacao) throws AppException{
		insert(macroOperacao);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public MacroOperacao consultarPorPK(Long id) throws AppException{
		return (MacroOperacao) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	
	public void alterar(MacroOperacao macroOperacao) throws AppException{
		update(macroOperacao);
	}
	
	public void excluir(MacroOperacao macroOperacao) throws AppException{
		remove(macroOperacao);
	}
	
}
