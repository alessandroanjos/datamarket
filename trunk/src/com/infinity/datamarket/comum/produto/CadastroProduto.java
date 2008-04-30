package com.infinity.datamarket.comum.produto;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
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

	public Produto consultarPorPK(Long id) throws AppException{
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
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Produto produto) throws AppException{
		getRepositorio().insert(produto);
		inserirDadoLote(produto);
	}
	
	public void alterar(Produto produto) throws AppException{
		getRepositorio().update(produto);
		alterarDadoLote(produto);
	}
	
	public void excluir(Produto produto) throws AppException{
		getRepositorio().remove(produto);
		excluirDadoLote(produto);
	}

	public Collection consultaProdutosPorSecao(String idLoja) throws AppException{
		return null;
	}

}
