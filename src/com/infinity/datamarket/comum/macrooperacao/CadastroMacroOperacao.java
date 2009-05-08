/**
 * 
 */
package com.infinity.datamarket.comum.macrooperacao;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.usuario.Perfil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;
import com.infinity.datamarket.pdv.maquinaestados.MacroOperacao;

/**
 * @author jonas
 *
 */
public class CadastroMacroOperacao extends Cadastro {

	private static CadastroMacroOperacao instancia;
	private static Class CLASSE = MacroOperacao.class;
	private CadastroMacroOperacao(){}
	public static CadastroMacroOperacao getInstancia(){
		if (instancia == null){
			instancia = new CadastroMacroOperacao();
		}
		return instancia;
	}


	public IRepositorioMacroOperacao getRepositorio() {
		return (IRepositorioMacroOperacao) super.getRepositorio(IRepositorio.REPOSITORIO_MACRO_OPERACAO);
	}

	public MacroOperacao consultarPorId(Long id) throws AppException{
		return (MacroOperacao) getRepositorio().consultarPorId(id);
	}
	
	public void inserir(MacroOperacao macroOperacao) throws AppException{
		getRepositorio().inserir(macroOperacao);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public MacroOperacao consultarPorPK(Long id) throws AppException{
		return (MacroOperacao) getRepositorio().consultarPorPK(id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	
	public void alterar(MacroOperacao macroOperacao) throws AppException{
		getRepositorio().alterar(macroOperacao);
	}
	
	public void excluir(MacroOperacao macroOperacao) throws AppException{
		getRepositorio().excluir(macroOperacao);
	}
	
}
