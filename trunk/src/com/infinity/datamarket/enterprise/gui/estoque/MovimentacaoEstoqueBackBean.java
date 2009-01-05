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
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.comum.Fachada;
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
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class MovimentacaoEstoqueBackBean extends BackBean {  

	MovimentacaoEstoque movimentacaoProdutoEstoque;
	private static final int HashSet = 0;
	private String id;
	private String codigoUsuario;
	private Date dataMovimentacao;
	private String idEstoqueSaida;
	private String idEstoqueEntrada;
	private Estoque estoqueSaida;
	private Estoque estoqueEntrada;
	private Collection<MovimentacaoEstoque> movimentacaoEstoque;
    private ProdutoMovimentacaoEstoque produtoMovimentacaoEstoque;
	// Atributos para montar os Produtos na movimentação de estoque
	private String idProduto;
	private String codigoExterno;
	private String descricao;
	private String quantidade;
	private String descricaoCompletaEstoque;
	
	// Atributos para montar consulta de produtos no popUp 
	// por estoque saida
	//
	String idProdutoEstoque ="";
	String descricaoCompleta = "";
	Collection<Produto> produtosEstoque=null;
	
	// Atributos ProdutoMovimentacaoEstoqueEntrada
	private Set<ProdutoMovimentacaoEstoque> arrayProduto = new HashSet<ProdutoMovimentacaoEstoque>(); 
	private String idEstoque;
    List<Estoque> estoques;
	
	// para uso de filtro de consulta
    private Date dataInicio;
	private Date dataFinal;
	private String idLoja;
    
	
	private String idExcluir; 

	private static final String TIPO_ENTRADA = "E";
	private static final String TIPO_SAIDA   = "A";
	private String tipoMovimentacao;

	
	public void resetProdutoBB() {
		this.setCodigoExterno(null);
		this.setIdProduto(null);
		this.setDescricao(null);
		this.setDescricaoCompletaEstoque(null);
		this.setQuantidade(null);
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

	public void inserirProduto() { 
		
		System.out.println("arrayProduto--> "+arrayProduto);
		
		if (arrayProduto == null){
			arrayProduto = new HashSet<ProdutoMovimentacaoEstoque>();
		}	
        
		Produto produto = null;
		try {
			produto = Fachada.getInstancia().consultarProdutoPorPK(new Long(this.idProduto));
		} catch (NumberFormatException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Produto inválido!", "");
			ctx.addMessage(null, msg);
			e.printStackTrace();
		} catch (AppException e) {

			// TODO Auto-generated catch block
			if  (e instanceof ObjectNotFoundException) {
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Produto não encontrado!", "");
				ctx.addMessage(null, msg);
			}
		}
		setDescricao(produto.getDescricaoCompleta());
		
		String msgValidacao = validaMovimentacao();
		if (msgValidacao.equals("")) {
			String qtd = "0";
			if (!this.getQuantidade().equals("")) {
				qtd = this.getQuantidade();
			}
			msgValidacao = validaProduto(produto,new BigDecimal(qtd));
		}
		if (!msgValidacao.equals("")) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					msgValidacao, "");
			ctx.addMessage(null, msg);
		}
		
		int i=0;
//		for (Iterator iter = arrayProduto.iterator(); iter.hasNext();) {
//			ProdutoMovimentacaoEstoque produtoTmp = (ProdutoMovimentacaoEstoque) iter.next();
//			if (produtoTmp.getProduto().getId().equals(produto.getId())) {
//				arrayProduto.remove(produtoTmp);
//				break;
//			}
//			i++;
//		} 
		
		for (Iterator iter = arrayProduto.iterator(); iter.hasNext();) {
			ProdutoMovimentacaoEstoque produtoTmp = (ProdutoMovimentacaoEstoque) iter.next();
			if (produtoTmp.getPk().getNumeroEntrada() > i) {
				i = produtoTmp.getPk().getNumeroEntrada();
			}
		} 		 
		
		BigDecimal qtdMovimento = new BigDecimal(this.quantidade);
		
		ProdutoMovimentacaoEstoque produtoMovimentacaoEstoque = new ProdutoMovimentacaoEstoque();
		produtoMovimentacaoEstoque.setProduto(produto);
		produtoMovimentacaoEstoque.setQuantidade(qtdMovimento);
		
		ProdutoMovimentacaoEstoquePK produtoMovimentacaoEstoquePK = new ProdutoMovimentacaoEstoquePK();
//		produtoMovimentacaoEstoquePK.setNumeroEntrada(i);
		produtoMovimentacaoEstoquePK.setNumeroEntrada(++i);
		produtoMovimentacaoEstoque.setPk(produtoMovimentacaoEstoquePK);

		arrayProduto.add(produtoMovimentacaoEstoque);
		resetProdutoBB();
	}
	
	public String inserir() {

		
		MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();

		//movimentacaoEstoque.setId(new Long(this.id));
		
		if (getId()==null) movimentacaoEstoque.setId(getIdInc(MovimentacaoEstoque.class));
		
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
		
	   
	    
	    String msgValidacao = validaMovimentacao();
		if (msgValidacao.equals("")) {
			 movimentacaoEstoque.setProdutosMovimentacao(arrayProduto);
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
			
			this.setMovimentacaoProdutoEstoque(movimentacaoEstoque);
			
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
	static String ERRO_NENHUM_PRODUTO 						  = "É nescessario pelo menos um produto!";
	public String validaMovimentacao() {
        
		if (getIdEstoqueSaida().equals(getIdEstoqueEntrada())) {
		   return ERRO_ESTOQUE_ENTRADA_SAIDA_IGUAL;
		}
		if (arrayProduto == null) {
			return ERRO_NENHUM_PRODUTO; 
		}

		return "";
	}
	
	public String validaProduto(Produto produto,BigDecimal quantidade) {

		EstoqueProduto estoqueProduto = buscaEstoqueProduto(produto);
		if (estoqueProduto!=null) {
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
		} else {
			return "Produto não encontrado no estoque de saida!";
		}
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
//		EntradaProduto entradaProduto = new EntradaProduto();
//		
//		entradaProduto.setDataEntrada(this.dataMovimentacao);
//		
//		try {
//			getFachada().alterarEntradaProduto(entradaProduto);
//			FacesContext ctx = FacesContext.getCurrentInstance();
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
//					"Operação Realizada com Sucesso!", "");
//			ctx.addMessage(null, msg);
//			
//			resetBB();
//		} catch (ObjectExistentException e) {
//			FacesContext ctx = FacesContext.getCurrentInstance();
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//					"Entrada já Existente!", "");
//			ctx.addMessage(null, msg);
//		} catch (AppException e) {
//			FacesContext ctx = FacesContext.getCurrentInstance();
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//					e.getMessage(), "");
//			ctx.addMessage(null, msg);
//		} catch (Exception e) {
//			e.printStackTrace();
//			FacesContext ctx = FacesContext.getCurrentInstance();
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//					"Erro de Sistema!", "");
//			ctx.addMessage(null, msg);
//		}
//		resetBB();
//		return "mesma";
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
				this.setId( movimentacaoEstoque.getId().toString());
				this.setDataMovimentacao(movimentacaoEstoque.getDataMovimentacao());
				
				this.setIdEstoqueEntrada(movimentacaoEstoque.getEstoqueEntrada().getPk().getId().toString());
				this.setEstoqueEntrada(movimentacaoEstoque.getEstoqueEntrada());
				
				this.setIdEstoqueSaida(movimentacaoEstoque.getEstoqueSaida().getPk().getId().toString());
				this.setEstoqueSaida(movimentacaoEstoque.getEstoqueSaida());
				
				this.setMovimentacaoProdutoEstoque(movimentacaoEstoque);
				
				return "proxima";
				
			} else {
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(MovimentacaoEstoque.class);
				Loja loja = buscaLoja(getIdEstoque());
				if (tipoMovimentacao.equals(TIPO_ENTRADA)) {
					filter.addProperty("estoqueEntrada.pk.id", loja);
				} else {
					filter.addProperty("estoqueSaida.pk.id", loja);
				}
				
				if (getId() != null && !"".equals(getId())) {
	            	filter.addProperty("id", getId());
					return consultarFiltro(filter);
				} else if (getDataInicio() != null && !"".equals(getDataFinal())) {
					filter.addPropertyInterval("dataMovimentacao",getDataInicio(), IntervalObject.MAIOR_IGUAL);
					filter.addPropertyInterval("dataMovimentacao",getDataFinal(), IntervalObject.MENOR_IGUAL);
					return consultarFiltro(filter);
				} else {
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Informe o periodo", "");
					ctx.addMessage(null, msg);
					setExisteRegistros(false);
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
	
	private Loja buscaLoja(String id) {

		Loja loja = null;
		
		try {
			loja = getFachada().consultarLojaPorId(new Long(id));
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loja;
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
			setExisteRegistros(false);
		} else if (col != null) {
			if (col.size() == 1) {
				
				MovimentacaoEstoque movimentacaoProduto = (MovimentacaoEstoque)col.iterator().next();
				
				this.setId( movimentacaoProduto.getId().toString());
				this.setDataMovimentacao(movimentacaoProduto.getDataMovimentacao());
				
				this.setIdEstoqueEntrada(movimentacaoProduto.getEstoqueEntrada().getPk().getId().toString());
				this.setEstoqueEntrada(movimentacaoProduto.getEstoqueEntrada());
				
				this.setIdEstoqueSaida(movimentacaoProduto.getEstoqueSaida().getPk().getId().toString());
				this.setEstoqueSaida(movimentacaoProduto.getEstoqueSaida());
				
				Set<ProdutoMovimentacaoEstoque> colProduto =  (Set<ProdutoMovimentacaoEstoque>) movimentacaoProduto.getProdutosMovimentacao();
				this.setArrayProduto(colProduto);
				
				return "proxima";
			} else {
				this.setMovimentacaoEstoque(col);
			}
		}
		return resetBB();
	}
	
	public void consultarProdutosEstoque(){
		try{
			setProdutosEstoque(null);
            
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Produto.class);			

			if (getIdProdutoEstoque() != null && !"".equals(getIdProdutoEstoque())){
				Produto produto = getFachada().consultarProdutoPorPK(new Long(getIdProdutoEstoque()));
				Collection col = new HashSet<Produto>();
				col.add(produto);
				setProdutosEstoque(col);
				setIdProdutoEstoque(null);
				this.setCodigoExterno(null);
				this.setIdProdutoEstoque(null);
				this.setDescricaoCompletaEstoque(null);
				return;
			}			
			
			if (getCodigoExterno() != null && !"".equals(getCodigoExterno())){
				Produto produto = getFachada().consultarProdutoPorCodigoExterno(getCodigoExterno());
				Collection col = new HashSet<Produto>();
				col.add(produto);
				setProdutosEstoque(col);
				this.setCodigoExterno(null);
				this.setIdProdutoEstoque(null);
				this.setDescricaoCompletaEstoque(null);
				return;
			}
			
			if (getDescricaoCompletaEstoque() != null && !"".equals(getDescricaoCompletaEstoque())){				
				filter.addProperty("descricaoCompleta", getDescricaoCompletaEstoque());
			}
			
			produtosEstoque = getFachada().consultarProdutoPorFiltro(filter, false);
			
			if (produtosEstoque == null || produtosEstoque.size() == 0){
				produtosEstoque = getFachada().consultarTodosProdutos();
				if (produtosEstoque == null || produtosEstoque.size() == 0){
					setProdutosEstoque(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				} else {
					setExisteRegistros(true);
					setProdutosEstoque(produtosEstoque);
					this.setCodigoExterno(null);
					this.setIdProdutoEstoque(null);
					this.setDescricaoCompletaEstoque(null);
				}
			} else {				
				this.setCodigoExterno(null);
				this.setIdProdutoEstoque(null);
				this.setDescricaoCompletaEstoque(null);
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
	}
	private List<Loja> carregarLojas() {

		List<Loja> lojas = null;
		try {
			lojas = (ArrayList<Loja>) getFachada().consultarTodosLoja();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return lojas;
	}

	public SelectItem[] getLojas() {
		SelectItem[] arrayLojas = null;
		try {
			List<Loja> lojas = carregarLojas();
			arrayLojas = new SelectItem[lojas.size()];
			int i = 0;
			for (Loja lojaTmp : lojas) {
				SelectItem item = new SelectItem(lojaTmp.getId().toString(),
						lojaTmp.getNome());
				arrayLojas[i++] = item;
			}

			if (this.getIdLoja() == null || this.getIdLoja().equals("")
					|| this.getIdLoja().equals("0")) {
				this.setIdLoja((String) arrayLojas[0].getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		if (this.idLoja == null) {
			this.idLoja = arrayLojas[0].getValue().toString();
		}
		return arrayLojas;
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
	
	
	public SelectItem[] getTipoMovimentoItens() {
		SelectItem[] tipoMovimentoItens = new SelectItem[]{new SelectItem(TIPO_SAIDA,"Saida"),
                new SelectItem(TIPO_ENTRADA,"Entrada")};
		if(getTipoMovimentacao() == null){
			setTipoMovimentacao(TIPO_SAIDA);
		}
		return tipoMovimentoItens;
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
		this.setDataInicio(null);
		this.setDataFinal(null);
		resetProdutoBB();
		return "mesma";
	}
    public String resetConsultaBB() {
		this.setDataInicio(null);
		this.setDataFinal(null);
		this.setMovimentacaoEstoque(null);
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
	 * @return the tipoMovimento
	 */
	public String getTipoMovimentacao() {
		return tipoMovimentacao;
	}
	/**
	 * @param tipoMovimento the tipoMovimento to set
	 */
	public void setTipoMovimentacao(String tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}
	/**
	 * @return the estoqueEntrada
	 */
	public Estoque getEstoqueEntrada() {
		return estoqueEntrada;
	}
	/**
	 * @param estoqueEntrada the estoqueEntrada to set
	 */
	public void setEstoqueEntrada(Estoque estoqueEntrada) {
		this.estoqueEntrada = estoqueEntrada;
	}
	/**
	 * @return the estoqueSaida
	 */
	public Estoque getEstoqueSaida() {
		return estoqueSaida;
	}
	/**
	 * @param estoqueSaida the estoqueSaida to set
	 */
	public void setEstoqueSaida(Estoque estoqueSaida) {
		this.estoqueSaida = estoqueSaida;
	}
	/**
	 * @param init the init to set
	 */
	public void setInit(HtmlForm init) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();            
		String param = (String)  params.get(ACAO);
		System.out.println("param--> "+param);
		if (param != null){			
			if(VALOR_ACAO.equals(param)){
				resetBB();
				setMovimentacaoEstoque(null);
			}
		}else if(params.get("acaoPopUp") != null && ((String)params.get("acaoPopUp")).equals("init")){
			resetProdutoBB();
			setProdutosEstoque(null);
		}else if(params.get("acaoLocal") != null && ((String)params.get("acaoLocal")).equals("pesquisarProdutos")){
			try {
				Produto prod = getFachada().consultarProdutoPorPK(new Long((String)params.get("codigoProduto")));
				if(prod != null){
					this.setDescricaoCompletaEstoque(prod.getDescricaoCompleta());
//					this.setPrecoVenda(prod.getPrecoPadrao());
					
				}
			} catch (Exception e) {				
				e.printStackTrace();			
			}
		}
	}
	/**
	 * @param init the init to set
	 */
	public void setInitEstoque(HtmlForm initEstoque) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();            
		String param = (String)  params.get(ACAO);
		if (param != null){
			resetProdutoBB();
			if(VALOR_ACAO.equals(param)){
				setMovimentacaoEstoque(null);
			}
		}
	}
	public void imprimirRecibo(){
		try {
			if(this.getMovimentacaoProdutoEstoque() != null){
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();			
				ServletOutputStream servletOutputStream = response.getOutputStream();
				getFachada().gerarReciboMovimentacaoEstoque(this.getMovimentacaoProdutoEstoque(), servletOutputStream);			
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "attachment;filename=ReciboMovimentacaoEstoque" + System.currentTimeMillis() + ".pdf");
				context.responseComplete();
				servletOutputStream.flush();
				servletOutputStream.close();
			}else{
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Não existe Recibo para imprimir!", "");
				ctx.addMessage(null, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Erro ao imprimir o Recibo!", "");
			ctx.addMessage(null, msg);
		}
	}
	
	public MovimentacaoEstoque getMovimentacaoProdutoEstoque() {
		return movimentacaoProdutoEstoque;
	}
	public void setMovimentacaoProdutoEstoque(
			MovimentacaoEstoque movimentacaoProdutoEstoque) {
		this.movimentacaoProdutoEstoque = movimentacaoProdutoEstoque;
	}
	public String getCodigoExterno() {
		return codigoExterno;
	}
	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}
	public String getDescricaoCompletaEstoque() {
		return descricaoCompletaEstoque;
	}
	public void setDescricaoCompletaEstoque(String descricaoCompletaEstoque) {
		this.descricaoCompletaEstoque = descricaoCompletaEstoque;
	}

}
