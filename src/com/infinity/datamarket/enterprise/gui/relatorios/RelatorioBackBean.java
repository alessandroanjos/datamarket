package com.infinity.datamarket.enterprise.gui.relatorios;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.collection.PersistentSet;

import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.usuario.Vendedor;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class RelatorioBackBean extends BackBean {
	
	Date dataInicial;
	Date dataFinal;
	
	String idLoja;
	String idEstoque;
	
	String idOperador;
	
	private String idStatus;
	private SelectItem[] listaStatus;
    
	private String idTipoOrdenacao = Constantes.CONSTANTE_VALOR;
	private SelectItem[] listaTiposOrdenacao;
	
	SelectItem[] lojas;
	SelectItem[] estoques;
	
	SelectItem[] operadores;
	
	String idVendedor;
	SelectItem[] vendedores;
	
	String idLojaSaida;
	String idLojaEntrada;
	String idEstoqueSaida;
	String idEstoqueEntrada;
	
	SelectItem[] lojasSaida;
	List<Estoque> estoquesSaida;
	SelectItem[] lojasEntrada;
	List<Estoque> estoquesEntrada;

	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}
	
	public String getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(String idOperador) {
		this.idOperador = idOperador;
	}

	private Set<Loja> carregarLojas() {		
//		List<Loja> lojas = null;
//		try {
//			lojas = (ArrayList<Loja>)getFachada().consultarTodosLoja();
		Set<Loja> lojas = null;
		try {
			lojas = (PersistentSet)LoginBackBean.getInstancia().getUsuario().getLojas();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return lojas;
	}
	
	private List<Usuario> carregarOperadores() {		
		List<Usuario> usuarios = null;
		try {
			usuarios = (ArrayList<Usuario>)getFachada().consultarTodosUsuario();			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return usuarios;
	}
	
	public SelectItem[] getOperadores(){
		SelectItem[] arrayOperadores = null;
		try {
			List<Usuario> operadores = carregarOperadores();
			arrayOperadores = new SelectItem[operadores.size()+1];
			SelectItem item0 = new SelectItem("0", "Todos");
			arrayOperadores[0] = item0;
			int i = 1;			
			for(Usuario usuarioTmp : operadores){
				SelectItem item = new SelectItem(usuarioTmp.getId().toString(), usuarioTmp.getNome());
				arrayOperadores[i++] = item;
			}
			if(this.getIdOperador() == null){
				this.setIdOperador(arrayOperadores[0].getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayOperadores;
				
	}
	
	public SelectItem[] getLojas(){
		SelectItem[] arrayLojasAssociadas = null;
		try {
			Set<Loja> lojas = carregarLojas();
			arrayLojasAssociadas = new SelectItem[lojas.size()];
			int i = 0;
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
	
	public void setOperadores(SelectItem[] operadores) {
		this.operadores = operadores;
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

	public String executarRelatorioAnaliticoEntrada(){
		FacesContext ctx = FacesContext.getCurrentInstance();
		try {
			validarRelatorioAnaliticoEntrada();
			
			String[] status = null; 
			
			if(this.getIdStatus().equals("T")){
				status = new String[]{Constantes.STATUS_ATIVO,Constantes.STATUS_CANCELADO};				
			}else{
				status = new String[]{this.getIdStatus()};
			}

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();
			ByteArrayOutputStream byteOutputStream = 
				(ByteArrayOutputStream)getFachada().gerarRelatorioAnaliticoEntradas(this.getDataInicial(), 
																				    this.getDataFinal(),
																				    status,
																				    this.getIdLoja(),
																				    this.getIdEstoque());
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioAnaliticoEntrada" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();
			return "";
		} catch (AppException e) {
			e.printStackTrace();
			
			String mensagem = "";
			if(e.getCause() != null && e.getCause().getMessage() != null){
				mensagem = e.getCause().getMessage();
			}else {
				mensagem = e.getMessage();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
		return "";
	}
	
	public String executarRelatorioAnaliticoMovimentacaoEstoque(){
		try {
			validarRelatorioAnaliticoMovimentacaoEstoque();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();
			ByteArrayOutputStream byteOutputStream = 
				(ByteArrayOutputStream)getFachada().gerarRelatorioAnaliticoMovimentacaoEstoque(this.getDataInicial(), 
																							   this.getDataFinal(),
																							   this.getIdLojaSaida(),
																							   this.getIdEstoqueSaida(),
																							   this.getIdLojaEntrada(),
																							   this.getIdEstoqueEntrada());
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioAnaliticoMovimentacaoEstoque" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();
			return "";
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			String mensagem = "";
			if(e.getCause() != null && e.getCause().getMessage() != null){
				mensagem = e.getCause().getMessage();
			}else {
				mensagem = e.getMessage();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
		return "";
	}

	public String executarRelatorioAnaliticoOperacoesDevolucao(){
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
			return "";
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			String mensagem = "";
			if(e.getCause() != null && e.getCause().getMessage() != null){
				mensagem = e.getCause().getMessage();
			}else {
				mensagem = e.getMessage();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
		return "";
	}

	public String executarRelatorioAnaliticoFechamentoVenda(){
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
			return "";
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			String mensagem = "";
			if(e.getCause() != null && e.getCause().getMessage() != null){
				mensagem = e.getCause().getMessage();
			}else {
				mensagem = e.getMessage();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
		return "";
	}
	
	public String executarRelatorioABCVendas(){
		try {
			validarRelatorioABCVendas();
			
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();
			
			ByteArrayOutputStream byteOutputStream = null;
			
			if (getIdTipoOrdenacao() != null && getIdTipoOrdenacao().equals(Constantes.CONSTANTE_QUANTIDADE)){
				byteOutputStream = 
					(ByteArrayOutputStream)getFachada().gerarRelatorioABCVendas(new Integer(this.getIdLoja()).intValue(),this.getDataInicial(), 
																					    this.getDataFinal(),
																					    Constantes.CONSTANTE_QUANTIDADE);
					
			}else if (getIdTipoOrdenacao() != null && getIdTipoOrdenacao().equals(Constantes.CONSTANTE_VALOR)){
				byteOutputStream = 
					(ByteArrayOutputStream)getFachada().gerarRelatorioABCVendas(new Integer(this.getIdLoja()).intValue(),this.getDataInicial(), 
																					    this.getDataFinal(),
																					    Constantes.CONSTANTE_VALOR);
				
			}
			
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioABCVendasValor" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();
			return "";
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			String mensagem = "";
			if(e.getCause() != null && e.getCause().getMessage() != null){
				mensagem = e.getCause().getMessage();
			}else {
				mensagem = e.getMessage();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
		return "";
	}
	
	
	public String executarRelatorioFechamentoCaixaGeral(){
		try {
			validarRelatorioABCVendas();
			
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();
			
			ByteArrayOutputStream byteOutputStream =  
					(ByteArrayOutputStream)getFachada().gerarRelatorioFechamentoCaixaGeral(new Integer(this.getIdLoja()).intValue(),this.getDataInicial(), 
																					    this.getDataFinal());					
			
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioFechamentoCaixaGeral" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();
			return "";
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			String mensagem = "";
			if(e.getCause() != null && e.getCause().getMessage() != null){
				mensagem = e.getCause().getMessage();
			}else {
				mensagem = e.getMessage();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
		return "";
	}
	
	public String executarRelatorioFechamentoCaixaOperador(){
		try {
			validarRelatorioABCVendas();
			
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();
			
			ByteArrayOutputStream byteOutputStream =
					(ByteArrayOutputStream)getFachada().gerarRelatorioFechamentoCaixaOperador(new Integer(this.getIdLoja()).intValue(),this.getDataInicial(), 
																					    this.getDataFinal(),new Integer(this.getIdOperador()));
													
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioFechamentoCaixaOperador" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();
			return "";
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			String mensagem = "";
			if(e.getCause() != null && e.getCause().getMessage() != null){
				mensagem = e.getCause().getMessage();
			}else {
				mensagem = e.getMessage();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
		return "";
	}
	
	public String executarRelatorioComissaoPorVendedor(){
		try {
			validarRelatorioComissaoPorVendedor();
			
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();
			
			ByteArrayOutputStream byteOutputStream =
					(ByteArrayOutputStream)getFachada().gerarRelatorioComissaoVendedor(new Integer(this.getIdLoja()).intValue(),this.getDataInicial(), 
																					    this.getDataFinal(),new Integer(this.getIdVendedor()).intValue());
													
			
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioComissaoPorVendedor" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();
			return "";
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			String mensagem = "";
			if(e.getCause() != null && e.getCause().getMessage() != null){
				mensagem = e.getCause().getMessage();
			}else {
				mensagem = e.getMessage();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
		return "";
	}
	
	public String executarRelatorioLucroBruto(){
		try {
			validarRelatorioLucroBruto();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();

			ByteArrayOutputStream byteOutputStream = 
				(ByteArrayOutputStream)getFachada().gerarRelatorioLucroBrutoVenda(new Integer(this.getIdLoja()).intValue(), 
																				  this.getDataInicial(), 
																				  this.getDataFinal());
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioLucroBruto" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();
			return "";
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			String mensagem = "";
			if(e.getCause() != null && e.getCause().getMessage() != null){
				mensagem = e.getCause().getMessage();
			}else {
				mensagem = e.getMessage();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
		return "";
	}
	
	public String executarRelatorioEstoqueAtual(){
		try {
			validarRelatorioEstoqueAtual();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			ServletOutputStream out = response.getOutputStream();

			ByteArrayOutputStream byteOutputStream = 
				(ByteArrayOutputStream)getFachada().gerarRelatorioEstoqueAtual(new Integer(this.getIdLoja()).intValue(), new Integer(this.getIdEstoque()).intValue());
			out.write(byteOutputStream.toByteArray(), 0, byteOutputStream.size());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment;filename=RelatorioEstoqueAtual" + System.currentTimeMillis() + ".pdf");
			context.responseComplete();
			out.flush();
			out.close();
			return "";
		} catch (AppException e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			String mensagem = "";
			if(e.getCause() != null && e.getCause().getMessage() != null){
				mensagem = e.getCause().getMessage();
			}else {
				mensagem = e.getMessage();
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			ctx.addMessage(null, msg);
		} catch (IOException e) {			
			e.printStackTrace();
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao executar o Relatório!", "");
			ctx.addMessage(null, msg);
		}
		return "";
	}
	
	public void validarRelatorioABCVendas() throws AppException{
		validaPeriodo();
	}
	
	public void validarRelatorioAnaliticoEntrada() throws AppException{
		if(this.getIdLoja() == null || this.getIdLoja().equals("0")){
			throw new AppException("É necessário selecionar uma Loja!");
		} else if(this.getIdEstoque() == null || this.getIdEstoque().equals("0")){
			throw new AppException("É necessário selecionar um Estoque!");
		}
		validaPeriodo();
	}
	
	public void validarRelatorioAnaliticoMovimentacaoEstoque() throws AppException{
		if(this.getIdLojaSaida() != null && this.getIdLojaEntrada() != null && this.getIdLojaSaida().equals(this.getIdLojaEntrada()) && this.getIdEstoqueSaida() != null && this.getIdEstoqueEntrada() != null && this.getIdEstoqueSaida().equals(this.getIdEstoqueEntrada())){
			throw new AppException("Estoque de Saída deve ser diferente do Estoque de Entrada para uma mesma Loja!");
		}
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
	
	public void validarRelatorioComissaoPorVendedor() throws AppException{
		validaPeriodo();
	}
	
	public void validarRelatorioLucroBruto() throws AppException{
		validaPeriodo();
	}
	
	public void validarRelatorioEstoqueAtual() throws AppException{
		if(this.getIdLoja() == null || this.getIdLoja().equals("0")){
			throw new AppException("É necessário selecionar uma Loja!");
		}
		
		if(this.getIdEstoque() == null || this.getIdEstoque().equals("0")){
			throw new AppException("É necessário selecionar um Estoque!");
		}
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
			this.setIdVendedor(null);
//			if(VALOR_ACAO.equals(param)){
//				setListaUsuarios(null);
//			}
		}
	}
	
	public void limparRelatorioABCVendas(){
		resetBB();
		setIdTipoOrdenacao(Constantes.CONSTANTE_VALOR);
	}
	
	public void limparRelatorioAnaliticoFechamentoVenda(){
		resetBB();
	}
	
	public void limparRelatorioFechamentoCaixaGeral(){
		resetBB();
	}
	
	public void limparRelatorioFechamentoCaixaOperador(){
		resetBB();
	}
	
	public void limparRelatorioAnaliticoOperacoesDevolucao(){
		resetBB();
	}
	
	public void limparRelatorioComissaoPorVendedor(){
		resetBB();
		this.setIdVendedor(null);
	}
	
	public void limparRelatorioLucroBruto(){
		resetBB();
	}
	
	public void limparRelatorioEstoqueAtual(){
		resetBB();
	}
	
	public void resetBB(){
		this.setIdLoja(null);
		this.setDataInicial(null);
		this.setDataFinal(null);		
	}

	public String getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(String idVendedor) {
		this.idVendedor = idVendedor;
	}
	
	private List<Usuario> carregarUsuarios(IPropertyFilter filter) {		
		List<Usuario> usuarios = null;
		try {
			usuarios = (ArrayList<Usuario>)getFachada().consultarUsuario(filter);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return usuarios;
	}

	public SelectItem[] getVendedores() {
		SelectItem[] arrayVendedores = null;
		try {
			Loja loja = null;
			if(this.getIdLoja() != null && !this.getIdLoja().equals("0")){
				loja = (Loja)getFachada().consultarLojaPorId(new Long(this.getIdLoja()));	
			}
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Vendedor.class);
			
			filter.addProperty("lojas", loja);
			List<Usuario> usuariosVendedores = carregarUsuarios(filter);
			arrayVendedores = new SelectItem[usuariosVendedores.size()+1];
			int i = 0;
			arrayVendedores[i++] = new SelectItem("0", "Todos");
			for(Usuario usuariosTmp : usuariosVendedores){
				SelectItem item = new SelectItem(usuariosTmp.getId().toString(), usuariosTmp.getNome());
				arrayVendedores[i++] = item;
			}
			if(this.getIdVendedor() == null && arrayVendedores.length > 0){
				this.setIdVendedor(arrayVendedores[0].getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayVendedores;
	}

	public void setVendedores(SelectItem[] vendedores) {
		this.vendedores = vendedores;
	}
	
	public void carregarVendedoresPorLoja(ValueChangeEvent event){
        try {
        	this.getVendedores();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		}
	}

	public String getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(String idEstoque) {
		this.idEstoque = idEstoque;
	}
	
	private List<Estoque> carregarEstoques(IPropertyFilter filter) {		
		List<Estoque> estoques = null;
		try {
			estoques = (ArrayList<Estoque>)getFachada().consultarEstoque(filter);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return estoques;
	}

	public void carregarEstoquesPorLoja(ValueChangeEvent event){
        try {
        	this.getEstoques();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		}
	}


	public SelectItem[] getEstoques() {
		SelectItem[] arrayEstoques = null;
		try {
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Estoque.class);
			
			filter.addProperty("pk.loja.id", new Long(this.getIdLoja()));
			List<Estoque> estoques = carregarEstoques(filter);
			arrayEstoques = new SelectItem[estoques.size()];
			int i = 0;
			for(Estoque estoquesTmp : estoques){
				SelectItem item = new SelectItem(estoquesTmp.getPk().getId().toString(), estoquesTmp.getDescricao());
				arrayEstoques[i++] = item;
			}
			if(this.getIdEstoque() == null && arrayEstoques.length > 0){
				this.setIdEstoque(arrayEstoques[0].getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayEstoques;
	}


	public void setEstoques(SelectItem[] estoques) {
		this.estoques = estoques;
	}

	public String getIdEstoqueEntrada() {
		return idEstoqueEntrada;
	}

	public void setIdEstoqueEntrada(String idEstoqueEntrada) {
		this.idEstoqueEntrada = idEstoqueEntrada;
	}

	public String getIdEstoqueSaida() {
		return idEstoqueSaida;
	}

	public void setIdEstoqueSaida(String idEstoqueSaida) {
		this.idEstoqueSaida = idEstoqueSaida;
	}

	public String getIdLojaEntrada() {
		return idLojaEntrada;
	}

	public void setIdLojaEntrada(String idLojaEntrada) {
		this.idLojaEntrada = idLojaEntrada;
	}

	public String getIdLojaSaida() {
		return idLojaSaida;
	}

	public void setIdLojaSaida(String idLojaSaida) {
		this.idLojaSaida = idLojaSaida;
	}
	
	public SelectItem[] getLojasSaida(){
		SelectItem[] arrayLojasAssociadas = null;
		try {
			Set<Loja> lojasSaida = carregarLojas();
			arrayLojasAssociadas = new SelectItem[lojasSaida.size()];
			int i = 0;
			for(Loja lojasAssociadasTmp : lojasSaida){
				SelectItem item = new SelectItem(lojasAssociadasTmp.getId().toString(), lojasAssociadasTmp.getNome());
				arrayLojasAssociadas[i++] = item;
			}
			if(this.getIdLojaSaida() == null){
				this.setIdLojaSaida(arrayLojasAssociadas[0].getValue().toString());
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

	public SelectItem[] getLojasEntrada(){
		SelectItem[] arrayLojasAssociadas = null;
		try {
			Set<Loja> lojasEntrada = carregarLojas();
			arrayLojasAssociadas = new SelectItem[lojasEntrada.size()];
			int i = 0;
			for(Loja lojasAssociadasTmp : lojasEntrada){
				SelectItem item = new SelectItem(lojasAssociadasTmp.getId().toString(), lojasAssociadasTmp.getNome());
				arrayLojasAssociadas[i++] = item;
			}
			if(this.getIdLojaEntrada() == null){
				this.setIdLojaEntrada(arrayLojasAssociadas[0].getValue().toString());
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
	
	public SelectItem[] getEstoquesSaida() {
		SelectItem[] arrayEstoques = null;
		try {
			List<Estoque> estoques = carregarEstoquesSaida();
			arrayEstoques = new SelectItem[estoques.size()];
			int i = 0;
			for (Estoque estoqueTmp : estoques) { 
				SelectItem item = new SelectItem(estoqueTmp.getPk().getId().toString(),estoqueTmp.getDescricao());
				arrayEstoques[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}

		return arrayEstoques;
	}
	
	public SelectItem[] getEstoquesEntrada() {
		SelectItem[] arrayEstoques = null;
		try {
			List<Estoque> estoques = carregarEstoquesEntrada();
			arrayEstoques = new SelectItem[estoques.size()];
			int i = 0;
			for (Estoque estoqueTmp : estoques) { 
				SelectItem item = new SelectItem(estoqueTmp.getPk().getId().toString(),estoqueTmp.getDescricao());
				arrayEstoques[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}

		return arrayEstoques;
	}
	
	private List<Estoque> carregarEstoquesEntrada() {

		try {
        	IPropertyFilter filter = new PropertyFilter();
        	filter.setTheClass(Estoque.class);
        	
        	filter.addProperty("pk.loja.id", new Long(this.getIdLojaEntrada() != null ? this.getIdLojaEntrada():"0"));
        	
        	estoquesEntrada = (ArrayList<Estoque>)getFachada().consultarEstoque(filter);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return estoquesEntrada;
	}

	private List<Estoque> carregarEstoquesSaida() {

		try {
        	IPropertyFilter filter = new PropertyFilter();
        	filter.setTheClass(Estoque.class);
        	
        	filter.addProperty("pk.loja.id", new Long(this.getIdLojaSaida() != null ? this.getIdLojaSaida():"0"));
        	
        	estoquesSaida = (ArrayList<Estoque>)getFachada().consultarEstoque(filter);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return estoquesSaida;
	}
	
	public void carregarEstoquesPorLojaSaida(ValueChangeEvent event){
        try {
        	this.getEstoquesSaida();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		}
	}
	
	public void carregarEstoquesPorLojaEntrada(ValueChangeEvent event){
        try {
        	this.getEstoquesEntrada();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		}
	}
}