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
import com.infinity.datamarket.comum.util.AppException;
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
	
	public void validarTipoProduto() throws AppException{
		if(this.getDescricao() == null || this.getDescricao().equals("")){
			throw new AppException("É necessário informar uma Descrição.");
		}
	}
	
	public String inserir(){
		try {
			
			validarTipoProduto();
			
			TipoProduto tipo = new TipoProduto();
			tipo.setDescricao(this.getDescricao());

			if (getId()==null) tipo.setId(getIdInc(TipoProduto.class));
			
			getFachada().inserirTipoProduto(tipo);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Tipo de Produto já Existente!", "");
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
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);	
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
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);
			setExisteRegistros(false);
		}catch(Exception e){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
			setExisteRegistros(false);
		}
		return "mesma";
	}
	
	public String alterar(){
		try {
			validarTipoProduto();
			
			TipoProduto tipo = new TipoProduto();
			tipo.setId(new Long(id));
			tipo.setDescricao(getDescricao());
			getFachada().alterarTipoProduto(tipo);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (AppException e) {
			e.printStackTrace();
			
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
	
	public String excluir(){
		try {
			TipoProduto tipo = new TipoProduto();
			tipo.setId(new Long(getId()));
			tipo.setDescricao(getDescricao());
			getFachada().excluirTipoProduto(tipo);
			
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
		this.descricao = null;
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
		if (param != null){
			resetBB();
			if(VALOR_ACAO.equals(param)){
				setTiposProduto(null);
			}
		}
	}

}
