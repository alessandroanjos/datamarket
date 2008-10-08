package com.infinity.datamarket.operacao;

import javax.ejb.Remote;

import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.util.AppException;

@Remote
public interface OperacaoServerRemote {
	public Operacao consultarOperacaoPorID(OperacaoPK pk) throws AppException;
}
