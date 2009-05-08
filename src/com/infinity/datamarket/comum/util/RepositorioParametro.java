package com.infinity.datamarket.comum.util;


public class RepositorioParametro extends Repositorio implements IRepositorioParametro{
	private static RepositorioParametro instancia;

	private RepositorioParametro(){

	}

	public static RepositorioParametro getInstancia(){
		if (instancia == null){
			instancia = new RepositorioParametro();
		}
		return instancia;
	}


	public Parametro getParametro(String chave) throws AppException{
		return (Parametro) findById(Parametro.class, chave);			
	}
	
	public void atualizarParametro(Parametro parametro) throws AppException{
		
		update(parametro);
		
	}

}
