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
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.collection.PersistentSet;
import org.hibernate.exception.ConstraintViolationException;

import com.infinity.datamarket.comum.fabricante.Fabricante;
import com.infinity.datamarket.comum.produto.Composicao;
import com.infinity.datamarket.comum.produto.ComposicaoPK;
import com.infinity.datamarket.comum.produto.GrupoProduto;
import com.infinity.datamarket.comum.produto.Imposto;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.produto.Unidade;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
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
	private BigDecimal precoPadrao;
	private BigDecimal precoPromocional;
	private BigDecimal precoCompra;	
	private String idTipoProduto;
	private String idUnidade;
	private String idImposto;
	private String idGrupo;
	private String idFabricante;
	private Fabricante fabricante;
	private String[] listaLojas;
	
	private Collection produtos;
	
	private String idEnquadramento;
	private SelectItem[] listaTiposEnquadramento;
	private String idProdutoComposicao;
	private String descricaoProdutoComposicao;
	private BigDecimal quantidadeProdutoComposicao;
	private List<Composicao> itensComposicao = new ArrayList<Composicao>();
	private List<Composicao> itensComposicaoModificados = new ArrayList<Composicao>();
	private BigDecimal quantidadeProdutoComposicaoTotal;
	private String enquadramentoSelecionado;

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


	public BigDecimal getPrecoPadrao() {
		return precoPadrao;
	}


	public void setPrecoPadrao(BigDecimal precoPadrao) {
		this.precoPadrao = precoPadrao;
	}


	public BigDecimal getPrecoPromocional() {
		return precoPromocional;
	}


	public void setPrecoPromocional(BigDecimal precoPromocional) {
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
		p.setPrecoPadrao(getPrecoPadrao());
		if (getPrecoPromocional() != null && !"".equals(getPrecoPromocional())){ 
			p.setPrecoPromocional(getPrecoPromocional());
		}
		if (getPrecoCompra() != null && !"".equals(getPrecoCompra())){ 
			p.setPrecoCompra(getPrecoCompra());
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
		
		p.setEnquadramento(this.getEnquadramentoSelecionado());
		
		if(this.getEnquadramentoSelecionado().equals(Produto.FABRICADO)){
			Collection<Composicao> c = new HashSet<Composicao>();

			Iterator itItensComposicao = this.getItensComposicao().iterator();
			while(itItensComposicao.hasNext()){
				Composicao comp = (Composicao)itItensComposicao.next();
				comp.getPk().setIdProduto(p.getId());
				c.add(comp);
			}
			
			p.setComposicao(c);
		}else{
			p.setComposicao(null);
		}
		
		return p;
	}
	
	private void setProduto(Produto p){
		setId(p.getId().toString());
		setCodigoAutomacao(p.getCodigoAutomacao());
		setCodigoExterno(p.getCodigoExterno());
		setDescricaoCompacta(p.getDescricaoCompacta());
		setDescricaoCompleta(p.getDescricaoCompleta());
		setPrecoPadrao(p.getPrecoPadrao());
		if (p.getPrecoPromocional() != null){
			setPrecoPromocional(p.getPrecoPromocional());
		}
		if(p.getPrecoCompra() != null){
			setPrecoCompra(p.getPrecoCompra());	
		}		
		setIdGrupo(p.getGrupo().getId().toString());
		setIdImposto(p.getImposto().getId().toString());
		setIdTipoProduto(p.getTipo().getId().toString());
		setIdUnidade(p.getUnidade().getId().toString());
		if (p.getFabricante()!= null) 
		setIdFabricante(p.getFabricante().getId().toString());
		carregaLojas(p.getLojas());
		
		this.setIdEnquadramento(p.getEnquadramento());
		this.setEnquadramentoSelecionado(p.getEnquadramento());
		
		if(p.getEnquadramento().equals(Produto.FABRICADO) && p.getComposicao() != null && p.getComposicao().size() > 0){
			if(this.getItensComposicao() == null){
				this.setItensComposicao(new ArrayList<Composicao>());
			}
			Iterator it = p.getComposicao().iterator();
			while(it.hasNext()){
				Composicao composicao = (Composicao)it.next();
				if(composicao != null){
					composicao.setAcao("N");
					this.getItensComposicao().add(composicao);
					this.setQuantidadeProdutoComposicaoTotal((this.getQuantidadeProdutoComposicaoTotal() != null ? this.getQuantidadeProdutoComposicaoTotal() : BigDecimal.ZERO).add(composicao.getQuantidade()));
				}
			}			
		}		
	}
	
	private Collection criaLojas(String[] idLojas){
		Collection<Loja> c = new HashSet<Loja>();
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
		
		if(this.getDescricaoCompleta() == null || this.getDescricaoCompleta().equals("")){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar uma Descrição Completa.");
		}

		if(this.getDescricaoCompacta() == null || this.getDescricaoCompacta().equals("")){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar uma Descrição Compacta.");
		}

		if(this.getPrecoPadrao() == null || (this.getPrecoPadrao() != null && this.getPrecoPadrao().setScale(2).equals(new BigDecimal("0.00")))){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar um Preço Padrão.");
		}

		if(this.getCodigoExterno() == null || this.getCodigoExterno().equals("")){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar um Código Externo.");
		}
		
		if(this.getCodigoAutomacao() == null || this.getCodigoAutomacao().equals("")){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar um Código de Automação.");
		}

		if(this.getIdTipoProduto() == null || this.getIdTipoProduto().equals("0")){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar um Tipo de Produto.");
		}

		if(this.getIdGrupo() == null || this.getIdGrupo().equals("0")){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar um Grupo de Produto.");
		}

		if(this.getIdUnidade() == null || this.getIdUnidade().equals("0")){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar uma Unidade.");
		}

		if(this.getIdImposto() == null || this.getIdImposto().equals("0")){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar um Imposto.");
		}

		if(this.getIdFabricante() == null || this.getIdFabricante().equals("0")){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar um Fabricante.");
		}
		
		if(this.getListaLojas() == null || this.getListaLojas().length == 0){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário informar pelo menos uma Loja.");
		}
		
		if(this.getEnquadramentoSelecionado() == null || this.getEnquadramentoSelecionado().equals("")){
			this.setAbaCorrente("tabMenuDiv0");
			throw new AppException("É necessário selecionar o Enquadramento do Produto.");
		}
		
		if(this.getEnquadramentoSelecionado().equals(Produto.FABRICADO) && 
				(this.getItensComposicao() == null || this.getItensComposicao().size() == 0)){
			this.setAbaCorrente("tabMenuDiv2");
			throw new AppException("É necessário informar a Composição do Produto Fabricado.");
		}
	}

	public String inserir(){
		try {

			validarProduto();
			
			Produto produto = getProduto(INSERIR); 
			
			getFachada().inserirProduto(produto);
			
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
			
			Produto produto = getProduto(ALTERAR);
			
			Collection<Composicao> cRemovidos = new ArrayList<Composicao>();

			if(this.getItensComposicaoModificados() != null){
				Iterator itItensComposicao = this.getItensComposicaoModificados().iterator();
				while(itItensComposicao.hasNext()){
					cRemovidos.add((Composicao)itItensComposicao.next());
				}
			}
			
			getFachada().alterarProduto(produto, cRemovidos);
			
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
			getFachada().excluirProduto(getProduto(EXCLUIR));
			
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
		this.precoPadrao = BigDecimal.ZERO.setScale(2);
		this.precoPromocional = BigDecimal.ZERO.setScale(2);
		this.precoCompra = BigDecimal.ZERO.setScale(2);
		this.idTipoProduto = null;
		this.idUnidade = null;
		this.idFabricante = null;
		this.idImposto = null;
		this.idGrupo = null;
		this.listaLojas = null;
		
		resetItensComposicaoBB();
		this.setQuantidadeProdutoComposicaoTotal(BigDecimal.ZERO.setScale(3));
		this.setItensComposicao(null);
		this.setItensComposicaoModificados(null);
		
		this.setAbaCorrente("tabMenuDiv0");
	}
	
	public String voltarConsulta(){
		 resetBB();
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	
	public SelectItem[] getGruposConsulta() {
		return getGrupos();
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
		return getTipos();
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
		return getUnidades();
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
		return getImpostos();
	}
	
	public SelectItem[] getFabricantesConsulta() {
		return getFabricantes();
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
		} else if(params.get("acaoLocal") != null && ((String)params.get("acaoLocal")).equals("pesquisarProdutos")){
			try {
//				Produto prod = getFachada().consultarProdutoPorPK(new Long((String)params.get("codigoProduto")));
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Produto.class);
				filter.addProperty("enquadramento", Produto.MATERIA_PRIMA);
				filter.addProperty("id", new Long((String)params.get("codigoProduto")));
				Collection c = getFachada().consultarProdutoPorFiltro(filter, true);
				if(c == null || c.isEmpty()){
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Produto não existe no cadastro ou não é um Produto Matéria-Prima!", "");
					getContextoApp().addMessage(null, msg);
				}else{
					Produto prod = (Produto)c.iterator().next();
					
					if(prod != null){
						this.descricaoProdutoComposicao = prod.getDescricaoCompleta();
					}
					this.setAbaCorrente("tabMenuDiv2");
				}
			} catch (Exception e) {				
				e.printStackTrace();			
				this.setAbaCorrente("tabMenuDiv2");
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
	public BigDecimal getPrecoCompra() {
		return precoCompra;
	}


	/**
	 * @param precoCompra the precoCompra to set
	 */
	public void setPrecoCompra(BigDecimal precoCompra) {
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
	
	public SelectItem[] getListaTiposEnquadramento() {
		SelectItem[] lista = new SelectItem[3];
		lista[0] = new SelectItem(Produto.FABRICADO, "Produto Fabricado");
		lista[1] = new SelectItem(Produto.REVENDA, "Produto para Revenda");
		lista[2] = new SelectItem(Produto.MATERIA_PRIMA, "Matéria-Prima");
		if(this.getIdEnquadramento() == null || this.getIdEnquadramento().equals("")){
			this.setIdEnquadramento(Produto.FABRICADO);
		}
		return lista;
	}

	public void setListaTiposEnquadramento(SelectItem[] listaTiposEnquadramento) {
		this.listaTiposEnquadramento = listaTiposEnquadramento;
	}


	public String getIdEnquadramento() {
		return idEnquadramento;
	}


	public void setIdEnquadramento(String idEnquadramento) {
		this.idEnquadramento = idEnquadramento;
	}


	public String getDescricaoProdutoComposicao() {
		return descricaoProdutoComposicao;
	}


	public void setDescricaoProdutoComposicao(String descricaoProdutoComposicao) {
		this.descricaoProdutoComposicao = descricaoProdutoComposicao;
	}


	public String getIdProdutoComposicao() {
		return idProdutoComposicao;
	}


	public void setIdProdutoComposicao(String idProdutoComposicao) {
		this.idProdutoComposicao = idProdutoComposicao;
	}


	public BigDecimal getQuantidadeProdutoComposicao() {
		return quantidadeProdutoComposicao;
	}


	public void setQuantidadeProdutoComposicao(BigDecimal quantidadeProdutoComposicao) {
		this.quantidadeProdutoComposicao = quantidadeProdutoComposicao;
	}
	
	public void inserirItemComposicao(){
		try {
			Produto produtoItemComposicao = validarItemComposicao();
			
			Composicao composicaoProduto = new Composicao();
			
			ComposicaoPK composicaoProdutoPK = new ComposicaoPK();
			
			if(this.getId() != null && !this.getId().equals("")){
				composicaoProdutoPK.setIdProduto(new Long(this.getId()));
			}else{
				composicaoProdutoPK.setIdProduto(null);	
			}
//			composicaoProdutoPK.setIdProduto(null);
			composicaoProdutoPK.setProduto(produtoItemComposicao);
			
			composicaoProduto.setPk(composicaoProdutoPK);
			
			composicaoProduto.setQuantidade(this.getQuantidadeProdutoComposicao());
				
			composicaoProduto.setAcao("I");
			
			if(this.getItensComposicao() == null){
				this.setItensComposicao(new ArrayList<Composicao>());
			}		
			this.getItensComposicao().add(composicaoProduto);
			
			this.setQuantidadeProdutoComposicaoTotal((this.getQuantidadeProdutoComposicaoTotal() != null ? this.getQuantidadeProdutoComposicaoTotal() : BigDecimal.ZERO).add(this.getQuantidadeProdutoComposicao()));
			
			resetItensComposicaoBB();
			this.setAbaCorrente("tabMenuDiv2");
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
			this.setAbaCorrente("tabMenuDiv2");
		}		
	}
	
	public void removerItemComposicao(ActionEvent event){
		try {
			UIParameter component = (UIParameter) event.getComponent().findComponent("idExcluirItemComposicao");
			Long produtoTmp = (Long) component.getValue();
			BigDecimal quantidade = BigDecimal.ZERO;
	        
	        if(this.getItensComposicaoModificados() == null){
				this.setItensComposicaoModificados(new ArrayList<Composicao>());
			}
			
			Iterator i = this.getItensComposicao().iterator();
			while(i.hasNext()){
				Composicao itemComposicaoTmp = (Composicao) i.next();
				if (itemComposicaoTmp.getPk().getProduto().getId().equals(produtoTmp)){
					this.getItensComposicao().remove(itemComposicaoTmp);
					itemComposicaoTmp.setAcao("E");
					this.getItensComposicaoModificados().add(itemComposicaoTmp);
					quantidade = quantidade.add(itemComposicaoTmp.getQuantidade());
					break;
				}
			}
			this.setQuantidadeProdutoComposicaoTotal((this.getQuantidadeProdutoComposicaoTotal() != null ? this.getQuantidadeProdutoComposicaoTotal() : BigDecimal.ZERO).subtract(quantidade));
			this.setAbaCorrente("tabMenuDiv2");
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
			this.setAbaCorrente("tabMenuDiv2");
		}
	}

	public Produto validarItemComposicao() throws Exception{
		Produto produto = null;
		
		if(this.getIdProdutoComposicao() == null || (this.getIdProdutoComposicao() != null && this.getIdProdutoComposicao().equals("0"))){
			this.setAbaCorrente("tabMenuDiv2");
			throw new Exception("É necessário informar um produto.");
		}else{
			produto = getFachada().consultarProdutoPorPK(new Long(this.getIdProdutoComposicao()));
			if(produto == null){
				this.setAbaCorrente("tabMenuDiv2");
				throw new Exception("O Produto informado é inválido!");
			}else{
				if(this.getItensComposicao() != null && this.getItensComposicao().size() > 0){
					for (Iterator iter = this.getItensComposicao().iterator(); iter.hasNext();) {
						Composicao itemComposicaoTmp = (Composicao) iter.next();
						if(produto.equals(itemComposicaoTmp.getPk().getProduto())){
							this.setAbaCorrente("tabMenuDiv2");
							throw new Exception("O Produto informado já existe na Composição!");
						}
					}
				}
			}
		}
		if(this.getQuantidadeProdutoComposicao() == null || (this.getQuantidadeProdutoComposicao() != null && this.getQuantidadeProdutoComposicao().compareTo(BigDecimal.ZERO.setScale(3)) <= 0)){
			this.setAbaCorrente("tabMenuDiv2");
			throw new Exception("A Quantidade informada é inválida!");
		}
		return produto;

	}
	
	public void resetItensComposicaoBB(){
		this.setIdProdutoComposicao(null);
		this.setDescricaoProdutoComposicao(null);
		this.setQuantidadeProdutoComposicao(null);
	}
	
	public List<Composicao> getItensComposicao() {
		return itensComposicao;
	}


	public void setItensComposicao(List<Composicao> itensComposicao) {
		this.itensComposicao = itensComposicao;
	}


	public BigDecimal getQuantidadeProdutoComposicaoTotal() {
		return quantidadeProdutoComposicaoTotal;
	}


	public void setQuantidadeProdutoComposicaoTotal(
			BigDecimal quantidadeProdutoComposicaoTotal) {
		this.quantidadeProdutoComposicaoTotal = quantidadeProdutoComposicaoTotal;
	}


	public List<Composicao> getItensComposicaoModificados() {
		return itensComposicaoModificados;
	}


	public void setItensComposicaoModificados(
			List<Composicao> itensComposicaoModificados) {
		this.itensComposicaoModificados = itensComposicaoModificados;
	}


	public String getEnquadramentoSelecionado() {
		return enquadramentoSelecionado;
	}


	public void setEnquadramentoSelecionado(String enquadramentoSelecionado) {
		this.enquadramentoSelecionado = enquadramentoSelecionado;
	}

}
