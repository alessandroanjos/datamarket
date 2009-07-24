/**
 * 
 */
package com.infinity.datamarket.enterprise.gui.loja;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.conta.ContaCorrente;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
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
	String idEstoqueAtual;
	String idContaAtual;
	

	public LojaBackBean() {
		resetBB();
	}
	
	private Collection lojas;
	SelectItem[] estoques;
	SelectItem[] contas;
	
	public void setEstoques(SelectItem[] estoques) {
		this.estoques = estoques;
	}
	
	public void setContas(SelectItem[] contas) {
		this.contas = contas;
	}
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
	
	public String getIdEstoqueAtual() {
		return idEstoqueAtual;
	}
	public void setIdEstoqueAtual(String idEstoqueAtual) {
		this.idEstoqueAtual = idEstoqueAtual;
	}
	
	public void validarLoja() throws AppException{
		if(this.getId() == null || this.getId().equals("")){
			throw new AppException("É necessário informar o Código.");
		}
		
		if(this.getId() != null && this.getId().equals("0")){
			throw new AppException("O Código deve ser diferente de 0 (zero).");
		}
		
		if(this.getNome() == null || this.getNome().equals("")){
			throw new AppException("É necessário informar um Nome para a Loja.");
		}
		
		if(this.getNumeroIp() == null || this.getNumeroIp().equals("")){
			throw new AppException("É necessário informar um IP para a Loja.");
		}
		
		if(this.getNumeroPorta() == null || this.getNumeroPorta().equals("")){
			throw new AppException("É necessário informar uma Porta para a Loja.");
		}		
	}
	
	public String inserir(){
		try {
			
			validarLoja();
			
			Loja loja = new Loja();
			
			loja.setId(new Long(this.id));
			loja.setNome(this.nome);
			loja.setNumeroIp(this.numeroIp);
			loja.setNumeroPorta(this.numeroPorta);

			loja.setIdEstoque(null);
			loja.setIdContaCorrente(null);
			getFachada().inserirLoja(loja);
			Usuario u = LoginBackBean.getInstancia().getUsuarioLogado();
			u.getLojas().add(loja);
			getFachada().alterarUsuario(u);			
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Loja já Existente!", "");
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
				Loja loja = getFachada().consultarLojaPorPK(new Long(getId()));
				this.setId(loja.getId().toString());
				this.setNome(loja.getNome());
				this.setNumeroIp(loja.getNumeroIp());
				this.setNumeroPorta(loja.getNumeroPorta());
				if(loja.getIdEstoque() != null){
					this.setIdEstoqueAtual(loja.getIdEstoque().toString());	
				}else{
					this.setIdEstoqueAtual("0");
				}
				if(loja.getIdContaCorrente() != null){
					this.setIdContaAtual(loja.getIdContaCorrente().toString());	
				}else{
					this.setIdContaAtual("0");
				}
				return "proxima";
			}else if ((getNome() != null && !"".equals(getNome())) || 
					(getNumeroIp() != null && !getNumeroIp().equals("")) ||
					(getNumeroPorta() != null && !getNumeroPorta().equals(""))){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Loja.class);
				if(!getNome().equals("")){
					filter.addProperty("nome", getNome());	
				}
				if(!getNumeroIp().equals("")){
					filter.addProperty("numeroIp", getNumeroIp());	
				}
				if(!getNumeroPorta().equals("")){
					filter.addProperty("numeroPorta", getNumeroPorta());	
				}
				Collection col = getFachada().consultarLoja(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setLojas(null);
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);	
					setExisteRegistros(false);
				}else if (col != null){
					if(col.size() == 1){
						Loja loja = (Loja)col.iterator().next();
						this.setId(loja.getId().toString());
						this.setNome(loja.getNome());
						this.setNumeroIp(loja.getNumeroIp());
						this.setNumeroPorta(loja.getNumeroPorta());
						if(loja.getIdEstoque() != null){
							this.setIdEstoqueAtual(loja.getIdEstoque().toString());	
						}else{
							this.setIdEstoqueAtual("0");
						}
						if(loja.getIdContaCorrente() != null){
							this.setIdContaAtual(loja.getIdContaCorrente().toString());	
						}else{
							this.setIdContaAtual("0");
						}
						return "proxima";
					}else{
						setExisteRegistros(true);
						setLojas(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodosLoja();
				if (c != null && c.size() > 0){
					setExisteRegistros(true);
				}else{
					setExisteRegistros(false);
				}
				setLojas(c);
			}
		}catch(ObjectNotFoundException e){
			this.setLojas(null);
			
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
			
			validarLoja();
			
			Loja loja = new Loja();
			loja.setId(new Long(this.getId()));
			loja.setNome(this.getNome());
			loja.setNumeroIp(this.getNumeroIp());
			loja.setNumeroPorta(this.getNumeroPorta());
			
			loja.setIdEstoque(new Long(this.getIdEstoqueAtual()));
			loja.setIdContaCorrente(new Long(this.getIdContaAtual()));
			getFachada().alterarLoja(loja);
			
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
	
	public String excluir(){
		try {
			Loja loja = new Loja();
			loja.setId(new Long(this.getId()));
			loja.setNome(this.getNome());
			loja.setNumeroIp(this.getNumeroIp());
			loja.setNumeroPorta(this.getNumeroIp());			
		
			loja.setIdEstoque(new Long(this.getIdEstoqueAtual()));	
			loja.setIdContaCorrente(new Long(this.getIdContaAtual()));
			
			getFachada().excluirLoja(loja);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		resetBB();
		return "mesma";
	}
	
	public void resetBB(){
		this.setId(null);
		this.setNome(null);
		this.setNumeroIp(null);
		this.setNumeroPorta(null);
		this.setIdEstoqueAtual("0");
		this.setIdContaAtual("0");
	}
	
	
	public String voltarConsulta(){
		resetBB();
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	
	private List<Estoque> carregarEstoques() {
		
		List<Estoque> estoques = null;
		try {
			estoques = (ArrayList<Estoque>)getFachada().consultarTodosEstoquesPorLoja(this.getId());	
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return estoques;
	}
	
	private List<ContaCorrente> carregarContas() {
		
		List<ContaCorrente> contas = null;
		try {
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(ContaCorrente.class);
			if (this.getId() != null && !this.getId().equals("")){
				Loja loja = new Loja();
				loja.setId(new Long(this.getId()));
				filter.addProperty("loja", loja);
				contas = (ArrayList<ContaCorrente>)getFachada().consultarContaCorrente(filter);
			}else{
				contas = new ArrayList<ContaCorrente>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return contas;
	}
	
	public SelectItem[] getEstoques(){
		SelectItem[] arrayEstoques = null;
		try {
			List<Estoque> estoques = carregarEstoques();
			
			int i = 0;
			SelectItem itemBranco = null;
			if(estoques == null || estoques.size() == 0){
				arrayEstoques = new SelectItem[1];
				itemBranco = new SelectItem("0", "Sem estoques Cadastrados.");
				arrayEstoques[i++] = itemBranco;
			}else{
				arrayEstoques = new SelectItem[estoques.size()];
			}
			
			for(Estoque estoqueTmp : estoques){
				SelectItem item = new SelectItem(estoqueTmp.getPk().getId().toString(), estoqueTmp.getDescricao());
				arrayEstoques[i++] = item;
			}
			
			if(this.getIdEstoqueAtual() == null || this.getIdEstoqueAtual().equals("") || this.getIdEstoqueAtual().equals("0")){
				this.setIdEstoqueAtual((String) arrayEstoques[0].getValue());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayEstoques;
	}
	
	
	public SelectItem[] getContas(){
		SelectItem[] arrayContas = null;
		try {
			List<ContaCorrente> contas = carregarContas();
			
			int i = 0;
			SelectItem itemBranco = null;
			if(contas == null || contas.size() == 0){
				arrayContas = new SelectItem[1];
				itemBranco = new SelectItem("0", "Sem contas Cadastradas.");
				arrayContas[i++] = itemBranco;
			}else{
				arrayContas = new SelectItem[contas.size()];
			}
			
			for(ContaCorrente conta : contas){
				SelectItem item = new SelectItem(conta.getId().toString(), conta.getNome());
				arrayContas[i++] = item;
			}
			
			if(this.getIdContaAtual() == null || this.getIdContaAtual().equals("") || this.getIdContaAtual().equals("0")){
				this.setIdContaAtual((String) arrayContas[0].getValue());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return arrayContas;
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
				setLojas(null);
			}
		}
	}

	public String getIdContaAtual() {
		return idContaAtual;
	}

	public void setIdContaAtual(String idContaAtual) {
		this.idContaAtual = idContaAtual;
	}

}
