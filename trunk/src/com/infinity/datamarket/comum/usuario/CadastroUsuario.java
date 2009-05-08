package com.infinity.datamarket.comum.usuario;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.AutorizacaoRecusadaException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.pdv.maquinaestados.MacroOperacao;

public class CadastroUsuario extends Cadastro{
	private static CadastroUsuario instancia;
	private static Class CLASSE = Usuario.class;
	private CadastroUsuario(){}
	
	public static CadastroUsuario getInstancia(){
		if (instancia == null){
			instancia = new CadastroUsuario();
		}
		return instancia;
	}
	
	
	public IRepositorioUsuario getRepositorio() {
		return (IRepositorioUsuario) super.getRepositorio(IRepositorio.REPOSITORIO_USUARIO);
	}

	public Usuario consultarPorId(Long id) throws AppException{
		return (Usuario) getRepositorio().consultarPorId(id);
	}

	public void inserir(Usuario usuario) throws AppException{
		getRepositorio().inserir(usuario);		
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public Usuario consultarPorPK(Long id) throws AppException{
		return getRepositorio().consultarPorPK(id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	
	public void alterar(Usuario usuario) throws AppException{
		getRepositorio().alterar(usuario);
	}
	
	public void excluir(Usuario usuario) throws AppException{
		getRepositorio().excluir(usuario);
	}

	public Usuario consultarPorId_IdMacro(Long id,Long idMacro) throws AppException{
		Usuario usu = (Usuario) getRepositorio().consultarPorId(id);
		if (verificaAutorizacao(usu, idMacro)){
			return usu;
		}else{
			throw new AutorizacaoRecusadaException("Autorizacao Recusada");
		}
	}

	private boolean verificaAutorizacao(Usuario usu, Long idMacro){
		Iterator i = usu.getPerfil().getOperacoes().iterator();
		while(i.hasNext()){
			MacroOperacao mo = (MacroOperacao) i.next();
			if (idMacro.equals(mo.getId())){
				return true;
			}
		}
		return false;
	}

	public Usuario login(Long id, String senha) throws AppException{
		Usuario usu = (Usuario) getRepositorio().consultarPorId(id);
		if (usu.getSenha().equals(senha)){
			return usu;
		}
		return null;
	}
	
	public Collection consultarUsuariosPorPerfil(Perfil perfil)  throws AppException{
		
		Collection col = null;
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addProperty("perfil.id", perfil.getId());
		
		col = this.consultar(filter);
				
		return col;
	}
	

}