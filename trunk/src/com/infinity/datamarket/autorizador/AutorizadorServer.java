package com.infinity.datamarket.autorizador;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.Stateless;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;

@Stateless
public class AutorizadorServer implements AutorizadorServerRemote {
	
	
	public DadosAutorizacaoCartaoProprio autorizaTransacaoCartaoProprio(String cpfCnpj, BigDecimal valor) throws AutorizacaoException{
		RepositoryManagerHibernateUtil.getInstancia().currentSession();
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(Cliente.class);
		filter.addProperty("cpfCnpj", cpfCnpj);
		Collection clientes = null;
		try {
			clientes = Fachada.getInstancia().consultarCliente(filter);
		} catch (AppException e) {
			throw new AutorizacaoException("Tente Novamente");
		}
		if (clientes == null || clientes.size() == 0){
			throw new AutorizacaoException("Cliente Inexistente");
		}
		Cliente cli = (Cliente) clientes.iterator().next();
		
		PropertyFilter filterAutorizacao = new PropertyFilter();
		filterAutorizacao.setTheClass(AutorizacaoCartaoProprio.class);
		filterAutorizacao.addProperty("cliente.id", cli.getId());
		filterAutorizacao.addProperty("status", AutorizacaoCartaoProprio.STATUS_PENDENTE);
		Collection autorizacoes;
		try {
			autorizacoes = Fachada.getInstancia().consultarAutorizacaoCartaoProprio(filterAutorizacao);
		} catch (AppException e) {
			e.printStackTrace();
			throw new AutorizacaoException("Tente Novamente");
		}
		Iterator i = autorizacoes.iterator();
		BigDecimal valorPendente = BigDecimal.ZERO;
		while(i.hasNext()){
			AutorizacaoCartaoProprio aut = (AutorizacaoCartaoProprio) i.next();
			valorPendente = valorPendente.add(aut.getValor());
		}
		
		System.out.println("Valor Pendente > "+valorPendente.toString());
		
		BigDecimal valorDisponivel = cli.getValorLimiteDisponivel().subtract(valorPendente);
		
		System.out.println("Valor Disponivel > "+valorDisponivel.toString());
		
		if (valorDisponivel.compareTo(valor) < 0){
			throw new AutorizacaoException("Saldo Insuficiente");
		}
		
		AutorizacaoCartaoProprio aut = new AutorizacaoCartaoProprio();
		aut.setCliente(cli);
		Date dataTransacao = new Date();
		aut.setData(dataTransacao);
		aut.setStatus(AutorizacaoCartaoProprio.STATUS_PENDENTE);
		aut.setValor(valor);
		
		try {
			Fachada.getInstancia().inserirAutorizacaoCartaoProprio(aut);
		} catch (AppException e) {
			e.printStackTrace();
			throw new AutorizacaoException("Tente Novamente");		
		}
		
		DadosAutorizacaoCartaoProprio dados = new DadosAutorizacaoCartaoProprio();
		dados.setAutrizacao(aut.getId().toString());
		dados.setValor(valor);
		dados.setData(dataTransacao);
		dados.setNome(cli.getNomeCliente());
		RepositoryManagerHibernateUtil.getInstancia().closeSession();
		return dados;
	}
	
	
	
	public void confirmaTransacaoCartaoProprio(Long id) throws AutorizacaoException{
		RepositoryManagerHibernateUtil.getInstancia().currentSession();
		AutorizacaoCartaoProprio autorizacao;
		try {
			autorizacao = Fachada.getInstancia().consultarAutorizacaoCartaoProprioPorPK(id);
		} catch (AppException e) {
			throw new AutorizacaoException("Transacao Inexistente");
		}
		
		autorizacao.setStatus(AutorizacaoCartaoProprio.STATUS_CONCLUIDO);
		
		try {
			Fachada.getInstancia().alterarAutorizacaoCartaoProprio(autorizacao);
		} catch (AppException e) {
			throw new AutorizacaoException("Tente Novamente");		
		}
		
		
		try {
			Cliente cli = Fachada.getInstancia().consultarClientePorPK(autorizacao.getCliente().getId());
			cli.setValorLimiteDisponivel(cli.getValorLimiteDisponivel().subtract(autorizacao.getValor()));
			Fachada.getInstancia().alterarCliente(cli);
		} catch (AppException e) {
			throw new AutorizacaoException("Tente Novamente");		
		}
		RepositoryManagerHibernateUtil.getInstancia().closeSession();
		
	}
	
	
	public void desfazTransacaoCartaoProprio(Long id) throws AutorizacaoException{
		RepositoryManagerHibernateUtil.getInstancia().currentSession();
		AutorizacaoCartaoProprio autorizacao;
		try {
			autorizacao = Fachada.getInstancia().consultarAutorizacaoCartaoProprioPorPK(id);
		} catch (AppException e) {
			throw new AutorizacaoException("Transacao Inexistente");
		}
		
		autorizacao.setStatus(AutorizacaoCartaoProprio.STATUS_DESFEITO);
		
		try {
			Fachada.getInstancia().alterarAutorizacaoCartaoProprio(autorizacao);
		} catch (AppException e) {
			throw new AutorizacaoException("Tente Novamente");		
		}
		RepositoryManagerHibernateUtil.getInstancia().closeSession();
	}
	
	public DadosConsultaCartaoProprio consultaDebito(String cpfCnpj) throws AutorizacaoException{
		RepositoryManagerHibernateUtil.getInstancia().currentSession();
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(Cliente.class);
		filter.addProperty("cpfCnpj", cpfCnpj);
		Collection clientes = null;
		try {
			clientes = Fachada.getInstancia().consultarCliente(filter);
		} catch (AppException e) {
			throw new AutorizacaoException("Tente Novamente");
		}
		if (clientes == null || clientes.size() == 0){
			throw new AutorizacaoException("Cliente inválido");
		}
		Cliente cli = (Cliente) clientes.iterator().next();	
		DadosConsultaCartaoProprio resposta = new DadosConsultaCartaoProprio();
		if(cli.getTipoPessoa().equals(Cliente.PESSOA_FISICA)){
			resposta.setNome(cli.getNomeCliente().toUpperCase());
		}else{
			resposta.setNome(cli.getNomeFantasia().toUpperCase());
		}
		resposta.setValorDebito(cli.getValorLimiteCompras().subtract(cli.getValorLimiteDisponivel()));
		RepositoryManagerHibernateUtil.getInstancia().closeSession();
		return resposta;
	}
	
}
