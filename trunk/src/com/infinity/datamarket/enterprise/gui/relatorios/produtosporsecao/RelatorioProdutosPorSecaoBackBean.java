/**
 * 
 */
package com.infinity.datamarket.enterprise.gui.relatorios.produtosporsecao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.infinity.datamarket.comum.produto.GrupoProduto;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;
//import com.infinity.datamarket.report.ConstantsReport;

/**
 * @author jonas
 *
 */
public class RelatorioProdutosPorSecaoBackBean extends BackBean {
	String idLoja;
	String nomeLoja;
	String idGrupoProduto;
	String descricaoGrupoProduto;
	SelectItem[] lojas;
	SelectItem[] grupos;
	
	ArrayList<String> listaSecoesSelecionadas;

	/**
	 * @return the descricaoGrupoProduto
	 */
	public String getDescricaoGrupoProduto() {
		return descricaoGrupoProduto;
	}
	/**
	 * @param descricaoGrupoProduto the descricaoGrupoProduto to set
	 */
	public void setDescricaoGrupoProduto(String descricaoGrupoProduto) {
		this.descricaoGrupoProduto = descricaoGrupoProduto;
	}
	/**
	 * @return the idGrupoProduto
	 */
	public String getIdGrupoProduto() {
		return idGrupoProduto;
	}
	/**
	 * @param idGrupoProduto the idGrupoProduto to set
	 */
	public void setIdGrupoProduto(String idGrupoProduto) {
		this.idGrupoProduto = idGrupoProduto;
	}
	/**
	 * @return the idLoja
	 */
	public String getIdLoja() {
		return idLoja;
	}
	/**
	 * @param idLoja the idLoja to set
	 */
	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}
	/**
	 * @return the nomeLoja
	 */
	public String getNomeLoja() {
		return nomeLoja;
	}
	/**
	 * @param nomeLoja the nomeLoja to set
	 */
	public void setNomeLoja(String nomeLoja) {
		this.nomeLoja = nomeLoja;
	}
	
	public String gerarRelatorioProdutosPorSecao(){
		
		Map<String,String> parametros = new HashMap<String,String>();
		
		parametros.put("ID_LOJA", "1");
		parametros.put("NOME_LOJA", "LAGOA DO ARAÇÁ");
		Collection produtos = null;
		try {
			produtos = getFachada().consultaProdutosPorSecao("1");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		String raizTemplate = "C:\\MyEclipse\\workspace\\EnterpriseServer\\src\\com\\infinity\\datamarket\\templatesreports";
		
//		System.out.println("caminho do relatorio: "+raizTemplate + File.separator + "jasper" + File.separator + NOME_RELATORIO + ConstantsReport.EXTENSAO_JASPER);
		
//		String relatorioProdutosPorSecao = raizTemplate + File.separator + "jasper" + File.separator + NOME_RELATORIO;

		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(produtos);
		
//		try {
//			JasperPrint jPrint = JasperFillManager.fillReport(relatorioProdutosPorSecao + ConstantsReport.EXTENSAO_JASPER, parametros, ds);
//			
//			JasperExportManager.exportReportToPdfFile(jPrint, relatorioProdutosPorSecao + ConstantsReport.EXTENSAO_PDF);
//		} catch (JRException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		this.setNomeRelatorio(relatorioProdutosPorSecao + ConstantsReport.EXTENSAO_PDF);
		
		return "mesma";
	}

	private List<GrupoProduto> carregarGruposProduto() {
		
		List<GrupoProduto> grupos = null;
		try {
			grupos = (ArrayList<GrupoProduto>)getFachada().consultarTodosGruposProduto();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return grupos;
	}
	
	public SelectItem[] getGrupos(){
		SelectItem[] arrayGruposAssociados = null;
		try {
			List<GrupoProduto> grupos = carregarGruposProduto();
			arrayGruposAssociados = new SelectItem[grupos.size()];
			int i = 0;
			for(GrupoProduto gruposAssociadosTmp : grupos){
				SelectItem item = new SelectItem(gruposAssociadosTmp.getId().toString(), gruposAssociadosTmp.getDescricao());
				arrayGruposAssociados[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayGruposAssociados;
	}
	/**
	 * @param grupos the grupos to set
	 */
	public void setGrupos(SelectItem[] grupos) {
		this.grupos = grupos;
	}
	
	private List<Loja> carregarLojas() {
		
		List<Loja> lojas = null;
		try {
			lojas = (ArrayList<Loja>)getFachada().consultarTodosLoja();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return lojas;
	}
	
	public SelectItem[] getLojas(){
		SelectItem[] arrayLojasAssociadas = null;
		try {
			List<Loja> lojas = carregarLojas();
			arrayLojasAssociadas = new SelectItem[lojas.size()];
			int i = 0;
			for(Loja lojasAssociadasTmp : lojas){
				SelectItem item = new SelectItem(lojasAssociadasTmp.getId().toString(), lojasAssociadasTmp.getNome());
				arrayLojasAssociadas[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayLojasAssociadas;
	}
	/**
	 * @param lojas the lojas to set
	 */
	public void setLojas(SelectItem[] lojas) {
		this.lojas = lojas;
	}
	/**
	 * @return the listaSecoesSelecionadas
	 */
	public ArrayList<String> getListaSecoesSelecionadas() {
		return listaSecoesSelecionadas;
	}
	/**
	 * @param listaSecoesSelecionadas the listaSecoesSelecionadas to set
	 */
	public void setListaSecoesSelecionadas(ArrayList<String> listaSecoesSelecionadas) {
		this.listaSecoesSelecionadas = listaSecoesSelecionadas;
	}	
	
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}

	public void resetBB(){
		this.setIdLoja(null);
		this.setNomeLoja(null);
		this.setIdGrupoProduto(null);
		this.setDescricaoGrupoProduto(null);
		this.setLojas(null);
		this.setGrupos(null);
	}
	
}
