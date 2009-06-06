package com.infinity.datamarket.comum.operacao;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioOperacao extends Repositorio implements IRepositorioOperacao{
	private static RepositorioOperacao instancia;
//	private static Class CLASSE = Operacao.class;
	private RepositorioOperacao(){}
	public static RepositorioOperacao getInstancia(){
		if (instancia == null){
			instancia = new RepositorioOperacao();
		}
		return instancia;
	}


	public void alterar(Operacao operacao)throws AppException{
		update(operacao);
	}
	
	public void inserir(Operacao operacao)throws AppException{
		insert(operacao);
	}

	public Operacao consultarPorPK(OperacaoPK pk) throws AppException{
		return(Operacao) findById(Operacao.class, pk);
		
	}
		
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}

	
}
