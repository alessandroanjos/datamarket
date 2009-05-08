package com.infinity.datamarket.comum.conta;

import java.util.Collection;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.IRepositorio;

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
	
	
	public IRepositorioMovimentacaoBancaria getRepositorio() {
		return (IRepositorioMovimentacaoBancaria) super.getRepositorio(IRepositorio.REPOSITORIO_MOVIMENTACAO_BANCARIA);
	}
	
	public IRepositorioContaCorrente getRepositorioContaCorrente() {
		return (IRepositorioContaCorrente) super.getRepositorio(IRepositorio.REPOSITORIO_CONTA_CORRENTE);
	}

	public MovimentacaoBancaria consultarPorId(Long id) throws AppException{
		return (MovimentacaoBancaria) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		getRepositorio().inserir(movimentacaoBancaria);
		ContaCorrente conta = Fachada.getInstancia().consultarContaCorrentePorID(movimentacaoBancaria.getConta().getId().toString());
		if (movimentacaoBancaria.getTipo().equals(Constantes.TIPO_OPERACAO_DEBITO)) {
			conta.setSaldo(conta.getSaldo().subtract(movimentacaoBancaria.getValor()));
		} else {
			conta.setSaldo(conta.getSaldo().add(movimentacaoBancaria.getValor()));
		}
		getRepositorioContaCorrente().alterar(conta);
	}
	
	public void alterar(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		getRepositorio().alterar(movimentacaoBancaria);
	}
	
	public void excluir(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		getRepositorio().excluir(movimentacaoBancaria);
	}
}