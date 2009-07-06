package com.infinity.datamarket.enterprise.gui.autorizadora;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;

import com.infinity.datamarket.comum.pagamento.Autorizadora;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class AutorizadoraBackBean extends BackBean {

	private String descricao;
	private String situacao;
	private BigDecimal desagil;
	private String id;

	Collection autorizadoras;

	public String voltarConsulta(){
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}

	public void validarAutorizadora() throws AppException{
		
		if(this.getId() == null || this.getId().equals("")){
			throw new AppException("É necessário informar um Código.");
		}
		
		if(this.getDescricao() == null || this.getDescricao().equals("")){
			throw new AppException("É necessário informar uma Descrição.");
		}
		
		if(this.getDesagil() == null || (this.getDesagil() != null && this.getDesagil().setScale(2).equals(new BigDecimal("0.00")))){
			throw new AppException("É necessário informar um Deságil.");
		}
		
		if(this.getSituacao() == null || this.getSituacao().equals("")){
			throw new AppException("É necessário informar uma Situação.");
		}
	}

	public String inserir(){
		try {
			
			validarAutorizadora();
			
			Autorizadora autorizadora = new Autorizadora();
			
			if (getId()==null) autorizadora.setId(getIdInc(Autorizadora.class));
			
			autorizadora.setDescricao(this.descricao);
			autorizadora.setDesagil(this.desagil);
			autorizadora.setSituacao(this.situacao); 
			
			getFachada().inserirAutorizadora(autorizadora);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Autorizadora já Existente!", "");
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
				Autorizadora autorizadora = getFachada().consultarAutorizadoraPorId(new Long(getId()));
				this.setId(autorizadora.getId().toString());
				this.setDescricao(autorizadora.getDescricao());
				this.setSituacao(autorizadora.getSituacao());
				this.setDesagil(autorizadora.getDesagil());
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Autorizadora.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarFormaRecebimento(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setAutorizadoras(col);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						Autorizadora autorizadora = getFachada().consultarAutorizadoraPorId(new Long(getId()));
						this.setId(autorizadora.getId().toString());
						this.setDescricao(autorizadora.getDescricao());
						this.setSituacao(autorizadora.getSituacao());
						this.setDesagil(autorizadora.getDesagil());
						return "proxima";
					}else{
						setExisteRegistros(true);
						this.setAutorizadoras(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodasAutorizadoras();
				if(c != null && c.size() > 0){
					setExisteRegistros(true);
					setAutorizadoras(c);
				}else{
					setExisteRegistros(false);	
				}				
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setAutorizadoras(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);			
		}catch(Exception e){
			setExisteRegistros(false);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}
	
	public String alterar(){
		try {
			
			validarAutorizadora();
			
			Autorizadora autorizadora = new Autorizadora();
			
			autorizadora.setId(new Long(this.id));
			autorizadora.setDescricao(this.descricao);
			autorizadora.setDesagil(this.desagil);
			autorizadora.setSituacao(this.situacao);

			getFachada().alterarAutorizadora(autorizadora);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
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
	
	public String excluir(){
		try {
			Autorizadora autorizadora = new Autorizadora();
			
			autorizadora.setId(new Long(this.id));
			autorizadora.setDescricao(this.descricao);
			autorizadora.setDesagil(this.desagil);
			autorizadora.setSituacao(this.situacao);
			
			getFachada().excluirAutorizadora(autorizadora);
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
		this.setId(null);
		this.setDescricao(null);
		this.setDesagil(null);
		this.setSituacao("N");		
	}
	/**
	 * @return the desagil
	 */
	public BigDecimal getDesagil() {
		return desagil;
	}
	/**
	 * @param desagil the desagil to set
	 */
	public void setDesagil(BigDecimal desagil) {
		this.desagil = desagil;
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
	 * @return the situacao
	 */
	public String getSituacao() {
		return situacao;
	}
	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
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
	 * @return the autorizadoras
	 */
	public Collection getAutorizadoras() {
		return autorizadoras;
	}
	/**
	 * @param autorizadoras the autorizadoras to set
	 */
	public void setAutorizadoras(Collection autorizadoras) {
		this.autorizadoras = autorizadoras;
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
				setAutorizadoras(null);
			}
		}
	}
}
