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
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.collection.PersistentSet;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.estoque.EntradaProduto;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.ParcelaEntradaProduto;
import com.infinity.datamarket.comum.estoque.ParcelaEntradaProdutoPK;
import com.infinity.datamarket.comum.estoque.ProdutoEntradaProduto;
import com.infinity.datamarket.comum.estoque.ProdutoEntradaProdutoPK;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Vendedor;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
import com.infinity.datamarket.enterprise.gui.util.BackBean;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF.ParcelaTEF;

public class EntradaProdutoBackBean extends BackBean {
	
	EntradaProduto entradaProduto;

	private static final int HashSet = 0;
	private String id;
	private String numeroNota;
	private Date dataEmissaoNota;
	private Date dataEntrada = new Date(System.currentTimeMillis());
	private BigDecimal frete = BigDecimal.ZERO;
	private BigDecimal icms  = BigDecimal.ZERO;
	private BigDecimal ipi	 = BigDecimal.ZERO;
	private BigDecimal desconto = BigDecimal.ZERO;
	private BigDecimal valor    = BigDecimal.ZERO;
	private BigDecimal valorNota = BigDecimal.ZERO;
	private BigDecimal valorSubTotalNota = BigDecimal.ZERO;
	private Estoque estoque;
	private String idFornecedor;
	private String nomeFornecedor;
	private Fornecedor fornecedor;
	private ProdutoEntradaProduto produtoEntrada;
	private Collection<EntradaProduto> entradasProduto;
	
	private Date dataVencimento;
	
	private String idStatus;
	private SelectItem[] listaStatus;
	
	private SelectItem[] lojas;
    
	// Atributos para montar os Produtos
	private String idProduto; 
	private String descricao;
	
	// Atributos ProdutoEntradaEntrada
	private Set<ProdutoEntradaProduto> arrayProduto; 
	private String idEstoque;
	private String idLoja;
	private BigDecimal quantidade        = BigDecimal.ZERO.setScale(3);
	private BigDecimal precoUnitario     = BigDecimal.ZERO.setScale(2);
	private BigDecimal descontoProduto   = BigDecimal.ZERO.setScale(2);
	private BigDecimal icmsProduto       = BigDecimal.ZERO.setScale(2);
	private BigDecimal valorIcmsProduto  = BigDecimal.ZERO.setScale(2);
	private BigDecimal ipiProduto	     = BigDecimal.ZERO.setScale(2);
	private BigDecimal valorIpiProduto	 = BigDecimal.ZERO.setScale(2);
	private BigDecimal total		     = BigDecimal.ZERO.setScale(2);
	private BigDecimal totalDescontoItem = BigDecimal.ZERO.setScale(2);
	private Date dataVencimentoProduto;
	private List<Estoque> estoques = null; 
	private String status;
	
//	 Atributos parcelas
	private Set<ParcelaEntradaProduto> arrayParcela; 
	private BigDecimal valorParcela      = BigDecimal.ZERO.setScale(2);
	private Date dataVencimentoParcela;
	
	
	private BigDecimal quantidadeTotal = BigDecimal.ZERO.setScale(3);
    
	// para uso de filtro de consulta
    private Date dataInicio;
	private Date dataFinal;

	private String idExcluir; 

	String abaCorrente;
	
	public String getAbaCorrente() {
		return abaCorrente;
	}
	public void setAbaCorrente(String abaCorrente) {
		this.abaCorrente = abaCorrente;
	}
	/**
	 * @return the produtoEntrada
	 */
	public ProdutoEntradaProduto getProdutoEntrada() {
		return produtoEntrada; 
	}
	/**
	 * @param produtoEntrada the produtoEntrada to set 
	 */
	public void setProdutoEntrada(ProdutoEntradaProduto produtoEntrada) {
		this.produtoEntrada = produtoEntrada;
	}
	public void resetProdutoBB() {
		this.setIdProduto(null);
		this.descricao = null;
		this.setIdEstoque(null);
		this.setDescontoProduto(BigDecimal.ZERO.setScale(2));
		this.setQuantidade(BigDecimal.ZERO.setScale(3));
		this.setIcmsProduto(BigDecimal.ZERO.setScale(2));
		this.setIpiProduto(BigDecimal.ZERO.setScale(2));
		this.setValorIcmsProduto(BigDecimal.ZERO.setScale(2));
		this.setValorIpiProduto(BigDecimal.ZERO.setScale(2));
		this.setPrecoUnitario(BigDecimal.ZERO.setScale(2));
		this.setDataVencimentoProduto(null);
		this.setStatus(null);
	}
	
