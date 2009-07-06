package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioEstoque extends Repositorio implements IRepositorioEstoque{
	private static RepositorioEstoque instancia;
	private static Class CLASSE = Estoque.class;
	private RepositorioEstoque(){}
	public static RepositorioEstoque getInstancia(){
		if (instancia == null){
			instancia = new RepositorioEstoque();
		}
		return instancia;
	}

	public Estoque consultarPorId(EstoquePK id) throws AppException{
		return (Estoque) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public EstoqueProduto consultarEstoqueProduto(EstoqueProdutoPK id) throws AppException{
		  return (EstoqueProduto) findById(EstoqueProduto.class, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(Estoque componente) throws AppException{
		insert(componente);
	}
	
	public void alterar(Estoque componente) throws AppException{
		update(componente);
	}
	
	public void excluir(Estoque componente) throws AppException{
		remove(componente);
	}
	
	public Collection consultarTodosPorLoja(String idLoja) throws AppException{
		Session sessao = RepositoryManagerHibernateUtil.currentSession();
		
		StringBuffer sql = new StringBuffer();
		sql.append("from Estoque estoque ");
		sql.append("where estoque.pk.loja.id = '" + ((idLoja == null || idLoja.equals("")== true)?"0":idLoja) + "'");
		
		Query query = sessao.createQuery(sql.toString());
		
		Collection c = query.list();
     
		return c;
	}

}
