package com.infinity.datamarket.comum.util;


public class CadastroControleId extends Cadastro{
	private static CadastroControleId instancia;

	public CadastroControleId(){

	}

	public static CadastroControleId getInstancia(){
		if (instancia == null){
			instancia = new CadastroControleId();
		}
		return instancia;
	}
	
	
	public IRepositorioControleId getRepositorio() {
		return (IRepositorioControleId) super.getRepositorio(IRepositorio.REPOSITORIO_CONTROLE_ID);
	}

	public Long retornaMaxId(Class classe){
		return getRepositorio().retornaMaxId(classe);
	}

	public Controle getControle(Class classe) throws AppException {
		Controle retorno = getRepositorio().getControle(classe);		
		if (retorno == null) {
			retorno = new Controle();
			retorno.setChave(classe.getSimpleName());
			retorno.setValor(retornaMaxId(classe)+1);
			getRepositorio().inserirControle(retorno);
		} else {
			retorno.setValor(retorno.getValor()+1);
			atualizarControle(retorno);
		}
		return retorno;
		
	}
	
	public void atualizarControle(Controle controle) throws AppException {		
		getRepositorio().atualizarControle(controle);
	}
}
