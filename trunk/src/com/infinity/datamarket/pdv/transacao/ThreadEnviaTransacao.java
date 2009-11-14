package com.infinity.datamarket.pdv.transacao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Hibernate;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.boleto.Boleto;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.transacao.TransacaoPagamento;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
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
			RepositoryManagerHibernateUtil.currentSession();
			try{
				Collection transacoes = getTransacoesNaoProcessadas();
				Iterator i = transacoes.iterator();				
//				TransactionServerRemote remote = (TransactionServerRemote) ServiceLocator.getJNDIObject(ServerConfig.TRANSACTION_SERVER_JNDI);
				//if (remote != null){	
					while(i.hasNext()){
						Transacao trans = (Transacao) i.next();						
						if (trans instanceof TransacaoVenda){
							Hibernate.initialize(((TransacaoVenda)trans).getEventosTransacao());							
						}else if(trans instanceof TransacaoPagamento){
							Hibernate.initialize(((TransacaoPagamento)trans).getEventosTransacao());
						}
						System.out.println("PROCESSANDO TRANSA플O >> "+trans.getPk());
						try{
//							URL urlCon = new URL("http://" + loja.getNumeroIp() + ":" + loja.getNumeroPortaServlet() + "/EnterpriseServer/GerarBoletoServlet.servlet");
							URL urlCon = new URL("http://" +
									ServerConfig.HOST_SERVIDOR_ES +
									":" +
									ServerConfig.PORTA_SERVIDOR_ES +
									"/" +
									ServerConfig.CONTEXTO_SERVIDOR_ES +
									"/" +
									ServerConfig.SERVLET_RECEPTOR_TRANSACAO);
							URLConnection huc1 = urlCon.openConnection();
	
							huc1.setAllowUserInteraction(true);
							huc1.setDoOutput(true);
	
							ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
							output.writeObject(trans);
							ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
							Object obj = input.readObject();
							if (obj instanceof String && obj.equals("OK")) {
								Fachada.getInstancia().atualizaTransacaoProcessada(trans);
								System.out.println("TRANSA플O PROCESSADA COM SUCESSO");
							} else if (obj instanceof Exception) {
								((Exception) obj).printStackTrace();
								System.out.println("ERRO NO PROCESSAMENTO DA TRANSA플O");
								break;
							}
						} catch (Exception e) {
							System.out.println("ERRO NO PROCESSAMENTO DA TRANSA플O");
							e.printStackTrace();
							break;
						}

//						try{
//							
//							remote.inserirTransacao(trans);
//							Fachada.getInstancia().atualizaTransacaoProcessada(trans);
//							System.out.println("TRANSA플O PROCESSADA COM SUCESSO");
//						}catch(AppException e){
//							System.out.println("ERRO NO PROCESSAMENTO DA TRANSA플O");
//							e.printStackTrace();
//						}
					}
//				}else{
//					System.out.println("TRANSA합ES N홒 PROCESSADAS");
//				}
			}catch (Throwable e){
				System.out.println(e.getMessage());
				//e.printStackTrace();
			}
			RepositoryManagerHibernateUtil.closeSession();
				
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
