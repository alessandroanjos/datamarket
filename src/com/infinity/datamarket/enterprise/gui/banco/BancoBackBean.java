package com.infinity.datamarket.enterprise.gui.banco;

/**
 * 
 */
import java.util.Collection;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;

import com.infinity.datamarket.comum.banco.Banco;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

/**
 * @author alessandro
 * 
 */
public class BancoBackBean extends BackBean {
	private String id;

	private String descricao;

	private String situacao;
	
	private Collection<Banco> bancos;

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


	public String inserir() {
		try {
			Banco banco = new Banco();
			
			banco.setId(new Long(this.id));
			banco.setDescricao(this.descricao);

			getFachada().inserirBanco(banco);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Forma de recebimento já Existente!", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}

	public String consultar() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();
			String param = (String) params.get("id");
			if (param != null && !"".equals(param)) {
				setId(param);
			}
			if (getId() != null && !"".equals(getId())) {
				Banco banco = getFachada()
						.consultarBancoPorID(getId());
				this.setId(banco.getId().toString());
				this.setDescricao(banco.getDescricao());


				return "proxima";
			} else if (getDescricao() != null && !"".equals(getDescricao())) {
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Banco.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarBanco(filter);
				if (col == null || col.size() == 0) {
					setExisteRegistros(false);
					this.setBancos(col);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);
					this.setExisteRegistros(false);
				} else if (col != null) {
					if (col.size() == 1) {
						Banco banco = (Banco) col
								.iterator().next();
						this.setId(banco.getId().toString());
						this.setDescricao(banco.getDescricao());
						
						return "proxima";
					} else {
						setExisteRegistros(true);
						this.setBancos(col);
					}
				}
			} else {
				Collection c = getFachada().consultarTodosBancos();
				if (c != null && c.size() > 0){
					this.setExisteRegistros(true);
				}else{
					this.setExisteRegistros(false);
				}				
				setBancos(c);
			}
		} catch (ObjectNotFoundException e) {
			this.setExisteRegistros(false);
			this.setBancos(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			this.setExisteRegistros(false);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}

		return "mesma";
	}

	public String alterar() {
		try {
			Banco banco = new Banco();

			banco.setId(new Long(this.id));
			banco.setDescricao(this.descricao);


			getFachada().alterarBanco(banco);
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

	public String excluir() {
		try {
			Banco banco = new Banco();

			banco.setId(new Long(this.id));
			banco.setDescricao(this.descricao);
			getFachada().excluirBanco(banco);
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

	public void resetBB() {
		this.setId(null);
		this.setDescricao(null);
		this.setSituacao(Constantes.STATUS_ATIVO);
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
				setBancos(null);
			}
		}
	}


	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Collection getBancos() {
		return bancos;
	}

	public void setBancos(Collection bancos) {
		this.bancos = bancos;
	}
}
