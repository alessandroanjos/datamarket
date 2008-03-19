package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroFormaRecebimento extends Cadastro{
	private static CadastroFormaRecebimento instancia;
	private static Class CLASSE = FormaRecebimento.class;
	private CadastroFormaRecebimento(){}
	public static CadastroFormaRecebimento getInstancia(){
		if (instancia == null){
			instancia = new CadastroFormaRecebimento();
		}
		return instancia;
	}

	public FormaRecebimento consultarPorId(Long id) throws AppException{
		return (FormaRecebimento) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(FormaRecebimento formaRecebimento) throws AppException{
		getRepositorio().insert(formaRecebimento);
	}
	
	public void alterar(FormaRecebimento formaRecebimento) throws AppException{
		getRepositorio().update(formaRecebimento);
	}
	
	public void excluir(FormaRecebimento formaRecebimento) throws AppException{
		getRepositorio().remove(formaRecebimento);
	}

}
