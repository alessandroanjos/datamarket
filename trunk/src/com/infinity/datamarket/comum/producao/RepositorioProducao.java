package com.infinity.datamarket.comum.producao;

import java.util.Collection;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioProducao extends Repositorio implements IRepositorioProducao{
	private static RepositorioProducao instancia;
	private static Class CLASSE = Producao.class;
	private RepositorioProducao(){}
	public static RepositorioProducao getInstancia(){
		if (instancia == null){
			instancia = new RepositorioProducao();
		}
		return instancia;
	}

	public Producao consultarPorId(Long id) throws AppException{
		return (Producao) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(Producao producao) throws AppException{
		insert(producao);
		
	}
	
	public void alterar(Producao producao) throws AppException{
		update(producao);
		
	}
	
	public void excluir(Producao producao) throws AppException{
		remove(producao);
	
	}
	
	public Integer consultarMaiorNumeroLote() throws AppException{
		Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
		SQLQuery query = session.createSQLQuery("select max(lote) from Producao");		
		Integer l  = (Integer) query.uniqueResult();
		return l;
	
	}

}
