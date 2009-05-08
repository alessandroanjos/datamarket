package com.infinity.datamarket.comum.macrooperacao;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.maquinaestados.MacroOperacao;

public interface IRepositorioMacroOperacao {

	public MacroOperacao consultarPorId(Long id) throws AppException;	
	public void inserir(MacroOperacao macroOperacao) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public MacroOperacao consultarPorPK(Long id) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void alterar(MacroOperacao macroOperacao) throws AppException;
	public void excluir(MacroOperacao macroOperacao) throws AppException;
	
}
