package com.infinity.datamarket.comum.produto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;
import com.infinity.datamarket.comum.util.ValidationException;

public class CadastroGrupoProduto extends Cadastro{
	
	private static CadastroGrupoProduto instancia;
	
	
	public static CadastroGrupoProduto getInstancia(){
		if (instancia == null){
			instancia = new CadastroGrupoProduto();			
		}
		return instancia;
	}
	
	
	public IRepositorioGrupoProduto getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioGrupoProduto) super.getRepositorio(IRepositorio.REPOSITORIO_GRUPO_PRODUTO);
	}
	
	public void inserir(GrupoProduto grupoProduto) throws AppException{		
		getRepositorio().inserir(grupoProduto);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public GrupoProduto consultarPorPK(Long id) throws AppException{
		return (GrupoProduto) getRepositorio().consultarPorPK(id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	
	public void alterar(GrupoProduto grupoProduto) throws AppException{
		if (grupoProduto.getGrupoSuperior() != null && grupoProduto.getGrupoSuperior().getId().equals(grupoProduto.getId())){
			throw new ValidationException("Grupo de produto superior não pode ser o próprio grupo de produto");
		}else{
			getRepositorio().alterar(grupoProduto);
		}		
	}
	
	public void excluir(GrupoProduto grupoProduto) throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(GrupoProduto.class);
		filter.addProperty("grupoSuperior", grupoProduto);				
		Collection c = getRepositorio().consultar(filter);		
		if (c != null && c.size() > 0){
			throw new ValidationException("Grupo de produto superior não pode ser excluido pois existe referência com outro grupos");
		}else{
			getRepositorio().excluir(grupoProduto);
		}
		
	}
}
