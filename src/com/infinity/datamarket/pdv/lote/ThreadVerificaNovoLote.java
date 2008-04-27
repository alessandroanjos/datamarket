package com.infinity.datamarket.pdv.lote;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.lote.LoteServerRemote;
import com.infinity.datamarket.pdv.util.ServerConfig;
import com.infinity.datamarket.pdv.util.ServiceLocator;
import com.infinity.datamarket.transaction.TransactionServerRemote;

public class ThreadVerificaNovoLote extends Thread{
	private int numeroLote;
	private boolean novoLote;
	
	public int getNumeroLote() {
		return numeroLote;
	}
	public void setNumeroLote(int numeroLote) {
		this.numeroLote = numeroLote;
	}
	public void run(){
		System.out.println("---------------------------------------------------" );
		System.out.println("-- INICIANDO SERVICO DE VERIFICAÇÃO DE NOVO LOTE --" );
		System.out.println("---------------------------------------------------" );
		
		while(true){
			try {
				Thread.currentThread().sleep(ServerConfig.SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try{ 
				LoteServerRemote remote = (LoteServerRemote) ServiceLocator.getJNDIObject(ServerConfig.LOTE_SERVER_JNDI);
				if (remote != null){	
					novoLote = remote.verificaNovoLoteLiberado(numeroLote);
					System.out.println("-- NOVO LOTE LIBERADO --" );
				}
			}catch (Throwable e){
				System.out.println(e.getMessage());
				//e.printStackTrace();
			}
				
		}
	}
	public boolean existeNovoLote() {
		return novoLote;
	}
}
