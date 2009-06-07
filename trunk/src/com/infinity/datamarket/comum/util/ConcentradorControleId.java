package com.infinity.datamarket.comum.util;

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
	
	
	public CadastroControleId getCadastro() {
		return CadastroControleId.getInstancia();
	}

	public Long retornaMaxId(Class classe){
		return getCadastro().retornaMaxId(classe);
	}

	public Controle getControle(Class classe) {
		Controle retorno = null;
		try{
				RepositoryManagerHibernateUtil.beginTrasaction();
				retorno = getCadastro().getControle(classe);
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

}
