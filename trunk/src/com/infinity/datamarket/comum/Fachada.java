package com.infinity.datamarket.comum;

import java.util.Collection;

import com.infinity.datamarket.comum.macrooperacao.CadastroMacroOperacao;
import com.infinity.datamarket.comum.pagamento.CadastroFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.CadastroPlanoPagamento;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.produto.CadastroGrupoProduto;
import com.infinity.datamarket.comum.produto.CadastroImposto;
import com.infinity.datamarket.comum.produto.CadastroProduto;
import com.infinity.datamarket.comum.produto.CadastroTipoProduto;
import com.infinity.datamarket.comum.produto.CadastroUnidade;
import com.infinity.datamarket.comum.produto.GrupoProduto;
import com.infinity.datamarket.comum.produto.Imposto;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.produto.TipoProduto;
import com.infinity.datamarket.comum.produto.Unidade;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.totalizadores.CadastroTotalizadores;
import com.infinity.datamarket.comum.totalizadores.TotalizadorNaoFiscal;
import com.infinity.datamarket.comum.transacao.CadastroTransacao;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.usuario.CadastroLoja;
import com.infinity.datamarket.comum.usuario.CadastroPerfil;
import com.infinity.datamarket.comum.usuario.CadastroUsuario;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Perfil;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.SistemaException;
import com.infinity.datamarket.pdv.maquinaestados.MacroOperacao;

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
	
	private CadastroGrupoProduto getCadastroGrupoProduto(){
		return CadastroGrupoProduto.getInstancia();
	}

	
	private CadastroImposto getCadastroImposto(){
		return CadastroImposto.getInstancia();
	}
	
	private CadastroUnidade getCadastroUnidade(){
		return CadastroUnidade.getInstancia();
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

	private CadastroPerfil getCadastroPerfil(){
		return CadastroPerfil.getInstancia();
	}
	
	private CadastroMacroOperacao getCadastroMacroOperacao(){
		return CadastroMacroOperacao.getInstancia();
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

	public Collection consultarUsuariosPorPerfil(Perfil perfil) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroUsuario().consultarUsuariosPorPerfil(perfil);
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
	public Collection consultarFormaRecebimento(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroFormaRecebimento().consultar(filter);
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
	public Collection consultarPlanoPagamento(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroPlanoPagamento().consultar(filter);
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
	
    
	
	public Collection consultarTodosPlanos() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroPlanoPagamento().consultarTodos();
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
	
	//unidade
	
	public void inserirUnidade(Unidade unidade) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroUnidade().inserir(unidade);
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
	
	public Collection consultarUnidade(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroUnidade().consultar(filter);
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
	
	public Collection consultarTodasUnidades() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroUnidade().consultarTodos();
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
	
	public Unidade consultarUnidadePorPK(Long id) throws AppException{
		Unidade u = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			u = getCadastroUnidade().consultarPorPK(id);
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
		return u;
	}
	
	public void alterarUnidade(Unidade unidade) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroUnidade().alterar(unidade);
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
	
	public void excluirUnidade(Unidade unidade) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroUnidade().excluir(unidade);
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
	
	
//imposto
	
	public void inserirImposto(Imposto imposto) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroImposto().inserir(imposto);
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
	
	public Collection consultarImposto(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroImposto().consultar(filter);
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
	
	public Collection consultarTodosImpostos() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroImposto().consultarTodos();
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
	
	public Imposto consultarImpostoPorPK(Long id) throws AppException{
		Imposto i = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			i = getCadastroImposto().consultarPorPK(id);
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
		return i;
	}
	
	public void alterarImposto(Imposto imposto) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroImposto().alterar(imposto);
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
	
	public void excluirImposto(Imposto imposto) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroImposto().excluir(imposto);
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
// 
	public void excluirFormaRecebimento(FormaRecebimento formaRecebimento) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroFormaRecebimento().excluir(formaRecebimento);
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

	public void alterarFormaRecebimento(FormaRecebimento formaRecebimento) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroFormaRecebimento().alterar(formaRecebimento);
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
	
	public void inserirFormaRecebimento(FormaRecebimento formaRecebimento) throws AppException{
	try{
		RepositoryManagerHibernateUtil.beginTrasaction();
		getCadastroFormaRecebimento().inserir(formaRecebimento);
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
// PlanoPagamento	
	public void excluirPlanoPagamento(PlanoPagamento planoPagamento) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroPlanoPagamento().excluir(planoPagamento);
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

	public void alterarPlanoPagamento(PlanoPagamento planoPagamento) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroPlanoPagamento().alterar(planoPagamento);
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
	
	public void inserirPlanoPagamento(PlanoPagamento planoPagamento) throws AppException{
	try{
		RepositoryManagerHibernateUtil.beginTrasaction();
		getCadastroPlanoPagamento().inserir(planoPagamento);
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
// Loja
	
	public void inserirLoja(Loja loja) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroLoja().inserir(loja);
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
	
	public Collection consultarLoja(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroLoja().consultar(filter);
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
	
	public Collection consultarTodosFormaRecebimento() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroFormaRecebimento().consultarTodos();
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
	public Collection consultarTodosLoja() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroLoja().consultarTodos();
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
	
	public Loja consultarLojaPorPK(Long id) throws AppException{
		Loja loja = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			loja = getCadastroLoja().consultarPorPK(id);
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
	
	public void alterarLoja(Loja loja) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroLoja().alterar(loja);
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
	public void excluirLoja(Loja loja) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroLoja().excluir(loja);
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

//grupo produto
	
	public void inserirGrupoProduto(GrupoProduto grupo) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroGrupoProduto().inserir(grupo);
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
	
	public Collection consultarGrupoProduto(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroGrupoProduto().consultar(filter);
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
	
	public Collection consultarTodosGruposProduto() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroGrupoProduto().consultarTodos();
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
	
	public GrupoProduto consultarGrupoProdutoPorPK(Long id) throws AppException{
		GrupoProduto g = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			g = getCadastroGrupoProduto().consultarPorPK(id);
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
		return g;
	}
	
	public void alterarGrupoProduto(GrupoProduto grupo) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroGrupoProduto().alterar(grupo);
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
	
	public void excluirGrupoProduto(GrupoProduto grupo) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroGrupoProduto().excluir(grupo);
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

// Perfil
	
	public void inserirPerfil(Perfil perfil) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroPerfil().inserir(perfil);
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
	
	public Collection consultarPerfil(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroPerfil().consultar(filter);
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
	
	public Collection consultarTodosPerfil() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroPerfil().consultarTodos();
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
	
	public Collection consultarPerfisPorPerfilSuperior(Perfil perfil) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroPerfil().consultarPerfisPorPerfilSuperior(perfil);
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
	
	public Perfil consultarPerfilPorPK(Long id) throws AppException{
		Perfil perfil = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			perfil = getCadastroPerfil().consultarPorPK(id);
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
		return perfil;
	}
	
	public void alterarPerfil(Perfil perfil) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroPerfil().alterar(perfil);
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
	public void excluirPerfil(Perfil perfil) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroPerfil().excluir(perfil);
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
	
// Macro Operacao
	
	public void inserirMacroOperacao(MacroOperacao macroOperacao) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroMacroOperacao().inserir(macroOperacao);
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
	
	public Collection consultarMacroOperacao(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroMacroOperacao().consultar(filter);
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
	
	public Collection consultarTodosMacroOperacao() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroMacroOperacao().consultarTodos();
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
	
	public MacroOperacao consultarMacroOperacaoPorPK(Long id) throws AppException{
		MacroOperacao macroOperacao = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			macroOperacao = getCadastroMacroOperacao().consultarPorPK(id);
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
		return macroOperacao;
	}
	
	public void alterarMacroOperacao(MacroOperacao macroOperacao) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroMacroOperacao().alterar(macroOperacao);
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
	public void excluirMacroOperacao(MacroOperacao macroOperacao) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroMacroOperacao().excluir(macroOperacao);
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
	
// produto
	
	public void inserirProduto(Produto produto) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroProduto().inserir(produto);
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
	
	public Collection consultarTodosProdutos() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroProduto().consultarTodos();
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
	
	public Produto consultarProdutoPorPK(Long id) throws AppException{
		Produto produto = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			 produto = getCadastroProduto().consultarPorPK(id);
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
		return produto;
	}
	
	public void alterarProduto(Produto produto) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroProduto().alterar(produto);
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
	public void excluirProduto(Produto produto) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroProduto().excluir(produto);
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

	public Collection consultarProdutoPorFiltro(IPropertyFilter filter, boolean preciso) throws AppException{
		Collection ret = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			ret = getCadastroProduto().consultarPorFiltro(filter,preciso);
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


}
