package com.infinity.datamarket.enterprise.gui.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlForm;

import org.apache.myfaces.custom.navmenu.NavigationMenuItem;
import org.hibernate.JDBCException;

import com.infinity.datamarket.comum.funcionalidade.Funcionalidade;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class LoginBackBean extends BackBean{
	private Usuario usuario;
	private Usuario usuarioLogado;
	private String id;
	private String senha;
	private NavigationMenuItem[] navItens;
	
	private static String pathAplicacao = "";
	
	private static LoginBackBean instancia;

	public String logar(){  
		try{			
			if(this.getId() == null || this.getId().equals("")){
			   throw new AppException("Informe seu Login.");	
			}
			if(this.getSenha() == null || this.getSenha().equals("")){
			   throw new AppException("Informe sua Senha.");	
			}
			
//			pathAplicacao = getFachada().consultarURLApp();
			
			pathAplicacao = getContextoApp().getExternalContext().getRequestContextPath();
			
			Usuario usu = getFachada().loginUsuario(new Long(id), senha);
			if (usu == null){
				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Usuário e/ou Senha inválido(s).","");
				getContextoApp().addMessage(null, msg);
//				resetBB();
				return "erro";
			}
			setUsuario(usu);
			setNavItems(usu);
			instancia = this;
		}catch(ObjectNotFoundException e){
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Usuário e/ou Senha inválido(s).","");
			getContextoApp().addMessage(null, msg);
//			resetBB();
			return "erro";
		}catch(AppException e){
			
			String msgErro = e.getMessage();
			if(e.getCause() instanceof JDBCException){
				msgErro = "Erro de comunicação com o Banco de Dados.";
			}
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					msgErro,"");
			getContextoApp().addMessage(null, msg);
//			resetBB();
			return "erro";
		}
		setId(null);
		setSenha(null);
		return "logado";
	}

	public void resetBB(){
		this.id = null;
		this.senha = null;
		this.usuario = null;
		this.navItens = null;
	}

	public String logout(){
		resetBB();
		setCodigoUsuarioLogado(null);
		this.setUsuario(null);		
		return "logout";
	}

	public Usuario getUsuario() {
		usuario = (Usuario) RepositoryManagerHibernateUtil.getInstancia().currentSession().load(usuario.getClass().getName(), usuario.getId());
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setNavItems(Usuario usu) {

		if ((navItens==null)||usuarioLogado==null||(!usu.getId().equals(usuarioLogado.getId()))) {
			
			Object[] funcSuperior = buscaFuncionalidadeSuperiores(usu); 
			
			NavigationMenuItem[] navigationMenus = new NavigationMenuItem[funcSuperior.length];
			
			for (int i = 0; i < funcSuperior.length; i++) {
			
				Funcionalidade func = (Funcionalidade)funcSuperior[i];
				navigationMenus[i] = new NavigationMenuItem(func.getDescricao(),func.getUrl());
				navigationMenus[i].setNavigationMenuItems(montaMenu(func));
			
			}
			setCodigoUsuarioLogado(usuario.getId().toString());
			setUsuarioLogado(usuario);
			setNavItens(navigationMenus);
			
		}	
	}

	private Object[] buscaFuncionalidadeSuperiores(Usuario usu) {

		Collection<Funcionalidade> listaSuperior = new ArrayList<Funcionalidade>();
		Iterator funcionalidades = usu.getPerfil().getFuncionalidades().iterator();

		while (funcionalidades.hasNext()) {

			Funcionalidade element = (Funcionalidade) funcionalidades.next();
			
			if (element.getFuncionalidadeSuperior()==null) {
			
				listaSuperior.add(element);
			
			}
			
		}

		return  listaSuperior.toArray();
	}


	public ArrayList montaMenu(Funcionalidade func){

		Iterator listaAux;

		ArrayList<NavigationMenuItem> subMenu = new ArrayList<NavigationMenuItem>();

		try {
			
			listaAux = getFachada().consultarFuncionalidadesPorFuncionalidadeSuperior(func).iterator();
			
			while (listaAux.hasNext()) {

				Funcionalidade funcAux = (Funcionalidade) listaAux.next();
                
				if (!checkPermissao(funcAux)) {
				   	continue;
                }
				
				Collection funcFilha = getFachada().consultarFuncionalidadesPorFuncionalidadeSuperior(funcAux);
				
				funcAux.setFuncionalidadesFilhas(funcFilha);
				
				NavigationMenuItem subMenuAux = null;
				if(funcAux.getUrl() == null){
					subMenuAux = new NavigationMenuItem(funcAux.getDescricao(),funcAux.getUrl());
				}else{
					if(funcAux.getId().equals(new Long(999))){
						subMenuAux = new NavigationMenuItem(funcAux.getDescricao(),"/EnterpriseServer/jsp/"+funcAux.getUrl()+".faces");
					}else{
						subMenuAux = new NavigationMenuItem(funcAux.getDescricao(),"javascript:window.open(\""+
//										pathAplicacao+"/EnterpriseServer/jsp/"+funcAux.getUrl()+
										pathAplicacao+"/jsp/"+funcAux.getUrl()+
										".faces?"+ACAO+"="+VALOR_ACAO+																				
										"\",\"\",\"top=0,left=0,status=yes,toolbar=no,menubar=no,location=no,scrollbars=auto,resizable,resize=yes,height=" +
										funcAux.getAltura() + ",width=" + funcAux.getLargura() + "\")");
//						System.out.println(subMenuAux.getAction());
					}					
				}
				
				if (funcAux.getFuncionalidadesFilhas() != null) {
				
					subMenuAux.setNavigationMenuItems(montaMenu(funcAux));
					
				}
				subMenuAux.setTarget("_blank");
				
				subMenu.add(subMenuAux);
	
			}


		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return subMenu;
		
	}
    
	public boolean checkPermissao(Funcionalidade func){
		
		Iterator<Funcionalidade> funcisUsuario = this.usuario.getPerfil().getFuncionalidades().iterator();
		
		while (funcisUsuario.hasNext()) {
			
			Funcionalidade element = (Funcionalidade) funcisUsuario.next();

			if (func.getId().longValue()==element.getId().longValue()) {
				return true;
			}
			
		}
		return false;
	}
	
	public void setNavItens(NavigationMenuItem[] navItens) {
		this.navItens = navItens;
	}



	public NavigationMenuItem[] getNavItens() {
		return navItens;
	}

	public Usuario getUsuarioLogado() {
		usuarioLogado = (Usuario) RepositoryManagerHibernateUtil.getInstancia().currentSession().load(usuarioLogado.getClass().getName(), usuarioLogado.getId());
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public void setInit(HtmlForm init) {
//		try {
//			FacesContext context = FacesContext.getCurrentInstance();
//			HttpSession sessaoAtual = (HttpSession)context.getExternalContext().getSession(false);
//			if(sessaoAtual != null){
//				sessaoAtual.invalidate();
//			}
//		} catch (Throwable t) {
//			t.printStackTrace();
//		}
		logout();
	}
	
	public static LoginBackBean getInstancia(){
		if(instancia == null){
			instancia = new LoginBackBean();
		}
		return instancia;
	}
}