package com.infinity.datamarket.pdv.util;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.infinity.datamarket.comum.util.Util;

public class BackupLogs {
	
	public static void main(String[] args) {
		backupTraces();
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
