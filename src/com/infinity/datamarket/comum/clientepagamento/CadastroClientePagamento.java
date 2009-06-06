package com.infinity.datamarket.comum.clientepagamento;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroClientePagamento extends Cadastro {
	private static CadastroClientePagamento instancia;
//	private static Class CLASSE = ClientePagamento.class;
	private CadastroClientePagamento(){}
	
	public static CadastroClientePagamento getInstancia(){
		if (instancia == null){
			instancia = new CadastroClientePagamento();
		}
		return instancia;
	}
	
	public IRepositorioClientePagamento getRepositorio() {
		return (IRepositorioClientePagamento) super.getRepositorio(IRepositorio.REPOSITORIO_CLIENTE_PAGAMENTO);
	}
	
	public ClientePagamento consultarPorId(Long id) throws AppException{
		return (ClientePagamento) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public ClientePagamento consultarPorPK(Long id) throws AppException{
		return getRepositorio().consultarPorPK(id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(ClientePagamento clientePagamento) throws AppException{
		getRepositorio().inserir(clientePagamento);
	}
	
	public void alterar(ClientePagamento clientePagamento) throws AppException{
		getRepositorio().alterar(clientePagamento);
	}
	
	public void excluir(ClientePagamento clientePagamento) throws AppException{
		getRepositorio().excluir(clientePagamento);
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
			e.printStackTrace();
		}
		
		return ++maxIDClientePagamento;
	}
}
