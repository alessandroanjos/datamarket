package com.infinity.datamarket.enterprise.gui.produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.collection.PersistentSet;
import org.hibernate.exception.ConstraintViolationException;

import com.infinity.datamarket.comum.fabricante.Fabricante;
import com.infinity.datamarket.comum.produto.GrupoProduto;
import com.infinity.datamarket.comum.produto.Imposto;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.produto.Unidade;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ValidationException;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class ProdutoBackBean extends BackBean{
	String abaCorrente;
	private String id;
	private String codigoExterno;
	private String codigoAutomacao;
	private String descricaoCompleta;
	private String descricaoCompacta;
	private String precoPadrao;
	private String precoPromocional;
	private String precoCompra;	
	private String idTipoProduto;
	private String idUnidade;
	private String idImposto;
	private String idGrupo;
	private String idFabricante;
	private Fabricante fabricante;
	private String[] listaLojas;
	
	private Collection produtos;
	

	private String idLoja;
	
	public String getIdLoja() {
		return idLoja;
	}


	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}


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

	
	private Produto getProduto(String operacao){
		Produto p = new Produto();
		if (!operacao.equals(BackBean.INSERIR)) {
			p.setId(new Long(this.getId()));
		} else {
			if (getId()==null) p.setId(getIdInc(Produto.class));
		}
		p.setCodigoExterno(getCodigoExterno());
		p.setCodigoAutomacao(getCodigoAutomacao());
		p.setDescricaoCompleta(getDescricaoCompleta());
		p.setDescricaoCompacta(getDescricaoCompacta());
		p.setPrecoPadrao(new BigDecimal(getPrecoPadrao()));
		if (getPrecoPromocional() != null && !"".equals(getPrecoPromocional())){ 
			p.setPrecoPromocional(new BigDecimal(getPrecoPromocional()));
		}
		if (getPrecoCompra() != null && !"".equals(getPrecoCompra())){ 
			p.setPrecoCompra(new BigDecimal(getPrecoCompra()));
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
		p.setStatus(Produto.ATIVO);
		p.setLojas(criaLojas(listaLojas));
		if (getIdFabricante() != null && !"0".equals(getIdFabricante())){
			Fabricante fabricante = new Fabricante();
			fabricante.setId(new Long(getIdFabricante()));
			p.setFabricante(fabricante);
		}
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
		if(p.getPrecoCompra() != null){
			setPrecoCompra(p.getPrecoCompra().toString());	
		}		
		setIdGrupo(p.getGrupo().getId().toString());
		setIdImposto(p.getImposto().getId().toString());
		setIdTipoProduto(p.getTipo().getId().toString());
		setIdUnidade(p.getUnidade().getId().toString());
		if (p.getFabricante()!= null) 
		setIdFabricante(p.getFabricante().getId().toString());
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
	
	public void validarProduto() throws AppException{
		
		if(this.getCodigoExterno() == null || this.getCodigoExterno().equals("")){
			throw new AppException("É necessário informar um Código Externo.");
		}
		
		if(this.getCodigoAutomacao() == null || this.getCodigoAutomacao().equals("")){
			throw new AppException("É necessário informar um Código de Automação.");
		}

		if(this.getDescricaoCompleta() == null || this.getDescricaoCompleta().equals("")){
			throw new AppException("É necessário informar uma Descrição Completa.");
		}

		if(this.getDescricaoCompacta() == null || this.getDescricaoCompacta().equals("")){
			throw new AppException("É necessário informar uma Descrição Compacta.");
		}

		if(this.getPrecoPadrao() == null || (this.getPrecoPadrao() != null && new BigDecimal(this.getPrecoPadrao()).setScale(2).equals(new BigDecimal("0.00")))){
			throw new AppException("É necessário informar um Preço Padrão.");
		}

		if(this.getIdImposto() == null || this.getIdImposto().equals("0")){
			throw new AppException("É necessário informar um Imposto.");
		}

		if(this.getIdTipoProduto() == null || this.getIdTipoProduto().equals("0")){
			throw new AppException("É necessário informar um Tipo de Produto.");
		}

		if(this.getIdUnidade() == null || this.getIdUnidade().equals("0")){
			throw new AppException("É necessário informar uma Unidade.");
		}

		if(this.getIdGrupo() == null || this.getIdGrupo().equals("0")){
			throw new AppException("É necessário informar um Grupo de Produto.");
		}

		if(this.getListaLojas() == null || this.getListaLojas().length == 0){
			throw new AppException("É necessário informar pelo menos uma Loja.");
		}
	}

	public String inserir(){
		try {

			validarProduto();
			
			getFachada().inserirProduto(getProduto(this.INSERIR));
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Produto já Existente!", "");
			getContextoApp().addMessage(null, msg);

		} catch (AppException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);

		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Código externo já existe!", "");
				getContextoApp().addMessage(null, msg);
			} else {
				e.printStackTrace();
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Sistema!", "");
				getContextoApp().addMessage(null, msg);
			}
			
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
//			PropertyFilter filter = new PropertyFilter();
//			filter.setTheClass(Produto.class);
//			if (getDescricaoCompleta() != null && !"".equals(getDescricaoCompleta())){				
//				filter.addProperty("descricaoCompleta", getDescricaoCompleta());
//			}
//			if (getIdTipoProduto() != null && !"0".equals(getIdTipoProduto())){				
//				filter.addProperty("tipo.id", new Long(getIdTipoProduto()));
//			}
//			if (getIdGrupo() != null && !"0".equals(getIdGrupo())){				
//				filter.addProperty("grupo.id", new Long(getIdGrupo()));
//			}
//			if (getIdImposto() != null && !"0".equals(getIdImposto())){				
//				filter.addProperty("imposto.id", new Long(getIdImposto()));
//			}
//			if (getIdUnidade() != null && !"0".equals(getIdUnidade())){				
//				filter.addProperty("unidade.id", new Long(getIdUnidade()));
//			}
//			if (getIdFabricante() != null && !"0".equals(getIdFabricante())){				
//				filter.addProperty("fabricante.id", new Long(getIdFabricante()));
//			}
			Produto prod = new Produto();
			if (getDescricaoCompleta() != null && !"".equals(getDescricaoCompleta())){				
				prod.setDescricaoCompleta(getDescricaoCompleta());
			}
			if (getIdTipoProduto() != null && !"0".equals(getIdTipoProduto())){
				TipoProduto tpProd = new TipoProduto();
				tpProd.setId(new Long(getIdTipoProduto()));
				prod.setTipo(tpProd);
			}
			if (getIdGrupo() != null && !"0".equals(getIdGrupo())){
				GrupoProduto gpProd = new GrupoProduto();
				gpProd.setId(new Long(getIdGrupo()));
				prod.setGrupo(gpProd);
			}
			if (getIdImposto() != null && !"0".equals(getIdImposto())){				
				Imposto imp = new Imposto();
				imp.setId(new Long(getIdImposto()));
				prod.setImposto(imp);
			}
			if (getIdUnidade() != null && !"0".equals(getIdUnidade())){				
				Unidade uni = new Unidade();
				uni.setId(new Long(getIdUnidade()));
				prod.setUnidade(uni);
			}
			if (getIdFabricante() != null && !"0".equals(getIdFabricante())){				
				Fabricante fab = new Fabricante();
				fab.setId(new Long(getIdFabricante()));
				prod.setFabricante(fab);
			}
			Collection col = getFachada().consultarProdutosPorFiltro(prod, this.getIdLoja());
			if (col == null || col.size() == 0){
				setProdutos(null);
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Nenhum Registro Encontrado", "");
				getContextoApp().addMessage(null, msg);	
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
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);
			setExisteRegistros(false);
		}catch(Exception e){
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
			setExisteRegistros(false);
		}
		return "mesma";
	}
	
	public String alterar(){
		try {
			
			validarProduto();
			
			getFachada().alterarProduto(getProduto(this.ALTERAR));
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ValidationException e){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (AppException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);

		} catch (Exception e) {
			
			if (e.getCause().getCause() instanceof HibernateException) {
				
			   if (e.getCause().getCause().getCause() instanceof ConstraintViolationException) {
				   
				   FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Código externo já existe!", "");
				   getContextoApp().addMessage(null, msg);
			   } else {
					e.printStackTrace();
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro de Sistema!", "");
					getContextoApp().addMessage(null, msg);
			   }
			} else {
				e.printStackTrace();
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Sistema!", "");
				getContextoApp().addMessage(null, msg);
			}
		}

		return "mesma";
	}
	
	public String excluir(){
		try {
			getFachada().excluirProduto(getProduto(this.EXCLUIR));
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}
	
	public void resetBB(){
		this.id = null;
		this.codigoExterno = null;
		this.codigoAutomacao = null;
		this.descricaoCompleta = null;
		this.descricaoCompacta = null;
		this.precoPadrao = null;
		this.precoPromocional = null;
		this.setPrecoCompra("");
		this.idTipoProduto = null;
		this.idUnidade = null;
		this.idFabricante = null;
		this.idImposto = null;
		this.idGrupo = null;
		this.listaLojas = null;
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
//		idGrupo = "";
		return retorno;
	}
	
	public SelectItem[] getGrupos() {
		Collection grupos = carregarGrupos();
		SelectItem[] itens = new SelectItem[grupos.size()+1];
		Iterator it = grupos.iterator();
		itens[0] = new SelectItem("0","");
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
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return grupos;
	}
	
	public SelectItem[] getTiposConsulta() {
		SelectItem[] retorno = getTipos(); 
//		idTipoProduto = "";
		return retorno;
	}
	
	public SelectItem[] getTipos() {
		Collection tipos = carregarTipos();
		SelectItem[] itens = new SelectItem[tipos.size()+1];
		itens[0] = new SelectItem("0","");
		Iterator it = tipos.iterator();
		for(int i = 1;it.hasNext();i++){
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
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return tipos;
	}
	
	public SelectItem[] getUnidadesConsulta() {
		SelectItem[] retorno = getUnidades(); 
//		idUnidade = "";
		return retorno;
	}
	
	public SelectItem[] getUnidades() {
		Collection unidades = carregarUnidades();
		SelectItem[] itens = new SelectItem[unidades.size()+1];
		Iterator it = unidades.iterator();
		itens[0] = new SelectItem("0","");
		for(int i = 1;it.hasNext();i++){
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
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return unidades;
	}
	
	
	public SelectItem[] getImpostosConsulta() {
		SelectItem[] retorno = getImpostos(); 
//		idImposto = "";
		return retorno;
	}
	public SelectItem[] getFabricantesConsulta() {
		SelectItem[] retorno = getFabricantes(); 
//		idFabricante = "";
		return retorno;
	}
	public SelectItem[] getImpostos() {
		Collection impostos = carregarImpostos();
		SelectItem[] itens = new SelectItem[impostos.size()+1];
		Iterator it = impostos.iterator();
		itens[0] = new SelectItem("0","");
		for(int i = 1;it.hasNext();i++){
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
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
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
//		Collection lojas = null;
//		try{
//			lojas = getFachada().consultarTodosLoja();
		Set<Loja> lojas = null;
		try {
			lojas = (PersistentSet)LoginBackBean.getInstancia().getUsuario().getLojas();
		}catch(Exception e){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
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
		if (param != null){
			resetBB();
			if(VALOR_ACAO.equals(param)){
				setProdutos(null);
			}
		}
	}


	/**
	 * @return the abaCorrente
	 */
	public String getAbaCorrente() {
		return abaCorrente;
	}


	/**
	 * @param abaCorrente the abaCorrente to set
	 */
	public void setAbaCorrente(String abaCorrente) {
		this.abaCorrente = abaCorrente;
	}


	/**
	 * @return the precoCompra
	 */
	public String getPrecoCompra() {
		return precoCompra;
	}


	/**
	 * @param precoCompra the precoCompra to set
	 */
	public void setPrecoCompra(String precoCompra) {
		this.precoCompra = precoCompra;
	}
	
	private List<Fabricante> carregarFabricantees() {

		List<Fabricante> fabricantes = null;
		try {
			fabricantes = (ArrayList<Fabricante>) getFachada().consultarTodosFabricantees();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return fabricantes;
	}
	public SelectItem[] getFabricantes() {
		SelectItem[] arrayFabricantes = null;
		try {
			List<Fabricante> fabricantees = carregarFabricantees();
			arrayFabricantes = new SelectItem[fabricantees.size()+1];
			int i = 0;
			arrayFabricantes[i++] = new SelectItem("0", "");
			for (Fabricante fabricanteTmp : fabricantees) {
				String nomeFabricante = "";
				if (fabricanteTmp.getNomeFantasia() != null)
					nomeFabricante = fabricanteTmp.getNomeFantasia();
				else	
					nomeFabricante = fabricanteTmp.getNomeFabricante();
				SelectItem item = new SelectItem(fabricanteTmp.getId().toString(),
						nomeFabricante);
				arrayFabricantes[i++] = item;
			}

			if (this.getIdFabricante() == null || this.getIdFabricante().equals("")
					|| this.getIdFabricante().equals("0")) {
				this.setIdFabricante((String) arrayFabricantes[0].getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		if (this.idFabricante== null) {
			this.idFabricante = arrayFabricantes[0].getValue().toString();
		}
		return arrayFabricantes;
	}


	/**
	 * @return the fabricante
	 */
	public Fabricante getFabricante() {
		return fabricante;
	}


	/**
	 * @param fabricante the fabricante to set
	 */
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}


	/**
	 * @return the idFabricante
	 */
	public String getIdFabricante() {
		return idFabricante;
	}


	/**
	 * @param idFabricante the idFabricante to set
	 */
	public void setIdFabricante(String idFabricante) {
		this.idFabricante = idFabricante;
	}
}
