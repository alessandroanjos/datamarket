package com.infinity.datamarket.pdv.op;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaMenssagem;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class OpSolicitaCargaBase extends Mic{

	public static String SINCRONIZADOR = "SINCRONIZADOR";
	
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
        int componente = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.COMPONENTE).getValorInteiro();
        int loja = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOJA).getValorInteiro();
		gerenciadorPerifericos.getDisplay().setMensagem("ATUALIZANDO BASE ....");

		
		
		try {
			
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Transacao.class);
			filter.addProperty("status", Transacao.NAO_PROCESSADO);
			
			Collection transacoes = Fachada.getInstancia().consultarTransacao(filter);
			if (transacoes != null && transacoes.size() > 0) {
				gerenciadorPerifericos.getDisplay().setMensagem("EXISTE TRANSACAO NAO PROCESSADA");
				System.out.println("EXISTE TRANSACAO NAO PROCESSADA");
				try {
					gerenciadorPerifericos.esperaVolta();
				} catch (Exception ee) {
				}
			}
			
			synchronized (SINCRONIZADOR) {
				byte[] zipFile = verificarNovoCargaBase(new Long(loja), new Long(componente));
				
				if (zipFile != null) {
					String diretorioTemp = Util.getDirTemp() + "/CargaBase_" + Util.getDirDataHora() + ".zip";
					FileOutputStream output = new FileOutputStream (diretorioTemp);
					output.write(zipFile);
					output.close();
					
					trocaBasePDV(diretorioTemp);
					
				} else {
					gerenciadorPerifericos.getDisplay().setMensagem("SEM CARGA DE BASE  [VOLTA]");
					try {
						gerenciadorPerifericos.esperaVolta();	
					} catch (Exception ee) {
					}
				}
				
			}
			
		} catch (IOException e) {
			gerenciadorPerifericos.getDisplay().setMensagem ("SEM COMUN. COM LOJA [VOLTA]");
			try {
				gerenciadorPerifericos.esperaVolta();	
			} catch (Exception ee) {
			}
			
		} catch (Exception e) {
			gerenciadorPerifericos.getDisplay().setMensagem (e.getMessage());
			try {
				gerenciadorPerifericos.esperaVolta();	
			} catch (Exception ee) {
			}
		}
        
		return ALTERNATIVA_1;
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
					ServerConfig.SERVLET_GERA_NOVA_CARGA_BASE + "?idLoja=" + idLoja + "&idComponente=" + idComponente);
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
		} catch (IOException e) {
			throw  e;
		}
		return dados;
	}
	
	
	/**
	 * Implementar a troca de base
	 * 
	 * @param bancoZip
	 * @throws IOException 
	 * @throws AppException 
	 */
	public static void trocaBasePDV(String bancoZip) throws IOException, AppException {
		
		ConcentradorParametro.getInstancia().removerParametro(ConcentradorParametro.LOTE);

        RepositoryManagerHibernateUtil.getInstancia().trocarBase(bancoZip);
		
        int lote = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOTE).getValorInteiro();

        GerenciadorPerifericos.getInstancia().getWindow().atualizaLote(lote);

	}
	
}


