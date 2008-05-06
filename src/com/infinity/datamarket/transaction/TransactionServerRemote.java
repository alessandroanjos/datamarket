package com.infinity.datamarket.transaction;

import javax.ejb.Remote;

import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.util.AppException;

@Remote
public interface TransactionServerRemote {
	public void inserirTransacao(Transacao transacao) throws AppException;
}
