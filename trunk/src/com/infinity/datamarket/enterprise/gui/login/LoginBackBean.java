package com.infinity.datamarket.enterprise.gui.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.navmenu.NavigationMenuItem;

import com.infinity.datamarket.comum.funcionalidade.Funcionalidade;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class LoginBackBean extends BackBean{
	private Usuario usuario;
	private Usuario usuarioLogado;
	private String id;
	private String senha;
	private NavigationMenuItem[] navItens;
    

	public String logar(){
		try{
			Usuario usu = getFachada().loginUsuario(new Long(id), senha);
			if (usu == null){
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Usuário e/ou Senha inválido(s).","");
				ctx.addMessage(null, msg);
				resetBB();
				return "erro";
			}
			setUsuario(usu);
			setNavItems(usu);
		}catch(ObjectNotFoundException e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Usuário e/ou Senha inválido(s).","");
			ctx.addMessage(null, msg);
			resetBB();
			return "erro";
		}catch(AppException e){
			resetBB();
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
		return "logout";
	}



	public Usuario getUsuario() {
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

		if ((navItens==null)||(!usu.getId().equals(usuarioLogado.getId()))) {
			
			Object[] funcSuperior = buscaFuncionalidadeSuperiores(usu); 
			
			NavigationMenuItem[] navigationMenus = new NavigationMenuItem[funcSuperior.length];
			
			for (int i = 0; i < funcSuperior.length; i++) {
			
				Funcionalidade func = (Funcionalidade)funcSuperior[i];
				navigationMenus[i] = new NavigationMenuItem(func.getDescricao(),func.getUrl());
				navigationMenus[i].setNavigationMenuItems(montaMenu(func));
			
			}
			
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
				
				Collection funcFilha   =  getFachada().consultarFuncionalidadesPorFuncionalidadeSuperior(funcAux);
				
				funcAux.setFuncionalidadesFilhas(funcFilha);
				
				NavigationMenuItem subMenuAux = null;
				if(funcAux.getUrl() == null){
					subMenuAux = new NavigationMenuItem(funcAux.getDescricao(),funcAux.getUrl());
				}else{
					subMenuAux = new NavigationMenuItem(funcAux.getDescricao(),getFachada().consultarURLApp()+"/EnterpriseServer/jsp/"+funcAux.getUrl()+".faces");
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
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}




}

