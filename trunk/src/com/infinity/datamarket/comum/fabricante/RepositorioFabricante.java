package com.infinity.datamarket.comum.fabricante;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioFabricante extends Repositorio implements IRepositorioFabricante{
	
	private static RepositorioFabricante instancia;
	private static Class CLASSE = Fabricante.class;
	private RepositorioFabricante(){}
	public static RepositorioFabricante getInstancia(){
		if (instancia == null){
			instancia = new RepositorioFabricante();
		}
		return instancia;
	}
	public Fabricante consultarPorId(Long id) throws AppException{
		return (Fabricante) findById(CLASSE, id);
	}
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(Fabricante fabricante) throws AppException{
		insert(fabricante);
		inserirDadoLote(fabricante);
		
	}
	public void alterar(Fabricante fabricante) throws AppException{
		update(fabricante);
		alterarDadoLote(fabricante);
	}
	public void excluir(Fabricante fabricante) throws AppException{
		remove(fabricante);
		excluirDadoLote(fabricante);
	}

}
