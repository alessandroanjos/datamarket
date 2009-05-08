package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroImposto extends Cadastro{
	
	private static CadastroImposto instancia;
	
	private static final Class CLASSE = Imposto.class;
	
	public static CadastroImposto getInstancia(){
		if (instancia == null){
			instancia = new CadastroImposto();			
		}
		return instancia;
	}
	
	
	public IRepositorioImposto getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioImposto) super.getRepositorio(IRepositorio.REPOSITORIO_IMPOSTO);
	}
	
	public void inserir(Imposto imposto) throws AppException{
		getRepositorio().inserir(imposto);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public Imposto consultarPorPK(Long id) throws AppException{
		return (Imposto) getRepositorio().consultarPorPK(id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	
	public void alterar(Imposto imposto) throws AppException{
		getRepositorio().alterar(imposto);
	}
	
	public void excluir(Imposto imposto) throws AppException{
		getRepositorio().excluir(imposto);
	}
}
