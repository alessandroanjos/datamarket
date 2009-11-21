package com.infinity.datamarket.operacao;

import javax.ejb.Stateless;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;

@Stateless
public class OperacaoServer implements OperacaoServerLocal, OperacaoServerRemote {
	
	public Operacao consultarOperacaoPorID(OperacaoPK pk) throws AppException{		
		RepositoryManagerHibernateUtil.getInstancia().currentSession();
		Operacao operacao = Fachada.getInstancia().consultarOperacaoPorPK(pk);
		RepositoryManagerHibernateUtil.getInstancia().closeSession();
		return operacao;
	}
	
	public void alteraStatusOperacao(OperacaoPK pk, int status) throws AppException{
		RepositoryManagerHibernateUtil.getInstancia().currentSession();
		Fachada.getInstancia().alterarStatusOperacao(pk, status);
		RepositoryManagerHibernateUtil.getInstancia().closeSession();
	}
}
