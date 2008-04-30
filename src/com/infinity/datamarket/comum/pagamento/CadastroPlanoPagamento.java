package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;
import java.util.List;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroPlanoPagamento extends Cadastro{
	private static CadastroPlanoPagamento instancia;
	private static Class CLASSE = PlanoPagamento.class;
	private CadastroPlanoPagamento(){}
	public static CadastroPlanoPagamento getInstancia(){
		if (instancia == null){
			instancia = new CadastroPlanoPagamento();
		}
		return instancia;
	}

	public PlanoPagamento consultarPorId(Long id) throws AppException{
		return (PlanoPagamento) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(PlanoPagamento planoPagamento) throws AppException{
		getRepositorio().insert(planoPagamento);
		inserirDadoLote(planoPagamento);
	}
	
	public void alterar(PlanoPagamento planoPagamento) throws AppException{
		getRepositorio().update(planoPagamento);
		alterarDadoLote(planoPagamento);
	}
	
	public void excluir(PlanoPagamento planoPagamento) throws AppException{
		getRepositorio().remove(planoPagamento);
		excluirDadoLote(planoPagamento);
	}
}
