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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.usuario.Vendedor;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
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

	public void executarRelatorioAnaliticoEntrada(){
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
	
	public void executarRelatorioABCVendas(){
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
	
	
	public void executarRelatorioFechamentoCaixaGeral(){
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
	
	public void executarRelatorioFechamentoCaixaOperador(){
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
	
	public void executarRelatorioComissaoPorVendedor(){
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
	
	public void executarRelatorioLucroBruto(){
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
	
	public void executarRelatorioEstoqueAtual(){
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
	
	public void validarRelatorioABCVendas() throws AppException{
		validaPeriodo();
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
		this.setIdLoja("0");
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
			Loja loja = null;
			if(this.getIdLoja() != null && !this.getIdLoja().equals("0")){
				loja = (Loja)getFachada().consultarLojaPorId(new Long(this.getIdLoja()));	
			}
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Estoque.class);
			
			filter.addProperty("pk.loja", loja);
			List<Estoque> estoques = carregarEstoques(filter);
			arrayEstoques = new SelectItem[estoques.size()+1];
			int i = 0;
			arrayEstoques[i++] = new SelectItem("0", "");
			for(Estoque estoquesTmp : estoques){
				SelectItem item = new SelectItem(estoquesTmp.getPk().getId().toString(), estoquesTmp.getDescricao());
				arrayEstoques[i++] = item;
			}
			if(this.getIdVendedor() == null && arrayEstoques.length > 0){
				this.setIdVendedor(arrayEstoques[0].getValue().toString());
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
}