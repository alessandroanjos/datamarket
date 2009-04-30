package com.infinity.datamarket.comum.util;

import org.hibernate.Query;

import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;

public class ConcentradorControleId extends Cadastro{
	private static ConcentradorControleId instancia;

	public ConcentradorControleId(){

	}

	public static ConcentradorControleId getInstancia(){
		if (instancia == null){
			instancia = new ConcentradorControleId();
		}
		return instancia;
	}

	public Long retornaMaxId(Class classe){
		Long maxId = null;
		
		Query query = RepositoryManagerHibernateUtil.currentSession().createQuery("select max(id) from " + classe.getSimpleName());
		Long qretorno = (Long)query.uniqueResult(); 		
		if (qretorno != null){
			maxId = qretorno;	
		}else{
			maxId = new Long(0);
		}	
		return maxId;
	}

	public Controle getControle(Class classe) {
		Controle retorno = null;

		try{
			
				RepositoryManagerHibernateUtil.beginTrasaction();
				try {
					retorno = (Controle) getRepositorio().findById(Controle.class, classe.getSimpleName());
				}catch(ObjectNotFoundException e){
				}
				if (retorno == null) {
					retorno = new Controle();
					retorno.setChave(classe.getSimpleName());
					retorno.setValor(retornaMaxId(classe)+1);
					getRepositorio().insert(retorno);
				} else {
					retorno.setValor(retorno.getValor()+1);
				}
				RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return retorno;
	}
	
	public void atualizarControle(Controle controle) {
		
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getRepositorio().update(controle);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
	}

}
