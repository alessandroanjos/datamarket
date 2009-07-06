package com.infinity.datamarket.comum.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioUsuario extends Repositorio implements IRepositorioUsuario{
	private static RepositorioUsuario instancia;
	private static Class CLASSE = Usuario.class;
	private RepositorioUsuario(){}
	
	public static RepositorioUsuario getInstancia(){
		if (instancia == null){
			instancia = new RepositorioUsuario();
		}
		return instancia;
	}

	public Usuario consultarPorId(Long id) throws AppException{
		return (Usuario) findById(CLASSE, id);
	}

	public void inserir(Usuario usuario) throws AppException{
		insert(usuario);
		inserirDadoLote(usuario);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
	public Usuario consultarPorPK(Long id) throws AppException{
		return (Usuario) findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return findAll(CLASSE);
	}
	
	public void alterar(Usuario usuario) throws AppException{
		update(usuario);
		alterarDadoLote(usuario);
	}
	
	public void excluir(Usuario usuario) throws AppException{
		remove(usuario);
		excluirDadoLote(usuario);
	}

	public Usuario consultarPorId_IdMacro(Long id) throws AppException{
		return (Usuario) findById(CLASSE, id);		
	}
	
	public Collection consultarUsuariosPorFiltro(Usuario usuario, String idLoja) throws AppException{
		Collection<Usuario> col = new ArrayList<Usuario>();
		Session session = RepositoryManagerHibernateUtil.currentSession();
		StringBuffer sqlSetence = new StringBuffer();
		sqlSetence.append("SELECT u.* from USUARIO u, USUARIO_LOJA ul ");
		sqlSetence.append("WHERE u.id = ul.id_usuario ");
		if(usuario.getNome() != null){
			sqlSetence.append("AND UPPER(u.nome) LIKE '%" + usuario.getNome().toUpperCase() + "%' ");	
		}		
		if(usuario.getPerfil() != null){
			sqlSetence.append("AND u.id_perfil = " + usuario.getPerfil().getId() + " ");	
		}		
		sqlSetence.append("AND ul.id_loja = " + idLoja);
		sqlSetence.append(" ORDER BY u.ID");
		SQLQuery query = session.createSQLQuery(sqlSetence.toString());
		List result = query.list();
		if(result != null && result.size() > 0){
			Iterator it = result.iterator();
			while(it.hasNext()){
				Object[] obj = (Object[])it.next();
				if(obj != null){
					col.add((Usuario)session.get(Usuario.class, new Long((Integer)obj[0])));
				}
			}
		}		
		return col;
	}
}
