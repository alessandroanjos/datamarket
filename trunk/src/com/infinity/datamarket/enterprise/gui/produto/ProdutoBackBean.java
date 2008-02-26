package com.infinity.datamarket.enterprise.gui.produto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.produto.GrupoProduto;
import com.infinity.datamarket.comum.produto.Imposto;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.produto.Unidade;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.ValidationException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class ProdutoBackBean extends BackBean{
	private String id;
	private String codigoExterno;
	private String codigoAutomacao;
	private String descricaoCompleta;
	private String descricaoCompacta;
	private String precoPadrao;
	private String precoPromocional;
	private String idTipoProduto;
	private String idUnidade;
	private String idImposto;
	private String idGrupo;
	
	private Collection produtos;
	
	
	
	public String getCodigoAutomacao() {
		return codigoAutomacao;
	}


	public void setCodigoAutomacao(String codigoAutomacao) {
		this.codigoAutomacao = codigoAutomacao;
	}


	public String getCodigoExterno() {
		return codigoExterno;
	}


	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}


	public String getDescricaoCompacta() {
		return descricaoCompacta;
	}


	public void setDescricaoCompacta(String descricaoCompacta) {
		this.descricaoCompacta = descricaoCompacta;
	}


	public String getDescricaoCompleta() {
		return descricaoCompleta;
	}


	public void setDescricaoCompleta(String descricaoCompleta) {
		this.descricaoCompleta = descricaoCompleta;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getIdGrupo() {
		return idGrupo;
	}


	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}


	public String getIdImposto() {
		return idImposto;
	}


	public void setIdImposto(String idImposto) {
		this.idImposto = idImposto;
	}


	public String getIdTipoProduto() {
		return idTipoProduto;
	}


	public void setIdTipoProduto(String idTipoProduto) {
		this.idTipoProduto = idTipoProduto;
	}


	public String getIdUnidade() {
		return idUnidade;
	}


	public void setIdUnidade(String idUnidade) {
		this.idUnidade = idUnidade;
	}


	public String getPrecoPadrao() {
		return precoPadrao;
	}


	public void setPrecoPadrao(String precoPadrao) {
		this.precoPadrao = precoPadrao;
	}


	public String getPrecoPromocional() {
		return precoPromocional;
	}


	public void setPrecoPromocional(String precoPromocional) {
		this.precoPromocional = precoPromocional;
	}


	public Collection getProdutos() {
		return produtos;
	}


	public void setProdutos(Collection produtos) {
		this.produtos = produtos;
	}

	
	private Produto getProduto(){
		Produto p = new Produto();
		p.setId(new Long(getId()));
		p.setCodigoExterno(getCodigoExterno());
		p.setCodigoAutomacao(getCodigoAutomacao());
		p.setDescricaoCompleta(getDescricaoCompleta());
		p.setDescricaoCompacta(getDescricaoCompacta());
		p.setPrecoPadrao(new BigDecimal(getPrecoPadrao()));
		p.setPrecoPromocional(new BigDecimal(getPrecoPromocional()));
		Imposto imp = new Imposto();
		imp.setId(new Long(getIdImposto()));
		p.setImposto(imp);
		TipoProduto tipo = new TipoProduto();
		tipo.setId(new Long(getIdTipoProduto()));
		p.setTipo(tipo);
		Unidade uni = new Unidade();
		uni.setId(new Long(getIdUnidade()));
		p.setUnidade(uni);
		GrupoProduto grupo = new GrupoProduto();
		grupo.setId(new Long(getIdGrupo()));
		p.setGrupo(grupo);
		return p;
	}
	
	private void setProduto(Produto p){

		setCodigoAutomacao(p.getCodigoAutomacao());
		setCodigoExterno(p.getCodigoExterno());
		setDescricaoCompacta(p.getDescricaoCompacta());
		setDescricaoCompleta(p.getDescricaoCompleta());
		setPrecoPadrao(p.getPrecoPadrao().toString());
		if (p.getPrecoPromocional() != null){
			setPrecoPromocional(p.getPrecoPromocional().toString());
		}
		setIdGrupo(p.getGrupo().getId().toString());
		setIdImposto(p.getImposto().getId().toString());
		setIdTipoProduto(p.getTipo().getId().toString());
		setIdUnidade(p.getUnidade().getId().toString());
		
	}

	public String inserir(){
		try {
			getFachada().inserirProduto(getProduto());
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Opera��o Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Grupo de Produto j� Existente!", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		resetBB();
		return "mesma";
	}
	
		
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();            
			String param = (String)  params.get("id");
			if (param != null && !"".equals(param)){
				setId(param);
			}
			if (getId() != null && !"".equals(getId())){
				Produto produto = getFachada().consultarProdutoPorPK(new Long(getId()));
				setProduto(produto);
				return "proxima";
			}else if (getDescricaoCompleta() != null && !"".equals(getDescricaoCompleta())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Produto.class);
				filter.addProperty("descricaoCompleta", getDescricaoCompleta());
				Collection col = getFachada().consultarProdutoPorFiltro(filter,false);
				if (col == null || col.size() == 0){
					setProdutos(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						Produto produto = (Produto)col.iterator().next();
						setProduto(produto);
						return "proxima";
					}else{
						setProdutos(col);
					}
				}
			}else{
				setProdutos(getFachada().consultarTodosProdutos());
			}
		}catch(ObjectNotFoundException e){
			setProdutos(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);			
		}catch(Exception e){
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		this.id = null;
		this.codigoExterno = null;
		this.codigoAutomacao = null;
		this.descricaoCompleta = null;
		this.descricaoCompacta = null;
		this.precoPadrao = null;
		this.precoPromocional = null;
		this.idTipoProduto = null;
		this.idUnidade = null;
		this.idImposto = null;
		this.idGrupo = null;
		return "mesma";
	}
	
	public String alterar(){
		try {
			getFachada().alterarProduto(getProduto());
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Opera��o Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
		} catch (ValidationException e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}
	
	public String excluir(){
		try {
			getFachada().excluirProduto(getProduto());
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Opera��o Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		resetBB();
		return "mesma";
	}
	
	public String resetBB(){
		this.id = null;
		this.codigoExterno = null;
		this.codigoAutomacao = null;
		this.descricaoCompleta = null;
		this.descricaoCompacta = null;
		this.precoPadrao = null;
		this.precoPromocional = null;
		this.idTipoProduto = null;
		this.idUnidade = null;
		this.idImposto = null;
		this.idGrupo = null;
		this.produtos = null;
		return "mesma";
	}
	
	public String voltarConsulta(){
		resetBB();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	
	public SelectItem[] getGrupos() {
		Collection grupos = carregarGrupos();
		SelectItem[] itens = new SelectItem[grupos.size() + 1];
		Iterator it = grupos.iterator();
		itens[0] = new SelectItem("","");
		for(int i = 1;it.hasNext();i++){
			GrupoProduto grupo = (GrupoProduto) it.next();
			SelectItem item = new SelectItem(String.valueOf(grupo.getId()),
					grupo.getDescricao());
			itens[i] = item;
		}
		if (idGrupo == null){
			idGrupo = (String) itens[0].getValue();
		}
		return itens;
	}

	private Collection carregarGrupos() {
		Collection grupos = null;
		try{
			grupos = getFachada().consultarTodosGruposProduto();
		}catch(Exception e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return grupos;
	}
	
	public SelectItem[] getTipos() {
		Collection tipos = carregarTipos();
		SelectItem[] itens = new SelectItem[tipos.size()];
		Iterator it = tipos.iterator();
		for(int i = 0;it.hasNext();i++){
			TipoProduto tipo = (TipoProduto) it.next();
			SelectItem item = new SelectItem(String.valueOf(tipo.getId()),tipo.getDescricao());
			itens[i] = item;
		}
		idTipoProduto = (String) itens[0].getValue();
		return itens;
	}

	private Collection carregarTipos() {
		Collection tipos = null;
		try{
			tipos = getFachada().consultarTodosTipoProduto();
		}catch(Exception e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return tipos;
	}
	
	public SelectItem[] getUnidades() {
		Collection unidades = carregarUnidades();
		SelectItem[] itens = new SelectItem[unidades.size()];
		Iterator it = unidades.iterator();
		for(int i = 0;it.hasNext();i++){
			Unidade unidade = (Unidade) it.next();
			SelectItem item = new SelectItem(String.valueOf(unidade.getId()),unidade.getDescricao());
			itens[i] = item;
		}
		idUnidade = (String) itens[0].getValue();
		return itens;
	}

	private Collection carregarUnidades() {
		Collection unidades = null;
		try{
			unidades = getFachada().consultarTodasUnidades();
		}catch(Exception e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return unidades;
	}
	
	public SelectItem[] getImpostos() {
		Collection impostos = carregarImpostos();
		SelectItem[] itens = new SelectItem[impostos.size()];
		Iterator it = impostos.iterator();
		for(int i = 0;it.hasNext();i++){
			Imposto imposto = (Imposto) it.next();
			SelectItem item = new SelectItem(String.valueOf(imposto.getId()),imposto.getDescricao());
			itens[i] = item;
		}
		idImposto = (String) itens[0].getValue();
		return itens;
	}

	private Collection carregarImpostos() {
		Collection impostos = null;
		try{
			impostos = getFachada().consultarTodosImpostos();
		}catch(Exception e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return impostos;
	}


}
