package com.infinity.datamarket.enterprise.gui.produto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
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
import com.infinity.datamarket.comum.usuario.Loja;
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
	private String[] listaLojas;
	
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
		p.setCodigoExterno(getCodigoExterno());
		p.setCodigoAutomacao(getCodigoAutomacao());
		p.setDescricaoCompleta(getDescricaoCompleta());
		p.setDescricaoCompacta(getDescricaoCompacta());
		p.setPrecoPadrao(new BigDecimal(getPrecoPadrao()));
		if (getPrecoPromocional() != null && !"".equals(getPrecoPromocional())){ 
			p.setPrecoPromocional(new BigDecimal(getPrecoPromocional()));
		}
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
		p.setLojas(criaLojas(listaLojas));
		return p;
	}
	
	private void setProduto(Produto p){
		setId(p.getId().toString());
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
		carregaLojas(p.getLojas());
		
	}
	
	private Collection criaLojas(String[] idLojas){
		Collection c = new HashSet();
		for (int i = 0; i < idLojas.length; i++){
			Loja l = new Loja();
			l.setId(new Long(idLojas[i]));
			c.add(l);
		}
		return c;
	}
	
	private void carregaLojas(Collection lojas){
		String[] listaLojas = new String[lojas.size()];
		Iterator i = lojas.iterator();
		for(int count = 0 ; i.hasNext(); count++){
			Loja l = (Loja) i.next();
			listaLojas[count] = l.getId().toString(); 
		}
		this.listaLojas = listaLojas;
	}

	public String inserir(){
		try {
			getFachada().inserirProduto(getProduto());
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Produto já Existente!", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
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
			}
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Produto.class);
			if (getDescricaoCompleta() != null && !"".equals(getDescricaoCompleta())){				
				filter.addProperty("descricaoCompleta", getDescricaoCompleta());
			}
			if (getIdTipoProduto() != null && !"".equals(getIdTipoProduto())){				
				filter.addProperty("tipo.id", new Long(getIdTipoProduto()));
			}
			if (getIdGrupo() != null && !"".equals(getIdGrupo())){				
				filter.addProperty("grupo.id", new Long(getIdGrupo()));
			}
			if (getIdImposto() != null && !"".equals(getIdImposto())){				
				filter.addProperty("imposto.id", new Long(getIdImposto()));
			}
			if (getIdUnidade() != null && !"".equals(getIdUnidade())){				
				filter.addProperty("unidade.id", new Long(getIdUnidade()));
			}
			Collection col = getFachada().consultarProdutoPorFiltro(filter,false);
			if (col == null || col.size() == 0){
				setProdutos(null);
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Nenhum Registro Encontrado", "");
				ctx.addMessage(null, msg);	
				setExisteRegistros(false);
			}else if (col != null){
				if(col.size() == 1){
					Produto produto = (Produto)col.iterator().next();
					setProduto(produto);
					return "proxima";
				}else{
					setExisteRegistros(true);
					setProdutos(col);
				}
			}
		}catch(ObjectNotFoundException e){
			setProdutos(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);
			setExisteRegistros(false);
		}catch(Exception e){
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
			setExisteRegistros(false);
		}
		return "mesma";
	}
	
	public String alterar(){
		try {
			getFachada().alterarProduto(getProduto());
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
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
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
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
		// resetBB();
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	
	public SelectItem[] getGruposConsulta() {
		SelectItem[] retorno = getGrupos(); 
		idGrupo = "";
		return retorno;
	}
	
	public SelectItem[] getGrupos() {
		Collection grupos = carregarGrupos();
		SelectItem[] itens = new SelectItem[grupos.size()];
		Iterator it = grupos.iterator();
		for(int i = 0;it.hasNext();i++){
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
	
	public SelectItem[] getTiposConsulta() {
		SelectItem[] retorno = getTipos(); 
		idTipoProduto = "";
		return retorno;
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
		if (idTipoProduto == null){
			idTipoProduto = (String) itens[0].getValue();
		}
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
	
	public SelectItem[] getUnidadesConsulta() {
		SelectItem[] retorno = getUnidades(); 
		idUnidade = "";
		return retorno;
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
		if (idUnidade == null){
			idUnidade = (String) itens[0].getValue();
		}
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
	
	
	public SelectItem[] getImpostosConsulta() {
		SelectItem[] retorno = getImpostos(); 
		idImposto = "";
		return retorno;
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
		if (idImposto == null){
			idImposto = (String) itens[0].getValue();
		}
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
	
	
	public SelectItem[] getLojas() {
		Collection lojas = carregarLojas();
		SelectItem[] itens = new SelectItem[lojas.size()];
		Iterator it = lojas.iterator();
		for(int i = 0;it.hasNext();i++){
			Loja loja = (Loja) it.next();
			SelectItem item = new SelectItem(String.valueOf(loja.getId()),loja.getNome());
			itens[i] = item;
		}
		return itens;
	}
	
	private Collection carregarLojas() {
		Collection lojas = null;
		try{
			lojas = getFachada().consultarTodosLoja();
		}catch(Exception e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return lojas;
	}


	public String[] getListaLojas() {
		return listaLojas;
	}


	public void setListaLojas(String[] listaLojas) {
		this.listaLojas = listaLojas;
	}
	
	/**
	 * @param init the init to set
	 */
	public void setInit(HtmlForm init) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();            
		String param = (String)  params.get(ACAO);
		resetBB();
		if (param != null && VALOR_ACAO.equals(param)){
			setProdutos(null);
		}
	}
}
