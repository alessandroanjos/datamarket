package com.infinity.datamarket.comum.util;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;


public class ConcentradorParametro extends Cadastro{

	public static final String LOJA = "LOJA";
	public static final String COMPONENTE = "COMPONENTE";
	public static final String VERSAO = "VERSAO";
	public static final String LOTE = "LOTE";
	public static final String LOGO_CLIENTE = "LOGO_CLIENTE";
	public static final String PEDE_VENDEDOR = "PEDE_VENDEDOR";
	public static final String IMPRIME_RECIBO = "IMPRIME_RECIBO";
	public static final String IMPRIME_MOVIMENTO_DIA = "IMPRIME_MOVIMENTO_DIA";
	public static final String IMPRIME_RECIBO_PAGAMENTO = "IMPRIME_RECIBO_PAGAMENTO";
	public static final String SOLICITA_CLIENTE = "SOLICITA_CLIENTE";
	public static final String ATUALIZA_COMISSAO_VENDEDOR = "ATUALIZA_COMISSAO_VENDEDOR";
	
	private static ConcentradorParametro instancia;

	private ConcentradorParametro(){

	}

	public static ConcentradorParametro getInstancia(){
		if (instancia == null){
			instancia = new ConcentradorParametro();
		}
		return instancia;
	}

	
	public IRepositorioParametro getRepositorio() {
		return (IRepositorioParametro) super.getRepositorio(IRepositorio.REPOSITORIO_PARAMETRO);
	}

	public void removerParametro(String chave) throws AppException{

		try{			
			RepositoryManagerHibernateUtil.getInstancia().beginTrasaction();
			getRepositorio().removerParametro(chave);
			RepositoryManagerHibernateUtil.getInstancia().commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}

	}
	
	public Parametro getParametro(String chave) {
		Parametro retorno = null;
		try{			
			RepositoryManagerHibernateUtil.getInstancia().beginTrasaction();
			retorno = (Parametro) getRepositorio().getParametro(chave);	
			RepositoryManagerHibernateUtil.getInstancia().commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}		
		return retorno;
	}

	public Collection<Parametro> consultarTodosParametro() throws AppException  {
			return getRepositorio().consultarTodosParametro();	
	}

	public void atualizarParametro(Parametro parametro) {

		Parametro p = getParametro(parametro.getChave());
		if (p == null ) {
			inserirParametro(parametro);
		}else{
			//p.setChave(parametro.getChave());
			p.setValor(parametro.getValor());
		}

		try{
			RepositoryManagerHibernateUtil.getInstancia().beginTrasaction();
			getRepositorio().atualizarParametro(p);
			RepositoryManagerHibernateUtil.getInstancia().commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}	
	}
	

	public void inserirParametro(Parametro parametro) {
		
		try{
			RepositoryManagerHibernateUtil.getInstancia().beginTrasaction();
			getRepositorio().inserirParametro(parametro);
			RepositoryManagerHibernateUtil.getInstancia().commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}	
	}
	
	public String consultarURLApp() throws AppException{
		String url = "http://localhost:8080";
		try{	
			RepositoryManagerHibernateUtil.getInstancia().beginTrasaction();
			Parametro p = (Parametro) getRepositorio().getParametro("URL");
			url = p.getValor();
			RepositoryManagerHibernateUtil.getInstancia().commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.getInstancia().rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		
		return url;
	}

}
