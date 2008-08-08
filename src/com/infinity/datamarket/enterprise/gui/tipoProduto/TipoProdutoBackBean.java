package com.infinity.datamarket.enterprise.gui.tipoProduto;

import java.util.Collection;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;

import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class TipoProdutoBackBean extends BackBean{
	
	private String id;
	private String descricao;
	private Collection tiposProduto;
		
	public Collection getTiposProduto() {
		return tiposProduto;
	}
	public void setTiposProduto(Collection tiposProduto) {
		this.tiposProduto = tiposProduto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String inserir(){
		TipoProduto tipo = new TipoProduto();
		tipo.setDescricao(this.getDescricao());
		try {
			getFachada().inserirTipoProduto(tipo);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Tipo de Produto já Existente!", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
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
				TipoProduto tipo = getFachada().consultarTipoProdutoPorPK(new Long(getId()));
				setId(tipo.getId().toString());
				setDescricao(tipo.getDescricao());
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(TipoProduto.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarTipoProduto(filter);
				if (col == null || col.size() == 0){
					setTiposProduto(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);	
					setExisteRegistros(false);
				}else if (col != null){
					if(col.size() == 1){
						TipoProduto tipo = (TipoProduto)col.iterator().next();
						setId(tipo.getId().toString());
						setDescricao(tipo.getDescricao());
						return "proxima";
					}else{
						setExisteRegistros(true);
						setTiposProduto(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodosTipoProduto();
				if (c != null && c.size() > 0){
					setExisteRegistros(true);
				}else{
					setExisteRegistros(false);
				}
				setTiposProduto(c);
			}
		}catch(ObjectNotFoundException e){
			setTiposProduto(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);
			setExisteRegistros(false);
		}catch(Exception e){
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
			TipoProduto tipo = new TipoProduto();
			tipo.setId(new Long(id));
			tipo.setDescricao(getDescricao());
			getFachada().alterarTipoProduto(tipo);
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
	
	public String excluir(){
		try {
			TipoProduto tipo = new TipoProduto();
			tipo.setId(new Long(getId()));
			tipo.setDescricao(getDescricao());
			getFachada().excluirTipoProduto(tipo);
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
		this.descricao = null;
		return "mesma";
	}
	
	public String voltarConsulta(){
//		resetBB();
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
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
			setTiposProduto(null);
		}
	}

}
