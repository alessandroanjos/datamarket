package com.infinity.datamarket.enterprise.gui.grupoLancamento;

import java.util.Collection;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;

import com.infinity.datamarket.comum.financeiro.GrupoLancamento;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class GrupoLancamentoBackBean extends BackBean {

	private String descricao;
	private String tipoRegistro;
	private String id;

	Collection grupoLancamentos;

	public String voltarConsulta(){
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}


	public String inserir(){
		
		try {
			validarGrupoLancamento();

			GrupoLancamento grupo = new GrupoLancamento();

			if (getId()==null) grupo.setId(getIdInc(GrupoLancamento.class));

			grupo.setDescricao(this.descricao);
			grupo.setTipoRegistro(GrupoLancamento.REGISTRO_USUARIO);
//			grupo.setTipoRegistro(this.tipoRegistro);

			getFachada().inserirGrupoLancamento(grupo);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Opera��o Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Grupo de Lan�amento j� Existente!", "");
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
				GrupoLancamento grupo = getFachada().consultarGrupoLancamentoPorId(new Long(getId()));
				this.setId(grupo.getId().toString());
				this.setDescricao(grupo.getDescricao());
				this.setTipoRegistro(grupo.getTipoRegistro());
				return "proxima";
			}else if (getDescricao() != null && !"".equals(getDescricao())){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(GrupoLancamento.class);
				filter.addProperty("tipoRegistro", GrupoLancamento.REGISTRO_USUARIO);
				filter.addProperty("descricao", getDescricao());
				Collection col = getFachada().consultarFormaRecebimento(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
					this.setGrupoLancamentos(col);
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);
				}else if (col != null){
					if(col.size() == 1){
						GrupoLancamento grupo = getFachada().consultarGrupoLancamentoPorId(new Long(getId()));
						this.setId(grupo.getId().toString());
						this.setDescricao(grupo.getDescricao());
						this.setTipoRegistro(grupo.getTipoRegistro());
						return "proxima";
					}else{
						setExisteRegistros(true);
						this.setGrupoLancamentos(col);
					}
				}
			}else{
				Collection c = getFachada().consultarTodosGrupoLancamentos();
				if(c != null && c.size() > 0){
					setExisteRegistros(true);
					setGrupoLancamentos(c);
				}else{
					setGrupoLancamentos(null);
					setExisteRegistros(false);
					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					getContextoApp().addMessage(null, msg);					
				}
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setGrupoLancamentos(null);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			getContextoApp().addMessage(null, msg);
		}catch(Exception e){
			setExisteRegistros(false);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			getContextoApp().addMessage(null, msg);
		}
		return "mesma";
	}

	public String alterar(){
		try {
			validarGrupoLancamento();
			
			GrupoLancamento grupo = new GrupoLancamento();

			grupo.setId(new Long(this.id));
			grupo.setDescricao(this.descricao);
			grupo.setTipoRegistro(GrupoLancamento.REGISTRO_USUARIO);
//			grupo.setTipoRegistro(this.tipoRegistro);

			getFachada().alterarGrupoLancamento(grupo);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Opera��o Realizada com Sucesso!", "");
			getContextoApp().addMessage(null, msg);
			resetBB();
		} catch (ObjectNotFoundException e) {
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Grupo de Lan�amento n�o encontrado!", "");
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

	public String excluir(){
		try {
			GrupoLancamento grupo = new GrupoLancamento();

			grupo.setId(new Long(this.id));
			grupo.setDescricao(this.descricao);
			grupo.setTipoRegistro(this.tipoRegistro);

			getFachada().excluirGrupoLancamento(grupo);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Opera��o Realizada com Sucesso!", "");
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

	public void resetBB(){
		this.setId(null);
		this.setDescricao(null);
//		this.setTipoRegistro(GrupoLancamento.REGISTRO_USUARIO);
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
	 * @return the tipo
	 */
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
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
	 * @return the grupoLancamentos
	 */
	public Collection getGrupoLancamentos() {
		return grupoLancamentos;
	}
	/**
	 * @param grupos the grupos to set
	 */
	public void setGrupoLancamentos(Collection grupos) {
		this.grupoLancamentos = grupos;
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
				setGrupoLancamentos(null);
			}
		}
	}
	
	public void validarGrupoLancamento() throws AppException {
		if(this.getDescricao() == null || this.getDescricao().equals("")){
			throw new AppException("O Campo Descri��o � obrigat�rio!");
		}
	}
}
