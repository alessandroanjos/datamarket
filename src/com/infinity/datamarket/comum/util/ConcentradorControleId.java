package com.infinity.datamarket.comum.util;


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
			retorno = getCadastro().getControle(classe);				
		}catch(Throwable e){
			throw new SistemaException(e);
		}		
		return retorno;
	}

}
