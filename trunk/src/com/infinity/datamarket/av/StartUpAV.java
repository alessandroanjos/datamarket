package com.infinity.datamarket.av;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Vector;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.av.op.OpAvEncerraIniciaAv;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOSArquivo;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.ControladorMaquinaEstado;
import com.infinity.datamarket.pdv.maquinaestados.Estado;
import com.infinity.datamarket.pdv.maquinaestados.LeitorMaquinaEstadoXML;
import com.infinity.datamarket.pdv.maquinaestados.Maquina;
import com.infinity.datamarket.pdv.transacao.ThreadEnviaTransacao;


public class StartUpAV {
	public static void main(String[] a) throws Exception{
		
		ControladorMaquinaEstado contr = LeitorMaquinaEstadoXML.lerArquivoXM("fluxoAv.xml");

		String diretorioH2 = Util.getBasePDV();

		Properties propreties = new Properties();
	    propreties.put("hibernate.connection.url", "jdbc:h2:" + diretorioH2);

		RepositoryManagerHibernateUtil.arquivoHibernate = RepositoryManagerHibernateUtil.HIBERNATE_PDV;
		RepositoryManagerHibernateUtil.properties = propreties;
		RepositoryManagerHibernateUtil.getInstancia().currentSession();
		
		GerenciadorPerifericos.PERIFERICOS = "PerifericosAV";

		GerenciadorPerifericos ger = GerenciadorPerifericos.getInstancia();

		Estado est = new Estado();
		est.setDescricao("Av Fechado [ENTER]");
		est.setId(new Long(1));
		est.setInputSize(0);
		est.setInputType(0);
		ger.getCmos().gravar(CMOSArquivo.ESTADO_ATUAL,est);
		
    	TelaAVInicial t = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
		Maquina maquina = Maquina.getInstancia(est, new Date(), ger, contr, t, OpAvEncerraIniciaAv.MENSAGEM_INICIAL);

		ThreadEnviaTransacao t1 = new ThreadEnviaTransacao();
		t1.start();
		maquina.iniciar();
		RepositoryManagerHibernateUtil.getInstancia().closeSession();
	}
	
	public static void backupTraces(){
		Vector vetorArquivos = new Vector();
		
		String nomeArquivoSO = "LogPDVSO.txt";
		String nomeArquivoRT = "LogPDVRT.txt";
		
		if(new File("." + File.separator + nomeArquivoSO).exists()){
			vetorArquivos.add(nomeArquivoSO);
		}
		
		if(new File("." + File.separator + nomeArquivoRT).exists()){
			vetorArquivos.add(nomeArquivoRT);
		}
		
		String arquivoBackup = "";
		Calendar c = new GregorianCalendar();

		int dia  = c.get(Calendar.DAY_OF_MONTH);
		int mes  = c.get(Calendar.MONTH)+1;
		int ano  = c.get(Calendar.YEAR);
		int hora = c.get(Calendar.HOUR_OF_DAY);
		int min  = c.get(Calendar.MINUTE);
		int seg  = c.get(Calendar.SECOND);

		String diretorio = "." + File.separator + "backupTraces";
		File f = null;
		f = new File(diretorio);
		if(!f.exists()){
			f.mkdir();	
		}
		
		String diretorioCompleto = 	(diretorio.endsWith(File.separator)?diretorio:diretorio + File.separator) +
									ano + Util.completaString(""+mes, "0", 2, true) + Util.completaString(""+dia,"0", 2, true);

		f = new File(diretorioCompleto);
		if(!f.exists()){
			f.mkdir();	
		}
		
		arquivoBackup = "Trace" + "_" + ano +
		Util.completaString(""+mes, "0", 2, true) +
		Util.completaString(""+dia, "0", 2, true) + "_" +
		Util.completaString(""+hora, "0", 2, true) +
		Util.completaString(""+min, "0", 2, true) +
		Util.completaString(""+seg, "0", 2, true);

		String[] arquivosParaCompactacao = new String[vetorArquivos.size()];
		vetorArquivos.copyInto(arquivosParaCompactacao);

		Util.compactaArquivoZip(arquivosParaCompactacao,
				(diretorioCompleto.endsWith(File.separator)?
					diretorioCompleto+ arquivoBackup:
					diretorioCompleto + File.separator + arquivoBackup));

	}
}
