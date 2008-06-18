package com.infinity.datamarket.enterprise.gui.grupoProduto;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.produto.GrupoProduto;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.ValidationException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class GrupoProdutoBackBean extends BackBean{
	private String id;
	private String descricao;
	private String idSuperior;
	private Collection gruposProduto;
	

	public Collection getGruposProduto() {
		return gruposProduto;
	}
	public void setGruposProduto(Collection gruposProduto) {
		this.gruposProduto = gruposProduto;
	}
	public String getIdSuperior() {
		return idSuperior;
	}
	public void setIdSuperior(String idSuperior) {
		this.idSuperior = idSuperior;
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
		GrupoProduto grupo = new GrupoProduto();
		if (idSuperior != null && !"".equals(idSuperior)){
			GrupoProduto grupoSuperior = new GrupoProduto();
			grupoSuperior.setId(new Long(idSuperior));
			grupo.setGrupoSuperior(grupoSuperior);
		}
		grupo.setId(new Long(id));
		grupo.setDescricao(descricao);
		try {
			getFachada().inserirGrupoProduto(grupo);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Grupo de Produto já Existente!", "");
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
	
		
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();            
			String param = (String)  params.get("id");
			if (param != null && !"".equals(param)){
				setId(param);
			}
			if (getId() != null && !"".equals(getId())){
				GrupoProduto grupo = getFachada().consultarGrupoProdutoPorPK(new Long(getId()));
				setId(grupo.getId().toString());
				setDescricao(grupo.getDescricao());
				if (grupo.getGrupoSuperior() != null){
					setIdSuperior(grupo.getGrupoSuperior().getId().toString());
				}
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(GrupoProduto.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarGrupoProduto(filter);
				if (col == null || col.size() == 0){
					setGruposProduto(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);
					setExisteRegistros(false);
				}else if (col != null){
					if(col.size() == 1){
						GrupoProduto grupo = (GrupoProduto)col.iterator().next();
						setId(grupo.getId().toString());
						setDescricao(grupo.getDescricao());
						if (grupo.getGrupoSuperior() != null){
							setIdSuperior(grupo.getGrupoSuperior().getId().toString());
						}
						return "proxima";
					}else{
						setExisteRegistros(true);
						setGruposProduto(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodosGruposProduto();
				if (c != null && c.size() > 0){
					setExisteRegistros(true);
				}else{
					setExisteRegistros(false);
				}
				setGruposProduto(c);
			}
		}catch(ObjectNotFoundException e){
			setGruposProduto(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);
			setExisteRegistros(false);
		}catch(Exception e){
			e.printStackTrace();
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
			GrupoProduto grupo = new GrupoProduto();
			if (idSuperior != null && !"".equals(idSuperior)){
				GrupoProduto grupoSuperior = new GrupoProduto();
				grupoSuperior.setId(new Long(idSuperior));
				grupo.setGrupoSuperior(grupoSuperior);
			}
			grupo.setId(new Long(id));
			grupo.setDescricao(descricao);
			getFachada().alterarGrupoProduto(grupo);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
		} catch (ValidationException e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}
	
	public String excluir(){
		try {
			GrupoProduto grupo = new GrupoProduto();
			if (idSuperior != null && !"".equals(idSuperior)){
				GrupoProduto grupoSuperior = new GrupoProduto();
				grupoSuperior.setId(new Long(idSuperior));
				grupo.setGrupoSuperior(grupoSuperior);
			}
			grupo.setId(new Long(id));
			grupo.setDescricao(descricao);
			getFachada().excluirGrupoProduto(grupo);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
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
	
	public String resetBB(){
		this.id = null;
		this.descricao = null;
		this.idSuperior = null;
		return "mesma";
	}
	
	public String voltarConsulta(){
		resetBB();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	
	public SelectItem[] getGrupos() {
		Collection grupos = carregarGrupos();
		SelectItem[] itens = new SelectItem[grupos.size() + 1];
		Iterator it = grupos.iterator();
		itens[0] = new SelectItem("","");
		for(int i = 1;it.hasNext();i++){
			GrupoProduto grupo = (GrupoProduto) it.next();
			SelectItem item = new SelectItem(String.valueOf(grupo.getId()),
					grupo.getDescricao());
			itens[i] = item;
		}
		if (idSuperior == null){
			idSuperior = (String) itens[0].getValue();
		}
		return itens;
	}

	private Collection carregarGrupos() {
		Collection grupos = null;
		try{
			grupos = getFachada().consultarTodosGruposProduto();
		}catch(Exception e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return grupos;
	}


}
