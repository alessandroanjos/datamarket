/**
 * 
 */
package com.infinity.datamarket.enterprise.gui.loja;

import java.util.Collection;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

/**
 * @author jonas
 *
 */
public class LojaBackBean extends BackBean {
	String id;
	String nome;
	String numeroIp;
	String numeroPorta;
	
	private Collection lojas;
	
	/**
	 * @return the lojas
	 */
	public Collection getLojas() {
		return lojas;
	}
	/**
	 * @param lojas the lojas to set
	 */
	public void setLojas(Collection lojas) {
		this.lojas = lojas;
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
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the numeroIp
	 */
	public String getNumeroIp() {
		return numeroIp;
	}
	/**
	 * @param numeroIp the numeroIp to set
	 */
	public void setNumeroIp(String numeroIp) {
		this.numeroIp = numeroIp;
	}
	/**
	 * @return the numeroPorta
	 */
	public String getNumeroPorta() {
		return numeroPorta;
	}
	/**
	 * @param numeroPorta the numeroPorta to set
	 */
	public void setNumeroPorta(String numeroPorta) {
		this.numeroPorta = numeroPorta;
	}
	
	public String inserir(){
		Loja loja = new Loja();
		loja.setId(new Long(this.id));
		loja.setNome(this.nome);
		loja.setNumeroIp(this.numeroIp);
		loja.setNumeroPorta(this.numeroPorta);
		try {
			getFachada().inserirLoja(loja);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Loja já Existente!", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		resetBB();
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
				Loja loja = getFachada().consultarLojaPorPK(new Long(getId()));
				this.setId(loja.getId().toString());
				this.setNome(loja.getNome());
				this.setNumeroIp(loja.getNumeroIp());
				this.setNumeroPorta(loja.getNumeroPorta());
				return "proxima";
			}else if (getNome() != null && !"".equals(getNome())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Loja.class);
				filter.addProperty("nome", getNome());
				Collection col = getFachada().consultarLoja(filter);
				if (col == null || col.size() == 0){
					this.setLojas(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						Loja loja = (Loja)col.iterator().next();
						this.setId(loja.getId().toString());
						this.setNome(loja.getNome());
						this.setNumeroIp(loja.getNumeroIp());
						this.setNumeroPorta(loja.getNumeroPorta());
						return "proxima";
					}else{
						setLojas(col);
					}
				}
			}else{
				setLojas(getFachada().consultarTodosLoja());
			}
		}catch(ObjectNotFoundException e){
			this.setLojas(null);
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
		this.setId(null);
		this.setNome(null);
		this.setNumeroIp(null);
		this.setNumeroPorta(null);
		return "mesma";
	}
	
	public String alterar(){
		try {
			Loja loja = new Loja();
			loja.setId(new Long(this.getId()));
			loja.setNome(this.getNome());
			loja.setNumeroIp(this.getNumeroIp());
			loja.setNumeroPorta(this.getNumeroPorta());
			getFachada().alterarLoja(loja);
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
		return "mesma";
	}
	
	public String excluir(){
		try {
			Loja loja = new Loja();
			loja.setId(new Long(this.getId()));
			loja.setNome(this.getNome());
			loja.setNumeroIp(this.getNumeroIp());
			loja.setNumeroPorta(this.getNumeroIp());
			getFachada().excluirLoja(loja);
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
		this.setId(null);
		this.setNome(null);
		this.setNumeroIp(null);
		this.setNumeroPorta(null);
		this.setLojas(null);
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
}
