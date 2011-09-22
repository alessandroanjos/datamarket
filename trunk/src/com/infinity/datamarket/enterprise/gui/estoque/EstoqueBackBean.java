/**
 * 
 */
package com.infinity.datamarket.enterprise.gui.estoque;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.collection.PersistentSet;

import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.EstoquePK;
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
public class EstoqueBackBean extends BackBean {
	String id;

	String descricao;
	
	String idLoja;

	Loja loja;

	Collection estoques;

	public String voltarConsulta() {
		consultar();
		return "voltar";
	}

	public String voltarMenu() {
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
	
	public void validarEstoque() throws AppException{
		if(this.getId() == null || this.getId().equals("")){
			throw new AppException("É necessário informar o Código.");
		}
		
		if(this.getDescricao() == null || this.getDescricao().equals("")){
			throw new AppException("É necessário informar uma Descrição.");
		}
		
		if(this.getIdLoja() == null || this.getIdLoja().equals("0")){
			throw new AppException("É necessário informar uma Loja.");
		}
	}

	public String inserir() {
		
		try {
			validarEstoque();
			
			Estoque estoque = new Estoque();
			EstoquePK pk = new EstoquePK();
			
			pk.setId(new Long(this.id));
			estoque.setDescricao(this.descricao);
			
			Loja loja = new Loja();
			loja.setId(new Long(this.idLoja));
			pk.setLoja(loja);
			
			estoque.setPk(pk);
			
			getFachada().inserirEstoque(estoque);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Estoque já Existente!", "");
			getContextoApp().addMessage(null, msg);
		} catch (AppException e) {
			
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

	public String consultar() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();
			String param = (String) params.get("id");
			String loja = (String) params.get("loja");
			if (param != null && !"".equals(param) && loja != null && !"".equals(loja)) {
				setId(param);
				setIdLoja(loja);
			}
			if (getId() != null && !"".equals(getId()) && getIdLoja() != null && !"0".equals(getIdLoja())) {
				EstoquePK pk = new EstoquePK();
				pk.setId(new Long(getId()));
				Loja l = new Loja();
				l.setId(new Long(getIdLoja()));
				pk.setLoja(l);
				Estoque estoque = getFachada().consultarEstoquePorId(pk);
				this.setId(estoque.getPk().getId().toString());
				this.setDescricao(estoque.getDescricao());

				this.setIdLoja(estoque.getPk().getLoja().getId().toString());
				return "proxima";
			} else if ((this.getId() != null && !"".equals(this.getId())) || 
					(this.getIdLoja() != null && !"0".equals(this.getIdLoja())) || 
					(getDescricao() != null && !"".equals(getDescricao()))) {
				
				PropertyFilter filter = new PropertyFilter();
				
				filter.setTheClass(Estoque.class);
				
				if(this.getDescricao() != null && !this.getDescricao().equals("")){
					filter.addProperty("descricao", getDescricao());
				}
				
//				EstoquePK estoquePk = new EstoquePK();
				if(this.getIdLoja() != null && !this.getIdLoja().equals("0")){
//					Loja lojaTmp = new Loja();
//					lojaTmp.setId(new Long(this.getIdLoja()));
//					estoquePk.setLoja(getFachada().consultarLojaPorId(new Long(this.getIdLoja())));
					filter.addProperty("pk.loja.id", new Long(this.getIdLoja()));//getFachada().consultarLojaPorId(new Long(this.getIdLoja())));
				}
				
				if(this.getId() != null && !this.getId().equals("")){
//					estoquePk.setId(new Long(this.getId()));
					filter.addProperty("pk.id", new Long(this.getId()));
				}
				
//				filter.addProperty("pk", estoquePk);
				
				Collection col = getFachada().consultarEstoque(filter);
				if (col == null || col.size() == 0) {
					this.setEstoques(col);
					
					FacesMessage msg = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);
					setExisteRegistros(false);
					return "mesma";
				} else if (col != null) {
					if (col.size() == 1) {
						Estoque estoque = (Estoque) col.iterator().next();
						this.setId(estoque.getPk().getId().toString());
						this.setDescricao(estoque.getDescricao());

						this.setIdLoja(estoque.getPk().getLoja().getId().toString());
						return "proxima";
					} else {
						setExisteRegistros(true);
						this.setEstoques(col);
					}
				}
			} else {
				Collection c = getFachada().consultarTodosEstoques();
				if(c != null & c.size() > 0){
					setEstoques(c);	
					setExisteRegistros(true);
				}else{
					setEstoques(null);
					setExisteRegistros(false);
				}
				
			}
		} catch (ObjectNotFoundException e) {
			this.setEstoques(null);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);
			setExisteRegistros(false);
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
			setExisteRegistros(false);
		}
		return "mesma";
	}

	public String alterar() {
		try {
			
			validarEstoque();
			
			Estoque estoque = new Estoque();
			EstoquePK pk = new EstoquePK();
			
			pk.setId(new Long(this.id));
			estoque.setDescricao(this.descricao);
			
			Loja loja = new Loja();
			loja.setId(new Long(this.idLoja));
			pk.setLoja(loja);
			
			estoque.setPk(pk);
			
			getFachada().alterarEstoque(estoque);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (AppException e) {
			
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

	public String excluir() {
		try {
			Estoque estoque = new Estoque();
			EstoquePK pk = new EstoquePK();
			
			pk.setId(new Long(this.id));
			estoque.setDescricao(this.descricao);
			
			Loja loja = new Loja();
			loja.setId(new Long(this.idLoja));
			pk.setLoja(loja);
			
			estoque.setPk(pk);
			
			getFachada().excluirEstoque(estoque);
			
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

	public String resetBB() {
		this.setId(null);
		this.setDescricao(null);
		this.setIdLoja(null);
		return "mesma";
	}

	private Set<Loja> carregarLojas() {

//		List<Loja> lojas = null;
//		try {
//			lojas = (ArrayList<Loja>) getFachada().consultarTodosLoja();
		Set<Loja> lojas = null;
		try {
			lojas = (PersistentSet)LoginBackBean.getInstancia().getUsuario().getLojas();
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return lojas;
	}

	public SelectItem[] getLojas() {
		SelectItem[] arrayLojas = null;
		try {
			Set<Loja> lojas = carregarLojas();
			arrayLojas = new SelectItem[lojas.size()];
			int i = 0;
			for (Loja lojaTmp : lojas) {
				SelectItem item = new SelectItem(lojaTmp.getId().toString(),
						lojaTmp.getNome());
				arrayLojas[i++] = item;
			}

			if ((this.getIdLoja() == null || this.getIdLoja().equals("")
					|| this.getIdLoja().equals("0")) && arrayLojas.length > 0) {
				this.setIdLoja((String) arrayLojas[0].getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		if (this.idLoja == null && arrayLojas.length > 0) {
			this.idLoja = arrayLojas[0].getValue().toString();
		}
		return arrayLojas;
	}

	public Collection getEstoques() {
		return estoques;
	}

	public void setEstoques(Collection estoques) {
		this.estoques = estoques;
	}

	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
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
				setEstoques(null);
			}
		}
	}
}
