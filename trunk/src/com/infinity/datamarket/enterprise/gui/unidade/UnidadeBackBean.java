package com.infinity.datamarket.enterprise.gui.unidade;

import java.util.Collection;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinity.datamarket.comum.produto.Unidade;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class UnidadeBackBean extends BackBean{
	private String id;
	private String descricao;
	private String descricaoCompacta;
	private String abreviacao;
	
	private Collection unidades;
	
	
	public Collection getUnidades() {
		return unidades;
	}
	public void setUnidades(Collection unidades) {
		this.unidades = unidades;
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
		Unidade unidade = new Unidade();
		unidade.setId(new Long(getId()));
		unidade.setDescricao(getDescricao());
		unidade.setDescricaoDisplay(getDescricaoCompacta());
		unidade.setAbreviacao(getAbreviacao());
		try {
			getFachada().inserirUnidade(unidade);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Unidade já Existente!", "");
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
				Unidade unidade = getFachada().consultarUnidadePorPK(new Long(getId()));
				setId(unidade.getId().toString());
				setDescricao(unidade.getDescricao());
				setDescricaoCompacta(unidade.getDescricaoDisplay());
				setAbreviacao(unidade.getAbreviacao());
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Unidade.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarUnidade(filter);
				if (col == null || col.size() == 0){
					setUnidades(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						Unidade unidade = (Unidade)col.iterator().next();
						setId(unidade.getId().toString());
						setDescricao(unidade.getDescricao());
						setDescricaoCompacta(unidade.getDescricaoDisplay());
						setAbreviacao(unidade.getAbreviacao());
						return "proxima";
					}else{
						setUnidades(col);
					}
				}
			}else{
				setUnidades(getFachada().consultarTodasUnidades());
			}
		}catch(ObjectNotFoundException e){
			setUnidades(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);			
		}catch(Exception e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		setId(null);
		setDescricao(null);
		return "mesma";
	}
	
	public String alterar(){
		try {
			Unidade unidade = new Unidade();
			unidade.setId(new Long(getId()));
			unidade.setDescricao(getDescricao());
			unidade.setDescricaoDisplay(getDescricaoCompacta());
			unidade.setAbreviacao(getAbreviacao());
			getFachada().alterarUnidade(unidade);
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
			Unidade unidade = new Unidade();
			unidade.setId(new Long(getId()));
			unidade.setDescricao(getDescricao());
			unidade.setDescricaoDisplay(getDescricaoCompacta());
			unidade.setAbreviacao(getAbreviacao());
			getFachada().excluirUnidade(unidade);
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
		this.descricaoCompacta = null;
		this.abreviacao = null;
		this.unidades = null;
		return "mesma";
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
	public String getAbreviacao() {
		return abreviacao;
	}
	public void setAbreviacao(String abraviacao) {
		this.abreviacao = abraviacao;
	}
	public String getDescricaoCompacta() {
		return descricaoCompacta;
	}
	public void setDescricaoCompacta(String descricaoCompacta) {
		this.descricaoCompacta = descricaoCompacta;
	}
}
