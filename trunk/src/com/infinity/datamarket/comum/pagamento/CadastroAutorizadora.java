package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroAutorizadora extends Cadastro{
	private static CadastroAutorizadora instancia;
	private static Class CLASSE = Autorizadora.class;
	private CadastroAutorizadora(){}
	public static CadastroAutorizadora getInstancia(){
		if (instancia == null){
			instancia = new CadastroAutorizadora();
		}
		return instancia;
	}

	public Autorizadora consultarPorId(Long id) throws AppException{
		return (Autorizadora) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Autorizadora autorizadora) throws AppException{
		getRepositorio().insert(autorizadora);
		inserirDadoLote(autorizadora);
	}
	
	public void alterar(Autorizadora autorizadora) throws AppException{
		getRepositorio().update(autorizadora);
		alterarDadoLote(autorizadora);
	}
	
	public void excluir(Autorizadora autorizadora) throws AppException{
		getRepositorio().remove(autorizadora);
		excluirDadoLote(autorizadora);
	}

}
