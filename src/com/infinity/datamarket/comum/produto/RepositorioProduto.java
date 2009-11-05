package com.infinity.datamarket.comum.produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ObjetoInexistenteException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioProduto extends Repositorio implements IRepositorioProduto{
	private static RepositorioProduto instancia;
	private static Class CLASSE = Produto.class;
	private RepositorioProduto(){}
	public static RepositorioProduto getInstancia(){
		if (instancia == null){
			instancia = new RepositorioProduto();
		}
		return instancia;
	}

	public Produto consultarPorPK(Long id) throws AppException{
		Produto produto = (Produto) findById(CLASSE, id);
		if (produto.getStatus().equals(produto.DESATIVADO)){
			throw new com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException("Produto Inexistente");
		}
		return produto;
	}

	public Produto consultarPorCodigoExterno(String codigo) throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addProperty("codigoExterno", codigo);
		filter.addProperty("status", Produto.ATIVO);
		filter.setIgnoreCase(true);
		List l = filter(filter, true);
		if (l.size() > 0){
			return (Produto) l.iterator().next();
		}else{
			throw new ObjetoInexistenteException("Produto Inexistente");
		}
	}

	public Collection consultarPorFiltro(IPropertyFilter filter, boolean preciso) throws AppException{
		filter.setTheClass(CLASSE);
		filter.addProperty("status", Produto.ATIVO);
		List l = filter(filter, preciso);
		return l;
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		filter.addProperty("status", Produto.ATIVO);
		return filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);		
		return consultar(filter);
	}
	public void inserir(Produto produto) throws AppException{
		insert(produto);
		inserirDadoLote(produto);
	}
	
	public void alterar(Produto produto) throws AppException{
		update(produto);
		alterarDadoLote(produto);
	}
	
	public void alterar(Produto produto, Collection<Composicao> itensComposicaoRemovidos) throws AppException{
		if(itensComposicaoRemovidos != null){
			Iterator<Composicao> it = itensComposicaoRemovidos.iterator();
			while(it.hasNext()){
				ComposicaoPK composicaoPK = it.next().getPk();
				removeById(Composicao.class, composicaoPK);
			}
		}
		update(produto);
		alterarDadoLote(produto);	
	}

	
	public void excluir(Produto produto) throws AppException{
		remove(produto);
		excluirDadoLote(produto);
	}

	public Collection consultarProdutosPorFiltro(Produto produto, String idLoja) throws AppException{
		Collection<Produto> col = new ArrayList<Produto>();
		Session session = RepositoryManagerHibernateUtil.currentSession();
		StringBuffer sqlSetence = new StringBuffer();
		sqlSetence.append("SELECT p.* from PRODUTO p, PRODUTO_LOJA pl ");
		sqlSetence.append("WHERE p.id = pl.id_produto ");
		
		if(produto.getDescricaoCompleta() != null){
			sqlSetence.append("AND UPPER(p.descricao_completa) LIKE '%" + produto.getDescricaoCompleta().toUpperCase() + "%' ");	
		}
		
		if (produto.getTipo() != null && produto.getTipo().getId() != null && !produto.getTipo().getId().equals(new Long(0))){
			sqlSetence.append("AND p.id_tipo_produto = " + produto.getTipo().getId() + " ");
		}
		
		if (produto.getGrupo() != null && produto.getGrupo().getId() != null && !produto.getGrupo().getId().equals(new Long(0))){
			sqlSetence.append("AND p.id_grupo_produto = " + produto.getGrupo().getId() + " ");
		}
		
		if (produto.getImposto() != null && produto.getImposto().getId() != null && !produto.getImposto().getId().equals(new Long(0))){
			sqlSetence.append("AND p.id_imposto = " + produto.getImposto().getId() + " ");
		}
		
		if (produto.getUnidade() != null && produto.getUnidade().getId() != null && !produto.getUnidade().getId().equals(new Long(0))){
			sqlSetence.append("AND p.id_unidade = " + produto.getUnidade().getId() + " ");
		}
		
		if (produto.getFabricante() != null && produto.getFabricante().getId() != null && !produto.getFabricante().getId().equals(new Long(0))){
			sqlSetence.append("AND p.id_fabricante = " + produto.getFabricante().getId() + " ");
		}
		
		sqlSetence.append("AND pl.id_loja = " + idLoja);		
		sqlSetence.append(" ORDER BY p.DESCRICAO_COMPLETA");
		
		SQLQuery query = session.createSQLQuery(sqlSetence.toString());
		
		List result = query.list();
		
		if(result != null && result.size() > 0){
			Iterator it = result.iterator();
			while(it.hasNext()){
				Object[] obj = (Object[])it.next();
				if(obj != null){
					col.add((Produto)session.get(Produto.class, new Long(((BigDecimal)obj[0]).toString())));
				}
			}
		}		
		return col;
	}
}

