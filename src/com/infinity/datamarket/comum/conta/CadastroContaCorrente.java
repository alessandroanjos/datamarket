package com.infinity.datamarket.comum.conta;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroContaCorrente extends Cadastro{
	private static CadastroContaCorrente instancia;
	
	private CadastroContaCorrente(){}
	public static CadastroContaCorrente getInstancia(){
		if (instancia == null){
			instancia = new CadastroContaCorrente();
		}
		return instancia;
	}
	
	public IRepositorioContaCorrente getRepositorio() {
		return (IRepositorioContaCorrente) super.getRepositorio(IRepositorio.REPOSITORIO_CONTA_CORRENTE);
	}

	public ContaCorrente consultarPorId(Long id) throws AppException{
		return (ContaCorrente) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(ContaCorrente contaCorrente) throws AppException{
		getRepositorio().inserir(contaCorrente);
	}
	
	public void alterar(ContaCorrente contaCorrente) throws AppException{
		getRepositorio().alterar(contaCorrente);
	}
	
	public void excluir(ContaCorrente contaCorrente) throws AppException{
		getRepositorio().excluir(contaCorrente);
	}

}
