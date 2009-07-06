/**
 * 
 */
package com.infinity.datamarket.enterprise.gui.usuario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.collection.PersistentSet;

import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Perfil;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.usuario.Vendedor;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

/**
 * @author jonas
 *
 */
public class UsuarioBackBean extends BackBean {
	
	public static final String SIM = "S";
	public static final String NAO = "N";
	
	String id;
	String nome;
	Perfil perfil;
	String senha;
	String comissao;
	
	String idPerfil;
	
	Collection listaUsuarios;
	ArrayList listaLojasAssociadas;
		
	SelectItem[] perfis;
	SelectItem[] lojas;
	
	SelectItem[] tiposUsuario;
	String idTipoUsuario;
	
	String idLoja;
		
	public String getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(String idLoja) {
		this.idLoja = idLoja;
	}

	public String getIdTipoUsuario() {
		return idTipoUsuario;
	}

	public void setIdTipoUsuario(String idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}

	public SelectItem[] getTiposUsuario() {
		SelectItem[] tipos = new SelectItem[2];
		tipos[0] = new SelectItem(SIM,"Sim");
		tipos[1] = new SelectItem(NAO,"Não");		
		if(this.getIdTipoUsuario() == null){
			this.setIdTipoUsuario(NAO);
		}		
		return tipos;
	}

	public void setTiposUsuario(SelectItem[] tiposUsuario) {
		this.tiposUsuario = tiposUsuario;
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
	 * @return the idPerfil
	 */
	public String getIdPerfil() {
		return idPerfil;
	}

	/**
	 * @param idPerfil the idPerfil to set
	 */
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}

	/**
	 * @return the listaLojasAssociadas
	 */
	public ArrayList getListaLojasAssociadas() {
		return listaLojasAssociadas;
	}

	/**
	 * @param listaLojasAssociadas the listaLojasAssociadas to set
	 */
	public void setListaLojasAssociadas(ArrayList listaLojasAssociadas) {
		this.listaLojasAssociadas = listaLojasAssociadas;
	}

	/**
	 * @return the listaUsuarios
	 */
	public Collection getListaUsuarios() {
		return listaUsuarios;
	}

