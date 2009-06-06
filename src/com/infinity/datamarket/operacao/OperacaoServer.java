package com.infinity.datamarket.operacao;

import javax.ejb.Stateless;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.util.AppException;

@Stateless
public class OperacaoServer implements OperacaoServerLocal, OperacaoServerRemote {
	
	public Operacao consultarOperacaoPorID(OperacaoPK pk) throws AppException{		
		return Fachada.getInstancia().consultarOperacaoPorPK(pk);
	}
	
	public void alteraStatusOperacao(OperacaoPK pk, int status) throws AppException{
		Fachada.getInstancia().alterarStatusOperacao(pk, status);
	}
}
