package com.infinity.datamarket.pdv.transacao;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.util.ServerConfig;
import com.infinity.datamarket.pdv.util.ServiceLocator;
import com.infinity.datamarket.transaction.TransactionServerRemote;

public class ThreadEnviaTransacao extends Thread{
		
	public void run(){
		System.out.println("---------------------------------------------" );
		System.out.println("-- INICIANDO SERVICO DE ENVIO DE TRANSACAO --" );
		System.out.println("---------------------------------------------" );
		while(true){
			try {
				Thread.currentThread().sleep(ServerConfig.SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try{
				Collection transacoes = getTransacoesNaoProcessadas();
				Iterator i = transacoes.iterator();
				 
				TransactionServerRemote remote = (TransactionServerRemote) ServiceLocator.getJNDIObject(ServerConfig.TRANSACTION_SERVER_JNDI);
				if (remote != null){	
					while(i.hasNext()){
						Transacao trans = (Transacao) i.next();
						System.out.println("PROCESSANDO TRANSAÇÃO >> "+trans.getPk());
						try{
							remote.inserirTransacao(trans);
							Fachada.getInstancia().atualizaTransacaoProcessada(trans);
							System.out.println("TRANSAÇÃO PROCESSADA COM SUCESSO");
						}catch(AppException e){
							System.out.println("ERRO NO PROCESSAMENTO DA TRANSAÇÃO");
							e.printStackTrace();
						}
					}
				}else{
					System.out.println("TRANSAÇÕES NÃO PROCESSADAS");
				}
			}catch (Throwable e){
				e.printStackTrace();
			}
				
		}
	}
	
	private Collection getTransacoesNaoProcessadas() throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(Transacao.class);
		filter.addProperty("status", Transacao.NAO_PROCESSADO);
		return Fachada.getInstancia().consultarTransacao(filter);
	}
	

	public static void main(String[] a){
		ThreadEnviaTransacao t = new ThreadEnviaTransacao();
		t.start();
	}
}
