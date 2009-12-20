package com.infinity.datamarket.pdv.transacao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Hibernate;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoDevolucao;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class ThreadEnviaOperacao extends Thread{
		
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
			RepositoryManagerHibernateUtil.getInstancia().currentSession();
			try{
				Collection operacoes = getOperacoesNaoEnviadas();
				Iterator i = operacoes.iterator();				
//				TransactionServerRemote remote = (TransactionServerRemote) ServiceLocator.getJNDIObject(ServerConfig.TRANSACTION_SERVER_JNDI);
				//if (remote != null){	
					while(i.hasNext()){
						Operacao opera = (Operacao) i.next();						
						if (opera instanceof OperacaoPedido){
							Hibernate.initialize(((OperacaoPedido)opera).getEventosOperacao());
							Hibernate.initialize(((OperacaoPedido)opera).getCliente());
							Hibernate.initialize(((OperacaoPedido)opera).getTransacaoVenda());
							Hibernate.initialize(((OperacaoPedido)opera).getUsuarioVendedor());
						}else if(opera instanceof OperacaoDevolucao){
							Hibernate.initialize(((OperacaoDevolucao)opera).getEventosOperacao());
							Hibernate.initialize(((OperacaoDevolucao)opera).getCliente());
						}

						System.out.println("PROCESSANDO TRANSA플O >> "+opera.getPk());
						try{
							URL urlCon = new URL("http://" +
									ServerConfig.HOST_SERVIDOR_ES +
									":" +
									ServerConfig.PORTA_SERVIDOR_ES +
									"/" +
									ServerConfig.CONTEXTO_SERVIDOR_ES +
									"/" +
									ServerConfig.RECEPTOR_OPERACAO_SERVLET);
							URLConnection huc1 = urlCon.openConnection();
	
							huc1.setAllowUserInteraction(true);
							huc1.setDoOutput(true);
	
							ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
							output.writeObject(opera);
							ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
							Object obj = input.readObject();
							if (obj instanceof String && obj.equals("OK")) {
								Fachada.getInstancia().atualizaOperacaoEnviada(opera);
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
					}
			}catch (Throwable e){
				System.out.println(e.getMessage());
				//e.printStackTrace();
			}
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
		}
	}
	
	private Collection getOperacoesNaoEnviadas() throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(Operacao.class);
		filter.addProperty("status", ConstantesOperacao.PARA_ENVIAR);
		return Fachada.getInstancia().consultarOperacao(filter);
	}

	public static void main(String[] a){
		ThreadEnviaTransacao t = new ThreadEnviaTransacao();
		t.start();
	}
}