	public void resetParcelaBB() {
		this.valorParcela = BigDecimal.ZERO.setScale(2);
		this.dataVencimentoParcela = null;		
	}
	public String excluirProdutoEntrada() {
		int i = 0;
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();  
		String param = (String)  params.get("idExcluir");
		
		for (Iterator iter = arrayProduto.iterator(); iter.hasNext();) {
			ProdutoEntradaProduto produtoTmp = (ProdutoEntradaProduto) iter.next();
			if (produtoTmp.getPk().getProduto().getId().equals(new Long(param))) {
				
				if (getValor().longValue()-produtoTmp.getTotal().longValue()>0) {
					this.setValorNota(getValor().subtract(produtoTmp.getTotal()));
				} else {
					this.setValorNota(BigDecimal.ZERO);
				}
				
				this.setValor(getValorNota());
				this.setValorSubTotalNota(this.getValorSubTotalNota().subtract(produtoTmp.getTotal()));
				
				if (getValor().longValue() >= (this.getDesconto().longValue() - getValor().longValue()))
				this.setValor(getValor().subtract(this.getDesconto()));
				
				this.setIpi(getIpi().subtract(produtoTmp.getIpi()));
				this.setIcms(getIcms().subtract(produtoTmp.getIcms()));
				if (this.getDescontoProduto()==null)
					this.setDescontoProduto(BigDecimal.ZERO);
				this.setTotalDescontoItem(getTotalDescontoItem().subtract(this.getDescontoProduto()));
				this.setQuantidadeTotal(this.getQuantidadeTotal().subtract(produtoTmp.getQuantidade()).setScale(3));
				arrayProduto.remove(produtoTmp);
				break;
			}
			i++;
		}
		return "mesma";
	}
	
	public String excluirParcela() {
		int i = 0;
		FacesContext context = FacesContext.getCurrentInstance();
		Map params = context.getExternalContext().getRequestParameterMap();  
		String param = (String)  params.get("idExcluirParcela");
		
		for (Iterator iter = arrayParcela.iterator(); iter.hasNext();) {
			ParcelaEntradaProduto parcela = (ParcelaEntradaProduto) iter.next();
			if (parcela.getPk().getId().equals(new Long(param))) {				
				arrayParcela.remove(parcela);
				break;
			}
			i++;
		}
		return "mesma";
	}
	
