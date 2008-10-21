package com.infinity.datamarket.enterprise.gui.imposto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;

import com.infinity.datamarket.comum.produto.GrupoProduto;
import com.infinity.datamarket.comum.produto.Imposto;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class ImpostoBackBean extends BackBean{
	private String id;
	private String descricao;
	private String impostoImpressora;
	private String percentual;
	
	private Collection impostos;
	
	
	public Collection getImpostos() {
		return impostos;
	}
	public void setImpostos(Collection impostos) {
		this.impostos = impostos;
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
		Imposto imposto = new Imposto();
		imposto.setDescricao(getDescricao());
		imposto.setImpostoImpressora(getImpostoImpressora());
		imposto.setPercentual(new BigDecimal(getPercentual()));
		try {
			if (getId()==null) imposto.setId(getIdInc(Imposto.class));
			getFachada().inserirImposto(imposto);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Imposto já Existente!", "");
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
				Imposto imposto = getFachada().consultarImpostoPorPK(new Long(getId()));
				setId(imposto.getId().toString());
				setDescricao(imposto.getDescricao());
				setImpostoImpressora(imposto.getImpostoImpressora());
				setPercentual(imposto.getPercentual().toString());
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Imposto.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarImposto(filter);
				if (col == null || col.size() == 0){
					setImpostos(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);	
					setExisteRegistros(false);
				}else if (col != null){
					if(col.size() == 1){
						Imposto imposto = (Imposto)col.iterator().next();
						setId(imposto.getId().toString());
						setDescricao(imposto.getDescricao());
						setImpostoImpressora(imposto.getImpostoImpressora());
						setPercentual(imposto.getPercentual().toString());
						return "proxima";
					}else{
						setImpostos(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodosImpostos();
				if(c != null && c.size() > 0){
					setImpostos(c);
					setExisteRegistros(true);
				}else{
					setImpostos(null);
					setExisteRegistros(false);
				}
				
			}
		}catch(ObjectNotFoundException e){
			setImpostos(null);
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
		setId(null);
		setDescricao(null);
		return "mesma";
	}
	
	public String alterar(){
		try {
			Imposto imposto = new Imposto();
			imposto.setId(new Long(getId()));
			imposto.setDescricao(getDescricao());
			imposto.setImpostoImpressora(getImpostoImpressora());
			imposto.setPercentual(new BigDecimal(getPercentual()));
			getFachada().alterarImposto(imposto);
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
			Imposto imposto = new Imposto();
			imposto.setId(new Long(getId()));
			imposto.setDescricao(getDescricao());
			imposto.setImpostoImpressora(getImpostoImpressora());
			imposto.setPercentual(new BigDecimal(getPercentual()));
			getFachada().excluirImposto(imposto);
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
	
	public void resetBB(){
		this.id = null;
		this.descricao = null;
		this.impostoImpressora = null;
		this.percentual = null;
	}
	
	public String voltarConsulta(){
	//  resetBB();
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	public String getImpostoImpressora() {
		return impostoImpressora;
	}
	public void setImpostoImpressora(String impostoImpressora) {
		this.impostoImpressora = impostoImpressora;
	}
	public String getPercentual() {
		return percentual;
	}
	public void setPercentual(String percentual) {
		this.percentual = percentual;
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
				setImpostos(null);
			}
		}
	}
}
