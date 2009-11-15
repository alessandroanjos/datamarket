package com.infinity.datamarket;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.banco.Banco;
import com.infinity.datamarket.comum.boleto.ArquivoProcessado;
import com.infinity.datamarket.comum.boleto.Boleto;
import com.infinity.datamarket.comum.boleto.PagamentoBoleto;
import com.infinity.datamarket.comum.boleto.PagamentoBoletoPK;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class ThreadProcessadorArquivoRetorno extends Thread {
	
	public static void main(String[] a) {
		new ThreadProcessadorArquivoRetorno().run();
	}
	
	
	@Override
	public void run() {

		try {
			ArrayList<String> todosProcessados = new ArrayList<String>();

			Parametro param = Fachada.getInstancia().consultarParametro("CAMINHO_ARQUIVOS_RET_BANCO");
			
//			String diretorio = "D:\\Projetos\\EnterpriseServer\\doc\\arquivosRetornoBanco\\";
			String diretorio = param.getValor();
			
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(ArquivoProcessado.class);
			Collection col = null;
			try {
				col = Fachada.getInstancia().consultarArquivoProcessado(filter);
				if (col == null || col.size() == 0){
					Iterator it = col.iterator();
					while (it.hasNext()) {
						ArquivoProcessado ap = (ArquivoProcessado)it.next();
						todosProcessados.add(ap.getNomeArquivo());
					}
				}

			}catch(ObjectNotFoundException e){}
				
			while(true){
				
				try {
					Thread.currentThread().sleep(ServerConfig.SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				File dir = new File(diretorio);
				String[] arquivos = dir.list();
				for (int i = 0; i < arquivos.length; i++) {
					String arquivo = arquivos[i];
					if (new File(dir, arquivo).isFile() && (arquivo.endsWith("RET") || arquivo.endsWith("ret"))) {
						if (!todosProcessados.contains(arquivo)) {
							todosProcessados.add(arquivo);
							ArquivoProcessado arquivoProcessado = processarArquivo(diretorio, arquivo);
							Fachada.getInstancia().inserirArquivoProcessado(arquivoProcessado);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ArquivoProcessado processarArquivoCaixa(String diretorio, String nomeArquivo) throws IOException, ParseException{
		Set<PagamentoBoleto> pagamentos = new HashSet<PagamentoBoleto>();
		
		ArquivoProcessado  ap = new ArquivoProcessado();
		Banco banco = null;
		Date dataHoraInicial = null;
		Date dataHoraFinal = null;

		LineNumberReader line = new LineNumberReader(new FileReader(new File(diretorio, nomeArquivo)));
		String linha = line.readLine();
		boolean primeiraLinha = true; 
		while (linha != null){
			String proximaLina = line.readLine();
			if (proximaLina != null && !primeiraLinha) {
				
				String nossoCodigo = linha.substring(64,72);

				String valorPrincipal = linha.substring(253,264);
				String centavos = linha.substring(264,266);					
				BigDecimal valor = new BigDecimal(valorPrincipal + "." + centavos);  
	
				String dia = linha.substring(293,295);
				String mes = linha.substring(295,297);
				String ano = "20" + linha.substring(297,299);
				String data = dia + "/" + mes + "/" + ano;
				Date dataProcessamento = Util.formatarStringParaData(data);
				if ((dataHoraInicial == null) ||
						(dataHoraInicial!= null && dataHoraInicial.getTime() > dataProcessamento.getTime())){
					dataHoraInicial = dataProcessamento;
				}
				if ((dataHoraFinal == null) ||
						(dataHoraFinal!= null && dataHoraFinal.getTime() < dataProcessamento.getTime())){
					dataHoraFinal = dataProcessamento;
				}

				
				PagamentoBoletoPK pk = new  PagamentoBoletoPK();
				pk.setId(new Long(pagamentos.size() + 1));

				PagamentoBoleto pb = new  PagamentoBoleto();
				pb.setPk(pk);
				pb.setValor(valor);
				pb.setDataPagamento(dataProcessamento);
				pb.setIdBoleto(new Long(nossoCodigo));

				pagamentos.add(pb);
			} else if (primeiraLinha) {
				
				String c = linha.substring(76,79);
				banco = new Banco();
				banco.setId(new Long(c));
			}
			primeiraLinha = false;
			linha = proximaLina;
		}

		ap.setNomeArquivo(nomeArquivo);
		ap.setDataHoraInicial(dataHoraInicial);
		ap.setDataHoraFinal(dataHoraFinal);
		ap.setBanco(banco);
		ap.setDataProcessamento(new Date());
		ap.setPagamentosBoleto(pagamentos);
		return ap;
	}


	private static ArquivoProcessado processarArquivo(String diretorio, String nomeArquivo) throws IOException, ParseException{

		LineNumberReader line = new LineNumberReader(new FileReader(new File(diretorio, nomeArquivo)));
		String linha = line.readLine();
		if (linha != null){
			String proximaLina = line.readLine();

			String c = linha.substring(76,79);
			int codigoBanco = new Long(c).intValue();
			if (codigoBanco == Boleto.TIPO_BANCO_CAIXA_ECONOMICA) {
				return processarArquivoCaixa(diretorio, nomeArquivo);
			}
		}

		return null;
	}

}