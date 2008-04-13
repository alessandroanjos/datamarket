package com.infinity.datamarket.report;

import java.util.Collection;
import java.util.Map;

public class RelatorioBean {

	private String nomeRelatorio;
	private String caminhoPathProjeto;
	private String caminhoTemplate;
	private String caminhoGeracao;
	
	private int tipoTemplate;
	private int tipoExtencaoRelatorio;
	private String extencaoRelatorio;
	
	private Collection colecao;
	private Map parametros;
	private boolean testeQuantidadeMaximaRegistros;
	private boolean testeQuantidadeColecao = true;
	
	public RelatorioBean(){}
	
	/**
	 * 
	 * @param caminhoPathProjeto
	 * @param nomeRelatorio
	 * @param tipoTemplate
	 * @param tipoExtencaoRelatorio
	 * @param colecao
	 * @param parametros
	 * @param testeQuantidadeMaximaRegistros
	 */
	public RelatorioBean(
			String caminhoPathProjeto,
			String nomeRelatorio,
			int tipoTemplate,
			int tipoExtencaoRelatorio,
			Collection colecao,
			Map parametros, 
			boolean testeQuantidadeMaximaRegistros){
		
		this.setNomeRelatorio(nomeRelatorio);		
		this.setTipoTemplate(tipoTemplate);
		this.setTipoExtencaoRelatorio(tipoExtencaoRelatorio);
		this.setCaminhoPathProjeto(caminhoPathProjeto);
		this.setCaminhoTemplate(this.getCaminhoPathProjeto(), tipoTemplate);
		this.setCaminhoGeracao(this.getCaminhoPathProjeto() + ConstantsReport.CAMINHO_TEMPORARIO);
		this.setColecao(colecao);
		this.setParametros(parametros);
		this.setTesteQuantidadeMaximaRegistros(testeQuantidadeMaximaRegistros);
	}
	
	public RelatorioBean(
			String caminhoPathProjeto,
			String nomeRelatorio,
			int tipoTemplate,
			int extencaoRelatorio,
			Collection colecao,
			Map parametros, 
			boolean testeQuantidadeMaximaRegistros,
			boolean testeQuantidadeColecao){
		
		this.setNomeRelatorio(nomeRelatorio);		
		this.setTipoTemplate(tipoTemplate);
		this.setTipoExtencaoRelatorio(tipoExtencaoRelatorio);
		this.setCaminhoPathProjeto(caminhoPathProjeto);
		this.setCaminhoTemplate(this.getCaminhoPathProjeto(), tipoTemplate);
		this.setCaminhoGeracao(this.getCaminhoPathProjeto() + ConstantsReport.CAMINHO_TEMPORARIO);
		this.setColecao(colecao);
		this.setParametros(parametros);
		this.setTesteQuantidadeMaximaRegistros(testeQuantidadeMaximaRegistros);
		this.setTesteQuantidadeColecao(testeQuantidadeColecao);
	}
	
	
	
	
	/**
	 * 
	 * @param caminhoTemplate
	 * @param caminhoGeracao
	 * @param nomeRelatorio
	 * @param tipoTemplate
	 * @param tipoExtencaoRelatorio
	 * @param colecao
	 * @param parametros
	 * @param testeQuantidadeMaximaRegistros
	 */
	public RelatorioBean(
			String caminhoTemplate,
			String caminhoGeracao,
			String nomeRelatorio,
			int tipoTemplate,
			int tipoExtencaoRelatorio,
			Collection colecao,
			Map parametros, 
			boolean testeQuantidadeMaximaRegistros){
		
		this.setNomeRelatorio(nomeRelatorio);
		this.setTipoTemplate(tipoTemplate);
		this.setTipoExtencaoRelatorio(tipoExtencaoRelatorio);		
		this.setCaminhoTemplate(caminhoTemplate);
		this.setCaminhoGeracao(caminhoGeracao + ConstantsReport.CAMINHO_TEMPORARIO);
		this.setColecao(colecao);
		this.setParametros(parametros);
		this.setTesteQuantidadeMaximaRegistros(testeQuantidadeMaximaRegistros);
	}	
	
