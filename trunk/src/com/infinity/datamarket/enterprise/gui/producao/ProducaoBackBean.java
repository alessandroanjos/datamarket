package com.infinity.datamarket.enterprise.gui.producao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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

import org.hibernate.collection.PersistentSet;



import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.EstoquePK;
import com.infinity.datamarket.comum.producao.Producao;
import com.infinity.datamarket.comum.produto.Composicao;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;

import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class ProducaoBackBean extends BackBean {  

	private List<Estoque> estoques = null; 
	private String idEstoque;
	private Producao producao;
	private String quantidade;
	private String idProduto;
	private Date fabricacao;
	private String lote;
	private String loteConsulta;
	private Produto produto;
	private String idLoja;
	private Date fabricacaoIni;
	private Date fabricacaoFim;
	private List producoes;
	private String descricao;
	private String valorUnitario;
	private String markUp;
	private String precoVenda;
	private String precoVendaAtual;
	private String idAjustarPrecoVenda;
	private SelectItem[] ajustarPrecoVenda;
	
	
	public SelectItem[] getAjustarPrecoVenda() {
		SelectItem[] tipos = new SelectItem[2];
		tipos[0] = new SelectItem(Constantes.SIM,"Sim");
		tipos[1] = new SelectItem(Constantes.NAO,"Não");		
		if(this.getIdAjustarPrecoVenda() == null){
			this.setIdAjustarPrecoVenda(Constantes.NAO);
		}		
		return tipos;
	}

	public void setAjustarPrecoVenda(SelectItem[] ajustarPrecoVenda) {
		this.ajustarPrecoVenda = ajustarPrecoVenda;
	}


	public String getIdAjustarPrecoVenda() {
		return idAjustarPrecoVenda;
	}

	public void setIdAjustarPrecoVenda(String idAjustarPrecoVenda) {
		this.idAjustarPrecoVenda = idAjustarPrecoVenda;
	}

	public String getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(String precoVenda) {
		this.precoVenda = precoVenda;
	}

	public String getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
//		this.descricao = descricao;
	}

	public List<Producao> getProducoes() {
		return producoes;
	}

	public void setProducoes(List<Producao> producoes) {
		this.producoes = producoes;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getLote() {
		Integer l = null;
		try {
			l = Fachada.getInstancia().consultarMaiorNumeroLote();
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Produção já Existente!", "");
			getContextoApp().addMessage(null, msg);
		} catch (AppException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		if (l != null){
			this.lote = l.toString();
		}else{
			l = null;
		}
		return l.toString();
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void resetBB(){

		this.lote = null;
		this.loteConsulta = null;
		this.producao = null;
		this.quantidade = null;
		this.idProduto = null;
		this.fabricacao = null;
		this.produto = null;
		this.producoes = null;
		this.fabricacaoFim = null;
		this.fabricacaoIni = null;
		this.descricao = null;
		this.valorUnitario = null;
		this.setIdAjustarPrecoVenda(Constantes.NAO);
		this.setPrecoVenda(null);
		this.setPrecoVendaAtual(null);
		this.setMarkUp(null);
	}
	
	public String validaProducaoManutencao(){
		String msg = null;
		if (quantidade == null || quantidade.equals("")){
			msg = "Quantidade Inválida";
		}
		return msg;
		
	}
	
	public String validaProducao(){
		String msg = null;
		if (idProduto != null && !idProduto.equals("")){
			try {
				Produto prod = getFachada().consultarProdutoPorPK(new Long(idProduto));
				if (!prod.getEnquadramento().equals(Produto.FABRICADO)){
					msg = "Produto nao é um produto frabicado!";
				}
			} catch (ObjectNotFoundException e) {
				msg = "Produto nao Encontrado!";
			} catch (AppException e) {
				msg = e.getMessage();
			}
			
		}else{
			msg = "Produto Inválido!";
		}
		if (fabricacao == null){
			msg = "Data de Fabricação Inválida";
		}
		if (quantidade == null || quantidade.equals("")){
			msg = "Quantidade Inválida";
		}
		if (idLoja == null || idLoja.equals("")){
			msg = "Loja Inválida";
		}
		return msg;
	}

	public String inserir(){
		try{
			
			String msgValidacao = validaProducao();
			
			if (msgValidacao == null || msgValidacao.equals("")){
			
				Produto produto = new Produto();
				produto.setId(new Long(idProduto));
				
				Producao producao = new Producao();
				producao.setDataFabricacao(fabricacao);
				producao.setId(new Long(lote));
				producao.setProduto(produto);
				producao.setQuantidade(new BigDecimal(quantidade));
				producao.setLote(Integer.parseInt(lote));
				producao.setValorUnitario(new BigDecimal(valorUnitario));
				producao.setPrecoVenda(new BigDecimal(precoVenda));
				producao.setPrecoVendaAnterior(new BigDecimal(precoVendaAtual));
				producao.setMarkUp(new BigDecimal(markUp));
				
				EstoquePK pk = new EstoquePK();
				pk.setId(new Long(idEstoque));
				Loja loja = new Loja();
				loja.setId(new Long(idLoja));
				pk.setLoja(loja);
								
				
				Estoque est = Fachada.getInstancia().consultarEstoquePorId(pk);
				
				producao.setEstoque(est);
				
				boolean ajustaProd = this.getIdAjustarPrecoVenda().equals(Constantes.SIM)?true:false;
				
				Fachada.getInstancia().inserirProducao(producao,ajustaProd);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Operação Realizada com Sucesso!", "");
				getContextoApp().addMessage(null, msg);
				resetBB();
			}else{
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						msgValidacao, "");
				getContextoApp().addMessage(null, msg);
			}
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Produção já Existente!", "");
			getContextoApp().addMessage(null, msg);
		} catch (AppException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}		
		return "mesma";
	}
	
	public String voltarConsulta(){
		Date dataFim = (Date) this.fabricacaoFim.clone();
		Date dataIni = (Date) this.fabricacaoIni.clone();
		resetBB();
		this.fabricacaoFim = dataFim;
		this.fabricacaoIni = dataIni;
		consultar();
		return "voltar";
	}
	
	private void validaPeriodo() throws AppException{
		if(this.fabricacaoIni == null || this.fabricacaoIni.equals("")){
			throw new AppException("É necessário informar a Data Inicial!");
		} else if(this.fabricacaoFim == null || this.fabricacaoFim.equals("")){
			throw new AppException("É necessário informar a Data Final!");
		} else if(this.fabricacaoIni.after(this.fabricacaoFim)){
			throw new AppException("A Data Final deve ser maior que a Data Inicial!");
		}
	}
	
	public String alterar(){
		String mensagem = validaProducaoManutencao();
		if (mensagem == null){	
		
			try{
				Fachada.getInstancia().alterarProducao(producao,new BigDecimal(this.quantidade));
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Operação Realizada com Sucesso!", "");
				getContextoApp().addMessage(null, msg);
				resetBB();			
			} catch (ObjectExistentException e) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Produção já Existente!", "");
				getContextoApp().addMessage(null, msg);
			} catch (AppException e) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						e.getMessage(), "");
				getContextoApp().addMessage(null, msg);
			} catch (Exception e) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Sistema!", "");
				getContextoApp().addMessage(null, msg);
			}	
		}else{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			getContextoApp().addMessage(null, msg);
			return "mesma";
		}
		return "consulta";
	}
	
	public String excluir(){
		String mensagem = validaProducaoManutencao();
		if (mensagem == null){	
			try{
				Fachada.getInstancia().excluirProducao(producao);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Operação Realizada com Sucesso!", "");
				getContextoApp().addMessage(null, msg);
				resetBB();			
			} catch (ObjectExistentException e) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Produção já Existente!", "");
				getContextoApp().addMessage(null, msg);
			} catch (AppException e) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						e.getMessage(), "");
				getContextoApp().addMessage(null, msg);
			} catch (Exception e) {
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro de Sistema!", "");
				getContextoApp().addMessage(null, msg);
			}		
		}else{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensagem, "");
			getContextoApp().addMessage(null, msg);
			return "mesma";
		}
		return "consulta";
	}
	
	public String manter(){
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();
		String id = (String) params.get("producao");
		try {
			this.producao = Fachada.getInstancia().consultarProducaoPorId(Long.parseLong(id));
			this.quantidade = this.producao.getQuantidade().toString();
			this.produto = this.producao.getProduto();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "manter";
	}
	
	public String consultar(){
		try{
			
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Producao.class);
			
			if (loteConsulta != null && !loteConsulta.equals("")){
				filter.addProperty("lote", Integer.parseInt(loteConsulta));
			}
			if (idProduto != null && !idProduto.equals("")){
				filter.addProperty("produto.id", Long.parseLong(idProduto));
			}
			
			validaPeriodo();
			
			filter.addPropertyInterval("dataFabricacao", fabricacaoIni,4);
			filter.addPropertyInterval("dataFabricacao", fabricacaoFim,2);
			
			producoes = (List) Fachada.getInstancia().consultarProducao(filter);

		
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Produção já Existente!", "");
			getContextoApp().addMessage(null, msg);
		} catch (AppException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}		
		return "mesma";
	}
	
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}



	public Date getFabricacao() {
		return fabricacao;
	}



	public void setFabricacao(Date fabricacao) {
		this.fabricacao = fabricacao;
	}



	public Producao getProducao() {
		return producao;
	}
	
	

	public void setProducao(Producao producao) {
		this.producao = producao;
	}



	public String getIdProduto() {
		return idProduto;
	}



	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}
	
	
	/**
	 * @param init the init to set
	 */
	public void setInit(HtmlForm init) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();            
		String param = (String)  params.get(ACAO);
		if (param != null){
			if(VALOR_ACAO.equals(param)){
				resetBB();
			}
		}else if((params.get("acaoLocal") != null && ((String)params.get("acaoLocal")).equals("pesquisarProdutos"))){
			try {
 				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Produto.class);
				filter.addProperty("enquadramento", Produto.FABRICADO);
				filter.addProperty("id", new Long((String)params.get("codigoProduto")));
				Collection c = getFachada().consultarProdutoPorFiltro(filter, true);
				if(c == null || c.isEmpty()){
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Produto não existe no cadastro ou não é um Produto Fabricado!", "");
					getContextoApp().addMessage(null, msg);
					this.idProduto = null;
					this.descricao = null;
				}else{
					Produto prod = (Produto)c.iterator().next();
					
					if(prod != null){
						
						this.produto = prod;
						this.descricao = prod.getDescricaoCompleta();
						BigDecimal valorUnitario = BigDecimal.ZERO;
						if (prod.getComposicao()!= null && prod.getComposicao().size() > 0){
							Iterator i = prod.getComposicao().iterator();
							while(i.hasNext()){
								Composicao comp = (Composicao) i.next();
								valorUnitario = valorUnitario.add((comp.getQuantidade().multiply(comp.getPk().getProduto().getPrecoCompra())));
							}
							
						}
						BigDecimal vendaAtual = prod.getPrecoPadrao();
						if (prod.getPrecoPromocional() != null && prod.getPrecoPromocional().compareTo(BigDecimal.ZERO) > 0){
							vendaAtual = prod.getPrecoPromocional();
						}
						this.setPrecoVendaAtual(vendaAtual.setScale(2).toString());	
						this.setValorUnitario(valorUnitario.setScale(2, BigDecimal.ROUND_DOWN).toString());
						this.setPrecoVenda(prod.getMarkup().multiply(valorUnitario).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toString());
						this.setMarkUp(prod.getMarkup().setScale(2).toString());
					}else{
						this.setPrecoVendaAtual(BigDecimal.ZERO.setScale(2).toString());
						this.setValorUnitario(BigDecimal.ZERO.setScale(2).toString());
						this.setIdAjustarPrecoVenda(Constantes.NAO);
						this.setPrecoVenda(BigDecimal.ZERO.setScale(2).toString());
						this.setMarkUp(BigDecimal.ZERO.setScale(2).toString());
					}
				}
			} catch (Exception e) {				
				e.printStackTrace();			
			}
		}
	}
	
	private Set<Loja> carregarLojas() {
		Set<Loja> lojas = null;
		try {
			lojas = (PersistentSet)LoginBackBean.getInstancia().getUsuario().getLojas();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return lojas;
	}


	public SelectItem[] getLojas() {
		SelectItem[] arrayLojas = null;
		try {
			Set<Loja> lojas = carregarLojas();
			arrayLojas = new SelectItem[lojas.size()];
			int i = 0;
			for (Loja lojaTmp : lojas) { 
				SelectItem item = new SelectItem(lojaTmp.getId().toString(), lojaTmp.getNome());
				arrayLojas[i++] = item;
			}
			if(this.getIdLoja() == null){
				this.setIdLoja((String)arrayLojas[0].getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}

		return arrayLojas;
	}

	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}
	
	public void carregarEstoquesPorLoja(ValueChangeEvent event){
        try {
        	this.getEstoques();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		}
	}
	
	public SelectItem[] getEstoques() {
		SelectItem[] arrayEstoques = null;
		try {
			List<Estoque> estoques = carregarEstoques();
			arrayEstoques = new SelectItem[estoques.size()];
			int i = 0;
			for (Estoque estoqueTmp : estoques) { 
				SelectItem item = new SelectItem(estoqueTmp.getPk().getId().toString(),
						estoqueTmp.getEstoqueVenda()==null?estoqueTmp.getDescricao():estoqueTmp.getEstoqueVenda());
				arrayEstoques[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}

		return arrayEstoques;
	}
	
	private List<Estoque> carregarEstoques() {

		try {
        	IPropertyFilter filter = new PropertyFilter();
        	filter.setTheClass(Estoque.class);
        	
        	filter.addProperty("pk.loja.id", new Long(this.getIdLoja() != null ? this.getIdLoja():"0"));
        	
        	estoques = (ArrayList<Estoque>)getFachada().consultarEstoque(filter);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return estoques;
	}

	public String getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(String idEstoque) {
		this.idEstoque = idEstoque;
	}

	public Date getFabricacaoFim() {
		return fabricacaoFim;
	}

	public void setFabricacaoFim(Date fabricacaoFim) {
		this.fabricacaoFim = fabricacaoFim;
	}

	public Date getFabricacaoIni() {
		return fabricacaoIni;
	}

	public void setFabricacaoIni(Date fabricacaoIni) {
		this.fabricacaoIni = fabricacaoIni;
	}

	public String getLoteConsulta() {
		return loteConsulta;
	}

	public void setLoteConsulta(String loteConsulta) {
		this.loteConsulta = loteConsulta;
	}

	public String getMarkUp() {
		return markUp;
	}

	public void setMarkUp(String markUp) {
		this.markUp = markUp;
	}

	public String getPrecoVendaAtual() {
		return precoVendaAtual;
	}

	public void setPrecoVendaAtual(String precoVendaAtual) {
		this.precoVendaAtual = precoVendaAtual;
	}

	


}
