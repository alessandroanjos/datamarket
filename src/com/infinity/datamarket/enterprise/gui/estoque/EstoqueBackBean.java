/**
 * 
 */
package com.infinity.datamarket.enterprise.gui.estoque;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.EstoquePK;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
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

	public String inserir() {
		Estoque estoque = new Estoque();
		EstoquePK pk = new EstoquePK();
		
		pk.setId(new Long(this.id));
		estoque.setDescricao(this.descricao);
		
		Loja loja = new Loja();
		loja.setId(new Long(this.idLoja));
		pk.setLoja(loja);
		
		estoque.setPk(pk);

		try {
			getFachada().inserirEstoque(estoque);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Estoque já Existente!", "");
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
			if (getId() != null && !"".equals(getId()) && getIdLoja() != null && !"".equals(getIdLoja())) {
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
			} else if (getDescricao() != null && !"".equals(getDescricao())) {
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(Estoque.class);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarEstoque(filter);
				if (col == null || col.size() == 0) {
					this.setEstoques(col);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);
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
				setEstoques(getFachada().consultarTodosEstoques());
			}
		} catch (ObjectNotFoundException e) {
			this.setEstoques(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);
			setExisteRegistros(false);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
			setExisteRegistros(false);
		}
		return "mesma";
	}

	public String alterar() {
		try {
			Estoque estoque = new Estoque();
			EstoquePK pk = new EstoquePK();
			
			pk.setId(new Long(this.id));
			estoque.setDescricao(this.descricao);
			
			Loja loja = new Loja();
			loja.setId(new Long(this.idLoja));
			pk.setLoja(loja);
			
			estoque.setPk(pk);
			
			getFachada().alterarEstoque(estoque);
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

	public String resetBB() {
		this.setId(null);
		this.setDescricao(null);
		this.setIdLoja(null);
		return "mesma";
	}

	private List<Loja> carregarLojas() {

		List<Loja> lojas = null;
		try {
			lojas = (ArrayList<Loja>) getFachada().consultarTodosLoja();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return lojas;
	}

	public SelectItem[] getLojas() {
		SelectItem[] arrayLojas = null;
		try {
			List<Loja> lojas = carregarLojas();
			arrayLojas = new SelectItem[lojas.size()];
			int i = 0;
			for (Loja lojaTmp : lojas) {
				SelectItem item = new SelectItem(lojaTmp.getId().toString(),
						lojaTmp.getNome());
				arrayLojas[i++] = item;
			}

			if (this.getIdLoja() == null || this.getIdLoja().equals("")
					|| this.getIdLoja().equals("0")) {
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
}
