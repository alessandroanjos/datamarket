package com.infinity.datamarket.report;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.view.JasperViewer;

public class GeneratesReports {

	/**
	 * 
	 * @param fileName
	 *            Nome do template
	 * @param archiveType
	 *            Tipo do relatorio a ser gerado, usar a calsse ConstantsReport
	 * @param collection
	 *            A coleção com a pesquisa
	 * @return
	 */
	public static String reportsConstruct(String fileName, int archiveType,
			Collection collection) {
		return reportsConstructMAP(fileName, archiveType, collection, null);
	}

	/**
	 * @param string
	 * @param arq_pdf
	 * @param colecao
	 * @param
	 * @param colecao
	 */
	public static String reportsConstructMAP(String fileName, int archiveType,
			Collection collection, Map parameters) {
		String reportName = null;
		// pesquisar uma maneira de setar o caminho raiz sem ser no action.
		String nomeArquivoTemplate = "";
		// ConstantsReport.CAMINHO_RAIZ + ConstantsReport.CAMINHO_TEMPLATES +
		// fileName + ConstantsReport.EXTENSAO_JRXML;
		System.out.println("GeneratesReports::reportsConstructMAP::nomeArquivoTemplate= " + nomeArquivoTemplate);

		// Pega o nome do template e gera um file
		// File file = new File(nomeArquivoTemplate);
		try {

			// Carrega o XML do Relatorio
			// JasperDesign jasperDesign = JRXmlLoader.load(file);
			// Compila o relatório
			// JasperReport jasperReport =
			// JasperCompileManager.compileReport(jDesign);

			// First, load JasperDesign from XML and compile it into
			// JasperReport
			System.out.println("GeneratesReports::reportsConstructMAP::compilando o arquivo");
			JasperDesign jasperDesign = JasperManager.loadXmlDesign(nomeArquivoTemplate);
			System.out.println("GeneratesReports::reportsConstructMAP::compilado o arquivo");
			System.out.println("GeneratesReports::reportsConstructMAP::compilando o arquivo design");
			JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
			System.out.println("GeneratesReports::reportsConstructMAP::compilado o arquivo design");

			// Implementação Default para Collection de Beans do JasperReports
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
					collection);

			// Troca os templates pelos seus valores no padrão I18N da CSI
			Map listaParametros = ParametersSearch
					.parametersSearchProperties(jasperDesign);
			// Caso tenha sido passado um Map
			if (parameters != null) {
				ParametersSearch.parametersSearchMap(jasperDesign,listaParametros, parameters);
			}

			System.out.println("GeneratesReports::reportsConstructMAP::rederizando relatorio");
			// Renderiza o Relatório
			JasperPrint jPrint = JasperFillManager.fillReport(jasperReport,listaParametros, ds);
			
			System.out.println("GeneratesReports::reportsConstructMAP::rederizado relatorio");

			System.out.println("GeneratesReports::reportsConstructMAP::mostrando o relatorio em Java 2D");
			// temp
			JasperViewer.viewReport(jPrint, false);
			reportName = reportsExport(jPrint, fileName, archiveType);

			System.out.println("relatorio criado com sucesso");

		} catch (JRException e) {
			System.out.println(e);
		}
		return reportName;
	}

	public static void geraRelatorio_Temp(String nomeArquivo, Collection colecao, Map parametros) {

		if (colecao != null && colecao.size() > 0) {
			try {
				JasperReport jasperReport = JasperCompileManager.compileReport(nomeArquivo + ConstantsReport.EXTENSAO_JRXML);

				// Implementação Default para Collection de Beans do
				// JasperReports
				JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(colecao);

				System.out.println("colecao.size= " + colecao.size());
				
				JasperPrint jPrint = JasperFillManager.fillReport(jasperReport, parametros, ds);
				
				JasperExportManager.exportReportToPdfFile(jPrint, nomeArquivo + ConstantsReport.EXTENSAO_PDF);

			} catch (JRException e) {
				System.out.println(e);
			}
//		} else {
//			// caso a pesquisa nao retorne valores, nao ha porque criar o
//			// relatorio
//			List erros = new ArrayList();
//			erros.add(LeitorMensagem.getMensagem("erro.generico.relatorioSemDados"));
//			throw new ExcecaoErrosValidacao(erros);
		}
	}

	/**
	 * 
	 * @param relatorioBean
	 * @param token
	 * @param valoresToken
	 * @return
	 */
	public static String reportsConstructEditingXML(RelatorioBean relatorioBean, String[] token, String[] valoresToken) {

		String reportName = null;
		try {

			String novoTemplete = LerXML.alterarXML(relatorioBean.getCaminhoTemplate() + relatorioBean.getNomeRelatorio(), token, valoresToken);

			System.out.println("GeneratesReports::reportsConstructEditingXML(RelatorioBean)::XML editado= "	+ novoTemplete);

			JasperReport jasperReport = JasperCompileManager.compileReport(novoTemplete + ConstantsReport.EXTENSAO_JRXML);

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(relatorioBean.getColecao());

			JasperPrint jPrint = JasperFillManager.fillReport(jasperReport, relatorioBean.getParametros(), dataSource);

			reportName = reportsExport(jPrint, relatorioBean.getCaminhoGeracao() + relatorioBean.getNomeRelatorio(), relatorioBean.getTipoExtencaoRelatorio());

			System.out.println("GeneratesReports::reportsConstructEditingXML(RelatorioBean)::criarRelatorioPDF --> " + reportName);

		} catch (JRException e) {
			System.out.println(e);
//			throw new ExcecaoSistema();
		}
		return reportName;
	}

	/**
	 * 
	 * @param relatorioBean
	 * @param connection
	 * @param token
	 * @param valoresToken
	 * @return
	 */
	public static String reportsConstructEditingXMLPassingConnection(
			RelatorioBean relatorioBean, Connection connection, String[] token,
			String[] valoresToken) {

		String reportName = null;
		try {

			String novoTemplete = LerXML.alterarXML(relatorioBean.getCaminhoTemplate() + relatorioBean.getNomeRelatorio(), token, valoresToken);

			System.out .println("GeneratesReports::reportsConstructEditingXMLPassingConnection::XML editado= " + novoTemplete);

			reportName = relatorioBean.getCaminhoPathProjeto()
					+ ConstantsReport.CAMINHO_TEMPLATES_JASPER
					+ relatorioBean.getNomeRelatorio()
					+ ConstantsReport.EXTENSAO_JASPER;

			// compila e salva o arquivo em arquivo
			JasperCompileManager.compileReportToFile(novoTemplete + ConstantsReport.EXTENSAO_JRXML, reportName);

			System.out.println("GeneratesReports::reportsConstructEditingXMLPassingConnection::jasper= " + reportName);

			// JasperPrint jPrint =
			// JasperFillManager.fillReport(
			// jasperReport,
			// relatorioBean.getParametros(),
			// connection);

			// reportName = reportsExport(
			// jPrint,
			// relatorioBean.getCaminhoGeracao() +
			// relatorioBean.getNomeRelatorio(),
			// relatorioBean.getTipoExtencaoRelatorio());

			// System.out.println("GeneratesReports::reportsConstruct(RelatorioBean)::criarRelatorioPDF
			// --> " + reportName);

		} catch (JRException e) {
			System.out.println(e);
//			throw new ExcecaoSistema();
		}

		return reportName;
	}

	/**
	 * Gera o relatorio passando apartir do RelatorioBean, usando um Connection
	 * 
	 * @param relatorioBean
	 * @param connection
	 * @return
	 */
	public static String reportsConstructPassingConnection(
			RelatorioBean relatorioBean, Connection connection) {

		String reportName = null;
		try {

			JasperPrint jasperPrint = null;
			if (relatorioBean.getExtencaoRelatorio().equals(
					ConstantsReport.EXTENSAO_JASPER)) {
				
				jasperPrint = JasperFillManager.fillReport(relatorioBean
						.getCaminhoTemplateConcatenado(), relatorioBean.getParametros(), connection);

			} else {
				JasperReport jasperReport = JasperCompileManager
						.compileReport(relatorioBean
								.getCaminhoTemplateConcatenado());

				jasperPrint = JasperFillManager.fillReport(jasperReport,
						relatorioBean.getParametros(), connection);
			}

			reportName = reportsExport(jasperPrint, relatorioBean
					.getCaminhoGeracao()
					+ relatorioBean.getNomeRelatorio(), relatorioBean
					.getTipoExtencaoRelatorio());
			
			if (relatorioBean.isTesteQuantidadeColecao()) {
				File f = new File(reportName);
				System.out.println("QUANTIDADE BYTES: " + f.length());
				if (f.length() < 760) {
//					throw new ExcecaoNaoExisteDados();
				}
			}

			System.out
					.println("GeneratesReports::reportsConstruct(RelatorioBean)::criarRelatorioPDF --> "
							+ reportName);
		} catch (JRException e) {
			System.out.println(e);
//			throw new ExcecaoSistema();
		}

		return reportName;
	}

	/**
	 * Gera o relatorio passando apartir do RelatorioBean, usando um Data source
	 * 
	 * @param relatorioBean
	 * @return Retorna o caminho do relatorio gerado
	 */
	public static String reportsConstruct(RelatorioBean relatorioBean) {

		if ((relatorioBean.isTesteQuantidadeColecao())
				&& (relatorioBean.getColecao() == null
						|| relatorioBean.getColecao().isEmpty() || relatorioBean
						.getColecao().size() < 0)) {
//			throw new ExcecaoNaoExisteDados();

		} else if (relatorioBean.isTesteQuantidadeMaximaRegistros()
				&& relatorioBean.getColecao().size() > ConstantsReport.MAXIMA_QUANTIDADE_REGISTROS) {
//			throw new ExcecaoLimiteDados();
		}

		String reportName = null;
		try {
			// Implementação Default para Collection de Beans do JasperReports
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					relatorioBean.getColecao());

			JasperPrint jPrint = JasperFillManager.fillReport(relatorioBean
					.getCaminhoTemplateConcatenado(), relatorioBean
					.getParametros(), dataSource);

			reportName = reportsExport(jPrint, relatorioBean
					.getCaminhoGeracao()
					+ relatorioBean.getNomeRelatorio(), relatorioBean
					.getTipoExtencaoRelatorio());

			System.out
					.println("GeneratesReports::reportsConstruct(RelatorioBean)::criarRelatorioPDF --> "
							+ reportName);
		} catch (JRException e) {
			System.out.println(e);
//			throw new ExcecaoSistema();
		}

		return reportName;
	}

	private static String reportsExport(JasperPrint jPrint,
			String fileNamePath, int archiveType) throws JRException {

		switch (archiveType) {
		case ConstantsReport.ARQ_PDF:
			// Acrescenta a extensão do PDF
			fileNamePath += System.currentTimeMillis()
					+ ConstantsReport.EXTENSAO_PDF;

			File arquivo = new File(fileNamePath);
			if (arquivo.exists()) {
				arquivo.delete();
			}

			JasperExportManager.exportReportToPdfFile(jPrint, fileNamePath);

			new File(fileNamePath).deleteOnExit();

			break;
		// …OUTROS FORMATOS …
		default:
			break;
		}
		return fileNamePath;
	}

}