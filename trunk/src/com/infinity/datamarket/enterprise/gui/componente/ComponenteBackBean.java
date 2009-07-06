package com.infinity.datamarket.enterprise.gui.componente;
/**
 * 
 */
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.collection.PersistentSet;

import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
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
	String versao="";
	String porta;
	Loja loja;
	Collection componentes;

	
	public String voltarConsulta(){
//		resetBB();
		consultar();
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


	public void validarComponentePorLoja(Componente componente) throws AppException{
		
	}
	
	public void validarComponente() throws AppException{
		if(this.getId() == null || this.getId().equals("")){
			throw new AppException("É necessário informar o Código.");
		}

		if(this.getDescricao() == null || this.getDescricao().equals("")){
			throw new AppException("É necessário informar uma Descrição.");
		}
		
		if(this.getIp() == null || this.getIp().equals("")){
			throw new AppException("É necessário informar um IP.");
		}
		
		if(this.getPorta() == null || this.getPorta().equals("")){
			throw new AppException("É necessário informar uma Porta.");
		}
		
		if(this.getIdLoja() == null || this.getIdLoja().equals("0")){
			throw new AppException("É necessário informar uma Loja.");
		}
	}

	public String inserir(){
		try {
			
			validarComponente();
			
			Componente componente = new Componente();
			
			componente.setId(new Long(this.id));
			componente.setDescricao(this.descricao);
		    componente.setIp(this.ip);
		    componente.setPorta(this.porta);
		    componente.setVersao("");
			Loja loja = new Loja();
			loja.setId(new Long(this.idLoja));
		    componente.setLoja(loja);
		    
			validarComponentePorLoja(componente);
			
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
		} catch (AppException e) {
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
//		resetBB();
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
			}else if ((this.getId() != null && !"".equals(this.getId())) || 
					(this.getDescricao() != null && !"".equals(this.getDescricao())) || 
					(this.getIdLoja() != null && !"0".equals(this.getIdLoja()))){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Componente.class);
				
				if(this.getId() != null && !this.getId().equals("")){
					filter.addProperty("id", this.getDescricao());	
				}
				
				if(this.getDescricao() != null && !this.getDescricao().equals("")){
					filter.addProperty("descricao", this.getDescricao());	
				}
				
				if(this.getIdLoja() != null && !this.getIdLoja().equals("0")){
					filter.addProperty("loja.id", new Long(this.getIdLoja()));	
				}
				
				Collection col = getFachada().consultarComponentes(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setComponentes(null);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);
					setExisteRegistros(false);
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
						setExisteRegistros(true);
						this.setComponentes(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodosComponentes();
				if (c != null && c.size() > 0){
					setExisteRegistros(true);
				}else{
					setExisteRegistros(false);
				}
				setComponentes(c);
			}
		}catch(ObjectNotFoundException e){
			this.setComponentes(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);
			setExisteRegistros(false);
			return "mesma";
		}catch(Exception e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
			setExisteRegistros(false);
			return "mesma";
		}
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
//		resetBB();
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
			getFachada().excluirComponente(componente);
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
	
	public void resetBB(){
		this.setId(null);
		this.setDescricao(null);
		this.setIp(null);
		this.setVersao("");
		this.setPorta(null);
		this.setIdLoja(null);
	}
	
    private Set<Loja> carregarLojas() {
		
//		List<Loja> lojas = null;
//		try {
//			lojas = (ArrayList<Loja>)getFachada().consultarTodosLoja();
		Set<Loja> lojas = null;
		try {
			lojas = (PersistentSet)LoginBackBean.getInstancia().getUsuario().getLojas();
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
			Set<Loja> lojas = carregarLojas();
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
		if (this.idLoja != null) {
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
				setComponentes(null);
			}
		}
	}
}
