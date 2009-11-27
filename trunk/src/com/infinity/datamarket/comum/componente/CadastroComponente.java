package com.infinity.datamarket.comum.componente;


import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;


public class CadastroComponente extends Cadastro {
	private static CadastroComponente instancia;
	private CadastroComponente(){}
	public static CadastroComponente getInstancia(){
		if (instancia == null){
			instancia = new CadastroComponente();
		}
		return instancia;
	}
	
	
	public IRepositorioComponente getRepositorio() {
		return (IRepositorioComponente) super.getRepositorio(IRepositorio.REPOSITORIO_COMPONENTE);
	}

	public Componente consultarPorId(Long id) throws AppException{
		return (Componente) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public Collection consultarTodos(long idLoja) throws AppException{
		
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(Componente.class);
		filter.addProperty("loja.id", new Long(idLoja));	
		return getRepositorio().consultar(filter );
	}
	public void inserir(Componente componente) throws AppException{
		getRepositorio().inserir(componente);
	}
	
	public void alterar(Componente componente) throws AppException{
		getRepositorio().alterar(componente);
	}
	
	public void excluir(Componente componente) throws AppException{
		getRepositorio().excluir(componente);
	}
}