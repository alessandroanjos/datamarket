package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroAutorizadora extends Cadastro{
	private static CadastroAutorizadora instancia;
	
	private CadastroAutorizadora(){}
	public static CadastroAutorizadora getInstancia(){
		if (instancia == null){
			instancia = new CadastroAutorizadora();
		}
		return instancia;
	}
	

	public IRepositorioAutorizadora getRepositorio() {
	
		return (IRepositorioAutorizadora) super.getRepositorio(IRepositorio.REPOSITORIO_AUTORIZADORA);
	}	

	public Autorizadora consultarPorId(Long id) throws AppException{
		return (Autorizadora) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(Autorizadora autorizadora) throws AppException{
		getRepositorio().inserir(autorizadora);
	}
	
	public void alterar(Autorizadora autorizadora) throws AppException{
		getRepositorio().alterar(autorizadora);
	}
	
	public void excluir(Autorizadora autorizadora) throws AppException{
		getRepositorio().excluir(autorizadora);
	}

}
