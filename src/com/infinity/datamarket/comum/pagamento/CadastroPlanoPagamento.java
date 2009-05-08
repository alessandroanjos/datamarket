package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroPlanoPagamento extends Cadastro{
	private static CadastroPlanoPagamento instancia;
	private CadastroPlanoPagamento(){}
	public static CadastroPlanoPagamento getInstancia(){
		if (instancia == null){
			instancia = new CadastroPlanoPagamento();
		}
		return instancia;
	}
	
	
	public IRepositorioPlanoPagamento  getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioPlanoPagamento) super.getRepositorio(IRepositorio.REPOSITORIO_PLANO_PAGAMENTO);
	}

	public PlanoPagamento consultarPorId(Long id) throws AppException{
		return (PlanoPagamento) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(PlanoPagamento planoPagamento) throws AppException{
		getRepositorio().inserir(planoPagamento);
	}
	
	public void alterar(PlanoPagamento planoPagamento) throws AppException{
		getRepositorio().alterar(planoPagamento);
	}
	
	public void excluir(PlanoPagamento planoPagamento) throws AppException{
		getRepositorio().excluir(planoPagamento);
	}
	
	// Plano de Pagamento Pre-datado
	public Collection consultarTodosPreDatado() throws AppException{
		return getRepositorio().consultarTodosPreDatado();
	}
	
}
