package com.infinity.datamarket.comum.banco;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroBanco extends Cadastro{
	private static CadastroBanco instancia;
	private CadastroBanco(){}
	public static CadastroBanco getInstancia(){
		if (instancia == null){
			instancia = new CadastroBanco();
		}
		return instancia;
	}
	
	
	public IRepositorioBanco getRepositorio() {
		return (IRepositorioBanco) super.getRepositorio(IRepositorio.REPOSITORIO_BANCO);
	}

	public Banco consultarPorId(Long id) throws AppException{
		return (Banco) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(Banco banco) throws AppException{
		getRepositorio().inserir(banco);
	}
	
	public void alterar(Banco banco) throws AppException{
		getRepositorio().alterar(banco);
	}
	
	public void excluir(Banco banco) throws AppException{
		getRepositorio().excluir(banco);
	}

}
