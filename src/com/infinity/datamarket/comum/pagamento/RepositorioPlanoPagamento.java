package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioPlanoPagamento extends Repositorio implements IRepositorioPlanoPagamento{
	private static RepositorioPlanoPagamento instancia;
	private static Class CLASSE = PlanoPagamento.class;
	private static Class CLASSE_PLANO_AVISTA = PlanoPagamentoAVista.class;
	private static Class CLASSE_PLANO_APRAZO = PlanoPagamentoAPrazo.class;
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
	
	// Plano de Pagamento A Vista
	public Collection consultarTodosAVista() throws AppException{
		return findAll(CLASSE_PLANO_AVISTA);
	}
	
	// Plano de Pagamento Pre-datado
	public Collection consultarTodosAPrazo() throws AppException{
		return findAll(CLASSE_PLANO_APRAZO);
	}

}