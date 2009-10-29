package com.infinity.datamarket.comum.boleto;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioBoleto extends Repositorio implements IRepositorioBoleto{
	
	private static RepositorioBoleto instancia;
	
	private static final Class CLASSE = Boleto.class;
	
	public static RepositorioBoleto getInstancia(){
		if (instancia == null){
			instancia = new RepositorioBoleto();			
		}
		return instancia;
	}
	
	public void inserir(Boleto Boleto) throws AppException{
		insert(Boleto);
//		inserirDadoLote(Boleto);
	}
//	
//	public Collection consultar(IPropertyFilter filter) throws AppException{
//		return filter(filter, false);
//	}
//	
//	public Boleto consultarPorPK(Long id) throws AppException{
//		return (Boleto) findById(CLASSE, id);
//	}
//	
//	public Collection consultarTodos() throws AppException{
//		return findAll(CLASSE);
//	}
//	
//	public void alterar(Boleto Boleto) throws AppException{
//		update(Boleto);
//		alterarDadoLote(Boleto);
//	}
//	
//	public void excluir(Boleto Boleto) throws AppException{
//		remove(Boleto);
//		excluirDadoLote(Boleto);
//	}
}
