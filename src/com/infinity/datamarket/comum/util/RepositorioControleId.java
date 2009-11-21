package com.infinity.datamarket.comum.util;

import org.hibernate.Query;

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;

public class RepositorioControleId extends Repositorio implements IRepositorioControleId{
	private static RepositorioControleId instancia;

	private RepositorioControleId(){

	}

	public static RepositorioControleId getInstancia(){
		if (instancia == null){
			instancia = new RepositorioControleId();
		}
		return instancia;
	}

	public Long retornaMaxId(Class classe){
		Long maxId = null;		
		Query query = RepositoryManagerHibernateUtil.getInstancia().currentSession().createQuery("select max(id) from " + classe.getSimpleName());
		Long qretorno = (Long)query.uniqueResult(); 		
		if (qretorno != null){
			maxId = qretorno;	
		}else{
			maxId = new Long(0);
		}	
		return maxId;
	}

	public Controle getControle(Class classe) throws AppException{
		return (Controle) findById(Controle.class, classe.getSimpleName());		
	}
	
	public void atualizarControle(Controle controle) throws AppException{
		update(controle);
	}
	
	public void inserirControle(Controle controle) throws AppException{
		insert(controle);
	}

}
