package com.infinity.datamarket.comum.transacao;

import java.math.BigDecimal;
import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.totalizadores.CadastroTotalizadores;
import com.infinity.datamarket.comum.totalizadores.ConstantesTotalizadoresNaoFiscais;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroTransacao extends Cadastro{
	private static CadastroTransacao instancia;
	private CadastroTransacao(){}
	public static CadastroTransacao getInstancia(){
		if (instancia == null){
			instancia = new CadastroTransacao();
		}
		return instancia;
	}

	private CadastroTotalizadores getCadastroTotalizadores(){
		return CadastroTotalizadores.getInstancia();
	}

	public void inserir(Transacao trans) throws AppException{
		getRepositorio().insert(trans);
		if (trans instanceof TransacaoResuprimento){
			TransacaoResuprimento transResuprimento = (TransacaoResuprimento) trans;
			BigDecimal valor = transResuprimento.getValor();
			getCadastroTotalizadores().incrementarTotalizador(ConstantesTotalizadoresNaoFiscais.SUPRIMENTO, valor);
		}else if (trans instanceof TransacaoSangria){
			TransacaoSangria transSangria = (TransacaoSangria) trans;
			BigDecimal valor = transSangria.getValor();
			getCadastroTotalizadores().incrementarTotalizador(ConstantesTotalizadoresNaoFiscais.SANGRIA, valor);
		}else if (trans instanceof TransacaoEntradaOperador){
			TransacaoEntradaOperador transEntradaOperador = (TransacaoEntradaOperador) trans;
			BigDecimal valor = transEntradaOperador.getFundoTroco();
			getCadastroTotalizadores().incrementarTotalizador(ConstantesTotalizadoresNaoFiscais.SUPRIMENTO, valor);
		}else if (trans instanceof TransacaoCancelamento){
			TransacaoCancelamento transCanc = (TransacaoCancelamento) trans;
			TransacaoPK pk = new TransacaoPK(transCanc.getLojaCancelada(),transCanc.getComponenteCancelado(), transCanc.getNumeroTransacaoCancelada(),transCanc.getDataTransacaoCancelada());
			TransacaoVenda transVenda = (TransacaoVenda) consultarPorPK(pk);
			transVenda.setSituacao(TransacaoVenda.CANCELADO);
			atualizar(transVenda);
		}
	}
	
	public Transacao consultarPorPK(TransacaoPK pk) throws AppException{
		return (Transacao) getRepositorio().findById(Transacao.class, pk);
	}
	
	private void atualizar(Transacao trans) throws AppException{
		getRepositorio().update(trans);
	}
	
	public void atualizaTransacaoProcessada(Transacao trans) throws AppException{
		trans.setStatus(Transacao.PROCESSADO);
		atualizar(trans);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}



}
