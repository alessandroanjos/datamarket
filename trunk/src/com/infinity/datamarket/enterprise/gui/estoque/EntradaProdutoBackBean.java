package com.infinity.datamarket.enterprise.gui.estoque;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.estoque.EntradaProduto;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.ProdutoEntradaProduto;
import com.infinity.datamarket.comum.estoque.ProdutoEntradaProdutoPK;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class EntradaProdutoBackBean extends BackBean {   
	private String id;
	private String numeroNota;
	private Date dataEmissaoNota;
	private Date dataEntrada;
	private BigDecimal frete;
	private BigDecimal icms;
	private BigDecimal ipi;
	private BigDecimal desconto;
	private BigDecimal valor;
	private String idFornecedor;
	private Fornecedor fornecedor;
	private ProdutoEntradaProduto produtoEntrada;
	 
	     
	// Atributos para montar os Produtos
	private String idProduto; 
	private String descricao;
	// Atributos ProdutoEntradaEntrada
	private ArrayList<ProdutoEntradaProduto> arrayProduto; 
	private String idEstoque;
	private String idLoja;
	private BigDecimal quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal descontoProduto;
	private BigDecimal icmsProduto;	
	private BigDecimal ipiProduto;
	private BigDecimal total;
	private List<Estoque> estoques = null; 
	
	

	
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
	public String resetProdutoBB() {
		this.setIdProduto(null);
		this.setDescricao(null);
		this.setIdEstoque(null);
		this.setDescontoProduto(null);
		this.setQuantidade(null);
		this.setIcmsProduto(null);
		this.setIpiProduto(null);
		this.setPrecoUnitario(null);
		return "mesma";
	}
	public String inserirProdutoEntrada() { 

		if (arrayProduto==null)
		arrayProduto = new ArrayList<ProdutoEntradaProduto>();

		ProdutoEntradaProduto produtoEntrada = new ProdutoEntradaProduto();
		ProdutoEntradaProdutoPK produtoEntradaPK = new ProdutoEntradaProdutoPK();
		produtoEntradaPK.setId(new Long(this.id));
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
		produtoEntradaPK.setProduto(produto);
		produtoEntrada.setPk(produtoEntradaPK);
		Estoque estoque = null;
		for (Iterator iter = estoques.iterator(); iter.hasNext();) {
			Estoque element = (Estoque) iter.next();
			if (element.getPk().getId().longValue()==new Long(this.idEstoque).longValue()) {
				estoque = (Estoque)element;
			}
			
		}

		produtoEntrada.setEstoque(estoque);
        
        produtoEntrada.setIpi(this.ipiProduto);
        produtoEntrada.setIcms(this.icmsProduto);
		produtoEntrada.setDesconto(this.descontoProduto);
		produtoEntrada.setQuantidade(this.quantidade);
		produtoEntrada.setPrecoUnitario(this.precoUnitario);
	    produtoEntrada.setTotal(this.total);
		
		int i=0;
		for (Iterator iter = arrayProduto.iterator(); iter.hasNext();) {
			ProdutoEntradaProduto produtoTmp = (ProdutoEntradaProduto) iter.next();
			if (produtoTmp.getPk().getProduto().getId().equals(produtoEntrada.getPk().getProduto().getId())) {
				arrayProduto.remove(i);
				break;
			}
			i++;
		}

		produtoEntrada.getPk().setNumeroEntrada(i);
		arrayProduto.add(produtoEntrada);
		resetProdutoBB();
		return "mesma";
	}
	
	public String inserir() {
		EntradaProduto entradaProduto = new EntradaProduto();
		//EntradaProdutoPK pk = new EntradaProdutoPK();
		
		//pk.setId(new Long(this.id));
		entradaProduto.setId(new Long(this.id));
		entradaProduto.setNumeroNota(this.numeroNota);
		entradaProduto.setDataEmissaoNota(this.dataEmissaoNota);
		entradaProduto.setDataEntrada(this.dataEntrada);
		entradaProduto.setFrete(this.frete);
		entradaProduto.setIcms(this.icms);
		entradaProduto.setIpi(this.ipi);
		entradaProduto.setDesconto(this.desconto);
		entradaProduto.setValor(this.valor);
		entradaProduto.setIdFornecedor(this.idFornecedor);
		entradaProduto.setFornecedor(this.fornecedor);
		
		/*Loja loja = new Loja();
		loja.setId(new Long(this.idLoja));
		pk.setLoja(loja);
		
		estoque.setPk(pk);*/

		try {
			getFachada().inserirEntradaProduto(entradaProduto);
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
		entradaProduto.setFornecedor(this.fornecedor);
		
		/*Loja loja = new Loja();
		loja.setId(new Long(this.idLoja));
		pk.setLoja(loja);
		
		estoque.setPk(pk);*/

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
				
				EntradaProduto entradaProduto = getFachada().consultarEntradaProdutoPorId(new Long(id));
				this.setNumeroNota(entradaProduto.getNumeroNota());
				this.setDataEmissaoNota(entradaProduto.getDataEmissaoNota());
				this.setDataEntrada(entradaProduto.getDataEntrada());
				this.setFrete(entradaProduto.getFrete());
				this.setIcms(entradaProduto.getIcms());
				this.setIpi(entradaProduto.getIpi());
				this.setDesconto(entradaProduto.getDesconto());
				this.setValor(entradaProduto.getValor());
				this.setFornecedor(entradaProduto.getFornecedor());
				return "proxima";
			/*} else if (getDescricao() != null && !"".equals(getDescricao())) {
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Estoque.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarEstoque(filter);
				if (col == null || col.size() == 0) {
					this.setEstoques(col);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);
				} else if (col != null) {
					if (col.size() == 1) {
						Estoque estoque = (Estoque) col.iterator().next();
						this.setId(estoque.getPk().getId().toString());
						this.setDescricao(estoque.getDescricao());
						this.setEstoqueVenda(estoque.getEstoqueVenda());
						this.setIdLoja(estoque.getPk().getLoja().getId().toString());
						return "proxima";
					} else {
						this.setEstoques(col);
					}
				}
			} else {
				setEntradas(getFachada().consultarTodosEstoques());*/
			}
		} catch (ObjectNotFoundException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		
		return resetBB();
	}
	
	private List<Fornecedor> carregarFornecedores() {

		List<Fornecedor> fornecedores = null;
		try {
			fornecedores = (ArrayList<Fornecedor>) getFachada().consultarTodosFornecedores();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return fornecedores;
	}

	public SelectItem[] getFornecedores() {
		SelectItem[] arrayFornecedores = null;
		try {
			List<Fornecedor> fornecedores = carregarFornecedores();
			arrayFornecedores = new SelectItem[fornecedores.size()];
			int i = 0;
			for (Fornecedor fornecedorTmp : fornecedores) {
				SelectItem item = new SelectItem(fornecedorTmp.getId().toString(),
						fornecedorTmp.getNomeFantazia());
				arrayFornecedores[i++] = item;
			}

			if (this.getIdFornecedor() == null || this.getIdFornecedor().equals("")
					|| this.getIdFornecedor().equals("0")) {
				this.setIdFornecedor((String) arrayFornecedores[0].getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		if (this.idFornecedor== null) {
			this.idFornecedor = arrayFornecedores[0].getValue().toString();
		}
		return arrayFornecedores;
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
				SelectItem item = new SelectItem(estoqueTmp.getPk().getId().toString(),
						estoqueTmp.getDescricao());
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
		this.setNumeroNota(null);
		this.setDataEmissaoNota(null);
		this.setDataEntrada(null);
		this.setFrete(null);
		this.setIcms(null);
		this.setIpi(null);
		this.setDesconto(null);
		this.setValor(null);
		this.setFornecedor(null);
		return "mesma";
	}

	public String voltarConsulta() {
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
	public ArrayList getArrayProduto() {
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
	public void setArrayProduto(ArrayList<ProdutoEntradaProduto> arrayProduto) {
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
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	

}
