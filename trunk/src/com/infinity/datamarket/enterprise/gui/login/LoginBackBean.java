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
	private String id;
	private String senha;
	private NavigationMenuItem[] navItens;


	public String logar(){
		try{
			Usuario usu = getFachada().loginUsuario(new Long(id), senha);
			if (usu == null){
				FacesContext ctx = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Usu�rio e/ou Senha inv�lido(s).","");
				ctx.addMessage(null, msg);
				resetBB();
				return "erro";
			}
			setUsuario(usu);
			setNavItems(usu);
		}catch(ObjectNotFoundException e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Usu�rio e/ou Senha inv�lido(s).","");
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


		Object[] funcSuperior = buscaFuncionalidadeSuperiores(usu); 
		NavigationMenuItem[] navigationMenus = new NavigationMenuItem[funcSuperior.length];
		for (int i = 0; i < funcSuperior.length; i++) {
			Funcionalidade func = (Funcionalidade)funcSuperior[i];
			navigationMenus[i] = new NavigationMenuItem(func.getDescricao(),func.getUrl(),null,false);
			navigationMenus[i].setNavigationMenuItems(montaMenu(func));
		}
		setNavItens(navigationMenus);		
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
				Collection funcFilha = getFachada().consultarFuncionalidadesPorFuncionalidadeSuperior(funcAux);
				funcAux.setFuncionalidadesFilhas(funcFilha);
				NavigationMenuItem subMenuAux = new NavigationMenuItem(funcAux.getDescricao(),funcAux.getUrl(),null,false);
				if (funcAux.getFuncionalidadesFilhas() != null) {
					subMenuAux.setNavigationMenuItems(montaMenu(funcAux));
				}
				subMenu.add(subMenuAux);
			}


		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return subMenu;
	}

	public void setNavItens(NavigationMenuItem[] navItens) {
		this.navItens = navItens;
	}



	public NavigationMenuItem[] getNavItens() {
		return navItens;
	}




}

