package com.infinity.datamarket.enterprise.gui.relatorios;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class RelatorioBackBean extends BackBean {
	
	Date dataInicial;
	Date dataFinal;
	
	String idLoja;
	
	private String idStatus;
	private SelectItem[] listaStatus;
    
	private String idTipoOrdenacao;
	private SelectItem[] listaTiposOrdenacao;
	
	SelectItem[] lojas;

	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
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
//			SelectItem item = new SelectItem("0", "Selecione uma Lojas");
//			arrayLojasAssociadas[i++] = item;
			for(Loja lojasAssociadasTmp : lojas){
				SelectItem item = new SelectItem(lojasAssociadasTmp.getId().toString(), lojasAssociadasTmp.getNome());
				arrayLojasAssociadas[i++] = item;
			}
			if(this.getIdLoja() == null){
				this.setIdLoja(arrayLojasAssociadas[0].getValue().toString());
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

	public void setLojas(SelectItem[] lojas) {
		this.lojas = lojas;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	
	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public void setListaStatus(SelectItem[] listaStatus) {
		this.listaStatus = listaStatus;
	}

	public SelectItem[] getListaStatus() {
		SelectItem[] lista = new SelectItem[3];
		lista[0] = new SelectItem("T", "Todas");
		lista[1] = new SelectItem(Constantes.STATUS_ATIVO, "Ativa");
		lista[2] = new SelectItem(Constantes.STATUS_CANCELADO, "Cancelada");
		if(this.getIdStatus() == null || this.getIdStatus().equals("")){
			this.setIdStatus("T");
		}
		return lista;
	}

	public String getIdTipoOrdenacao() {
		return idTipoOrdenacao;
	}

	public void setIdTipoOrdenacao(String idTipoOrdenacao) {
		this.idTipoOrdenacao = idTipoOrdenacao;
	}

	public void setListaTiposOrdenacao(SelectItem[] listaTiposOrdenacao) {
		this.listaTiposOrdenacao = listaTiposOrdenacao;
	}

	public SelectItem[] getListaTiposOrdenacao() {
		SelectItem[] lista = new SelectItem[2];
		lista[0] = new SelectItem(Constantes.CONSTANTE_VALOR, "Valor");
		lista[1] = new SelectItem(Constantes.CONSTANTE_QUANTIDADE, "Quantidade");
		if(this.getIdStatus() == null || this.getIdStatus().equals("")){
			this.setIdStatus(Constantes.CONSTANTE_VALOR);
		}
		return lista;
	}

	public void executarRelatorioAnaliticoEntrada(){
		try {
			validarRelatorioAnaliticoEntrada();
			
			String status = "";
			
			if(this.getIdStatus().equals("T")){
				status = Constantes.STATUS_ATIVO + "', '" + Constantes.STATUS_CANCELADO;				
			}else{
				status = this.getIdStatus();
			}

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();
			ByteArrayOutputStream byteOutputStream = 
				(ByteArrayOutputStream)getFachada().gerarRelatorioAnaliticoEntradas(this.getDataInicial(), 
																				    this.getDataFinal(),
																				    status);
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioAnaliticoEntrada" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();			
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
	}
	
	public void executarRelatorioAnaliticoMovimentacaoEstoque(){
		try {
			validarRelatorioAnaliticoMovimentacaoEstoque();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();
			ByteArrayOutputStream byteOutputStream = 
				(ByteArrayOutputStream)getFachada().gerarRelatorioAnaliticoMovimentacaoEstoque(this.getDataInicial(), 
																							   this.getDataFinal());
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioAnaliticoMovimentacaoEstoque" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();			
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
	}

	public void executarRelatorioAnaliticoOperacoesDevolucao(){
		try {
			validarRelatorioAnaliticoOperacoesDevolucao();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();
			ByteArrayOutputStream byteOutputStream = 
				(ByteArrayOutputStream)getFachada().gerarRelatorioAnaliticoOperacoesDevolucao(new Integer(this.getIdLoja()).intValue(), 
																							  this.getDataInicial(), 
																							  this.getDataFinal());
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioAnaliticoOperacoesDevolucao" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();			
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
	}

	public void executarRelatorioAnaliticoFechamentoVenda(){
		try {
			validarRelatorioAnaliticoFechamentoVenda();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();
			ByteArrayOutputStream byteOutputStream = 
				(ByteArrayOutputStream)getFachada().gerarRelatorioAnaliticoVendas(new Integer(this.getIdLoja()).intValue(), 
																				  this.getDataInicial(), 
																				  this.getDataFinal());
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioAnaliticoFechamentoVendas" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();			
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
	}
	
	public void validarRelatorioAnaliticoEntrada() throws AppException{
		validaPeriodo();
	}
	
	public void validarRelatorioAnaliticoMovimentacaoEstoque() throws AppException{
		validaPeriodo();
	}
	
	public void validarRelatorioAnaliticoOperacoesDevolucao() throws AppException{
		if(this.getIdLoja() == null || this.getIdLoja().equals("0")){
			throw new AppException("É necessário selecionar uma Loja!");
		}
		validaPeriodo();
	}
	
	public void validarRelatorioAnaliticoFechamentoVenda() throws AppException{
		if(this.getIdLoja() == null || this.getIdLoja().equals("0")){
			throw new AppException("É necessário selecionar uma Loja!");
		}
		validaPeriodo();
	}
	
	public void validaPeriodo() throws AppException{
		if(this.getDataInicial() == null || this.getDataInicial().equals("")){
			throw new AppException("É necessário informar a Data Inicial!");
		} else if(this.getDataFinal() == null || this.getDataFinal().equals("")){
			throw new AppException("É necessário informar a Data Final!");
		} else if(this.getDataInicial().after(this.getDataFinal())){
			throw new AppException("A Data Final deve ser maior que a Data Inicial!");
		}
	}
	
	public void setInit(HtmlForm init) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();            
		String param = (String)  params.get(ACAO);
		if (param != null){
			resetBB();
//			if(VALOR_ACAO.equals(param)){
//				setListaUsuarios(null);
//			}
		}
	}
	
	public void resetBB(){
		this.setIdLoja("0");
		this.setDataInicial(null);
		this.setDataFinal(null);
		
//		this.idLoja = "0";
//		this.dataInicial = null;
//		this.dataFinal = null;
	}

}