	public void inserirProdutoEntrada() { 
		try {
			
			
			
			if (arrayProduto==null){
				arrayProduto = new HashSet<ProdutoEntradaProduto>();
				inicializaValoreNota();
			}	
			
			String msgValidacao =  validaNota();
			
			if (!"".equals(msgValidacao)) {
				throw new AppException(msgValidacao);
			}
			
			msgValidacao =  validaProduto();
			
			if (!"".equals(msgValidacao)) {
				throw new AppException(msgValidacao);
			}
			
			msgValidacao =  validaParcela();
			
			if (!"".equals(msgValidacao)) {
				throw new AppException(msgValidacao);
			}
			
			ProdutoEntradaProduto produtoEntrada = new ProdutoEntradaProduto();
			ProdutoEntradaProdutoPK produtoEntradaPK = new ProdutoEntradaProdutoPK();
			Produto produto = null;
			try{
				produto = Fachada.getInstancia().consultarProdutoPorPK(new Long(this.idProduto));
			}catch(ObjectNotFoundException e){
				throw new AppException("Produto inexistente no cadastro!");
			}
			
			if(produto == null){
				throw new AppException("Produto inexistente no cadastro!");
			}
			
			setDescricao(produto.getDescricaoCompleta());
			produtoEntradaPK.setProduto(produto);
			produtoEntrada.setPk(produtoEntradaPK);
			
//			Estoque estoque = null;
//			for (Iterator iter = estoques.iterator(); iter.hasNext();) {
//				Estoque element = (Estoque) iter.next();
//				if (element.getPk().getId().longValue()==new Long(this.idEstoque).longValue()) {
//					estoque = (Estoque)element;
//					break;
//				}				
//			}
			
			produtoEntrada.setVencimento(dataVencimentoProduto);
			
			if (this.ipiProduto == null) this.ipiProduto = BigDecimal.ZERO.setScale(2);
			if (this.icmsProduto == null) this.icmsProduto = BigDecimal.ZERO.setScale(2);
			if (this.descontoProduto == null) this.descontoProduto = BigDecimal.ZERO.setScale(2);
			if (this.desconto == null) this.desconto = BigDecimal.ZERO.setScale(2);
//	        produtoEntrada.setIpi(this.ipiProduto.setScale(2));
//	        produtoEntrada.setIcms(this.icmsProduto.setScale(2));
	        produtoEntrada.setIpi(this.valorIpiProduto.setScale(2));
	        produtoEntrada.setIcms(this.valorIcmsProduto.setScale(2));
			produtoEntrada.setDesconto(this.descontoProduto.setScale(2));
			produtoEntrada.setQuantidade(this.quantidade.setScale(3));
			this.setQuantidadeTotal(this.getQuantidadeTotal().add(this.quantidade.setScale(3)));
			produtoEntrada.setPrecoUnitario(this.precoUnitario.setScale(2));
			this.setTotal(this.precoUnitario.setScale(2));
		    produtoEntrada.setTotal(this.total.setScale(2));
			
			int i=0;
			for (Iterator iter = arrayProduto.iterator(); iter.hasNext();) {
				ProdutoEntradaProduto produtoTmp = (ProdutoEntradaProduto) iter.next();
				if (produtoTmp.getPk().getProduto().getId().equals(produtoEntrada.getPk().getProduto().getId())) {
					this.setValorNota(getValor().subtract(produtoTmp.getTotal()));
					this.setValor(getValorNota());
					
//					if (getValor().longValue() >= (this.getDesconto().longValue() - getValor().longValue()))
//					this.setValor(getValor().subtract(this.getDesconto()));
					
					this.setIpi(getIpi().subtract(produtoTmp.getIpi()));
					this.setIcms(getIcms().subtract(produtoTmp.getIcms()));
					this.setTotalDescontoItem(getTotalDescontoItem().subtract(produtoEntrada.getDesconto()));
					arrayProduto.remove(produtoTmp);
					break;
				}
				i++;
			}

			produtoEntrada.getPk().setNumeroEntrada(i);
			arrayProduto.add(produtoEntrada);
			this.setValorNota(getValor().add(produtoEntrada.getTotal()));
			this.setValor(getValorNota());
			this.setValorSubTotalNota(this.getValorSubTotalNota().add(produtoEntrada.getTotal()));
			
//			if (getValor().longValue() >= (this.getDesconto().longValue() - getValor().longValue()))
//			this.setValor(getValor().subtract(this.getDesconto()));
			
			this.setIpi(this.getIpi().add(produtoEntrada.getIpi()));
			this.setIcms(this.getIcms().add(produtoEntrada.getIcms()));
			this.setTotalDescontoItem(getTotalDescontoItem().subtract(produtoEntrada.getDesconto()));
			resetProdutoBB();
		} catch (AppException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
	}
	
	public void inserirParcela() { 
		try {
			
			
			
			if (arrayParcela==null){
				arrayParcela = new HashSet<ParcelaEntradaProduto>();				
			}	
			
			String msgValidacao =  validaNota();
			
			if (!"".equals(msgValidacao)) {
				throw new AppException(msgValidacao);
			}
			
			msgValidacao =  validaProduto();
			
			if (!"".equals(msgValidacao)) {
				throw new AppException(msgValidacao);
			}
			
			msgValidacao =  validaParcela();
			
			if (!"".equals(msgValidacao)) {
				throw new AppException(msgValidacao);
			}
			
			ParcelaEntradaProduto parcela = new ParcelaEntradaProduto();
			ParcelaEntradaProdutoPK parcelaPK = new ParcelaEntradaProdutoPK();
			
			
			
			parcela.setPk(parcelaPK);
			
	        parcela.setDataVencimento(dataVencimentoParcela);
	        parcela.setValor(valorParcela.setScale(2));						

			parcela.getPk().setId(new Long(arrayParcela != null && arrayParcela.size() > 0? arrayParcela.size()+1:1));
			arrayParcela.add(parcela);
			
			resetParcelaBB();
			
		} catch (AppException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				e.getMessage(), "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
	}
	
	public String validaNota() {
		  String msg = "";
		if  (this.numeroNota == null || "".equals(this.numeroNota)) {
			msg = 	"Informe Número da Nota!";
		} else if (this.dataEmissaoNota == null || "".equals(this.dataEmissaoNota)) {
			msg = 	"Informe Data de Emissão da Nota!";
		} else if (this.dataEntrada == null || "".equals(this.dataEntrada)) {
			msg = 	"Informe Data de Entrada da Nota!";
		} else if (this.dataEmissaoNota.after(this.dataEntrada)) {
			msg = 	"Data de Emissão da Nota não pode ser maior que a Data de Entrada da Nota!";
		} else if (this.idFornecedor == null || "0".equals(this.idFornecedor)) {
			msg = 	"Informe Fornecedor da Nota!";
		} else if (this.valor == null || "".equals(this.valor)) {
			msg = 	"Informe Valor da Nota!";
		} else if (this.idFornecedor == null || "".equals(this.idFornecedor)) {
			msg = 	"Informe Fornecedor da Nota!";
		} else if (this.arrayProduto == null ) {
			msg = 	"Informe pelo menos um produto na Nota!";
		} else if (this.dataVencimento == null || "".equals(this.dataVencimento)) {
			msg = 	"Informe a Data de Vencimento!";
		}
		return msg;	  
	}
	
	public String validaParcela() {
		  String msg = "";
		if (arrayProduto == null || arrayProduto.size() == 0){
			msg = "Adicione Primeiro os Produtos depois as Parcelas!";
		}else if(valorNota == null || valorNota.compareTo(BigDecimal.ZERO) == 0){
			msg = "Adicione Primeiro os Produtos depois as Parcelas!";
		}else if (valorParcela == null || valorParcela.compareTo(BigDecimal.ZERO) <= 0){
			msg = "Informe um valor maior que zero para o valor da parcela!";
		}else if (dataVencimentoParcela == null){
			msg = "Informe a data de vencimento da parcela!";
		}if (arrayParcela != null && arrayParcela.size() > 0){
			Iterator i = arrayParcela.iterator();
			BigDecimal valorParc = new BigDecimal(0).setScale(2);
			while (i.hasNext()) {
				ParcelaEntradaProduto parcela = (ParcelaEntradaProduto) i.next();
				valorParc = valorParc.add(parcela.getValor());
			}
			valorParc = valorParc.add(valorParcela);
			if (valorParc.compareTo(valorNota) > 0){
				msg = "Valor das parcelas ultrapassa valor total da nota!";
			}
		}else if (arrayParcela == null || arrayParcela.size() == 0){
			if (valorParcela.compareTo(valorNota) > 0){
				msg = "Valor da parcelas ultrapassa valor total da nota!";
			}
		}
		return msg;	  
	}
	
	public String validaProduto() {
		String msg = "";
		if  (this.idProduto == null || "".equals(this.idProduto)) {
			msg = 	"Informe o Código do Produto!";
		} else if (this.quantidade == null || "".equals(this.quantidade) || this.quantidade.equals(BigDecimal.ZERO)) {
			msg = 	"A Quantidade do Produto deve ser informada!";
		} else if (this.precoUnitario == null || "".equals(this.precoUnitario)){// || this.precoUnitario.equals(BigDecimal.ZERO)) {
			msg = 	"O Preço Unitário do Produto deve ser informado!";
		}else if (this.dataVencimentoProduto != null && Util.comparaDatasSemHora(this.dataVencimentoProduto, new Date()) < 0){
			msg = "Produto com data de vencimento inválida";		
		} else if (this.descontoProduto != null && !"".equals(this.descontoProduto)) {
			BigDecimal tmpTotalProduto = this.precoUnitario.multiply(this.quantidade);
			if (this.descontoProduto.compareTo(tmpTotalProduto) > 0) {
				msg = "Desconto do produto deve ser menor que o Valor Total do produto!";	
			}
		}
		
		return msg;	  
	}	
	public String inserir() {
		
		EntradaProduto entradaProduto = new EntradaProduto();

		String msgValidacao = validaNota();
		
		if (!"".equals(msgValidacao)) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					msgValidacao, "");
			getContextoApp().addMessage(null, msg);
			return "mesma";
		}
		
	    if (this.desconto != null && !"".equals(this.desconto)) {
//	    	BigDecimal tmpValor = valorNota;
//	    	tmpValor = tmpValor.subtract(this.desconto);
//			if (tmpValor.longValue() <= this.desconto.longValue() ) {
	    	if(this.desconto.compareTo(this.valorNota) > 0){
				msgValidacao = 	"Desconto da nota deve ser menor que o total da nota!";	
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						msgValidacao, "");
				getContextoApp().addMessage(null, msg);
				return "mesma";
			}
		}
	    
