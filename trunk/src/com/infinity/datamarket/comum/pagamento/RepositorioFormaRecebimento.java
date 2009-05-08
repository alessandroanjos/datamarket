package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioFormaRecebimento extends Repositorio implements IRepositorioFormaRecebomento{
	private static RepositorioFormaRecebimento instancia;
	private static Class CLASSE = FormaRecebimento.class;
	private RepositorioFormaRecebimento(){}
	public static RepositorioFormaRecebimento getInstancia(){
		if (instancia == null){
			instancia = new RepositorioFormaRecebimento();
		}
		return instancia;
	}

	public FormaRecebimento consultarPorId(Long id) throws AppException{
		return (FormaRecebimento) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(FormaRecebimento formaRecebimento) throws AppException{
		insert(formaRecebimento);
		inserirDadoLote(formaRecebimento);
	}
	
	public void alterar(FormaRecebimento formaRecebimento) throws AppException{
		update(formaRecebimento);
		alterarDadoLote(formaRecebimento);
	}
	
	public void excluir(FormaRecebimento formaRecebimento) throws AppException{
		remove(formaRecebimento);
		excluirDadoLote(formaRecebimento);
	}

}
