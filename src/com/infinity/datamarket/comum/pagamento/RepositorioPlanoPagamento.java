package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioPlanoPagamento extends Repositorio implements IRepositorioPlanoPagamento{
	private static RepositorioPlanoPagamento instancia;
	private static Class CLASSE = PlanoPagamento.class;
	private static Class CLASSE_CHEQUE_PRE = PlanoPagamentoChequePredatado.class;
	private RepositorioPlanoPagamento(){}
	public static RepositorioPlanoPagamento getInstancia(){
		if (instancia == null){
			instancia = new RepositorioPlanoPagamento();
		}
		return instancia;
	}

	public PlanoPagamento consultarPorId(Long id) throws AppException{
		return (PlanoPagamento) findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	public void inserir(PlanoPagamento planoPagamento) throws AppException{
		insert(planoPagamento);
		inserirDadoLote(planoPagamento);
	}
	
	public void alterar(PlanoPagamento planoPagamento) throws AppException{
		update(planoPagamento);
		alterarDadoLote(planoPagamento);
	}
	
	public void excluir(PlanoPagamento planoPagamento) throws AppException{
		remove(planoPagamento);
		excluirDadoLote(planoPagamento);
	}
	
	// Plano de Pagamento Pre-datado
	public Collection consultarTodosPreDatado() throws AppException{
		return findAll(CLASSE_CHEQUE_PRE);
	}

}
