package com.infinity.datamarket.pdv.lote;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.lote.LoteServerRemote;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOSArquivo;
import com.infinity.datamarket.pdv.gui.telas.swing.TelaCargaDados;
import com.infinity.datamarket.pdv.maquinaestados.Estado;
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
				novoLote =  verificarNovoLote(numeroLote);
				
				Estado estadoAtual = (Estado)GerenciadorPerifericos.getInstancia().getCmos().ler(CMOSArquivo.ESTADO_ATUAL);
				
        		if (estadoAtual != null && estadoAtual.getId().equals(Estado.DISPONIVEL)){
	        		//verifica se tem novo lote liberado
        			System.out.println("Maquina.ThreadProcessaMacro.run: threadVerificaNovoLote.existeNovoLote(): "+existeNovoLote());
        			if (existeNovoLote()){
        				System.out.println("HÁ UM NOVO LOTE LIBERADO");
        				TelaCargaDados tela = new TelaCargaDados(GerenciadorPerifericos.getInstancia().getWindow().getFrame().getWidth(),GerenciadorPerifericos.getInstancia().getWindow().getFrame().getHeight());
        				Thread thread = new Thread(tela);
        				thread.start();
	        			atualizaLote();
	        			GerenciadorPerifericos.getInstancia().getWindow().atualizaLote(getNumeroLote());
	        			tela.stop();	        			
	        		}else{
	        			System.out.println("NÃO HÁ NOVO LOTE LIBERADO");
	        		}
        		}

//				LoteServerRemote remote = (LoteServerRemote) ServiceLocator.getJNDIObject(ServerConfig.LOTE_SERVER_JNDI);
//				if (remote != null){	
//					novoLote = remote.verificaNovoLoteLiberado(numeroLote);
//				}
			}catch (Throwable e){
				System.out.println(e.getMessage());
				//e.printStackTrace();
			}
				
		}
	}
	public boolean existeNovoLote() {
		return novoLote;
	}
	
	private boolean verificarNovoLote(int numeroLote) throws Exception {
		boolean novoLote = false;
		try{
			URL urlCon = new URL("http://" +
					ServerConfig.HOST_SERVIDOR_ES +
					":" +
					ServerConfig.PORTA_SERVIDOR_ES +
					"/" +
					ServerConfig.CONTEXTO_SERVIDOR_ES +
					"/" +
					ServerConfig.SERVLET_VERIFICADOR_NOVO_LOTE + "?numeroLote=" + numeroLote);
			URLConnection huc1 = urlCon.openConnection();

			huc1.setAllowUserInteraction(true);
			huc1.setDoOutput(true);

			ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
			output.writeObject("");
			ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
			Object obj = input.readObject();
			if (obj instanceof Boolean) {
				novoLote =  ((Boolean)obj).booleanValue();
			} else if (obj instanceof Exception) {
				throw (Exception)obj;
			}
		} catch (Exception e) {
			throw  e;
		}
		return novoLote;
	}
	
	private Collection getLote(int numeroLote, int numeroLoja) throws Exception {
		Collection novoLote = null;
		try{
			URL urlCon = new URL("http://" +
					ServerConfig.HOST_SERVIDOR_ES +
					":" +
					ServerConfig.PORTA_SERVIDOR_ES +
					"/" +
					ServerConfig.CONTEXTO_SERVIDOR_ES +
					"/" +
					ServerConfig.SERVLET_TRANSMISSOR_LOTE + "?numeroLote=" + numeroLote + "&numeroLoja=" + numeroLoja);
			URLConnection huc1 = urlCon.openConnection();

			huc1.setAllowUserInteraction(true);
			huc1.setDoOutput(true);

			ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
			output.writeObject("");
			ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
			Object obj = input.readObject();
			if (obj instanceof Collection) {
				novoLote =  (Collection)obj;
			} else if (obj instanceof Exception) {
				throw (Exception)obj;
			}
		} catch (Exception e) {
			throw  e;
		}
		return novoLote;
	}

	public void atualizaLote(){
		try{
//			LoteServerRemote remote = (LoteServerRemote) ServiceLocator.getJNDIObject(ServerConfig.LOTE_SERVER_JNDI);
//			if (remote != null){	


				while (verificarNovoLote(numeroLote)) {
//				while(remote.verificaNovoLoteLiberado(numeroLote)){						
					Collection c = getLote(numeroLote, numeroLoja);
//					Collection c = remote.getLote(numeroLote, numeroLoja);
					System.out.println("ATUALIZANDO O LOTE "+ (numeroLote + 1));
					AtualizadorLote atualizador = AtualizadorLote.getInstancia();
					atualizador.atualizarLote(c);					
					numeroLote = numeroLote + 1;
					Parametro p = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOTE);
        			p.setValorInteiro(numeroLote);
        			ConcentradorParametro.getInstancia().atualizarParametro(p);
        			System.out.println("FIM DA ATUALIZAÇÃO DO LOTE "+numeroLote);
				}
//			}
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
