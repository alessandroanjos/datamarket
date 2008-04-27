package com.infinity.datamarket.pdv.lote;

import java.io.Serializable;

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;
import com.infinity.datamarket.comum.util.RepositorioHI;
import com.infinity.datamarket.comum.util.SistemaException;

public class AtualizadorLote {
	
	private static AtualizadorLote instancia;
	
	private AtualizadorLote(){
		
	}
	
	public static AtualizadorLote getInstancia(){
		if (instancia == null){
			instancia = new AtualizadorLote();
		}
		return instancia;
	}
	
	public IRepositorio getRepositorio(){
	 	return RepositorioHI.getInstancia();
	}
	public void excluir(Serializable obj) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getRepositorio().remove(obj);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
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
	
	public void incluir(Serializable obj) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getRepositorio().insert(obj);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
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
	
	public void alterar(Serializable obj) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getRepositorio().update(obj);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
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
