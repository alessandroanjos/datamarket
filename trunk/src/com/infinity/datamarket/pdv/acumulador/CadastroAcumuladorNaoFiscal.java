/**
 * 
 */
package com.infinity.datamarket.pdv.acumulador;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

/**
 * @author alessandro
 *
 */
public class CadastroAcumuladorNaoFiscal extends Cadastro {
	private static CadastroAcumuladorNaoFiscal instancia;
//	private static Class CLASSE = AcumuladorNaoFiscal.class;
	private CadastroAcumuladorNaoFiscal(){}
	public static CadastroAcumuladorNaoFiscal getInstancia(){
		if (instancia == null){
			instancia = new CadastroAcumuladorNaoFiscal();
		}
		return instancia;
	}
	
	private IRepositorioAcumuladorNaoFiscal getRepositorio(){
		return (IRepositorioAcumuladorNaoFiscal) getRepositorio(IRepositorio.REPOSITORIO_ACUMULADOR_NAO_FISCAL);
	}

	public AcumuladorNaoFiscal consultarPorId(Long id) throws AppException{
		return (AcumuladorNaoFiscal) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}

}
