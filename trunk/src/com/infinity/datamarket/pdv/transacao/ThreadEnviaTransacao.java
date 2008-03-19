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
import com.infinity.datamarket.transaction.TransactionServerRemote;

public class ThreadEnviaTransacao extends Thread{
	
	private static final String INITIAL_CONTEXT_FACTORY;
	private static final String PROVIDER_URL;
	private static final String TRANSACTION_SERVER_JNDI;
	private static final int SLEEP;
	
	static{
	
		ResourceBundle rb = ResourceBundle.getBundle("TransactionServer");
		INITIAL_CONTEXT_FACTORY = rb.getString("INITIAL_CONTEXT_FACTORY");
		PROVIDER_URL = rb.getString("PROVIDER_URL");
		TRANSACTION_SERVER_JNDI = rb.getString("TRANSACTION_SERVER_JNDI");
		SLEEP = Integer.parseInt(rb.getString("SLEEP"));
	}
	
	public ThreadEnviaTransacao(){
		criaPropriedades();
	}
	
	public void run(){
		System.out.println("---------------------------------------------" );
		System.out.println("-- INICIANDO SERVICO DE ENVIO DE TRANSACAO --" );
		System.out.println("---------------------------------------------" );
		while(true){
			try {
				Thread.currentThread().sleep(SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try{
				Collection transacoes = getTransacoesNaoProcessadas();
				Iterator i = transacoes.iterator();
				Context ctx = new InitialContext (prop); 
				TransactionServerRemote remote = (TransactionServerRemote) ctx.lookup (TRANSACTION_SERVER_JNDI);
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
			}catch(NamingException e){
				System.out.println("TRANSACTION SERVER NÃO ENCONTRADO");
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
	
	private Hashtable prop;
	
	private void criaPropriedades(){
		prop = new Hashtable (); 
		prop.put (InitialContext.INITIAL_CONTEXT_FACTORY, this.INITIAL_CONTEXT_FACTORY); 
		prop.put (InitialContext.PROVIDER_URL,this.PROVIDER_URL);
	}
	
	public static void main(String[] a){
		ThreadEnviaTransacao t = new ThreadEnviaTransacao();
		t.start();
	}
}
