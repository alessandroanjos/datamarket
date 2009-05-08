package com.infinity.datamarket.autorizador;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.usuario.IRepositorioPerfil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroAutorizacaoCartaoProprio extends Cadastro {
	private static CadastroAutorizacaoCartaoProprio instancia;
	private static Class CLASSE = AutorizacaoCartaoProprio.class;
	private CadastroAutorizacaoCartaoProprio(){}
	
	public static CadastroAutorizacaoCartaoProprio getInstancia(){
		if (instancia == null){
			instancia = new CadastroAutorizacaoCartaoProprio();
		}
		return instancia;
	}
	
	private IRepositorioAutorizacaoCartaoProprio getRepositorio(){
		return (IRepositorioAutorizacaoCartaoProprio) getRepositorio(IRepositorio.REPOSITORIO_AUTORIZACAO_CARTAO_PROPRIO);
	}
	
	public AutorizacaoCartaoProprio consultarPorId(Long id) throws AppException{
		return getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
		
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(AutorizacaoCartaoProprio autorizacao) throws AppException{
		getRepositorio().inserir(autorizacao);
	}
	
	public void alterar(AutorizacaoCartaoProprio autorizacao) throws AppException{
		getRepositorio().alterar(autorizacao);
	}
	
	public void excluir(AutorizacaoCartaoProprio autorizacao) throws AppException{
		getRepositorio().excluir(autorizacao);
	}
}
