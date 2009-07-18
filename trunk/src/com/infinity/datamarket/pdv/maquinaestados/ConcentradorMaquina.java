package com.infinity.datamarket.pdv.maquinaestados;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;
import com.infinity.datamarket.comum.util.SistemaException;

public class ConcentradorMaquina extends Cadastro{
	private static ConcentradorMaquina instancia;
	private ConcentradorMaquina(){

	}
	public static ConcentradorMaquina getInstancia(){
		if (instancia == null){
			instancia = new ConcentradorMaquina();
		}
		return instancia;
	}
	
	
	public IRepositorioMaquina getRepositorio() {
		return (IRepositorioMaquina) super.getRepositorio(IRepositorio.REPOSITORIO_MAQUINA);
	}

	public Tecla consultaTecla(IPropertyFilter filtro) throws AppException{
		Collection col = null;

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			col = getRepositorio().consultaTecla(filtro);
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
		}
		if (col.size() > 0){
			return (Tecla) col.iterator().next();
		}else{
			return null;
		}
	}

	public Tecla consultaTeclaPorId(Long id) throws AppException{
		Tecla tecla = null;

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			tecla = (Tecla) getRepositorio().consultaTeclaPorId(id);
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
		}
		return tecla;
	}


	public MacroOperacao consultaMacroOperacao(IPropertyFilter filtro) throws AppException{
		Collection col = null;

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			col = getRepositorio().consultaMacroOperacao(filtro);
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
		}
		if (col.size() > 0){
			return (MacroOperacao) col.iterator().next();
		}else{
			return null;
		}
	}
		
	
}
