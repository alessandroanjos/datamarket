/**
 * 
 */
package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

/**
 * @author alessandro
 *
 */
public class CadastroEstoque extends Cadastro {
	private static CadastroEstoque instancia;
	private static Class CLASSE = Estoque.class;
	private CadastroEstoque(){}
	public static CadastroEstoque getInstancia(){
		if (instancia == null){
			instancia = new CadastroEstoque();
		}
		return instancia;
	}

	public Estoque consultarPorId(EstoquePK id) throws AppException{
		return (Estoque) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public EstoqueProduto consultarEstoqueProduto(EstoqueProdutoPK id) throws AppException{
		  return (EstoqueProduto) getRepositorio().findById(EstoqueProduto.class, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Estoque componente) throws AppException{
		getRepositorio().insert(componente);
	}
	
	public void alterar(Estoque componente) throws AppException{
		getRepositorio().update(componente);
	}
	
	public void excluir(Estoque componente) throws AppException{
		getRepositorio().remove(componente);
	}
	
	public Collection consultarTodosPorLoja(String idLoja) throws AppException{
		Session sessao = RepositoryManagerHibernateUtil.currentSession();
		
		StringBuffer sql = new StringBuffer();
		sql.append("from Estoque estoque ");
		sql.append("where estoque.pk.loja.id = " + idLoja);
		
		Query query = sessao.createQuery(sql.toString());
		
		Collection c = query.list();
     
		return c;
	}
}
