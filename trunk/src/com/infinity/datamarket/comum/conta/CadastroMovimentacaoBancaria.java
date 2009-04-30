package com.infinity.datamarket.comum.conta;

import java.util.Collection;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.Constantes;

public class CadastroMovimentacaoBancaria extends Cadastro {
	private static CadastroMovimentacaoBancaria instancia;
	private static Class CLASSE = MovimentacaoBancaria.class;
	private CadastroMovimentacaoBancaria(){}
	public static CadastroMovimentacaoBancaria getInstancia(){
		if (instancia == null){
			instancia = new CadastroMovimentacaoBancaria();
		}
		return instancia;
	}

	public MovimentacaoBancaria consultarPorId(Long id) throws AppException{
		return (MovimentacaoBancaria) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		getRepositorio().insert(movimentacaoBancaria);
		ContaCorrente conta = Fachada.getInstancia().consultarContaCorrentePorID(movimentacaoBancaria.getConta().getId().toString());
		if (movimentacaoBancaria.getTipo().equals(Constantes.TIPO_OPERACAO_DEBITO)) {
			conta.setSaldo(conta.getSaldo().subtract(movimentacaoBancaria.getValor()));
		} else {
			conta.setSaldo(conta.getSaldo().add(movimentacaoBancaria.getValor()));
		}
		getRepositorio().update(conta);
	}
	
	public void alterar(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		getRepositorio().update(movimentacaoBancaria);
	}
	
	public void excluir(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		getRepositorio().remove(movimentacaoBancaria);
	}
}
