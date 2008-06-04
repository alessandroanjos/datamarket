package com.infinity.datamarket.enterprise.gui.estoque;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.estoque.AjusteEstoque;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.MovimentacaoEstoque;
import com.infinity.datamarket.comum.estoque.ProdutoMovimentacaoEstoque;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class AjusteEstoqueBackBean extends BackBean {
    private String id;
	private Produto produto;
	private BigDecimal quantidadeAntes;
	private BigDecimal quantidadeDepois;
	private Date data;
	private Long codigoUsuario=1L;
  
//	 Atributos para montar os Produtos na movimentação de estoque
	private String idProduto; 
	private String descricao;

	private String idEstoque;
    List<Estoque> estoques;
    
    //  para uso de filtro de consulta
    private Date dataInicio;
	private Date dataFinal;

    Collection<AjusteEstoque> ajusteEstoques;
    
    // para uso de apresentacao
    AjusteEstoque ajusteEstoque;
	
	public String voltarConsulta() {
		resetBB();
		return "voltar";
	}

	public String voltarMenu() {
		resetBB();
		return "voltar";
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
				
				this.ajusteEstoque = getFachada().consultarAjustePorId(new Long(id));
				this.setIdEstoque(ajusteEstoque.getEstoque().getPk().getId().toString());
				this.setIdProduto(ajusteEstoque.getProduto().getId().toString());
				this.setDescricao(ajusteEstoque.getProduto().getDescricaoCompleta());
				this.setData(ajusteEstoque.getData());
				this.setQuantidadeAntes(ajusteEstoque.getQuantidadeAntes());
				this.setQuantidadeDepois(ajusteEstoque.getQuantidadeDepois());
				return "proxima";
				
			} else {
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(AjusteEstoque.class);
				if (getIdEstoque() != null && !"".equals(getIdEstoque())) {
					filter.addProperty("estoque.pk.id",new Long(getIdEstoque()));
				}
				if (getId() != null && !"".equals(getId())) {
	            	filter.addProperty("id", getId());
					return consultarFiltro(filter);
				} else if (getDataInicio() != null && !"".equals(getDataFinal())) {
					filter.addPropertyInterval("data",getDataInicio(), IntervalObject.MAIOR_IGUAL);
					filter.addPropertyInterval("data",getDataFinal(), IntervalObject.MENOR_IGUAL);
					if (getIdEstoque() == null && "".equals(getIdEstoque())) {
					   return consultarFiltro(filter);
					}
				}
				return consultarFiltro(filter);				
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
	
	private String consultarFiltro(PropertyFilter filter) {

		Collection col=null;
		try {
			col = getFachada().consultarAjusteEstoque(filter);
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
				AjusteEstoque ajusteEstoque = (AjusteEstoque)col.iterator().next();
				this.setIdEstoque(ajusteEstoque.getEstoque().getPk().getId().toString());
				this.setIdProduto(ajusteEstoque.getProduto().getId().toString());
				this.setDescricao(ajusteEstoque.getProduto().getDescricaoCompleta());
				this.setData(ajusteEstoque.getData());
				this.setQuantidadeAntes(ajusteEstoque.getQuantidadeAntes());
				this.setQuantidadeDepois(ajusteEstoque.getQuantidadeDepois());
				return "proxima";
			} else {
				this.setAjusteEstoques(col);
			}
			
		}
		return resetBB();
	}
	public String inserir() {
		
		
		try {
			AjusteEstoque ajusteEstoque = new AjusteEstoque();
			
			ajusteEstoque.setId(new Long(this.id));
			ajusteEstoque.setCodigoUsuario(1L);
			ajusteEstoque.setData(new Date());
			ajusteEstoque.setQuantidadeAntes(this.quantidadeAntes);
			ajusteEstoque.setQuantidadeDepois(this.quantidadeDepois);
			
			Estoque estoque = null;
			for (Iterator iter = estoques.iterator(); iter.hasNext();) {
				Estoque element = (Estoque) iter.next();
				if (element.getPk().getId().longValue()==new Long(this.idEstoque).longValue()) {
					estoque = (Estoque)element;
					estoque.getPk().getLoja().setIdEstoque(estoque.getPk().getId());
				}
				
			}
			
			ajusteEstoque.setEstoque(estoque);
			
			produto = getFachada().consultarProdutoPorPK(new Long(idProduto));
			
			ajusteEstoque.setProduto(produto);
			
			getFachada().inserirAjusteEstoque(ajusteEstoque);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ajuste de estoque já existente!", "");
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

	

	public String resetBB() {
		this.setId(null);
        this.setCodigoUsuario(null);
        this.setData(null);
		this.setIdEstoque(null);
		this.setIdProduto(null);
		this.setDescricao(null);
		this.setQuantidadeAntes(null);
		this.setQuantidadeDepois(null);
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
			arrayEstoques = new SelectItem[estoques.size()+1];
			int i = 0;
			arrayEstoques[i++] = new SelectItem("","");
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
	
	/**
	 * @return the codigoUsuario
	 */
	public Long getCodigoUsuario() {
		return codigoUsuario;
	}

	/**
	 * @param codigoUsuario the codigoUsuario to set
	 */
	public void setCodigoUsuario(Long codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return the produto
	 */
	public Produto getProduto() {
		return produto;
	}

	/**
	 * @param produto the produto to set
	 */
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	/**
	 * @return the quantidadeAntes
	 */
	public BigDecimal getQuantidadeAntes() {
		return quantidadeAntes;
	}

	/**
	 * @param quantidadeAntes the quantidadeAntes to set
	 */
	public void setQuantidadeAntes(BigDecimal quantidadeAntes) {
		this.quantidadeAntes = quantidadeAntes;
	}

	/**
	 * @return the quantidadeDepois
	 */
	public BigDecimal getQuantidadeDepois() {
		return quantidadeDepois;
	}

	/**
	 * @param quantidadeDepois the quantidadeDepois to set
	 */
	public void setQuantidadeDepois(BigDecimal quantidadeDepois) {
		this.quantidadeDepois = quantidadeDepois;
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
	 * @return the ajusteEstoques
	 */
	public Collection<AjusteEstoque> getAjusteEstoques() {
		return ajusteEstoques;
	}

	/**
	 * @param ajusteEstoques the ajusteEstoques to set
	 */
	public void setAjusteEstoques(Collection<AjusteEstoque> ajusteEstoques) {
		this.ajusteEstoques = ajusteEstoques;
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
	 * @return the ajusteEstoque
	 */
	public AjusteEstoque getAjusteEstoque() {
		return ajusteEstoque;
	}

	/**
	 * @param ajusteEstoque the ajusteEstoque to set
	 */
	public void setAjusteEstoque(AjusteEstoque ajusteEstoque) {
		this.ajusteEstoque = ajusteEstoque;
	}
}
