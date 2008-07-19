package com.infinity.datamarket.enterprise.gui.estoque;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.estoque.EntradaProduto;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.EstoqueProduto;
import com.infinity.datamarket.comum.estoque.EstoqueProdutoPK;
import com.infinity.datamarket.comum.estoque.MovimentacaoEstoque;
import com.infinity.datamarket.comum.estoque.ProdutoMovimentacaoEstoque;
import com.infinity.datamarket.comum.estoque.ProdutoMovimentacaoEstoquePK;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class MovimentacaoEstoqueBackBean extends BackBean {   
	private static final int HashSet = 0;
	private String id;
	private String codigoUsuario;
	private Date dataMovimentacao;
	private String idEstoqueSaida;
	private String idEstoqueEntrada;
	private Collection<MovimentacaoEstoque> movimentacaoEstoque;
    private ProdutoMovimentacaoEstoque produtoMovimentacaoEstoque;
	// Atributos para montar os Produtos na movimentação de estoque
	private String idProduto; 
	private String descricao;
	private String quantidade;
	
	// Atributos para montar consulta de produtos no popUp 
	// por estoque saida
	//
	String idProdutoEstoque ="";
	String descricaoCompleta = "";
	Collection<Produto> produtosEstoque=null;
	
	// Atributos ProdutoMovimentacaoEstoqueEntrada
	private Set<ProdutoMovimentacaoEstoque> arrayProduto; 
	private String idEstoque;
    List<Estoque> estoques;
	
	// para uso de filtro de consulta
    private Date dataInicio;
	private Date dataFinal;

    
	
	private String idExcluir; 

	public String resetProdutoBB() {
		this.setIdProduto(null);
		this.setDescricao(null);
		this.setQuantidade(null);
		return "mesma";
	}
	public String excluirProduto() {
		int i = 0;
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();  
		String param = (String)  params.get("idExcluir");
		
		for (Iterator iter = arrayProduto.iterator(); iter.hasNext();) {
			ProdutoMovimentacaoEstoque produtoTmp = (ProdutoMovimentacaoEstoque) iter.next();
			if (produtoTmp.getProduto().getId().equals(new Long(param))) {
				arrayProduto.remove(produtoTmp);
				break;
			}
			i++;
		}
		return "mesma";
	}

	public String inserirProduto() { 
		
		
		
		if (arrayProduto==null){
			arrayProduto = new HashSet<ProdutoMovimentacaoEstoque>();
		}	
        
		Produto produto = null;
		
		try {
			produto = Fachada.getInstancia().consultarProdutoPorPK(new Long(this.idProduto));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String msgValidacao = validaMovimentacao();
		if (msgValidacao.equals("")) {
			msgValidacao = validaProduto(produto,new BigDecimal(this.getQuantidade()));
		}
		if (!msgValidacao.equals("")) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					msgValidacao, "");
			ctx.addMessage(null, msg);
			return "mesma";
		}
		
		int i=0;
		for (Iterator iter = arrayProduto.iterator(); iter.hasNext();) {
			ProdutoMovimentacaoEstoque produtoTmp = (ProdutoMovimentacaoEstoque) iter.next();
			if (produtoTmp.getProduto().getId().equals(produto.getId())) {
				arrayProduto.remove(produtoTmp);
				break;
			}
			i++;
		}
		
		BigDecimal qtdMovimento = new BigDecimal(this.quantidade);
		
		
		
		ProdutoMovimentacaoEstoque produtoMovimentacaoEstoque = new ProdutoMovimentacaoEstoque();
		produtoMovimentacaoEstoque.setProduto(produto);
		produtoMovimentacaoEstoque.setQuantidade(qtdMovimento);
		
		ProdutoMovimentacaoEstoquePK produtoMovimentacaoEstoquePK = new ProdutoMovimentacaoEstoquePK();
		produtoMovimentacaoEstoquePK.setNumeroEntrada(i);
		produtoMovimentacaoEstoque.setPk(produtoMovimentacaoEstoquePK);
		
		
		arrayProduto.add(produtoMovimentacaoEstoque);
		resetProdutoBB();
		return "mesma";
	}
	public String inserir() {

		
		MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();

		movimentacaoEstoque.setId(new Long(this.id));
		movimentacaoEstoque.setDataMovimentacao(new Date());
		
		movimentacaoEstoque.setCodigoUsuario(Integer.parseInt(getCodigoUsuarioLogado()));

		Estoque estoqueSaida = null;
		for (Iterator iter = estoques.iterator(); iter.hasNext();) {
			Estoque element = (Estoque) iter.next();
			if (element.getPk().getId().longValue()==new Long(this.idEstoqueSaida).longValue()) {
				estoqueSaida = (Estoque)element;
				estoqueSaida.getPk().getLoja().setIdEstoque(estoqueSaida.getPk().getId());
			}
			
		}
		
		movimentacaoEstoque.setEstoqueSaida(estoqueSaida);

		
		Estoque estoqueEntrada = null;
		for (Iterator iter = estoques.iterator(); iter.hasNext();) {
			Estoque element = (Estoque) iter.next();
			if (element.getPk().getId().longValue()==new Long(this.idEstoqueEntrada).longValue()) {
				estoqueEntrada = (Estoque)element;
				estoqueEntrada.getPk().getLoja().setIdEstoque(estoqueEntrada.getPk().getId());
			}
			
		}
		
		movimentacaoEstoque.setEstoqueEntrada(estoqueEntrada);
		
	    movimentacaoEstoque.setProdutosMovimentacao(arrayProduto);
	    
	    String msgValidacao = validaMovimentacao();
		if (msgValidacao.equals("")) {
			
			// Validando quantidade disponivél;
			
			for (Iterator iter = arrayProduto.iterator(); iter.hasNext();) {
				ProdutoMovimentacaoEstoque produtoTmp = (ProdutoMovimentacaoEstoque) iter.next();
				    msgValidacao = validaProduto(produtoTmp.getProduto(),produtoTmp.getQuantidade());
				    if (!msgValidacao.equals("")){
					   break;
				    }   
				}
				
		}
			
		if (!msgValidacao.equals("")) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					msgValidacao, "");
			ctx.addMessage(null, msg);
			return "mesma";
		}
	    
		try {
			getFachada().inserirMovimentacaoEstoque(movimentacaoEstoque);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Entrada já Existente!", "");
			ctx.addMessage(null, msg);
		} catch (AppException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
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

	static String ERRO_ESTOQUE_ENTRADA_SAIDA_IGUAL            = "Estoque saida, igual estoque entrada.";
	static String ERRO_QUANTIDADE_SOLICITADA_IGUAL_ZERO 	  = "Quantidade solicitada do produto %1, tem que ser maior que zero";
	static String ERRO_QUANTIDADE_SOLICITADA_MAIOR_DISPONIVEL =	"Produto %1, Quantidade Solicitada %2, Quantidade Disponivél %3.";
	
	public String validaMovimentacao() {
        
		if (getIdEstoqueSaida().equals(getIdEstoqueEntrada())) {
		   return ERRO_ESTOQUE_ENTRADA_SAIDA_IGUAL;
		}

		return "";
	}
	
	public String validaProduto(Produto produto,BigDecimal quantidade) {

		EstoqueProduto estoqueProduto = buscaEstoqueProduto(produto);
		BigDecimal qtdEstoque = estoqueProduto.getQuantidade();
		BigDecimal qtdMovimento = quantidade;
		
		if (qtdMovimento.doubleValue()<=0) {
			return ERRO_QUANTIDADE_SOLICITADA_IGUAL_ZERO.replaceFirst("%1", produto.getDescricaoCompleta());
		} else if (qtdMovimento.doubleValue()>qtdEstoque.doubleValue()) {
			String msgValida = ERRO_QUANTIDADE_SOLICITADA_MAIOR_DISPONIVEL.replaceAll("%1", produto.getDescricaoCompleta());
			msgValida = msgValida.replaceFirst("%2", qtdMovimento.toString());
			msgValida = msgValida.replaceFirst("%3", qtdEstoque.toString());
			return msgValida;
		}
		return "";
	}
	
    public EstoqueProduto buscaEstoqueProduto(Produto produto) {
	    	Estoque estoqueSaida = null;
			for (Iterator iter = estoques.iterator(); iter.hasNext();) {
				Estoque element = (Estoque) iter.next();
				if (element.getPk().getId().longValue()==new Long(this.idEstoqueSaida).longValue()) {
					estoqueSaida = (Estoque)element;
					estoqueSaida.getPk().getLoja().setIdEstoque(estoqueSaida.getPk().getId());
				}
				
			}
    	   EstoqueProdutoPK estoqueProdutoPk = new EstoqueProdutoPK();
    	   estoqueProdutoPk.setEstoque(estoqueSaida);
    	   estoqueProdutoPk.setProduto(produto);
    	   EstoqueProduto estoqueProduto;
		try {
			estoqueProduto = getFachada().consultarEstoqueProduto(estoqueProdutoPk);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	   return estoqueProduto;
    	      	   
    }

	public String alterar() {
		EntradaProduto entradaProduto = new EntradaProduto();
		
		entradaProduto.setDataEntrada(this.dataMovimentacao);
		
		try {
			getFachada().alterarEntradaProduto(entradaProduto);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Entrada já Existente!", "");
			ctx.addMessage(null, msg);
		} catch (AppException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
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
	public String consultar() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();
			String param = (String) params.get("id");

			if (param != null && !"".equals(param)) {
				setId(param);
			}
			if (getId() != null && !"".equals(getId())) {
				
				MovimentacaoEstoque movimentacaoEstoque = getFachada().consultarMovimentacaoEstoquePorId(new Long(id));
				if  (movimentacaoEstoque.getProdutosMovimentacao()!=null) {
					Set<ProdutoMovimentacaoEstoque> produtoMovimentacaoEstoque =  (Set<ProdutoMovimentacaoEstoque>) movimentacaoEstoque.getProdutosMovimentacao();
					this.setArrayProduto(produtoMovimentacaoEstoque);
				}
				this.setDataMovimentacao(movimentacaoEstoque.getDataMovimentacao());
				this.setIdEstoqueEntrada(movimentacaoEstoque.getEstoqueEntrada().getPk().getId().toString());
				this.setIdEstoqueSaida(movimentacaoEstoque.getEstoqueSaida().getPk().getId().toString());
				return "proxima";
				
			} else {
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(MovimentacaoEstoque.class);
				if (getId() != null && !"".equals(getId())) {
	            	filter.addProperty("id", getId());
					return consultarFiltro(filter);
				} else if (getDataInicio() != null && !"".equals(getDataFinal())) {
					filter.addPropertyInterval("dataMovimentacao",getDataInicio(), IntervalObject.MAIOR_IGUAL);
					filter.addPropertyInterval("dataMovimentacao",getDataFinal(), IntervalObject.MENOR_IGUAL);
					return consultarFiltro(filter);
				}
 			}
		} catch (ObjectNotFoundException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);
			setExisteRegistros(false);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
			setExisteRegistros(false);
		}
		
		return resetBB();
	}
	
	private String consultarFiltro(PropertyFilter filter) {

		Collection col=null;
		try {
			col = getFachada().consultarMovimentoEstoque(filter);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (col == null || col.size() == 0) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);
		} else if (col != null) {
			if (col.size() == 1) {
				MovimentacaoEstoque movimentacaoProduto = (MovimentacaoEstoque)col.iterator().next();
				this.setId( movimentacaoProduto.getId().toString());
				Set<ProdutoMovimentacaoEstoque> colProduto =  (Set<ProdutoMovimentacaoEstoque>) movimentacaoProduto.getProdutosMovimentacao();
				this.setArrayProduto(colProduto);
				return "proxima";
			} else {
				this.setMovimentacaoEstoque(col);
			}
		}
		return resetBB();
	}
	
	public String consultarProdutosEstoque(){
		try{
			
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();            
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Produto.class);			
			
			String param = (String)  params.get("idProduto");
			if (param != null && !"".equals(param)){
				setIdProdutoEstoque(param);
				filter.addProperty("id", getIdProdutoEstoque());
			}			

			
			if (getDescricaoCompleta() != null && !"".equals(getDescricaoCompleta())){				
				filter.addProperty("descricaoCompleta", getDescricaoCompleta());
			}
			
			produtosEstoque = getFachada().consultarProdutoPorFiltro(filter, false);
			
			if (produtosEstoque == null || produtosEstoque.size() == 0){
				setProdutosEstoque(null);
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Nenhum Registro Encontrado", "");
				ctx.addMessage(null, msg);					
			}
		}catch(ObjectNotFoundException e){
			setProdutosEstoque(null);
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
		return "mesma";
	}
	
	private List<Estoque> carregarEstoques() {
		try {
			estoques = (ArrayList<Estoque>) getFachada().consultarTodosEstoques();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return estoques;
	}

	public SelectItem[] getEstoques() {
		SelectItem[] arrayEstoques = null;
		try {
			List<Estoque> estoques = carregarEstoques();
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
	public String resetBB() {
		this.setId(null);
		this.setArrayProduto(null);
		resetProdutoBB();
		return "mesma";
	}
    
	public String voltarConsulta() {
		consultar();
		resetProdutoBB();
		resetBB();
		return "voltar";
	}

	public String voltarMenu() {
		resetBB();
		return "voltar";
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * @return the dataEntrada
	 */
	public Date getDataMovimentacao() {
		return dataMovimentacao;
	}
	/**
	 * @param dataEntrada the dataEntrada to set
	 */
	public void setDataMovimentacao(Date dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the arrayProduto
	 */
	public Set<ProdutoMovimentacaoEstoque> getArrayProduto() {
		return arrayProduto;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the idProduto
	 */
	public String getIdProduto() {
		return idProduto;
	}
	/**
	 * @param idProduto the idProduto to set
	 */
	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}
	/**
	 * @param arrayProduto the arrayProduto to set
	 */
	public void setArrayProduto(Set<ProdutoMovimentacaoEstoque> arrayProduto) {
		this.arrayProduto = arrayProduto;
	}
	/**
	 * @return the idEstoque
	 */
	public String getIdEstoque() {
		return idEstoque;
	}
	/**
	 * @param idEstoque the idEstoque to set
	 */
	public void setIdEstoque(String idEstoque) {
		this.idEstoque = idEstoque;
	}
	/**
	 * @return the idExcluir
	 */
	public String getIdExcluir() {
		return idExcluir;
	}
	/**
	 * @param idExcluir the idExcluir to set
	 */
	public void setIdExcluir(String idExcluir) {
		this.idExcluir = idExcluir;
	}

	/**
	 * @return the codigoUsuario
	 */
	public String getCodigoUsuario() {
		return codigoUsuario;
	}
	/**
	 * @param codigoUsuario the codigoUsuario to set
	 */
	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	/**
	 * @return the idEstoqueEntrada
	 */
	public String getIdEstoqueEntrada() {
		return idEstoqueEntrada;
	}
	/**
	 * @param idEstoqueEntrada the idEstoqueEntrada to set
	 */
	public void setIdEstoqueEntrada(String idEstoqueEntrada) {
		this.idEstoqueEntrada = idEstoqueEntrada;
	}
	/**
	 * @return the idEstoqueSaida
	 */
	public String getIdEstoqueSaida() {
		return idEstoqueSaida;
	}
	/**
	 * @param idEstoqueSaida the idEstoqueSaida to set
	 */
	public void setIdEstoqueSaida(String idEstoqueSaida) {
		this.idEstoqueSaida = idEstoqueSaida;
	}
	/**
	 * @return the movimentacaoEstoque
	 */
	public Collection<MovimentacaoEstoque> getMovimentacaoEstoque() {
		return movimentacaoEstoque;
	}
	/**
	 * @param col the movimentacaoEstoque to set
	 */
	public void setMovimentacaoEstoque(
			Collection<MovimentacaoEstoque> col) {
		this.movimentacaoEstoque = (Collection<MovimentacaoEstoque>) col;
	}
	/**
	 * @param estoques the estoques to set
	 */
	public void setEstoques(List<Estoque> estoques) {
		this.estoques = estoques;
	}
	/**
	 * @return the quantidade
	 */
	public String getQuantidade() {
		return quantidade;
	}
	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	/**
	 * @return the produtosEstoque
	 */
	public Collection<Produto> getProdutosEstoque() {
		return produtosEstoque;
	}
	/**
	 * @param produtosEstoque the produtosEstoque to set
	 */
	public void setProdutosEstoque(Collection<Produto> produtosEstoque) {
		this.produtosEstoque = produtosEstoque;
	}
	/**
	 * @return the idProdutoEstoquePop
	 */
	public String getIdProdutoEstoque() {
		return idProdutoEstoque;
	}
	/**
	 * @param idProdutoEstoquePop the idProdutoEstoquePop to set
	 */
	public void setIdProdutoEstoque(String idProdutoEstoque) {
		this.idProdutoEstoque = idProdutoEstoque;
	}
	/**
	 * @return the descricaoCompleta
	 */
	public String getDescricaoCompleta() {
		return descricaoCompleta;
	}
	/**
	 * @param descricaoCompleta the descricaoCompleta to set
	 */
	public void setDescricaoCompleta(String descricaoCompleta) {
		this.descricaoCompleta = descricaoCompleta;
	}
	/**
	 * @return the produtoMovimentacaoEstoque
	 */
	public ProdutoMovimentacaoEstoque getProdutoMovimentacaoEstoque() {
		return produtoMovimentacaoEstoque;
	}
	/**
	 * @param produtoMovimentacaoEstoque the produtoMovimentacaoEstoque to set
	 */
	public void setProdutoMovimentacaoEstoque(
			ProdutoMovimentacaoEstoque produtoMovimentacaoEstoque) {
		this.produtoMovimentacaoEstoque = produtoMovimentacaoEstoque;
	}
	/**
	 * @return the dataFinal
	 */
	public Date getDataFinal() {
		return dataFinal;
	}
	/**
	 * @param dataFinal the dataFinal to set
	 */
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}
	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
}
