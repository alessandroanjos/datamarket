package com.infinity.datamarket.pdv.maquinaestados;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;
import com.infinity.datamarket.comum.util.SistemaException;

public class ConcentradorMaquina extends Cadastro implements ControladorMaquinaEstado, Serializable{
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
/*
	public Tecla consultaTecla(IPropertyFilter filtro) throws AppException{
		Collection col = null;

		try{
			RepositoryManagerHibernateUtil.getInstancia().beginTrasaction();
			col = getRepositorio().consultaTecla(filtro);
			RepositoryManagerHibernateUtil.getInstancia().commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
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
*/
	
	public Map<Tecla, MacroOperacao> getDescTeclasDescMacro(Long idEstadoAtual) throws AppException{
		return null;
	}

	public Estado getEstado(Long id) throws AppException{
		return null;
	}

	public Tecla consultaTeclaPorId(Long id) throws AppException{
		Tecla tecla = null;

		try{
			RepositoryManagerHibernateUtil.getInstancia().beginTrasaction();
			tecla = (Tecla) getRepositorio().consultaTeclaPorId(id);
			RepositoryManagerHibernateUtil.getInstancia().commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
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
			RepositoryManagerHibernateUtil.getInstancia().beginTrasaction();
			col = getRepositorio().consultaMacroOperacao(filtro);
			RepositoryManagerHibernateUtil.getInstancia().commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
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
