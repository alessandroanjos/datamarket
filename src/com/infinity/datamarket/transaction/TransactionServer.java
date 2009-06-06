package com.infinity.datamarket.transaction;

import javax.ejb.Stateless;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.util.AppException;

@Stateless
public class TransactionServer implements TransactionServerRemote, TransactionServerLocal {
	public TransactionServer(){
		System.out.println("----------------------------------" );
		System.out.println("-- TRANSACTION SERVER INICIADO  --" );
		System.out.println("----------------------------------" );
		
	}
	
	public Fachada getFachada(){
		return Fachada.getInstancia();
	}
	public void inserirTransacao(Transacao transacao) throws AppException{
		getFachada().getInstancia().inserirTransacaoES(transacao);
		System.out.println("TRANSACAO INSERIDA >> "+transacao.getPk());
	}
	
}
