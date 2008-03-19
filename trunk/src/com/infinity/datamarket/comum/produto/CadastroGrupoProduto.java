package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.ValidationException;

public class CadastroGrupoProduto extends Cadastro{
	
	private static CadastroGrupoProduto instancia;
	
	private static final Class CLASSE = GrupoProduto.class;
	
	public static CadastroGrupoProduto getInstancia(){
		if (instancia == null){
			instancia = new CadastroGrupoProduto();			
		}
		return instancia;
	}
	
	public void inserir(GrupoProduto grupoProduto) throws AppException{		
		getRepositorio().insert(grupoProduto);		
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public GrupoProduto consultarPorPK(Long id) throws AppException{
		return (GrupoProduto) getRepositorio().findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	
	public void alterar(GrupoProduto grupoProduto) throws AppException{
		if (grupoProduto.getGrupoSuperior() != null && grupoProduto.getGrupoSuperior().getId().equals(grupoProduto.getId())){
			throw new ValidationException("Grupo de produto superior não pode ser o próprio grupo de produto");
		}else{
			getRepositorio().update(grupoProduto);
		}		
	}
	
	public void excluir(GrupoProduto grupoProduto) throws AppException{
		getRepositorio().remove(grupoProduto);
	}
}
