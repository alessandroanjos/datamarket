package com.infinity.datamarket.comum.produto;

import java.util.Collection;
import java.util.List;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.ObjetoInexistenteException;

public class CadastroProduto extends Cadastro{
	private static CadastroProduto instancia;
	private static Class CLASSE = Produto.class;
	private CadastroProduto(){}
	public static CadastroProduto getInstancia(){
		if (instancia == null){
			instancia = new CadastroProduto();
		}
		return instancia;
	}

	public Produto consultarPorId(Long id) throws AppException{
		return (Produto) getRepositorio().findById(CLASSE, id);
	}

	public Produto consultarPorCodigoExterno(String codigo) throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addProperty("codigoExterno", codigo);
		filter.setIgnoreCase(true);
		List l = getRepositorio().filter(filter, true);
		if (l.size() > 0){
			return (Produto) l.iterator().next();
		}else{
			throw new ObjetoInexistenteException("Produto Inexistente");
		}
	}

	public Collection consultarPorFiltro(IPropertyFilter filter, boolean preciso) throws AppException{
		filter.setTheClass(CLASSE);
		List l = getRepositorio().filter(filter, preciso);
		return l;
	}

}
