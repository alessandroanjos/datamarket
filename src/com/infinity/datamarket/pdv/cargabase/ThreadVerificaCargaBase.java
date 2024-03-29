package com.infinity.datamarket.pdv.cargabase;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOSArquivo;
import com.infinity.datamarket.pdv.gui.telas.swing.TelaCargaDados;
import com.infinity.datamarket.pdv.maquinaestados.Estado;
import com.infinity.datamarket.pdv.op.OpSolicitaCargaBase;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class ThreadVerificaCargaBase extends Thread implements Serializable{
	/**
	 * 
	 */
	
	public ThreadVerificaCargaBase(Long numeroComponente, Long numeroLoja){
		this.numeroComponente = numeroComponente;
		this.numeroLoja = numeroLoja;
	}
	
	private static final long serialVersionUID = -849614647967981958L;
	private Long numeroComponente;
	private Long numeroLoja;
	private boolean novoCargaBase;
	
	public Long getNumeroComponente() {
		return numeroComponente;
	}
	public void setNumeroComponente(Long numeroComponente) {
		this.numeroComponente = numeroComponente;
	}
	public void run(){
		System.out.println("-------------------------------------------------------------" );
		System.out.println("-- INICIANDO SERVICO DE VERIFICA��O DE NOVA CARGA DE BASE ---" );
		System.out.println("-------------------------------------------------------------" );
		
        Componente componente = (Componente)GerenciadorPerifericos.getInstancia().getCmos().ler(CMOSArquivo.COMPONENTE);
        
		while(true){
			try {
				Thread.currentThread().sleep(ServerConfig.SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try{ 
				
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Transacao.class);
				filter.addProperty("status", Transacao.NAO_PROCESSADO);
				
				Collection transacoes = Fachada.getInstancia().consultarTransacao(filter);
				if (transacoes != null && transacoes.size() > 0) {
					System.out.println("Maquina.ThreadVerificaCargaBase.run: EXISTE TRANSACAO NAO PROCESSADA - NAO FOI VERIFICADO A NOVA CARGA BASE");
					continue;
					
				}
				
				synchronized (OpSolicitaCargaBase.SINCRONIZADOR) {
					
					novoCargaBase =  verificarNovaCargaBase(numeroLoja, numeroComponente);
					if (novoCargaBase) {
						Estado estadoAtual = (Estado)GerenciadorPerifericos.getInstancia().getCmos().ler(CMOSArquivo.ESTADO_ATUAL);
	
		        		if (componente.getTipoComponente() == Componente.TIPO_COMPONENTE_PDV &&
		        				(estadoAtual != null && (estadoAtual.getId().equals(Estado.DISPONIVEL_PDV) ||  estadoAtual.getId().equals(Estado.FECHADO_PDV)  || estadoAtual.getId().equals(Estado.FECHADO_PARCIAL_PDV ))) || 
		        			componente.getTipoComponente() == Componente.TIPO_COMPONENTE_AV &&
		        			(estadoAtual != null && (estadoAtual.getId().equals(Estado.FECHADO_AV )|| estadoAtual.getId().equals(Estado.ABERTO_AV) ))){


		//	        		//verifica se tem novo lote liberado
		        			System.out.println("Maquina.ThreadVerificaCargaBase.run: ThreadVerificaCargaBase.existeNovaCargaBase(): "+existeNovoLote());
		        			if (existeNovoLote()){
		        				System.out.println("H� UMA NOVA CARGA DE BASE");
		        				TelaCargaDados tela = new TelaCargaDados(GerenciadorPerifericos.getInstancia().getWindow().getFrame().getWidth(),GerenciadorPerifericos.getInstancia().getWindow().getFrame().getHeight());
		        				try{
			        					
			        				Thread thread = new Thread(tela);
			        				thread.start();
				        			byte[] zipFile = verificarNovoCargaBase(numeroLoja, numeroComponente);
				        			
				        			String diretorioTemp = Util.getDirTemp() + "/CargaBase_" + Util.getDirDataHora() + ".zip";
				    				FileOutputStream output = new FileOutputStream (diretorioTemp);
				        			output.write(zipFile);
				        			output.close();
			
				        			OpSolicitaCargaBase.trocaBasePDV(diretorioTemp);
		
				        			apagaCargaBaseServer(numeroLoja, numeroComponente);
		        				} finally {
				        			tela.stop();	        			
		        				}
			        		}else{
			        			System.out.println("N�O H� NOVO LOTE LIBERADO");
			        		}
		        		}
					}
				}
			}catch (Throwable e){
				System.out.println(e.getMessage());
				//e.printStackTrace();
			}
		}
	}
	public boolean existeNovoLote() {
		return novoCargaBase;
	}
	

	private boolean verificarNovaCargaBase(Long idLoja, Long idComponente) throws Exception {
		boolean dados = false;
		try{
			URL urlCon = new URL("http://" +
					ServerConfig.HOST_SERVIDOR_ES +
					":" +
					ServerConfig.PORTA_SERVIDOR_ES +
					"/" +
					ServerConfig.CONTEXTO_SERVIDOR_ES +
					"/" +
					ServerConfig.SERVLET_VERIFICADOR_NOVA_CARGA_BASE + "?idLoja=" + idLoja + "&idComponente=" + idComponente);
			URLConnection huc1 = urlCon.openConnection();

			huc1.setAllowUserInteraction(true);
			huc1.setDoOutput(true);

			ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
			output.writeObject("");
			ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
			Object obj = input.readObject();
			if (obj instanceof Boolean) {
				dados =  ((Boolean)obj).booleanValue();
			} else if (obj instanceof String) {
				System.out.println("##############################"+obj+"##############################");
			} else if (obj instanceof Exception) {
				throw (Exception)obj;
			}
		} catch (Exception e) {
			throw  e;
		}
		return dados;
	}

	private byte[] verificarNovoCargaBase(Long idLoja, Long idComponente) throws Exception {
		byte[] dados = null;
		try{
			URL urlCon = new URL("http://" +
					ServerConfig.HOST_SERVIDOR_ES +
					":" +
					ServerConfig.PORTA_SERVIDOR_ES +
					"/" +
					ServerConfig.CONTEXTO_SERVIDOR_ES +
					"/" +
					ServerConfig.SERVLET_CONSULTAR_NOVO_CARGA_BASE + "?idLoja=" + idLoja + "&idComponente=" + idComponente);
			URLConnection huc1 = urlCon.openConnection();

			huc1.setAllowUserInteraction(true);
			huc1.setDoOutput(true);

			ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
			output.writeObject("");
			ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
			Object obj = input.readObject();
			if (obj instanceof byte[]) {
				dados =  ((byte[])obj);
			} else if (obj instanceof String) {
				System.out.println("##############################"+obj+"##############################");
			} else if (obj instanceof Exception) {
				throw (Exception)obj;
			}
		} catch (Exception e) {
			throw  e;
		}
		return dados;
	}
	

	private void apagaCargaBaseServer(Long idLoja, Long idComponente) throws Exception {
		try{
			URL urlCon = new URL("http://" +
					ServerConfig.HOST_SERVIDOR_ES +
					":" +
					ServerConfig.PORTA_SERVIDOR_ES +
					"/" +
					ServerConfig.CONTEXTO_SERVIDOR_ES +
					"/" +
					ServerConfig.SERVLET_APAGAR_NOVA_CARGA_BASE + "?idLoja=" + idLoja + "&idComponente=" + idComponente);
			URLConnection huc1 = urlCon.openConnection();

			huc1.setAllowUserInteraction(true);
			huc1.setDoOutput(true);

			ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
			output.writeObject("");
			ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
			Object obj = input.readObject();
//			if (obj instanceof byte[]) {
//				dados =  ((byte[])obj);
//			} else if (obj instanceof String) {
//				System.out.println("##############################"+obj+"##############################");
//			} else if (obj instanceof Exception) {
//				throw (Exception)obj;
//			}
		} catch (Exception e) {
			throw  e;
		}
	}
	
	
}