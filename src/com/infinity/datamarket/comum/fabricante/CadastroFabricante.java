/**
 * 
 */
package com.infinity.datamarket.comum.fabricante;

import java.util.Collection;

import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

/**
 * @author alessandro
 *
 */
public class CadastroFabricante extends Cadastro {
	private static CadastroFabricante instancia;
	private CadastroFabricante(){}
	public static CadastroFabricante getInstancia(){
		if (instancia == null){
			instancia = new CadastroFabricante();
		}
		return instancia;
	}
	
	public IRepositorioFabricante getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioFabricante) super.getRepositorio(IRepositorio.REPOSITORIO_FABRICANTE);
	}
	
	public Fabricante consultarPorId(Long id) throws AppException{
		return (Fabricante) getRepositorio().consultarPorId(id);
	}
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(Fabricante fabricante) throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(Fabricante.class);
		filter.addProperty("cpfCnpj", fabricante.getCpfCnpj());
		Collection c = getRepositorio().consultar(filter);
		if (c != null && c.size() > 0){
			throw new AppException("Fabricante com mesmo CPF/CNPJ já cadastrado");
		}
		getRepositorio().inserir(fabricante);
		
	}
	public void alterar(Fabricante fabricante) throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(Fabricante.class);
		filter.addProperty("cpfCnpj", fabricante.getCpfCnpj());
		Collection c = getRepositorio().consultar(filter);
		if (c != null && c.size() > 0){
			throw new AppException("Fabricante com mesmo CPF/CNPJ já cadastrado");
		}
		getRepositorio().alterar(fabricante);
	}
	public void excluir(Fabricante fabricante) throws AppException{
		getRepositorio().excluir(fabricante);
	}	
}
