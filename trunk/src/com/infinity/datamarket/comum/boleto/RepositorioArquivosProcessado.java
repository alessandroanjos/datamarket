package com.infinity.datamarket.comum.boleto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioArquivosProcessado extends Repositorio implements IRepositorioArquivosProcessado{
	
	private static RepositorioArquivosProcessado instancia;
	private static Class CLASSE = ArquivoProcessado.class;
	private RepositorioArquivosProcessado(){}
	public static RepositorioArquivosProcessado getInstancia(){
		if (instancia == null){
			instancia = new RepositorioArquivosProcessado();
		}
		return instancia;
	}

	public ArquivoProcessado consultarPorId(Long id) throws AppException{
		return (ArquivoProcessado) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(ArquivoProcessado ArquivosProcessado) throws AppException{
		insert(ArquivosProcessado);
		inserirDadoLote(ArquivosProcessado);
	}
	
	public void alterar(ArquivoProcessado ArquivosProcessado) throws AppException{
		update(ArquivosProcessado);
		alterarDadoLote(ArquivosProcessado);
	}
	
	public void excluir(ArquivoProcessado ArquivosProcessado) throws AppException{
		remove(ArquivosProcessado);
		excluirDadoLote(ArquivosProcessado);
	}


}
