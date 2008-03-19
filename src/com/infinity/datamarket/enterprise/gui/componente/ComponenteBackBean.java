package com.infinity.datamarket.enterprise.gui.componente;
/**
 * 
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

/**
 * @author alessandro
 *
 */
public class ComponenteBackBean extends BackBean {
	String id;
	String descricao;
	String idLoja;
	String ip;
	String versao;
	String porta;
	Loja loja;
	Collection componentes;

	
	public String voltarConsulta(){
		resetBB();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
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
		Componente componente = new Componente();
		
		componente.setId(new Long(this.id));
		componente.setDescricao(this.descricao);
	    componente.setIp(this.ip);
	    componente.setPorta(this.porta);
	    componente.setVersao(this.versao);
		Loja loja = new Loja();
		loja.setId(new Long(this.idLoja));
	    componente.setLoja(loja);
	    
		try {
			getFachada().inserirComponente(componente);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Componente já Existente!", "");
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
				Componente componente = getFachada().consultarComponentePorId(new Long(getId()));
				this.setId(componente.getId().toString());
				this.setDescricao(componente.getDescricao());
				this.setIp(componente.getIp());
				this.setVersao(componente.getVersao());
				this.setPorta(componente.getPorta());
				this.setIdLoja(componente.getLoja().getId().toString());
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Componente.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarComponentes(filter);
				if (col == null || col.size() == 0){
					this.setComponentes(col);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						Componente componente = (Componente)col.iterator().next();
						this.setId(componente.getId().toString());
						this.setDescricao(componente.getDescricao());
						this.setIp(componente.getIp());
						this.setVersao(componente.getVersao());
						this.setPorta(componente.getPorta());
						this.setIdLoja(componente.getLoja().getId().toString());
						return "proxima";
					}else{
						this.setComponentes(col);
					}
				}
			}else{
				setComponentes(getFachada().consultarTodosComponentes());
			}
		}catch(ObjectNotFoundException e){
			this.setComponentes(null);
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
		this.setDescricao(null);
		this.setIp(null);
		this.setVersao(null);
		this.setPorta(null);
		this.setIdLoja(null);
		return "mesma";
	}
	
	public String alterar(){
		try {
			Componente componente = new Componente();
			
			componente.setId(new Long(this.id));
			componente.setDescricao(this.descricao);
		    componente.setIp(this.ip);
		    componente.setPorta(this.porta);
		    componente.setVersao(this.versao);
			Loja loja = new Loja();
			loja.setId(new Long(this.idLoja));	
			componente.setLoja(loja);
			
			getFachada().alterarComponente(componente);
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
			Componente componente = new Componente();
			
			componente.setId(new Long(this.id));
			componente.setDescricao(this.descricao);
		    componente.setIp(this.ip);
		    componente.setPorta(this.porta);
		    componente.setVersao(this.versao);
			Loja loja = new Loja();
			loja.setId(new Long(this.idLoja));			
			componente.setLoja(loja);
			getFachada().alterarComponente(componente);
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
		this.setDescricao(null);
		this.setIp(null);
		this.setVersao(null);
		this.setPorta(null);
		this.setIdLoja(null);
		return "mesma";
	}
    private List<Loja> carregarLojas() {
		
		List<Loja> lojas = null;
		try {
			lojas = (ArrayList<Loja>)getFachada().consultarTodosLoja();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return lojas;
	}
	
	public SelectItem[] getLojas(){
		SelectItem[] arrayLojas = null;
		try {
			List<Loja> lojas = carregarLojas();
			arrayLojas = new SelectItem[lojas.size()];
			int i = 0;
			for(Loja lojaTmp : lojas){
				SelectItem item = new SelectItem(lojaTmp.getId().toString(), lojaTmp.getNome());
				arrayLojas[i++] = item;
			}
			
			if(this.getIdLoja() == null || this.getIdLoja().equals("") || this.getIdLoja().equals("0")){
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
	public Collection getComponentes() {
		return componentes;
	}
	public void setComponentes(Collection componentes) {
		this.componentes = componentes;
	}
	public String getIdLoja() {
		return idLoja;
	}
	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Loja getLoja() {
		return loja;
	}
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	public String getPorta() {
		return porta;
	}
	public void setPorta(String porta) {
		this.porta = porta;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	
}
