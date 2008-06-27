package com.infinity.datamarket.comum.clientepagamento;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroClientePagamento extends Cadastro {
	private static CadastroClientePagamento instancia;
	private static Class CLASSE = ClientePagamento.class;
	private CadastroClientePagamento(){}
	
	public static CadastroClientePagamento getInstancia(){
		if (instancia == null){
			instancia = new CadastroClientePagamento();
		}
		return instancia;
	}
	
	public ClientePagamento consultarPorId(Long id) throws AppException{
		return (ClientePagamento) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public ClientePagamento consultarPorPK(Long id) throws AppException{
		return (ClientePagamento) getRepositorio().findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addOrderByProperty("dataPagamento", PropertyFilter.DESC);
		return getRepositorio().filter(filter, false);
//		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(ClientePagamento clientePagamento) throws AppException{
		getRepositorio().insert(clientePagamento);
	}
	
	public void alterar(ClientePagamento clientePagamento) throws AppException{
		getRepositorio().update(clientePagamento);
	}
	
	public void excluir(ClientePagamento clientePagamento) throws AppException{
		getRepositorio().remove(clientePagamento);
	}
	
	public Long retornaMaxIDClientePagamento() throws AppException{
		Long maxIDClientePagamento = new Long(0);
		
		try {
			Collection c = consultarTodos();
			if (c != null && c.size() > 0){
				Iterator it = c.iterator();
				while (it.hasNext()){
					ClientePagamento cp = (ClientePagamento)it.next();
					if(cp.getId().longValue() > maxIDClientePagamento.longValue()){
						maxIDClientePagamento = cp.getId();						
					}
				}				
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Query q = RepositoryManagerHibernateUtil.currentSession().createQuery("select max(i");
//		q.list();
		return ++maxIDClientePagamento;
	}
}