	/**
	 * @param listaUsuarios the listaUsuarios to set
	 */
	public void setListaUsuarios(Collection listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	/**
	 * @param lojas the lojas to set
	 */
	public void setLojas(SelectItem[] lojas) {
		this.lojas = lojas;
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
	 * @return the perfil
	 */
	public Perfil getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @param perfis the perfis to set
	 */
	public void setPerfis(SelectItem[] perfis) {
		this.perfis = perfis;
	}

	public String inserir(){
		
		try {
			Usuario usuario = null;
			if (this.getIdTipoUsuario().equals(SIM)){
				Vendedor vendedor = new Vendedor();
				if (this.comissao != null && !"".equals(this.comissao)){
					vendedor.setComissao(new BigDecimal(this.comissao));
				}else{
					vendedor.setComissao(new BigDecimal("0.00"));
				}
				usuario = vendedor;
			}else{
				usuario = new Usuario();
			}
			
			usuario.setNome(this.getNome());

			Perfil perfilTmp = getFachada().consultarPerfilPorPK(new Long(this.getIdPerfil()));
			usuario.setPerfil(perfilTmp);
			usuario.setSenha(this.getSenha());
			
			if(this.getListaLojasAssociadas() != null && this.getListaLojasAssociadas().size() > 0){
				ArrayList lista = this.getListaLojasAssociadas();
				Set<Loja> lojasTmp = new HashSet<Loja>(); 
				for (int i = 0; i < lista.size(); i++) {
					String idLoja = (String)lista.get(i);
					Loja loja = new Loja();
					loja.setId(new Long(idLoja));
					loja = getFachada().consultarLojaPorPK(loja.getId());
					lojasTmp.add(loja);					
				}
				usuario.setLojas(lojasTmp);
			}else{
				usuario.setLojas(null);	
			}
			
			if (getId()==null) usuario.setId(getIdInc(Usuario.class));
			
			getFachada().inserirUsuario(usuario);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!"+'\n'+
					"O código gerado foi : "+usuario.getId(), "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Usuário já Existente!", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", e.getMessage());
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
				Usuario usuario = getFachada().consultarUsuarioPorPK(new Long(getId()));
				if (usuario instanceof Vendedor){
					Vendedor vendedor = (Vendedor) usuario;
					if (vendedor.getComissao() != null){
						this.comissao = vendedor.getComissao().toString();
					}
					this.setIdTipoUsuario(SIM);
				}
				this.setId(usuario.getId().toString());
				this.setNome(usuario.getNome());
				this.setSenha(usuario.getSenha());

				this.setIdPerfil(usuario.getPerfil().getId().toString());
				
				if(this.getIdPerfil() != null && !this.getIdPerfil().equals("0")){
					Perfil perfilTmp = getFachada().consultarPerfilPorPK(new Long(this.getIdPerfil()));
					this.setPerfil(perfilTmp);
				}else{
					this.setPerfil(null);
				}
				
				Set lojasAssociadas = usuario.getLojas();
				List<String> listaTemp = new ArrayList<String>();
				
				for (Iterator iter = lojasAssociadas.iterator(); iter
						.hasNext();) {
					Loja lojaAssociada = (Loja) iter.next();
					listaTemp.add(lojaAssociada.getId().toString());
				}
				this.setListaLojasAssociadas((ArrayList)listaTemp);

				return "proxima";
			}
			
			Usuario usuTmp = null;
			
			if (this.getIdTipoUsuario().equals(SIM)){
				usuTmp = new Vendedor();
			}else{
				usuTmp = new Usuario();
			}
			
			if (getNome() != null && !"".equals(getNome())){	
				usuTmp.setNome(this.getNome());
			}
			
			if (getIdPerfil() != null && !"".equals(getIdPerfil()) && !"0".equals(getIdPerfil())){	
				Perfil perfil = new Perfil();
				perfil.setId(new Long(this.getIdPerfil()));
				usuTmp.setPerfil(perfil);
			}
			
			Collection col = getFachada().consultarUsuariosPorFiltro(usuTmp, this.getIdLoja());
			
//			PropertyFilter filter = new PropertyFilter();
//			if (getNome() != null && !"".equals(getNome())){	
//				filter.addProperty("nome", getNome());
//			}
//			if (getIdPerfil() != null && !"".equals(getIdPerfil()) && !"0".equals(getIdPerfil())){	
//				filter.addProperty("perfil.id", new Long(getIdPerfil()));
//			}
//			
//			if (this.getIdLoja() != null && !"".equals(this.getIdLoja()) && !"0".equals(this.getIdLoja())){
//				Loja loja = new Loja();
//				loja.setId(new Long(getIdLoja()));
//				filter.addProperty("loja", loja);
//			}
//			
//			if (this.getIdTipoUsuario().equals(SIM)){
//				filter.setTheClass(Vendedor.class);
//			}else{
//				filter.setTheClass(Usuario.class);
//			}
			
//			Collection col = getFachada().consultarUsuario(filter);
			
			if (col == null || col.size() == 0){
				setExisteRegistros(false);
				this.setListaUsuarios(null);
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Nenhum Registro Encontrado", "");
				ctx.addMessage(null, msg);					
			}else if (col != null){
				if(col.size() == 1){
					Usuario usuario = (Usuario)col.iterator().next();
					if (usuario instanceof Vendedor){
						Vendedor vendedor = (Vendedor) usuario;
						if (vendedor.getComissao() != null){
							this.comissao = vendedor.getComissao().toString();
						}
						this.setIdTipoUsuario(SIM);
					}
					this.setId(usuario.getId().toString());
					this.setNome(usuario.getNome());
					this.setSenha(usuario.getSenha());

					this.setIdPerfil(usuario.getPerfil().getId().toString());
					
					if(this.getIdPerfil() != null && !this.getIdPerfil().equals("0")){
						Perfil perfilTmp = getFachada().consultarPerfilPorPK(new Long(this.getIdPerfil()));
						this.setPerfil(perfilTmp);
					}else{
						this.setPerfil(null);
					}
					
					Set lojasAssociadas = usuario.getLojas();
					List<String> listaTemp = new ArrayList<String>();
					
					for (Iterator iter = lojasAssociadas.iterator(); iter
							.hasNext();) {
						Loja lojaAssociada = (Loja) iter.next();
						listaTemp.add(lojaAssociada.getId().toString());
					}
					this.setListaLojasAssociadas((ArrayList)listaTemp);

					return "proxima";
				}else{
					setExisteRegistros(true);
					setListaUsuarios(col);
				}
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setPerfis(null);
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
//		this.setId(null);
//		this.setNome(null);
		this.setSenha(null);
//		this.setVendedor(NAO);
		this.setComissao(null);
		this.setPerfil(null);
//		this.setIdPerfil(null);
		this.setListaLojasAssociadas(null);
		this.setLojas(null);
		return "mesma";
	}	
	
	public String alterar(){
		try {
			Usuario usuario = null;
			
			if (this.getIdTipoUsuario().equals(SIM)){
				Vendedor vendedor = new Vendedor();
				if (this.comissao != null && !"".equals(this.comissao)){
					vendedor.setComissao(new BigDecimal(this.comissao));
				}
				usuario = vendedor;
			}else{
				usuario = new Usuario();
			}
			usuario.setId(new Long(this.getId()));
			usuario.setNome(this.getNome());
			usuario.setSenha(this.getSenha());
			
			if(!this.getIdPerfil().equals("0")){
				Perfil perfil = getFachada().consultarPerfilPorPK(new Long(this.getIdPerfil()));
				usuario.setPerfil(perfil);
			}else{
				throw new Exception("É obrigatório selecionar um perfil.");
			}
			
			if(this.getListaLojasAssociadas() != null && this.getListaLojasAssociadas().size() > 0){
				ArrayList lista = this.getListaLojasAssociadas();
				Set<Loja> lojasTmp = new HashSet<Loja>(); 
				for (int i = 0; i < lista.size(); i++) {
					String idLoja = (String)lista.get(i);
					Loja loja = new Loja();
					loja.setId(new Long(idLoja));
					loja = getFachada().consultarLojaPorPK(loja.getId());
					lojasTmp.add(loja);					
				}
				usuario.setLojas(lojasTmp);
			}else{
				usuario.setLojas(null);	
			}
			
			
						
			getFachada().alterarUsuario(usuario);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro na alteração do Usuário!", e.getMessage());
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}
	
	public String excluir(){
		try {
			Usuario usuario = null;
			if (this.getIdTipoUsuario().equals(SIM)){
				usuario = new Vendedor();
				if (this.comissao != null && !"".equals(this.comissao)){
					((Vendedor)usuario).setComissao(new BigDecimal(this.comissao));
				}
			}else{
				usuario = new Usuario();
			}
			usuario.setId(new Long(this.getId()));
			
			usuario.setNome(this.getNome());
//
			usuario.setSenha(this.getSenha());
//			
			if (!this.getIdPerfil().equals("0")) {
				Perfil perfil = getFachada().consultarPerfilPorPK(
						new Long(this.getIdPerfil()));
				usuario.setPerfil(perfil);
			} else {
				usuario.setPerfil(null);
			}
					
			if(this.getListaLojasAssociadas() != null && this.getListaLojasAssociadas().size() > 0){
				ArrayList lista = this.getListaLojasAssociadas();
				Set<Loja> lojasTmp = new HashSet<Loja>(); 
				for (int i = 0; i < lista.size(); i++) {
					String idLoja = (String)lista.get(i);
					Loja loja = new Loja();
					loja.setId(new Long(idLoja));
					loja = getFachada().consultarLojaPorPK(loja.getId());
					lojasTmp.add(loja);					
				}
				usuario.setLojas(lojasTmp);
			}else{
				usuario.setLojas(null);	
			}
			
			getFachada().excluirUsuario(usuario);	
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", e.getMessage());
			ctx.addMessage(null, msg);
		}
		
		return "mesma";
	}
	
	public void resetBB(){
		this.setId(null);
		this.setNome(null);
		this.setSenha(null);
		this.setPerfil(null);
//		this.setIdPerfil(null);
		this.setListaLojasAssociadas(null);
		this.setLojas(null);
//		this.setListaUsuarios(null);
		this.setIdTipoUsuario(NAO);
		this.setComissao(null);
	}

	public String voltarConsulta(){
//		resetBB();
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}
	
	private List<Perfil> carregarPerfis() {
		
		List<Perfil> perfis = null;
		try {
			perfis = (ArrayList<Perfil>)getFachada().consultarTodosPerfil();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return perfis;
	}
	
	public SelectItem[] getPerfis(){
		SelectItem[] arrayPerfis = null;
		try {
			List<Perfil> perfis = carregarPerfis();
			arrayPerfis = new SelectItem[perfis.size()];
			int i = 0;
//			SelectItem itemBranco = new SelectItem("0", "");
//			arrayPerfis[i++] = itemBranco;
			for(Perfil perfilTmp : perfis){
				SelectItem item = new SelectItem(perfilTmp.getId().toString(), perfilTmp.getDescricao());
				arrayPerfis[i++] = item;
			}
			
			if(this.getIdPerfil() == null || this.getIdPerfil().equals("") || this.getIdPerfil().equals("0")){
				this.setIdPerfil((String) arrayPerfis[0].getValue());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayPerfis;
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
		SelectItem[] arrayLojasAssociadas = null;
		try {
			Set<Loja> lojas = carregarLojas();
			arrayLojasAssociadas = new SelectItem[lojas.size()];
			int i = 0;
			for(Loja lojasAssociadasTmp : lojas){
				SelectItem item = new SelectItem(lojasAssociadasTmp.getId().toString(), lojasAssociadasTmp.getNome());
				arrayLojasAssociadas[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayLojasAssociadas;
	}

	public String getComissao() {
		return comissao;
	}

	public void setComissao(String comissao) {
		this.comissao = comissao;
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
				setListaUsuarios(null);
			}
		}
	}

}