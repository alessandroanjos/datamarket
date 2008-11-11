package com.infinity.datamarket.pdv.lote;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.lote.DadoLote;
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
	
	public void atualizarLote(Collection col) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			Iterator i = col.iterator();
			while(i.hasNext()){				
				DadoLote dado = (DadoLote) i.next();
				if (dado.getOperacao().equals(dado.INSERIR)){
					getRepositorio().insert(dado.getDado());
				}else if (dado.getOperacao().equals(dado.ALTERAR)){
					getRepositorio().merge(dado.getDado());
				}else if (dado.getOperacao().equals(dado.EXCLUIR)){	
					getRepositorio().remove(dado.getDado());
				}
				RepositoryManagerHibernateUtil.currentSession().flush();
				RepositoryManagerHibernateUtil.currentSession().evict(dado.getDado());
				RepositoryManagerHibernateUtil.currentSession().clear();
			}
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
