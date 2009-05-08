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
	
	
	public IRepositorioControleId getRepositorio() {
		return (IRepositorioControleId) super.getRepositorio(IRepositorio.REPOSITORIO_CONTROLE_ID);
	}

	public Long retornaMaxId(Class classe){
		return getRepositorio().retornaMaxId(classe);
	}

	public Controle getControle(Class classe) {
		Controle retorno = null;

		try{
			
				RepositoryManagerHibernateUtil.beginTrasaction();
				try {
					retorno = getRepositorio().getControle(classe);
				}catch(ObjectNotFoundException e){
				}
				if (retorno == null) {
					retorno = new Controle();
					retorno.setChave(classe.getSimpleName());
					retorno.setValor(retornaMaxId(classe)+1);
					getRepositorio().inserirControle(retorno);
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
			getRepositorio().atualizarControle(controle);
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
