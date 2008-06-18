/**
 * 
 */
package com.infinity.datamarket.pdv.acumulador;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.Constantes;

/**
 * @author alessandro
 *
 */
public class CadastroAcumuladorNaoFiscal extends Cadastro {
	private static CadastroAcumuladorNaoFiscal instancia;
	private static Class CLASSE = AcumuladorNaoFiscal.class;
	private CadastroAcumuladorNaoFiscal(){}
	public static CadastroAcumuladorNaoFiscal getInstancia(){
		if (instancia == null){
			instancia = new CadastroAcumuladorNaoFiscal();
		}
		return instancia;
	}

	public AcumuladorNaoFiscal consultarPorId(Long id) throws AppException{
		return (AcumuladorNaoFiscal) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}

}
