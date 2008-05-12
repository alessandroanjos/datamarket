package com.infinity.datamarket.pdv.lote;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.lote.DadoLote;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.lote.LoteServerRemote;
import com.infinity.datamarket.pdv.util.ServerConfig;
import com.infinity.datamarket.pdv.util.ServiceLocator;

public class ThreadVerificaNovoLote extends Thread implements Serializable{
	/**
	 * 
	 */
	
	public ThreadVerificaNovoLote(int numeroLote, int numeroLoja){
		this.numeroLote = numeroLote;
		this.numeroLoja = numeroLoja;
	}
	
	private static final long serialVersionUID = -849614647967981958L;
	private int numeroLote;
	private int numeroLoja;
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
	
	public void atualizaLote(){
		try{
			LoteServerRemote remote = (LoteServerRemote) ServiceLocator.getJNDIObject(ServerConfig.LOTE_SERVER_JNDI);
			while(remote.verificaNovoLoteLiberado(numeroLote)){			
				if (remote != null){	
					Collection c = remote.getLote(numeroLote, numeroLoja);
					System.out.println("ATUALIZANDO O LOTE "+ (numeroLote + 1));
					Iterator i = c.iterator();
					while(i.hasNext()){
						AtualizadorLote atualizador = AtualizadorLote.getInstancia();
						DadoLote dado = (DadoLote) i.next();
						if (dado.getOperacao().equals(dado.INSERIR)){
							atualizador.incluir(dado.getDado());
						}else if (dado.getOperacao().equals(dado.ALTERAR)){
							atualizador.alterar(dado.getDado());
						}else if (dado.getOperacao().equals(dado.EXCLUIR)){	
							atualizador.excluir(dado.getDado());
						}
					}					
					numeroLote = numeroLote + 1;
					Parametro p = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOTE);
        			p.setValorInteiro(numeroLote);
        			ConcentradorParametro.getInstancia().atualizarParametro(p);
        			System.out.println("FIM DA ATUALIZAÇÃO DO LOTE "+numeroLote);
				}
			}
		}catch (Throwable e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
		novoLote = false;
	}
	public int getNumeroLoja() {
		return numeroLoja;
	}
	public void setNumeroLoja(int numeroLoja) {
		this.numeroLoja = numeroLoja;
	}
	
	
	
}
