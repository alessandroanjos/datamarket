package com.infinity.datamarket;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.boleto.ArquivosProcessado;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;

public class ProcessadorArquivoRetorno {

	public static void main(String[] a) {

		try {
			ArrayList todosProcessados = new ArrayList();
			
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(ArquivosProcessado.class);
//			filter.addProperty("conta.id", new Long(getIdContaConsulta()));
//			filter.addPropertyInterval("data",getDataInicio(), IntervalObject.MAIOR_IGUAL);
//			Date dataFinal = new Date(getDataFinal().getTime());
//			dataFinal.setDate(dataFinal.getDate()+1);
//			filter.addPropertyInterval("data",dataFinal, IntervalObject.MENOR_IGUAL);
			Collection col = null;
			try {
				col = Fachada.getInstancia().consultarArquivosProcessado(filter);
				if (col == null || col.size() == 0){
					Iterator it = col.iterator();
					while (it.hasNext()) {
						ArquivosProcessado ap = (ArquivosProcessado)it.next();
						todosProcessados.add(ap.getNomeArquivo());
					}
				}

			}catch(ObjectNotFoundException e){}
				
			String diretorio = "H:\\workspace\\es\\doc\\arquivosRetornoBanco";

			File dir = new File(diretorio);
			String[] arquivos = dir.list();
			for (int i = 0; i < arquivos.length; i++) {
				String arquivo = arquivos[i];
				if (new File(dir, arquivo).isFile() && (arquivo.endsWith("RET") || arquivo.endsWith("ret"))) {
					if (!todosProcessados.contains(arquivo)) {
						todosProcessados.add(arquivo);
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private ArquivosProcessado processarArquivo(String diretorio, String nomeArquivo){
		ArquivosProcessado  ap = new ArquivosProcessado();
//		ap.setNomeArquivo(arquivo);
//		ap.setDataHoraInicial();
//		ap.setDataHoraFinal( );
//		ap.setBanco();
//		ap.setDataHoraFinal();
//		
		
		return ap;
	}

}