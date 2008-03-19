package com.infinity.datamarket.enterprise.gui.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.navmenu.NavigationMenuItem;

import com.infinity.datamarket.comum.funcionalidade.Funcionalidade;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.usuario.Perfil;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class LoginBackBean extends BackBean{
	private Usuario usuario; 
	private String id;
	private String senha;
	private NavigationMenuItem[] navItens;
	private ArrayList subItens = new ArrayList();
	
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
		
		Set funcionalidadesTmp = usu.getPerfil().getFuncionalidades();
		
		for (Iterator iter = funcionalidadesTmp.iterator(); iter.hasNext();) {
			Funcionalidade element = (Funcionalidade) iter.next();
			if (element.getFuncionalidadeSuperior() == null) {
				System.out.print(element.getDescricao());
			}
		}
		
	}

	public void setNavItens(NavigationMenuItem[] navItens) {
		this.navItens = navItens;
	}
}
