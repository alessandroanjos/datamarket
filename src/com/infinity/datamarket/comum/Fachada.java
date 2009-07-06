package com.infinity.datamarket.comum;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;

import com.infinity.datamarket.autorizador.AutorizacaoCartaoProprio;
import com.infinity.datamarket.autorizador.CadastroAutorizacaoCartaoProprio;
import com.infinity.datamarket.comum.banco.Banco;
import com.infinity.datamarket.comum.banco.CadastroBanco;
import com.infinity.datamarket.comum.cliente.CadastroCliente;
import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.clientepagamento.CadastroClientePagamento;
import com.infinity.datamarket.comum.clientepagamento.ClientePagamento;
import com.infinity.datamarket.comum.componente.CadastroComponente;
import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.conta.CadastroContaCorrente;
import com.infinity.datamarket.comum.conta.CadastroMovimentacaoBancaria;
import com.infinity.datamarket.comum.conta.ContaCorrente;
import com.infinity.datamarket.comum.conta.MovimentacaoBancaria;
import com.infinity.datamarket.comum.estoque.AjusteEstoque;
import com.infinity.datamarket.comum.estoque.CadastroAjusteEstoque;
import com.infinity.datamarket.comum.estoque.CadastroEntradaProduto;
import com.infinity.datamarket.comum.estoque.CadastroEstoque;
import com.infinity.datamarket.comum.estoque.CadastroEstoqueProduto;
import com.infinity.datamarket.comum.estoque.CadastroMovimentacaoEstoque;
import com.infinity.datamarket.comum.estoque.EntradaProduto;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.EstoquePK;
import com.infinity.datamarket.comum.estoque.EstoqueProduto;
import com.infinity.datamarket.comum.estoque.EstoqueProdutoPK;
import com.infinity.datamarket.comum.estoque.MovimentacaoEstoque;
import com.infinity.datamarket.comum.fabricante.CadastroFabricante;
import com.infinity.datamarket.comum.fabricante.Fabricante;
import com.infinity.datamarket.comum.financeiro.CadastroGrupoLancamento;
import com.infinity.datamarket.comum.financeiro.CadastroLancamento;
import com.infinity.datamarket.comum.financeiro.GrupoLancamento;
import com.infinity.datamarket.comum.financeiro.Lancamento;
import com.infinity.datamarket.comum.fornecedor.CadastroFornecedor;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.funcionalidade.CadastroFuncionalidade;
import com.infinity.datamarket.comum.funcionalidade.Funcionalidade;
import com.infinity.datamarket.comum.lote.CadastroDadoLote;
import com.infinity.datamarket.comum.macrooperacao.CadastroMacroOperacao;
import com.infinity.datamarket.comum.operacao.CadastroOperacao;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoDevolucao;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.pagamento.Autorizadora;
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
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.totalizadores.CadastroTotalizadores;
import com.infinity.datamarket.comum.totalizadores.TotalizadorNaoFiscal;
import com.infinity.datamarket.comum.transacao.CadastroTransacao;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.transacao.TransacaoPagamentoCartaoProprio;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.usuario.CadastroLoja;
import com.infinity.datamarket.comum.usuario.CadastroPerfil;
import com.infinity.datamarket.comum.usuario.CadastroUsuario;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Perfil;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.SistemaException;
import com.infinity.datamarket.infocomponent.CadastroInfoComponente;
import com.infinity.datamarket.infocomponent.InfoComponent;
import com.infinity.datamarket.pdv.acumulador.AcumuladorNaoFiscal;
import com.infinity.datamarket.pdv.acumulador.CadastroAcumuladorNaoFiscal;
import com.infinity.datamarket.pdv.maquinaestados.MacroOperacao;
import com.infinity.datamarket.report.GerenciadorRelatorio;

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
	
	private ConcentradorParametro getConcentradorParametro(){
		return ConcentradorParametro.getInstancia();
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
	
	private CadastroOperacao getCadastroOperacao(){
		return CadastroOperacao.getInstancia();
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
	
	private CadastroComponente getCadastroComponente(){
		return CadastroComponente.getInstancia();
	}
	
	private CadastroPerfil getCadastroPerfil(){
		return CadastroPerfil.getInstancia();
	}
	
	private CadastroMacroOperacao getCadastroMacroOperacao(){
		return CadastroMacroOperacao.getInstancia();
	}

	private CadastroFuncionalidade getCadastroFuncionalidade(){
		return CadastroFuncionalidade.getInstancia();
	}
	
	private CadastroInfoComponente getCadastroInfoComponente(){
		return CadastroInfoComponente.getInstancia();
	}
	
	private CadastroEstoque getCadastroEstoque(){
		return CadastroEstoque.getInstancia();
	}
	
	private CadastroFornecedor getCadastroFornecedor(){
		return CadastroFornecedor.getInstancia();
	}

	private CadastroFabricante getCadastroFabricante(){
		return CadastroFabricante.getInstancia();
	}

	private CadastroEntradaProduto getCadastroEntradaProduto(){
		return CadastroEntradaProduto.getInstancia();
	}
	private CadastroMovimentacaoEstoque getCadastroMovimentacaoEstoque(){
		return CadastroMovimentacaoEstoque.getInstancia();
	}
	
	private com.infinity.datamarket.comum.pagamento.CadastroAutorizadora getCadastroAutorizadora(){
		return com.infinity.datamarket.comum.pagamento.CadastroAutorizadora.getInstancia();
	}

	
	private CadastroDadoLote getCadastroDadoLote(){
		return CadastroDadoLote.getInstancia();
	}
	
	private CadastroAjusteEstoque getCadastroAjusteEstoque(){
		return CadastroAjusteEstoque.getInstancia();
	}
	
	private CadastroCliente getCadastroCliente(){
		return CadastroCliente.getInstancia();
	}
	
	private CadastroClientePagamento getCadastroClientePagamento(){
		return CadastroClientePagamento.getInstancia();
	}
	
	private CadastroAutorizacaoCartaoProprio getCadastroAutorizacaoCartaoProprio(){
		return CadastroAutorizacaoCartaoProprio.getInstancia();
	}
	
	private CadastroEstoqueProduto getCadastroEstoqueProduto(){
		return CadastroEstoqueProduto.getInstancia();
	}
	
	private CadastroAcumuladorNaoFiscal getCadastroAcumuladorNaoFiscal(){
		return CadastroAcumuladorNaoFiscal.getInstancia();
	}
	
	private CadastroGrupoLancamento getCadastroGrupoLancamento() {
		return CadastroGrupoLancamento.getInstancia();
	}
	
	private CadastroLancamento getCadastroLancamento() {
		return CadastroLancamento.getInstancia();
	}	
	
	private CadastroBanco getCadastroBanco() {
		return CadastroBanco.getInstancia();
	}	
	
	private CadastroContaCorrente getCadastroContaCorrente() {
		return CadastroContaCorrente.getInstancia();
	}
	
	private CadastroMovimentacaoBancaria getCadastroMovimentacaoBancaria() {
		return CadastroMovimentacaoBancaria.getInstancia();
	}
	
	private GerenciadorRelatorio getGerenciadorRelatorio(){
		return GerenciadorRelatorio.getInstancia();
	}
	

	public void gerarReciboVenda(TransacaoVenda transacao, OutputStream out) throws AppException{
		try{
			getGerenciadorRelatorio().gerarReciboVenda(transacao, out);
		}catch(AppException e){
			throw new SistemaException(e);
		}
	}
	
	public void gerarReciboOperacaoDevolucao(OperacaoDevolucao devolucao, OutputStream out) throws AppException{
		try{
			getGerenciadorRelatorio().gerarReciboOperacaoDevolucao(devolucao, out);
		}catch(AppException e){
			throw new SistemaException(e);
		}
	}

	
	public OutputStream gerarReciboPagamentoCliente(TransacaoPagamentoCartaoProprio transacao) throws AppException{
		try{
			return getGerenciadorRelatorio().gerarReciboPagamentoCliente(transacao);
		}catch(AppException e){
			throw new SistemaException(e);
		}
	}

	public void gerarReciboMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque, OutputStream out) throws AppException{
		try{
			getGerenciadorRelatorio().gerarReciboMovimentacaoEstoque(movimentacaoEstoque, out);
		}catch(AppException e){
			throw new SistemaException(e);
		}
	}
	
	

	public void gerarReciboEntradaProdutosEstoque(EntradaProduto entradaProduto, OutputStream out) throws AppException{
		try{
			getGerenciadorRelatorio().gerarReciboEntradaProdutosEstoque(entradaProduto, out);
		}catch(AppException e){
			throw new SistemaException(e);
		}
	}

	public OutputStream gerarRelatorioAnaliticoVendas(int loja, Date data_inicio_movimento,Date data_fim_movimento) throws AppException{		
		OutputStream out = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			out = getGerenciadorRelatorio().gerarRelatorioAnaliticoVendas(loja, data_inicio_movimento, data_fim_movimento);
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
		return out;
	}
	
	public OutputStream gerarRelatorioLucroBrutoVenda(int loja, Date data_inicio_movimento, Date data_fim_movimento) throws AppException{
		OutputStream out = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			out = getGerenciadorRelatorio().gerarRelatorioLucroBrutoVenda(loja, data_inicio_movimento, data_fim_movimento);
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
		return out;
	}
	
	public OutputStream gerarRelatorioComissaoVendedor(int loja, Date data_inicio_movimento, Date data_fim_movimento, int vendedor) throws AppException{
		
		OutputStream out = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			out = getGerenciadorRelatorio().gerarRelatorioComissaoVendedor(loja, data_inicio_movimento, data_fim_movimento, vendedor);
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
		return out;
	}
	
	public OutputStream gerarRelatorioEstoqueAtual(int loja, int estoque) throws AppException{
		OutputStream out = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			out = getGerenciadorRelatorio().gerarRelatorioEstoqueAtual(loja, estoque);
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
		return out;
	}

	
	public OutputStream gerarRelatorioABCVendas(int loja, Date data_inicio_movimento,Date data_fim_movimento, String tipo) throws AppException{		
		OutputStream out = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			out = getGerenciadorRelatorio().gerarRelatorioABCVendas(loja, data_inicio_movimento, data_fim_movimento, tipo);
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
		return out;
	}
	
	public OutputStream gerarRelatorioFechamentoCaixaOperador(int loja, Date data_inicio_movimento,Date data_fim_movimento, Integer operador) throws AppException{		
		OutputStream out = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			out = getGerenciadorRelatorio().gerarRelatorioFechamentoCaixaOperador(loja, data_inicio_movimento, data_fim_movimento, operador);
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
		return out;
	}
	
	public OutputStream gerarRelatorioFechamentoCaixaGeral(int loja, Date data_inicio_movimento,Date data_fim_movimento) throws AppException{		
		OutputStream out = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			out = getGerenciadorRelatorio().gerarRelatorioFechamentoCaixaGeral(loja, data_inicio_movimento, data_fim_movimento);
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
		return out;
	}
	
	public OutputStream gerarRelatorioAnaliticoEntradas(Date data_inicio_movimento,Date data_fim_movimento, String[] status, String idLoja, String idEstoque) throws AppException{		
		OutputStream out = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			out = getGerenciadorRelatorio().gerarRelatorioAnaliticoEntradas(data_inicio_movimento, data_fim_movimento, status, idLoja, idEstoque);
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
		return out;
	}
	
	public OutputStream gerarRelatorioAnaliticoMovimentacaoEstoque(Date data_inicio_movimento,Date data_fim_movimento, String idLojaSaida, String idEstoqueSaida, String idLojaEntrada, String idEstoqueEntrada) throws AppException{		
		OutputStream out = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			out = getGerenciadorRelatorio().gerarRelatorioAnaliticoMovimentacaoEstoque(data_inicio_movimento, data_fim_movimento, idLojaSaida, idEstoqueSaida, idLojaEntrada, idEstoqueEntrada);
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
		return out;
	}
	
	public OutputStream gerarRelatorioAnaliticoOperacoesDevolucao(int loja, Date data_inicio_movimento,Date data_fim_movimento) throws AppException{		
		OutputStream out = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			out = getGerenciadorRelatorio().gerarRelatorioAnaliticoOperacoesDevolucao(loja,data_inicio_movimento, data_fim_movimento);
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
		return out;
	}
	
	public void gerarReciboDevolucaoProdutos(OperacaoDevolucao operacaoDevolucao, OutputStream out) throws AppException{
		try{
			getGerenciadorRelatorio().gerarReciboDevolucaoProdutos(operacaoDevolucao, out);
		}catch(AppException e){
			throw new SistemaException(e);
		}
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
	
	public String consultarURLApp() throws AppException{
		return getConcentradorParametro().consultarURLApp();		
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
			if (trans instanceof TransacaoVenda){
				TransacaoVenda transVenda = (TransacaoVenda) trans;
				if (transVenda.getCliente() != null){
					try{
						RepositoryManagerHibernateUtil.beginTrasaction();
						getCadastroTransacao().inserirCliente(transVenda.getCliente());
						RepositoryManagerHibernateUtil.commitTransation();
					}catch(Exception e){					
						RepositoryManagerHibernateUtil.beginTrasaction();
						getCadastroTransacao().atualizarCliente(transVenda.getCliente());
						RepositoryManagerHibernateUtil.commitTransation();
					}
				}
			}
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
	
	
	public void inserirTransacaoES(Transacao trans) throws AppException{

		try{
			if (trans instanceof TransacaoVenda){
				TransacaoVenda transVenda = (TransacaoVenda) trans;
				if (transVenda.getCliente() != null){
					try{
						RepositoryManagerHibernateUtil.beginTrasaction();
						getCadastroTransacao().inserirCliente(transVenda.getCliente());
						RepositoryManagerHibernateUtil.commitTransation();
					}catch(Exception e){					
						RepositoryManagerHibernateUtil.beginTrasaction();
						getCadastroTransacao().atualizarCliente(transVenda.getCliente());
						RepositoryManagerHibernateUtil.commitTransation();
					}
				}
			}
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroTransacao().inserirES(trans);
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
	
	public void atualizarTransacaoES(Transacao trans, Collection<EventoItemRegistrado> itensRegistradosRemovidos) throws AppException{
		try{
			if (trans instanceof TransacaoVenda){
				TransacaoVenda transVenda = (TransacaoVenda) trans;
				if (transVenda.getCliente() != null){
					try{
						RepositoryManagerHibernateUtil.beginTrasaction();
						getCadastroTransacao().atualizarCliente(transVenda.getCliente());
						RepositoryManagerHibernateUtil.commitTransation();
					}catch(Exception e){					
						RepositoryManagerHibernateUtil.beginTrasaction();
						getCadastroTransacao().inserirCliente(transVenda.getCliente());
						RepositoryManagerHibernateUtil.commitTransation();
					}
				}
			}
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroTransacao().atualizarES(trans, itensRegistradosRemovidos);
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
			trans = getCadastroTransacao().consultarPorPK(pk);
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
	
	public Collection consultarTransacao(IPropertyFilter filter) throws AppException{
		Collection col = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			col = getCadastroTransacao().consultar(filter);
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
		return col;
	}
	
	public void atualizaTransacaoProcessada(Transacao trans) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroTransacao().atualizaTransacaoProcessada(trans);
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
    
 //PLANO
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
	
	public Collection consultarTodosPlanosChequePre() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroPlanoPagamento().consultarTodosPreDatado();
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
	
	// componente
	public void inserirComponente(Componente componente) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroComponente().inserir(componente);
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
	public void alterarComponente(Componente componente) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroComponente().alterar(componente);
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
	public void excluirComponente(Componente componente) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroComponente().excluir(componente);
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
	public Componente consultarComponentePorId(Long id) throws AppException{
		Componente plano = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			plano = getCadastroComponente().consultarPorId(id);
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
	public Collection consultarComponentes(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroComponente().consultar(filter);
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
	
    
	
	public Collection consultarTodosComponentes() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroComponente().consultarTodos();
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

	// autorizadora
	
	public void inserirAutorizadora(Autorizadora autorizadora) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroAutorizadora().inserir(autorizadora);
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
	public void alterarAutorizadora(Autorizadora autorizadora) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroAutorizadora().alterar(autorizadora);
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
	public void excluirAutorizadora(Autorizadora autorizadora) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroAutorizadora().excluir(autorizadora);
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
	public Autorizadora consultarAutorizadoraPorId(Long id) throws AppException{
		Autorizadora autorizadora = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			autorizadora = getCadastroAutorizadora().consultarPorId(id);
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
		return autorizadora;
	}
	public Collection consultarAutorizadora(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroAutorizadora().consultar(filter);
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
	
	public Collection consultarTodasAutorizadoras() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroAutorizadora().consultarTodos();
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

// grupoLancamento
	
	public void inserirGrupoLancamento(GrupoLancamento grupo) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroGrupoLancamento().inserir(grupo);
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
	public void alterarGrupoLancamento(GrupoLancamento grupo) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroGrupoLancamento().alterar(grupo);
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
	public void excluirGrupoLancamento(GrupoLancamento GrupoLancamento) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroGrupoLancamento().excluir(GrupoLancamento);
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
	public GrupoLancamento consultarGrupoLancamentoPorId(Long id) throws AppException{
		GrupoLancamento GrupoLancamento = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			GrupoLancamento = getCadastroGrupoLancamento().consultarPorId(id);
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
		return GrupoLancamento;
	}
	public Collection consultarGrupoLancamento(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroGrupoLancamento().consultar(filter);
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
	
	public Collection consultarTodosGrupoLancamentos() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroGrupoLancamento().consultarTodos();
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

// Lancamento
	
	public void inserirLancamento(Lancamento lancamento) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroLancamento().inserir(lancamento);
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
	public void alterarLancamento(Lancamento lancamento) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroLancamento().alterar(lancamento);
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
	
	public void baixarLancamento(Lancamento lancamento) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroLancamento().baixar(lancamento);
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

	public void excluirLancamento(Lancamento Lancamento) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroLancamento().excluir(Lancamento);
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
	public Lancamento consultarLancamentoPorId(Long id) throws AppException{
		Lancamento Lancamento = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			Lancamento = getCadastroLancamento().consultarPorId(id);
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
		return Lancamento;
	}
	public Collection consultarLancamento(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroLancamento().consultar(filter);
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
	
	public Collection consultarTodasLancamentos() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroLancamento().consultarTodos();
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
	
	public Collection consultarProdutosPorFiltro(Produto produto, String idLoja) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroProduto().consultarProdutosPorFiltro(produto, idLoja);
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


// Usuario	
	
	public void inserirUsuario(Usuario usuario) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroUsuario().inserir(usuario);
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
	
	public Collection consultarUsuario(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroUsuario().consultar(filter);
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

	public Collection consultarUsuariosPorFiltro(Usuario usuario, String idLoja) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroUsuario().consultarUsuariosPorFiltro(usuario, idLoja);
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

	public Collection consultarTodosUsuario() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroUsuario().consultarTodos();
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
	
	public Usuario consultarUsuarioPorPK(Long id) throws AppException{
		Usuario usuario = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			usuario = getCadastroUsuario().consultarPorPK(id);
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
		return usuario;
	}
	
	public void alterarUsuario(Usuario usuario) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroUsuario().alterar(usuario);
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
	public void excluirUsuario(Usuario usuario) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroUsuario().excluir(usuario);
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
	
// Funcionalidade
	
	public void inserirFuncionalidade(Funcionalidade funcionalidade) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroFuncionalidade().inserir(funcionalidade);
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
	
	public Collection consultarFuncionalidade(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroFuncionalidade().consultar(filter);
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
	
	public Collection consultarTodosFuncionalidade() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroFuncionalidade().consultarTodos();
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
	
	public Collection consultarFuncionalidadesPorFuncionalidadeSuperior(Funcionalidade funcionalidade) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroFuncionalidade().consultarFuncionalidadesPorFuncionalidadeSuperior(funcionalidade);
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
	
	public Funcionalidade consultarFuncionalidadePorPK(Long id) throws AppException{
		Funcionalidade funcionalidade = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			funcionalidade = getCadastroFuncionalidade().consultarPorPK(id);
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
		return funcionalidade;
	}
	
	public void alterarFuncionalidade(Funcionalidade funcionalidade) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroFuncionalidade().alterar(funcionalidade);
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
	public void excluirFuncionalidade(Funcionalidade funcionalidade) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroFuncionalidade().excluir(funcionalidade);
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
	public InfoComponent consultarInfoCompometPorPK(String id) throws AppException{
		InfoComponent infoComponent = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			infoComponent = getCadastroInfoComponente().consultarPorId(id);
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
		return infoComponent;
	}
	
	public void salvarInfoComponent(InfoComponent infoComponent) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroInfoComponente().alterar(infoComponent);	
			RepositoryManagerHibernateUtil.commitTransation();
		} catch (ObjectExistentException e) {
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroInfoComponente().inserir(infoComponent);	
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
    //   Estoque
	public void inserirEstoque(Estoque estoque) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroEstoque().inserir(estoque);
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
	public void alterarEstoque(Estoque estoque) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroEstoque().alterar(estoque);
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
	public void excluirEstoque(Estoque estoque) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroEstoque().excluir(estoque);
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
	public Estoque consultarEstoquePorId(EstoquePK id) throws AppException{
		Estoque estoque = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			estoque = getCadastroEstoque().consultarPorId(id);
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
		return estoque;
	}
	public Collection consultarEstoque(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroEstoque().consultar(filter);
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
	
    
	
	public Collection consultarTodosEstoques() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroEstoque().consultarTodos();
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

	public Collection consultarTodosEstoquesPorLoja(String idLoja) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			
			c = getCadastroEstoque().consultarTodosPorLoja(idLoja);
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
	
	public EstoqueProduto consultarEstoqueProduto(EstoqueProdutoPK id) throws AppException {
		EstoqueProduto estoque = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			estoque = getCadastroEstoqueProduto().consultarPorId(id);
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
		return estoque;
	}

	

	//	 Ajuste
	public void inserirAjusteEstoque(AjusteEstoque ajustEstoque) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroAjusteEstoque().inserir(ajustEstoque);
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
	
	public AjusteEstoque consultarAjustePorId(Long id) throws AppException{
		AjusteEstoque ajustEstoque = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			ajustEstoque = getCadastroAjusteEstoque().consultarPorId(id);
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
		return ajustEstoque;
	}
	public Collection consultarAjusteEstoque(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroAjusteEstoque().consultar(filter);
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
//	 Fornecedor
	public void inserirFornecedor(Fornecedor fornecedor) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroFornecedor().inserir(fornecedor);
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
	public void alterarFornecedor(Fornecedor fornecedor) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroFornecedor().alterar(fornecedor);
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
	public void excluirFornecedor(Fornecedor fornecedor) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroFornecedor().excluir(fornecedor);
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
	public Fornecedor consultaFornecedorPorId(Long id) throws AppException{
		Fornecedor fornecedor = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			fornecedor = getCadastroFornecedor().consultarPorId(id);
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
		return fornecedor;
	}
	public Collection consultarFornecedor(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroFornecedor().consultar(filter);
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
	
    
	
	public Collection consultarTodosFornecedores() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroFornecedor().consultarTodos();
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
	
//	 Fabricante
	public void inserirFabricante(Fabricante fabricante) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroFabricante().inserir(fabricante);
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
	public void alterarFabricante(Fabricante fabricante) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroFabricante().alterar(fabricante);
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
	public void excluirFabricante(Fabricante fabricante) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroFabricante().excluir(fabricante);
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
	public Fabricante consultaFabricantePorId(Long id) throws AppException{
		Fabricante fabricante = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			fabricante = getCadastroFabricante().consultarPorId(id);
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
		return fabricante;
	}
	public Collection consultarFabricante(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroFabricante().consultar(filter);
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
	
    
	
	public Collection consultarTodosFabricantees() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroFabricante().consultarTodos();
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
//	 Relatorios
	public Collection consultaProdutosPorSecao(String idLoja) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroProduto().consultaProdutosPorSecao(idLoja);
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
	
	
	//	 Entrada Produto
	public void inserirEntradaProduto(EntradaProduto entradaProduto) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroEntradaProduto().inserir(entradaProduto);
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
	
	public void alterarEntradaProduto(EntradaProduto entradaProduto) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroEntradaProduto().alterar(entradaProduto);
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
	
	public void excluirEntradaProduto(EntradaProduto entradaProduto) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroEntradaProduto().excluir(entradaProduto);
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
	public Collection consultarTodosEntradaProdutos() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroEntradaProduto().consultarTodos();
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
	public EntradaProduto consultarEntradaProdutoPorId(Long id) throws AppException{
		EntradaProduto entradaProduto = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			entradaProduto = getCadastroEntradaProduto().consultarPorId(id);
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
		return entradaProduto;
	}
	public Collection consultarEntradaProduto(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroEntradaProduto().consultar(filter);
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
	
    
	
	public Collection consultarTodasEntradaProduto() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroEntradaProduto().consultarTodos();
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

	//	 Movimentacao Estoque
	public void inserirMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroMovimentacaoEstoque().inserir(movimentacaoEstoque);
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
	
	public void alterarMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroMovimentacaoEstoque().alterar(movimentacaoEstoque);
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
	
	public void cancelarMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroMovimentacaoEstoque().cancelar(movimentacaoEstoque);
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

	
	public void excluirMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque) throws AppException{

		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroMovimentacaoEstoque().excluir(movimentacaoEstoque);
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
	public Collection consultarTodosMovimentacaoEstoque() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroMovimentacaoEstoque().consultarTodos();
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
	public MovimentacaoEstoque consultarMovimentacaoEstoquePorId(Long id) throws AppException{
		MovimentacaoEstoque movimentacaoEstoque = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			movimentacaoEstoque = getCadastroMovimentacaoEstoque().consultarPorId(id);
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
		return movimentacaoEstoque;
	}
	public Collection consultarMovimentoEstoque(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroMovimentacaoEstoque().consultar(filter);
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
	
    
	
	
	
	
//CLIENTE
	
	public void inserirCliente(Cliente cliente) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroCliente().inserir(cliente);
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
	
	public Collection consultarCliente(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroCliente().consultar(filter);
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
	
	public Collection consultarTodosClientes() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroCliente().consultarTodos();
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
	
	public Cliente consultarClientePorPK(Long id) throws AppException{
		Cliente c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroCliente().consultarPorPK(id);
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
	
	public void alterarCliente(Cliente cliente) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroCliente().alterar(cliente);
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
	
	public void excluirCliente(Cliente cliente) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroCliente().excluir(cliente);
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
	
// CLIENTE PAGAMENTO
	
	public void inserirClientePagamento(ClientePagamento clientePagamento) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroClientePagamento().inserir(clientePagamento);
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
	
	public Collection consultarClientePagamento(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroClientePagamento().consultar(filter);
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
	
	public Collection consultarTodosClientesPagamentos() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroClientePagamento().consultarTodos();
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
	
	public ClientePagamento consultarClientePagamentoPorPK(Long id) throws AppException{
		ClientePagamento c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroClientePagamento().consultarPorPK(id);
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
	
	public void alterarClientePagamento(ClientePagamento clientePagamento) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroClientePagamento().alterar(clientePagamento);
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
	
	public void excluirClientePagamento(ClientePagamento clientePagamento) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroClientePagamento().excluir(clientePagamento);
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
	
	public Long consultarMaxIdClientePagamento() throws AppException{
		Long maxId = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			maxId = getCadastroClientePagamento().retornaMaxIDClientePagamento();
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
		return maxId;
	}
	
// LOTE
	
	public Collection consultarDadosLote(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroDadoLote().consultar(filter);
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
	
	public void inserirAjusteEstque(AjusteEstoque ajuste) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroAjusteEstoque().inserir(ajuste);
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
	
	
	public void inserirAutorizacaoCartaoProprio(AutorizacaoCartaoProprio autorizacaoCartaoProprio) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroAutorizacaoCartaoProprio().inserir(autorizacaoCartaoProprio);
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
	
	public Collection consultarAutorizacaoCartaoProprio(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroAutorizacaoCartaoProprio().consultar(filter);
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
	
	public Collection consultarTodasAutorizacoesCartaoProprio() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroAutorizacaoCartaoProprio().consultarTodos();
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
	
	public AutorizacaoCartaoProprio consultarAutorizacaoCartaoProprioPorPK(Long id) throws AppException{
		AutorizacaoCartaoProprio c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroAutorizacaoCartaoProprio().consultarPorId(id);
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
	
	public void alterarAutorizacaoCartaoProprio(AutorizacaoCartaoProprio autorizacaoCartaoProprio) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroAutorizacaoCartaoProprio().alterar(autorizacaoCartaoProprio);
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
	
	public void excluirAutorizacaoCartaoProprio(AutorizacaoCartaoProprio autorizacaoCartaoProprio) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroAutorizacaoCartaoProprio().excluir(autorizacaoCartaoProprio);
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
	
	
	public void inserirEstoqueProduto(EstoqueProduto ep) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroEstoqueProduto().inserir(ep);
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
	
	public Collection consultarEstoqueProduto(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroEstoqueProduto().consultar(filter);
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
	
	public Collection consultarTodosEstoqueProduto() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroEstoqueProduto().consultarTodos();
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
	
	public EstoqueProduto consultarEstoqueProdutoPorPK(EstoqueProdutoPK pk) throws AppException{
		EstoqueProduto c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroEstoqueProduto().consultarPorId(pk);
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
	
	public Collection consultarAcumuladorNaoFiscal(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroAcumuladorNaoFiscal().consultar(filter);
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
	
	public Collection consultarTodosAcumuladoresNaoFiscais() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroAcumuladorNaoFiscal().consultarTodos();
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
	
	public AcumuladorNaoFiscal consultarAcumuladorNaoFiscalPorPK(Long pk) throws AppException{
		AcumuladorNaoFiscal c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroAcumuladorNaoFiscal().consultarPorId(pk);
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
	
	public void alterarEstoqueProduto(EstoqueProduto ep) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroEstoqueProduto().alterar(ep);
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

	public ClienteTransacao consultarClienteTransacaoPorID(String id) throws AppException{
		ClienteTransacao cli = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			cli = getCadastroTransacao().consultarClienteTransacaoPorID(id);
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
		return cli;
	}
	
	public Collection consultarClienteTransacao(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroTransacao().consultarClienteTransacao(filter);
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
	
	public void inserirOperacaoES(Operacao operacao) throws AppException{

		try{
			if (operacao instanceof OperacaoDevolucao){
				OperacaoDevolucao operacaoDevolucao = (OperacaoDevolucao) operacao;
				if (operacaoDevolucao.getCliente() != null){
					try{
						RepositoryManagerHibernateUtil.beginTrasaction();
						getCadastroTransacao().inserirCliente(operacaoDevolucao.getCliente());
						RepositoryManagerHibernateUtil.commitTransation();
					}catch(Exception e){					
						RepositoryManagerHibernateUtil.beginTrasaction();
						getCadastroTransacao().atualizarCliente(operacaoDevolucao.getCliente());
						RepositoryManagerHibernateUtil.commitTransation();
					}
				}
			}
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroOperacao().inserirES(operacao);
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
	
	public void alterarOperacao(Operacao operacao) throws AppException{

		try{
			if (operacao instanceof OperacaoDevolucao){
				OperacaoDevolucao operacaoDevolucao = (OperacaoDevolucao) operacao;
				if (operacaoDevolucao.getCliente() != null){
					try{
						RepositoryManagerHibernateUtil.beginTrasaction();
						getCadastroTransacao().inserirCliente(operacaoDevolucao.getCliente());
						RepositoryManagerHibernateUtil.commitTransation();
					}catch(Exception e){					
						RepositoryManagerHibernateUtil.beginTrasaction();
						getCadastroTransacao().atualizarCliente(operacaoDevolucao.getCliente());
						RepositoryManagerHibernateUtil.commitTransation();
					}
				}
			}
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroOperacao().alterar(operacao);
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
	
	public Operacao consultarOperacaoPorPK(OperacaoPK pk) throws AppException{
		Operacao operacao = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			operacao = getCadastroOperacao().consultarPorPK(pk);
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
		return operacao;
	}
	
	public Collection consultarOperacao(IPropertyFilter filter) throws AppException{
		Collection col = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			col = getCadastroOperacao().consultar(filter);
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
		return col;
	}

	
	public Integer retornaMaxIdOperacaoPorLoja(OperacaoPK pk) throws AppException{
		Integer maxId = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			maxId = getCadastroOperacao().retornaMaxIdOperacaoPorLoja(pk);
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
		return maxId;
	}


	
	public void alterarStatusOperacao(OperacaoPK pk, int status) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroOperacao().alterarStatus(pk, status);
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
 /// Bancos 
	public void excluirBanco(Banco banco) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroBanco().excluir(banco);
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

	public void alterarBanco(Banco banco) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroBanco().alterar(banco);
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
	
	public void inserirBanco(Banco banco) throws AppException{
	try{
		RepositoryManagerHibernateUtil.beginTrasaction();
		getCadastroBanco().inserir(banco);
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
	public Collection consultarTodosBancos() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroBanco().consultarTodos();
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
	public Banco consultarBancoPorID(String id) throws AppException{
		Banco banco = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			banco = getCadastroBanco().consultarPorId(new Long(id));
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
		return banco;
	}
	public Collection consultarBanco(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroBanco().consultar(filter);
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
	
//	/ Conta Corrente
	public void excluirContaCorrente(ContaCorrente contaCorrente) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroContaCorrente().excluir(contaCorrente);
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

	public void alterarContaCorrente(ContaCorrente contaCorrente) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroContaCorrente().alterar(contaCorrente);
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
	
	public void inserirContaCorrente(ContaCorrente contaCorrente) throws AppException{
	try{
		RepositoryManagerHibernateUtil.beginTrasaction();
		getCadastroContaCorrente().inserir(contaCorrente);
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
	public Collection consultarTodosContaCorrente() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroContaCorrente().consultarTodos();
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
	public ContaCorrente consultarContaCorrentePorID(String id) throws AppException{
		ContaCorrente contaCorrente = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			contaCorrente = getCadastroContaCorrente().consultarPorId(new Long(id));
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
		return contaCorrente;
	}
	public Collection consultarContaCorrente(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroContaCorrente().consultar(filter);
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
	
// Movimentacao Bancaria
	
	public void excluirMovimentacaoBancaria(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroMovimentacaoBancaria().excluir(movimentacaoBancaria);
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

	public void alterarMovimentacaoBancaria(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			getCadastroMovimentacaoBancaria().alterar(movimentacaoBancaria);
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
	
	public void inserirMovimentacaoBancaria(MovimentacaoBancaria movimentacaoBancaria) throws AppException{
	try{
		RepositoryManagerHibernateUtil.beginTrasaction();
		getCadastroMovimentacaoBancaria().inserir(movimentacaoBancaria);
		getCadastroMovimentacaoBancaria().atualizarContasCorrentes();
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
	public Collection consultarTodosMovimentacaoBancaria() throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroMovimentacaoBancaria().consultarTodos();
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
	public MovimentacaoBancaria consultarMovimentacaoBancariaPorID(String id) throws AppException{
		MovimentacaoBancaria movimentacaoBancaria = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			movimentacaoBancaria = getCadastroMovimentacaoBancaria().consultarPorId(new Long(id));
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
		return movimentacaoBancaria;
	}
	public Collection consultarMovimentacaoBancaria(IPropertyFilter filter) throws AppException{
		Collection c = null;
		try{
			RepositoryManagerHibernateUtil.beginTrasaction();
			c = getCadastroMovimentacaoBancaria().consultar(filter);
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
}
