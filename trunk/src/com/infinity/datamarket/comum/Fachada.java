package com.infinity.datamarket.comum;

import java.util.Collection;

import com.infinity.datamarket.comum.pagamento.CadastroFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.CadastroPlanoPagamento;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.produto.CadastroProduto;
import com.infinity.datamarket.comum.produto.CadastroTipoProduto;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.totalizadores.CadastroTotalizadores;
import com.infinity.datamarket.comum.totalizadores.TotalizadorNaoFiscal;
import com.infinity.datamarket.comum.transacao.CadastroTransacao;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.usuario.CadastroLoja;
import com.infinity.datamarket.comum.usuario.CadastroUsuario;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.SistemaException;

public class Fachada {

	private static Fachada instancia;

	private Fachada(){

	}
	public static Fachada getInstancia(){
		if (instancia == null){
			instancia = new Fachada();
		}
		return instancia;
	}

	private CadastroUsuario getCadastroUsuario(){
		return CadastroUsuario.getInstancia();
	}
	
	private CadastroTipoProduto getCadastroTipoProduto(){
		return CadastroTipoProduto.getInstancia();
	}
	
	private CadastroLoja getCadastroLoja(){
		return CadastroLoja.getInstancia();
	}

	private CadastroProduto getCadastroProduto(){
		return CadastroProduto.getInstancia();
	}

	private CadastroTransacao getCadastroTransacao(){
		return CadastroTransacao.getInstancia();
	}

	private CadastroTotalizadores getCadastroTotalizadores(){
		return CadastroTotalizadores.getInstancia();
	}

	private CadastroFormaRecebimento getCadastroFormaRecebimento(){
		return CadastroFormaRecebimento.getInstancia();
	}

	private CadastroPlanoPagamento getCadastroPlanoPagamento(){
		return CadastroPlanoPagamento.getInstancia();
	}


	public Usuario loginUsuario(Long id, String senha) throws AppException{
		Usuario usu = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			usu = getCadastroUsuario().login(id, senha);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return usu;
	}
	
	public Loja consultarLojaPorId(Long id) throws AppException{
		Loja loja = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			loja = getCadastroLoja().consultarPorId(id);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return loja;
	}

	public Usuario consultarUsuarioPorId(Long id) throws AppException{
		Usuario usu = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			usu = getCadastroUsuario().consultarPorId(id);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return usu;
	}

	public Usuario consultarUsuarioPorId_IdMacro(Long id,Long idMacro) throws AppException{
		Usuario usu = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			usu = getCadastroUsuario().consultarPorId_IdMacro(id, idMacro);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return usu;
	}

	public Produto consultarProdutoPorCodigoExterno(String codigo) throws AppException{
		Produto pro = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			pro = getCadastroProduto().consultarPorCodigoExterno(codigo);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return pro;
	}

	public Collection consultarProdutoPorDescricao(String descricao, boolean preciso) throws AppException{
		Collection ret = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			ret = getCadastroProduto().consultarPorDescricao(descricao,preciso);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return ret;
	}


	public void inserirTransacao(Transacao trans) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroTransacao().inserir(trans);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
	}
	
	public Transacao consultarTransacaoPorPK(TransacaoPK pk) throws AppException{
		Transacao trans = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroTransacao().consultarPorPK(pk);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return trans;
	}


	public void zerarTotalizador(Long totalizador) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroTotalizadores().zerarTotalizador(totalizador);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
	}

	public void zerarTodosTotalizadores() throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroTotalizadores().zerarTodosTotalizadores();
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
	}

	public TotalizadorNaoFiscal consultarTotalizador(Long totalizador) throws AppException{
		TotalizadorNaoFiscal tot = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			tot = getCadastroTotalizadores().consultarTotalizador(totalizador);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return tot;
	}

	public FormaRecebimento consultarFormaRecebimentoPorId(Long id) throws AppException{
		FormaRecebimento forma = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			forma = getCadastroFormaRecebimento().consultarPorId(id);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return forma;
	}

	public PlanoPagamento consultarPlanoPagamentoPorId(Long id) throws AppException{
		PlanoPagamento plano = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			plano = getCadastroPlanoPagamento().consultarPorId(id);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return plano;
	}
	
	//tipo produto
	
	public void inserirTipoProduto(TipoProduto tipo) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroTipoProduto().inserir(tipo);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
	}
	
	public Collection consultarTipoProduto(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroTipoProduto().consultar(filter);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return c;
	}
	
	public Collection consultarTodosTipoProduto() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroTipoProduto().consultarTodos();
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return c;
	}
	
	public TipoProduto consultarTipoProdutoPorPK(Long id) throws AppException{
		TipoProduto t = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			t = getCadastroTipoProduto().consultarPorPK(id);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
		return t;
	}
	
	public void alterarTipoProduto(TipoProduto tipo) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroTipoProduto().alterar(tipo);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
	}
	
	public void excluirTipoProduto(TipoProduto tipo) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroTipoProduto().excluir(tipo);
			RepositoryManagerHibernateUtil.commitTransation();
		}catch(AppException e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
			throw e;
		}catch(Throwable e){
			try{
				RepositoryManagerHibernateUtil.rollbackTransation();
				throw new SistemaException(e);
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}finally{
			try{
				RepositoryManagerHibernateUtil.closeSession();
			}catch(Exception ex){
				throw new SistemaException(ex);
			}
		}
	}

}