	/**
	 * @return Returns the caminhoGeracao.
	 */
	public String getCaminhoGeracao() {
		return caminhoGeracao;
	}
	/**
	 * @param caminhoGeracao The caminhoGeracao to set.
	 */
	public void setCaminhoGeracao(String caminhoGeracao) {
		this.caminhoGeracao = caminhoGeracao;
	}
	/**
	 * @return Returns the caminhoTemplate.
	 */
	public String getCaminhoTemplate() {
		return caminhoTemplate;
	}
	/**
	 * @param caminhoTemplate The caminhoTemplate to set.
	 */
	public void setCaminhoTemplate(String caminhoTemplate) {
		this.caminhoTemplate = caminhoTemplate;
	}
	/**
	 * @param caminhoTemplate The caminhoTemplate to set.
	 */
	public void setCaminhoTemplate(String caminhoPathProjeto, int tipoTemplate) {
		
        switch (tipoTemplate) {
        case ConstantsReport.ARQ_JRXML:
        	caminhoPathProjeto += ConstantsReport.CAMINHO_TEMPLATES_JRXML;
        	this.setExtencaoRelatorio(ConstantsReport.EXTENSAO_JRXML);
        	break;
        	
        case ConstantsReport.ARQ_JASPER:
        	caminhoPathProjeto += ConstantsReport.CAMINHO_TEMPLATES_JASPER;
        	this.setExtencaoRelatorio(ConstantsReport.EXTENSAO_JASPER);
        	break;
        //…OUTROS FORMATOS …
        default:
            break;
        }
        
		this.caminhoTemplate = caminhoPathProjeto;
	}
		
	
	/**
	 * @return Returns the colecao.
	 */
	public Collection getColecao() {
		return colecao;
	}
	/**
	 * @param colecao The colecao to set.
	 */
	public void setColecao(Collection colecao) {
		this.colecao = colecao;
	}


	/**
	 * @return Returns the testeQuantidadeMaximaRegistros.
	 */
	public boolean isTesteQuantidadeMaximaRegistros() {
		return testeQuantidadeMaximaRegistros;
	}

	/**
	 * @param testeQuantidadeMaximaRegistros The testeQuantidadeMaximaRegistros to set.
	 */
	public void setTesteQuantidadeMaximaRegistros(
			boolean testeQuantidadeMaximaRegistros) {
		this.testeQuantidadeMaximaRegistros = testeQuantidadeMaximaRegistros;
	}

	/**
	 * @return Returns the nomeRelatorio.
	 */
	public String getNomeRelatorio() {
		return nomeRelatorio;
	}
	/**
	 * @param nomeRelatorio The nomeRelatorio to set.
	 */
	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}
	/**
	 * @return Returns the parametros.
	 */
	public Map getParametros() {
		return parametros;
	}
	/**
	 * @param parametros The parametros to set.
	 */
	public void setParametros(Map parametros) {
		this.parametros = parametros;
	}

	/**
	 * @return Returns the extencaoRelatorio.
	 */
	public String getExtencaoRelatorio() {
		return extencaoRelatorio;
	}

	/**
	 * @param extencaoRelatorio The extencaoRelatorio to set.
	 */
	public void setExtencaoRelatorio(String extencaoRelatorio) {
		this.extencaoRelatorio = extencaoRelatorio;
	}

	/**
	 * @return Returns the tipoExtencaoRelatorio.
	 */
	public int getTipoExtencaoRelatorio() {
		return tipoExtencaoRelatorio;
	}

	/**
	 * @param tipoExtencaoRelatorio The tipoExtencaoRelatorio to set.
	 */
	public void setTipoExtencaoRelatorio(int tipoExtencaoRelatorio) {
		this.tipoExtencaoRelatorio = tipoExtencaoRelatorio;
	}

	/**
	 * @return Returns the tipoTemplate.
	 */
	public int getTipoTemplate() {
		return tipoTemplate;
	}

	/**
	 * @param tipoTemplate The tipoTemplate to set.
	 */
	public void setTipoTemplate(int tipoTemplate) {
		this.tipoTemplate = tipoTemplate;
	}

	/**
	 * @return Returns the testeQuantidadeColecao.
	 */
	public boolean isTesteQuantidadeColecao() {
		return testeQuantidadeColecao;
	}

	/**
	 * @param testeQuantidadeColecao The testeQuantidadeColecao to set.
	 */
	public void setTesteQuantidadeColecao(boolean testeQuantidadeColecao) {
		this.testeQuantidadeColecao = testeQuantidadeColecao;
	}

	
	
		
	/**
	 * @return Returns the caminhoPathProjeto.
	 */
	public String getCaminhoPathProjeto() {
		return caminhoPathProjeto;
	}

	/**
	 * @param caminhoPathProjeto The caminhoPathProjeto to set.
	 */
	public void setCaminhoPathProjeto(String caminhoPathProjeto) {
		this.caminhoPathProjeto = caminhoPathProjeto;
	}

	public String getCaminhoTemplateConcatenado(){
		return
			this.getCaminhoTemplate() + 
			this.getNomeRelatorio() + 
			this.getExtencaoRelatorio();
	}
}
