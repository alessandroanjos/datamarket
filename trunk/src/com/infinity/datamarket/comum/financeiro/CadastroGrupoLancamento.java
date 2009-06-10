package com.infinity.datamarket.comum.financeiro;

import java.util.Collection;

import com.infinity.datamarket.comum.financeiro.GrupoLancamento;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroGrupoLancamento extends Cadastro{
	private static CadastroGrupoLancamento instancia;
	private CadastroGrupoLancamento(){}
	public static CadastroGrupoLancamento getInstancia(){
		if (instancia == null){
			instancia = new CadastroGrupoLancamento();
		}
		return instancia;
	}
	
	
	public IRepositorioGrupoLancamento getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioGrupoLancamento) super.getRepositorio(IRepositorio.REPOSITORIO_GRUPO_LANCAMENTO);
	}

	public GrupoLancamento consultarPorId(Long id) throws AppException{
		return (GrupoLancamento) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(GrupoLancamento grupo) throws AppException{
		if (grupo.getId().equals(GrupoLancamento.GRUPO_VENDA) || grupo.getId().equals(GrupoLancamento.GRUPO_ENTRADA_PRODUTO)){
			throw new AppException("Código de Grupo de Lançamento Reservado");
		} 
		getRepositorio().inserir(grupo);
	}

	public void alterar(GrupoLancamento grupo) throws AppException{
		getRepositorio().alterar(grupo);
	}

	public void excluir(GrupoLancamento grupo) throws AppException{
		getRepositorio().excluir(grupo);
	}

}
