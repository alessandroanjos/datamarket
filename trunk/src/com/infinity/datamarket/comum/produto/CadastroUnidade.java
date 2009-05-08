package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroUnidade extends Cadastro{
	
	private static CadastroUnidade instancia;
	
	public static CadastroUnidade getInstancia(){
		if (instancia == null){
			instancia = new CadastroUnidade();			
		}
		return instancia;
	}
	
	public IRepositorioUnidade getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioUnidade) super.getRepositorio(IRepositorio.REPOSITORIO_UNIDADE);
	}
	
	public void inserir(Unidade unidade) throws AppException{
		getRepositorio().inserir(unidade);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public Unidade consultarPorPK(Long id) throws AppException{
		return (Unidade) getRepositorio().consultarPorPK(id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	
	public void alterar(Unidade unidade) throws AppException{
		getRepositorio().alterar(unidade);
	}
	
	public void excluir(Unidade unidade) throws AppException{
		getRepositorio().excluir(unidade);
	}
}
