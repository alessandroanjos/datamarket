package com.infinity.datamarket.comum.conta;

import java.util.Collection;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioMovimentacaoBancaria extends Repositorio implements IRepositorioMovimentacaoBancaria{
	
	private static RepositorioMovimentacaoBancaria instancia;
	private static Class CLASSE = MovimentacaoBancaria.class;
	private RepositorioMovimentacaoBancaria(){}
	public static RepositorioMovimentacaoBancaria getInstancia(){
		if (instancia == null){
			instancia = new RepositorioMovimentacaoBancaria();
		}
		return instancia;
	}

	public MovimentacaoBancaria consultarPorId(Long id) throws AppException{
		return (MovimentacaoBancaria) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		insert(movimentacaoBancaria);
	}
	
	public void alterar(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		update(movimentacaoBancaria);
	}
	
	public void excluir(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		remove(movimentacaoBancaria);
	}

}