		entradaProduto.setNumeroNota(this.numeroNota);
		entradaProduto.setDataEmissaoNota(this.dataEmissaoNota);
		entradaProduto.setDataEntrada(this.dataEntrada);
		entradaProduto.setFrete(this.frete);
		entradaProduto.setIcms(this.icms);
		entradaProduto.setIpi(this.ipi);
		if (this.desconto == null) {
			entradaProduto.setDesconto(BigDecimal.ZERO);	
		} else {
		    entradaProduto.setDesconto(this.desconto);
		    this.valorNota = this.valorNota.subtract(this.desconto);
		}
		if(this.frete != null && this.frete.setScale(2).compareTo(BigDecimal.ZERO.setScale(2)) > 0){
			entradaProduto.setValor(this.valorNota.add(this.frete));//this.valorNota.subtract(getDesconto()).add(this.frete)	
		}else{
			entradaProduto.setValor(this.valorNota);//.subtract(getDesconto()));
		}
		
		entradaProduto.setQuantidadeTotal(this.getQuantidadeTotal().setScale(3));
		
		entradaProduto.setIdFornecedor(this.idFornecedor);

		Fornecedor fornecedor = null;
		try {
			fornecedor = Fachada.getInstancia().consultaFornecedorPorId(new Long(this.idFornecedor));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AppException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		entradaProduto.setFornecedor(fornecedor);
//		entradaProduto.setDesconto(this.getDesconto());

		try {
			if (getId()==null) entradaProduto.setId(getIdInc(EntradaProduto.class));
			Collection col = arrayProduto;
			if (col!=null) {
				Iterator it = col.iterator();
				while(it.hasNext()){
					ProdutoEntradaProduto pep = (ProdutoEntradaProduto) it.next();
					pep.getPk().setId(entradaProduto.getId());
				}
			}	
			entradaProduto.setProdutosEntrada(arrayProduto);
			
			col = arrayParcela;
			if (col!=null) {
				Iterator it = col.iterator();
				while(it.hasNext()){
					ParcelaEntradaProduto pep = (ParcelaEntradaProduto) it.next();
					pep.getPk().setIdEntradaProduto(entradaProduto.getId());
				}
			}	
			entradaProduto.setProdutosEntrada(arrayProduto);
			
			Estoque estoque = null;
			for (Iterator iter = estoques.iterator(); iter.hasNext();) {
				Estoque element = (Estoque) iter.next();
				if (element.getPk().getId().longValue()==new Long(this.idEstoque).longValue()) {
					estoque = (Estoque)element;
					break;
				}				
			}
			
			entradaProduto.setEstoque(estoque);
			
			entradaProduto.setStatus(Constantes.STATUS_ATIVO);
			
			entradaProduto.setDataVencimento(this.getDataVencimento());

			getFachada().inserirEntradaProduto(entradaProduto);
			
			this.setEntradaProduto(entradaProduto);
			
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Entrada já Existente!", "");
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
	
	public String alterar() {
		EntradaProduto entradaProduto = new EntradaProduto();
		//EntradaProdutoPK pk = new EntradaProdutoPK();
		
		//pk.setId(new Long(this.id));
		
		entradaProduto.setNumeroNota(this.numeroNota);
		entradaProduto.setDataEmissaoNota(this.dataEmissaoNota);
		entradaProduto.setDataEntrada(this.dataEntrada);
		entradaProduto.setFrete(this.frete);
		entradaProduto.setIcms(this.icms);
		entradaProduto.setIpi(this.ipi);
		entradaProduto.setDesconto(this.desconto);
		entradaProduto.setValor(this.valor);
		
		entradaProduto.setQuantidadeTotal(this.getQuantidadeTotal().setScale(3));
		
		entradaProduto.setFornecedor(this.fornecedor);
		entradaProduto.setEstoque(estoque);
		
		/*Loja loja = new Loja();
		loja.setId(new Long(this.idLoja));
		pk.setLoja(loja);
		
		estoque.setPk(pk);*/

		try {
			getFachada().alterarEntradaProduto(entradaProduto);
			
			this.setEntradaProduto(entradaProduto);
			
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Entrada já Existente!", "");
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
		resetBB();
		return "mesma";
	}
	
	public void cancelar(ActionEvent event) {
		
		try {
//			FacesContext context = FacesContext.getCurrentInstance();
//			Map params = context.getExternalContext().getRequestParameterMap();
//			String param = (String) params.get("idCancelarEntradaProduto");
			UIParameter component = (UIParameter) event.getComponent().findComponent("idCancelarEntradaProduto");
			String param = (String) component.getValue();
			if(param != null && !param.equals("")){ 
				EntradaProduto entradaProduto = getFachada().consultarEntradaProdutoPorId(new Long(param));
			
				if(entradaProduto.getStatus().equals(Constantes.STATUS_CANCELADO)){
					throw new AppException("Entrada de Produtos já cancelada!");
				}
				
				entradaProduto.setStatus(Constantes.STATUS_CANCELADO);
				
				getFachada().alterarEntradaProduto(entradaProduto);
			
				this.setEntradaProduto(entradaProduto);
				
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Operação Realizada com Sucesso!", "");
				getContextoApp().addMessage(null, msg);
				consultar();
			}else{
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Nenhuma Entrada Selecionada!", "");
				getContextoApp().addMessage(null, msg);
			}
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Entrada já Existente!", "");
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
				
				EntradaProduto entradaProduto = getFachada().consultarEntradaProdutoPorId(new Long(id));
				if  (!entradaProduto.getProdutosEntrada().isEmpty()) {
					Set<ProdutoEntradaProduto> produtoEntradaProduto =  (Set<ProdutoEntradaProduto>) entradaProduto.getProdutosEntrada();
					this.setArrayProduto(produtoEntradaProduto);
				}	
				this.setNumeroNota(entradaProduto.getNumeroNota());
				this.setDataEmissaoNota(entradaProduto.getDataEmissaoNota());
				this.setDataEntrada(entradaProduto.getDataEntrada());
				this.setFrete(entradaProduto.getFrete());
				this.setIcms(entradaProduto.getIcms());
				this.setIpi(entradaProduto.getIpi());
				this.setDesconto(entradaProduto.getDesconto());
				this.setValor(entradaProduto.getValor());
				this.setQuantidadeTotal(entradaProduto.getQuantidadeTotal().setScale(3));
				this.setFornecedor(entradaProduto.getFornecedor());
				this.setEstoque(entradaProduto.getEstoque());
				this.setStatus(entradaProduto.getStatus());
				
				if(entradaProduto.getFornecedor() != null){
					if(entradaProduto.getFornecedor().getTipoPessoa().equals(Fornecedor.PESSOA_FISICA)){
						this.setNomeFornecedor(entradaProduto.getFornecedor().getNomeFornecedor());
					}else{
						this.setNomeFornecedor(entradaProduto.getFornecedor().getNomeFantasia());
					}
				}
				
				
				
				this.setEntradaProduto(entradaProduto);
				
				return "proxima";
				
			} else {
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(EntradaProduto.class);
				if(getNumeroNota() != null || (getDataInicio() != null && getDataFinal() != null) || this.getIdStatus() != null){
					if (getNumeroNota() != null && !"".equals(getNumeroNota())) {
		            	filter.addProperty("numeroNota", getNumeroNota());
						
					} 
					if (getDataInicio() != null && !"".equals(getDataInicio())) {
						filter.addPropertyInterval("dataEntrada",getDataInicio(), IntervalObject.MAIOR_IGUAL);
						if (getDataFinal() != null && !"".equals(getDataFinal())){
							Date dataFinal = new Date(this.getDataFinal().getTime());					
							dataFinal.setDate(dataFinal.getDate()+1);
							filter.addPropertyInterval("dataEntrada", dataFinal, IntervalObject.MENOR_IGUAL);
						}
							
						
						
					}
					if(this.getIdStatus() != null){
						if(!this.getIdStatus().equals("T")){
							filter.addProperty("status", this.getIdStatus());	
						}						
					}
					
					return consultarFiltro(filter);
				}
				
				Collection col = getFachada().consultarTodasEntradaProduto();
				if(col != null && col.size() > 0){
					this.setExisteRegistros(true);
				}else{
					this.setExisteRegistros(false);
				}
				setEntradasProduto(col);
 			}	
		} catch (ObjectNotFoundException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);
			setExisteRegistros(false);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
			setExisteRegistros(false);
		}
		
		return resetBB();
	}
	
	private String consultarFiltro(PropertyFilter filter) {

		Collection col=null;
		try {
			col = getFachada().consultarEntradaProduto(filter);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (col == null || col.size() == 0) {
			this.setEntradasProduto(null);
			this.setExisteRegistros(false);
			
			FacesMessage msg = new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);
		} else if (col != null) {
			if (col.size() == 1) {
				EntradaProduto entradaProduto = (EntradaProduto)col.iterator().next();
				this.setId(entradaProduto.getId().toString());
				this.setNumeroNota(entradaProduto.getNumeroNota());
				this.setDataEmissaoNota(entradaProduto.getDataEmissaoNota());
				this.setDataEntrada(entradaProduto.getDataEntrada());
				this.setFrete(entradaProduto.getFrete());
				this.setIcms(entradaProduto.getIcms());
				this.setIpi(entradaProduto.getIpi());
				this.setDesconto(entradaProduto.getDesconto());
				this.setValor(entradaProduto.getValor());
				this.setQuantidadeTotal(entradaProduto.getQuantidadeTotal().setScale(3));
				this.setFornecedor(entradaProduto.getFornecedor());
				this.setEstoque(entradaProduto.getEstoque());
				this.setStatus(entradaProduto.getStatus());
				if(entradaProduto.getFornecedor() != null){
					if(entradaProduto.getFornecedor().getTipoPessoa().equals(Fornecedor.PESSOA_FISICA)){
						this.setNomeFornecedor(entradaProduto.getFornecedor().getNomeFornecedor());
					}else{
						this.setNomeFornecedor(entradaProduto.getFornecedor().getNomeFantasia());
					}
				}
				Collection<ProdutoEntradaProduto> colProduto = entradaProduto.getProdutosEntrada();
				Set<ProdutoEntradaProduto> produtos = (Set<ProdutoEntradaProduto>)colProduto;
				this.setArrayProduto(produtos);
				return "proxima";
			} else {
				this.setExisteRegistros(true);
				this.setEntradasProduto(col);
			}
		}
		return resetBB();
	}
	
	private List<Fornecedor> carregarFornecedores() {

		List<Fornecedor> fornecedores = null;
		try {
			fornecedores = (ArrayList<Fornecedor>) getFachada().consultarTodosFornecedores();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return fornecedores;
	}
	
	public SelectItem[] getFornecedores() {
		SelectItem[] arrayFornecedores = null;
		try {
			List<Fornecedor> fornecedores = carregarFornecedores();
			arrayFornecedores = new SelectItem[fornecedores.size()+1];
			int i = 0;
			arrayFornecedores[i++] = new SelectItem("0", "");
			for (Fornecedor fornecedorTmp : fornecedores) {
				String nomeFornecedor = "";
				if (fornecedorTmp.getNomeFantasia() != null)
					nomeFornecedor = fornecedorTmp.getNomeFantasia();
				else	
					nomeFornecedor = fornecedorTmp.getNomeFornecedor();
				SelectItem item = new SelectItem(fornecedorTmp.getId().toString(),
						nomeFornecedor);
				arrayFornecedores[i++] = item;
			}

			if (this.getIdFornecedor() == null || this.getIdFornecedor().equals("")
					|| this.getIdFornecedor().equals("0")) {
				this.setIdFornecedor((String) arrayFornecedores[0].getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		if (this.idFornecedor== null) {
			this.idFornecedor = arrayFornecedores[0].getValue().toString();
		}
		return arrayFornecedores;
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


	
	public String resetBB() {
		this.setId(null);
		this.setNumeroNota(null);
		this.setDataEmissaoNota(null);
		this.setDataEntrada(new Date(System.currentTimeMillis()));
		this.setDataInicio(null);
		this.setDataFinal(null);
		this.setFrete(BigDecimal.ZERO.setScale(2));
		this.setIcms(BigDecimal.ZERO.setScale(2));
		this.setIpi(BigDecimal.ZERO.setScale(2));
		this.setDesconto(BigDecimal.ZERO.setScale(2));
		this.setValor(BigDecimal.ZERO.setScale(2));
		this.setFornecedor(null);
		this.setEstoque(null);
		this.setArrayProduto(null);
		this.setQuantidadeTotal(BigDecimal.ZERO.setScale(3));
		resetProdutoBB();
		inicializaValoreNota();
		this.setStatus(null);
		return "mesma";
	}
    
	public void inicializaValoreNota(){
		this.setIcms(BigDecimal.ZERO.setScale(2));
		this.setIpi(BigDecimal.ZERO.setScale(2));
		this.setValor(BigDecimal.ZERO.setScale(2));
		this.setQuantidadeTotal(BigDecimal.ZERO.setScale(3));
    }
	
	 
	public String voltarConsulta() {
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
	 * @return the dataEmissaoNota
	 */
	public Date getDataEmissaoNota() {
		return dataEmissaoNota;
	}
	/**
	 * @param dataEmissaoNota the dataEmissaoNota to set
	 */
	public void setDataEmissaoNota(Date dataEmissaoNota) {
		this.dataEmissaoNota = dataEmissaoNota;
	}
	/**
	 * @return the dataEntrada
	 */
	public Date getDataEntrada() {
		return dataEntrada;
	}
	/**
	 * @param dataEntrada the dataEntrada to set
	 */
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	/**
	 * @return the desconto
	 */
	public BigDecimal getDesconto() {
		return desconto;
	}
	/**
	 * @param desconto the desconto to set
	 */
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	/**
	 * @return the fornecedor
	 */
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	/**
	 * @param fornecedor the fornecedor to set
	 */
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	/**
	 * @return the frete
	 */
	public BigDecimal getFrete() {
		return frete;
	}
	/**
	 * @param frete the frete to set
	 */
	public void setFrete(BigDecimal frete) {
		this.frete = frete;
	}
	/**
	 * @return the icms
	 */
	public BigDecimal getIcms() {
		return icms;
	}
	/**
	 * @param icms the icms to set
	 */
	public void setIcms(BigDecimal icms) {
		this.icms = icms;
	}
	/**
	 * @return the ipi
	 */
	public BigDecimal getIpi() {
		return ipi;
	}
	/**
	 * @param ipi the ipi to set
	 */
	public void setIpi(BigDecimal ipi) {
		this.ipi = ipi;
	}
	/**
	 * @return the numeroNota
	 */
	public String getNumeroNota() {
		return numeroNota;
	}
	/**
	 * @param numeroNota the numeroNota to set
	 */
	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}
	/**
	 * @return the valor
	 */
	public BigDecimal getValor() {
		this.valor.setScale(Constantes.DOIS_DECIMAL,BigDecimal.ROUND_HALF_EVEN);
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
	 * @return the idFornecedor
	 */
	public String getIdFornecedor() {
		return idFornecedor;
	}
	/**
	 * @param idFornecedor the idFornecedor to set
	 */
	public void setIdFornecedor(String idFornecedor) {
		this.idFornecedor = idFornecedor;
	}
	/**
	 * @return the arrayProduto
	 */
	public Set<ProdutoEntradaProduto> getArrayProduto() {
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
		//this.descricao = descricao;		
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
	public void setArrayProduto(Set<ProdutoEntradaProduto> arrayProduto) {
		this.arrayProduto = arrayProduto;
	}
	/**
	 * @return the descontoProduto
	 */
	public BigDecimal getDescontoProduto() {
		return descontoProduto;
	}
	/**
	 * @param descontoProduto the descontoProduto to set
	 */
	public void setDescontoProduto(BigDecimal descontoProduto) {
		this.descontoProduto = descontoProduto;
	}
	/**
	 * @return the icmsProduto
	 */
	public BigDecimal getIcmsProduto() {
		return icmsProduto;
	}
	/**
	 * @param icmsProduto the icmsProduto to set
	 */
	public void setIcmsProduto(BigDecimal icmsProduto) {
		this.icmsProduto = icmsProduto;
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
	 * @return the ipiProduto
	 */
	public BigDecimal getIpiProduto() {
		return ipiProduto;
	}
	/**
	 * @param ipiProduto the ipiProduto to set
	 */
	public void setIpiProduto(BigDecimal ipiProduto) {
		this.ipiProduto = ipiProduto;
	}
	/**
	 * @return the precoUnitario
	 */
	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}
	/**
	 * @param precoUnitario the precoUnitario to set
	 */
	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}
	/**
	 * @return the quantidade
	 */
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return this.total.setScale(Constantes.DOIS_DECIMAL,BigDecimal.ROUND_HALF_EVEN);
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = this.precoUnitario;
		this.total = total.multiply(this.quantidade).setScale(2, BigDecimal.ROUND_DOWN);
		
		if (this.descontoProduto != null) 
			this.total = this.total.subtract(this.descontoProduto);
		
//		if (this.ipiProduto != null) 
//		this.total = this.total.add(this.ipiProduto);
//		
//		if (this.icmsProduto != null) { 
//		this.total = this.total.add(this.icmsProduto);
//		}
		if (this.valorIpiProduto != null) 
			this.total = this.total.add(this.valorIpiProduto);
			
		if (this.valorIcmsProduto != null) { 
			this.total = this.total.add(this.valorIcmsProduto);
		}
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
	 * @return the entradasProduto
	 */
	public Collection<EntradaProduto> getEntradasProduto() {
		return entradasProduto;
	}
	/**
	 * @param entradasProduto the entradasProduto to set
	 */
	public void setEntradasProduto(Collection<EntradaProduto> entradasProduto) {
		this.entradasProduto = entradasProduto;
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
	 * @return the dateFinal
	 */
	public Date getDataFinal() {
		return dataFinal;
	}
	/**
	 * @param dateFinal the dateFinal to set
	 */
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	/**
	 * @return the totalDescontoItem
	 */
	public BigDecimal getTotalDescontoItem() {
		return totalDescontoItem==null?BigDecimal.ZERO:totalDescontoItem;
	}
	/**
	 * @param totalDescontoItem the totalDescontoItem to set
	 */
	public void setTotalDescontoItem(BigDecimal totalDescontoItem) {
		this.totalDescontoItem = totalDescontoItem;
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
				setEntradasProduto(null);
			}
		}else if(params.get("acaoLocal") != null && ((String)params.get("acaoLocal")).equals("pesquisarProdutos")){
			try {
				Produto prod = getFachada().consultarProdutoPorPK(new Long((String)params.get("codigoProduto")));
				if (prod.getEnquadramento().equals(prod.FABRICADO)){
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Não é possível dar entrada em um produto fabricado!", "");
					getContextoApp().addMessage(null, msg);
					this.descricao = null;
					this.idProduto = null;
					return;
				}
				if(prod != null){
					this.descricao = prod.getDescricaoCompleta();
//					this.setPrecoVenda(prod.getPrecoPadrao());
					
				}
			} catch (Exception e) {				
				e.printStackTrace();			
			}
		}
	}
	/**
	 * @return the valorNota
	 */
	public BigDecimal getValorNota() {
		this.valorNota.setScale(Constantes.DOIS_DECIMAL,BigDecimal.ROUND_HALF_EVEN);
		return valorNota;
	}
	/**
	 * @param valorNota the valorNota to set
	 */
	public void setValorNota(BigDecimal valorNota) {
		
		this.valorNota = valorNota;
	}
	public EntradaProduto getEntradaProduto() {
		return entradaProduto;
	}
	public void setEntradaProduto(EntradaProduto entradaProduto) {
		this.entradaProduto = entradaProduto;
	}
	
	public void imprimirRecibo(){
		try {
			if(this.getEntradaProduto() != null){
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();			
				ServletOutputStream servletOutputStream = response.getOutputStream();
				getFachada().gerarReciboEntradaProdutosEstoque(this.getEntradaProduto(), servletOutputStream);			
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "attachment;filename=ReciboEntradaProdutoEstoque" + System.currentTimeMillis() + ".pdf");
				context.responseComplete();
				servletOutputStream.flush();
				servletOutputStream.close();
			}else{
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Não existe Recibo para imprimir!", "");
				getContextoApp().addMessage(null, msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Erro ao imprimir o Recibo!", "");
			getContextoApp().addMessage(null, msg);
		}
	}
	public String getNomeFornecedor() {
		return nomeFornecedor;
	}
	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
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
	public BigDecimal getQuantidadeTotal() {
		return quantidadeTotal;
	}
	public void setQuantidadeTotal(BigDecimal quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}
	public BigDecimal getValorSubTotalNota() {
		return valorSubTotalNota;
	}
	public void setValorSubTotalNota(BigDecimal valorSubTotalNota) {
		this.valorSubTotalNota = valorSubTotalNota;
	}
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public Date getDataVencimentoProduto() {
		return dataVencimentoProduto;
	}
	public void setDataVencimentoProduto(Date dataVencimentoProduto) {
		this.dataVencimentoProduto = dataVencimentoProduto;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getValorIcmsProduto() {
		return valorIcmsProduto;
	}
	public void setValorIcmsProduto(BigDecimal valorIcmsProduto) {
		this.valorIcmsProduto = valorIcmsProduto;
	}
	public BigDecimal getValorIpiProduto() {
		return valorIpiProduto;
	}
	public void setValorIpiProduto(BigDecimal valorIpiProduto) {
		this.valorIpiProduto = valorIpiProduto;
	}
}
