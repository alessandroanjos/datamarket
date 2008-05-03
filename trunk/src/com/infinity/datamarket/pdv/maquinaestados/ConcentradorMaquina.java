package com.infinity.datamarket.pdv.maquinaestados;

import java.util.Collection;

import com.infinity.datamarket.comum.lote.DadoLote;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
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

	public Tecla consultaTecla(IPropertyFilter filtro) throws AppException{
		Collection col = null;

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			col = getRepositorio().filter(filtro, true);
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
			tecla = (Tecla) getRepositorio().findById(Tecla.class, id);
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
		return tecla;
	}


	public MacroOperacao consultaMacroOperacao(IPropertyFilter filtro) throws AppException{
		Collection col = null;

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			col = getRepositorio().filter(filtro, true);
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
			}catch(Throwable ex){
				throw new SistemaException(ex);
			}
		}
		if (col.size() > 0){
			return (MacroOperacao) col.iterator().next();
		}else{
			return null;
		}
	}
	
	
	public void consulta() throws AppException{
		Tecla tecla = null;

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(DadoLote.class);
			filter.addProperty("lote", 7);
			filter.addOrderByProperty("sequencial", filter.ASC);
			getRepositorio().filter(filter, true);
			
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
	
	public static void  main(String[] a){
		try {
			ConcentradorMaquina.getInstancia().consulta();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
