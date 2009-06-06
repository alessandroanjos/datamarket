package com.infinity.datamarket.comum.usuario;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroLoja extends Cadastro{
	private static CadastroLoja instancia;
//	private static Class CLASSE = Loja.class;
	private CadastroLoja(){}
	public static CadastroLoja getInstancia(){
		if (instancia == null){
			instancia = new CadastroLoja();
		}
		return instancia;
	}
	
	
	public IRepositorioLoja getRepositorio() {
		return (IRepositorioLoja) super.getRepositorio(IRepositorio.REPOSITORIO_LOJA);
	}

	public Loja consultarPorId(Long id) throws AppException{
		return (Loja) getRepositorio().consultarPorId(id);
	}
	
	public void inserir(Loja loja) throws AppException{
		getRepositorio().inserir(loja);		
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public Loja consultarPorPK(Long id) throws AppException{
		return getRepositorio().consultarPorPK(id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	
	public void alterar(Loja loja) throws AppException{
		getRepositorio().alterar(loja);
	}
	
	public void excluir(Loja loja) throws AppException{
		getRepositorio().excluir(loja);
	}
}
