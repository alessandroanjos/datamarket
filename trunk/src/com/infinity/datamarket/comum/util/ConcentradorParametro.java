package com.infinity.datamarket.comum.util;

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;

public class ConcentradorParametro extends Cadastro{

	public static final String LOJA = "LOJA";
	public static final String COMPONENTE = "COMPONENTE";
	public static final String VERSAO = "VERSAO";
	public static final String LOTE = "LOTE";
	public static final String LOGO_CLIENTE = "LOGO_CLIENTE";
	public static final String PEDE_VENDEDOR = "PEDE_VENDEDOR";

	private static ConcentradorParametro instancia;

	private ConcentradorParametro(){

	}

	public static ConcentradorParametro getInstancia(){
		if (instancia == null){
			instancia = new ConcentradorParametro();
		}
		return instancia;
	}


	public Parametro getParametro(String chave) {
		Parametro retorno = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			retorno = (Parametro) getRepositorio().findById(Parametro.class, chave);
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
