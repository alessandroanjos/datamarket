package com.infinity.datamarket.comum.conta;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.IRepositorio;
import com.infinity.datamarket.comum.util.ObjetoClonavel;

public class CadastroMovimentacaoBancaria extends Cadastro {
	private static CadastroMovimentacaoBancaria instancia;
//	private static Class CLASSE = MovimentacaoBancaria.class;
	
	private Set<ContaCorrente> hashContas = new TreeSet<ContaCorrente>();
	
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
		
		ContaCorrente conta = Fachada.getInstancia().consultarContaCorrentePorID(movimentacaoBancaria.getConta().getId().toString());
		
		if(hashContas != null){
			Iterator<ContaCorrente> it = hashContas.iterator();
			while (it.hasNext()){
				ContaCorrente cc = it.next();
				if(cc.getId().equals(conta.getId())){
					conta = cc;
					break;
				}
			}
		}else{
			hashContas = new TreeSet<ContaCorrente>();
		}
		
		movimentacaoBancaria.setSaldoAnteriorConta(conta.getSaldo());
		
		if (movimentacaoBancaria.getTipo().equals(Constantes.TIPO_OPERACAO_DEBITO)) {
			conta.setSaldo(conta.getSaldo().subtract(movimentacaoBancaria.getValor()));
		} else {
			conta.setSaldo(conta.getSaldo().add(movimentacaoBancaria.getValor()));
		}
		
		hashContas.add((ContaCorrente)ObjetoClonavel.clone(conta));
		getRepositorio().inserir(movimentacaoBancaria);
	}
	
	public void atualizarContasCorrentes() throws AppException {
		Set<ContaCorrente> hashContas = CadastroMovimentacaoBancaria.getInstancia().getHashContas();
		
		Iterator<ContaCorrente> it = hashContas.iterator();
		
		while (it.hasNext()){
			ContaCorrente cTmp = (ContaCorrente)it.next();
			ContaCorrente c = CadastroContaCorrente.getInstancia().consultarPorId(cTmp.getId());
			c.setSaldo(cTmp.getSaldo());
			RepositorioContaCorrente repCtaCorrente = (RepositorioContaCorrente)super.getRepositorio(IRepositorio.REPOSITORIO_CONTA_CORRENTE);
			repCtaCorrente.alterar(c);			
		}
		
		
		this.setHashContas(new TreeSet<ContaCorrente>());
	}
	
	public void alterar(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		getRepositorio().alterar(movimentacaoBancaria);
	}
	
	public void excluir(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		getRepositorio().excluir(movimentacaoBancaria);
	}
	public Set<ContaCorrente> getHashContas() {
		return hashContas;
	}
	public void setHashContas(Set<ContaCorrente> hashContas) {
		this.hashContas = hashContas;
	}
}
