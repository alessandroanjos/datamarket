package com.infinity.datamarket.geradorbase;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.hibernate.Session;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.banco.Banco;
import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.conta.ContaCorrente;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Perfil;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Parametro;
import com.infinity.datamarket.comum.util.ServiceLocator;

public abstract class GeradorBaseComponente {

	public void geraBase(Long codigoLoja) {
		System.out.println("######################################################################");
		System.out.println("## INICIO DA GERA플O DA BASE DE COMPONENTE PARA LOJA "
						+ codigoLoja + " ##");
		System.out.println("######################################################################");
		System.out.println();
		try {
			inicio(codigoLoja);
		} catch (Exception e) {
			System.out.println("## ERRO NA INICIALIZA플O DA GERA플O DA BASE!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
			return;
		}
		geraBaseAcumuladorNaoFiscal();
		geraBaseTotalizadoresNaoFiscais();
		geraBaseAutorizadora();
		geraBaseLoja(codigoLoja);
		geraBaseComponente(codigoLoja);
		geraBaseFormaRecebimento();
		geraBaseMacroOperacao();
		geraBasePerfil();
		geraBaseUsuario(codigoLoja);
		geraBaseImposto();
		geraBaseFabricante();
		geraBaseTipoProduto();
		geraBaseGrupoProduto();
		geraBaseUnidade();
		geraBaseProduto();
		geraBasePlanoPagamento();
		geraBaseBanco();
		geraBaseContaCorrente(codigoLoja);
		geraBaseParametros(codigoLoja);
		
		System.out.println();
		System.out.println("######################################################################");
		System.out.println("##   FIM DA GERA플O DA BASE DE COMPONENTE PARA LOJA " + codigoLoja + "  ##");
		System.out.println("######################################################################");

	}

	private void geraBaseAcumuladorNaoFiscal() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection acumuladores = Fachada.getInstancia().consultarTodosAcumuladoresNaoFiscais();
			session.clear();
			geraBaseAcumuladorNaoFiscal(acumuladores);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE ACUMULADORES GERADA OK!!");
			System.out.println("## REGISTROS => " + acumuladores.size());
		} catch (Exception e) {
			System.out.println("## BASE DE ACUMULADORES GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseMacroOperacao() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection macros = Fachada.getInstancia()
					.consultarTodosMacroOperacao();
			session.clear();
			geraBaseMacroOperacao(macros);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE MACROS GERADA OK!!");
			System.out.println("## REGISTROS => " + macros.size());
		} catch (Exception e) {
			System.out.println("## BASE DE MACROS GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseAutorizadora() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection autorizadoras = Fachada.getInstancia()
					.consultarTodasAutorizadoras();
			session.clear();
			geraBaseAutorizadora(autorizadoras);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE AUTORIZADORA GERADA OK!!");
			System.out.println("## REGISTROS => " + autorizadoras.size());
		} catch (Exception e) {
			System.out.println("## BASE DE AUTORIZADORA GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseComponente(Long loja) {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Componente.class);
			filter.addProperty("loja.id", loja);
			Collection componentes = Fachada.getInstancia()
					.consultarComponentes(filter);
			session.clear();
			geraBaseComponente(componentes);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE COMPONENTE GERADA OK!!");
			System.out.println("## REGISTROS => " + componentes.size());
		} catch (Exception e) {
			System.out.println("## BASE DE COMPONENTE GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseFormaRecebimento() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection formas = Fachada.getInstancia()
					.consultarTodosFormaRecebimento();
			session.clear();
			geraBaseFormaRecebimento(formas);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE FORMA GERADA OK!!");
			System.out.println("## REGISTROS => " + formas.size());
		} catch (Exception e) {
			System.out.println("## BASE DE FORMA GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseLoja(Long loja) {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Loja.class);
			filter.addProperty("id", loja);
			Collection lojas = Fachada.getInstancia().consultarLoja(filter);
			session.clear();
			geraBaseLoja(lojas);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE LOJA GERADA OK!!");
			System.out.println("## REGISTROS => " + lojas.size());
		} catch (Exception e) {
			System.out.println("## BASE DE LOJA GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBasePlanoPagamento() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection<PlanoPagamento> planos = Fachada.getInstancia().consultarTodosPlanos();
			session.clear();
			geraBasePlanos(planos);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE PLANO GERADA OK!!");
			System.out.println("## REGISTROS => " + planos.size());
		} catch (Exception e) {
			System.out.println("## BASE DE PLANO GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseBanco() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection<Banco> bancos = Fachada.getInstancia().consultarTodosBancos();
			session.clear();
			geraBaseBancos(bancos);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE BANCOS GERADA OK!!");
			System.out.println("## REGISTROS => " + bancos.size());
		} catch (Exception e) {
			System.out.println("## BASE DE BANCOS GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}


	private void geraBaseContaCorrente(long iLoja) {
		try {
			
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			
			IPropertyFilter filter = new PropertyFilter();
	    	filter.setTheClass(ContaCorrente.class);
	    	
	    	filter.addProperty("loja.id", new Long(iLoja));
	    	
	    	Collection<ContaCorrente> cc = Fachada.getInstancia().consultarContaCorrente(filter);
			session.clear();
			geraBaseContaCorrente(cc);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE Conta corrente GERADA OK!!");
			System.out.println("## REGISTROS => " + cc.size());
		} catch (Exception e) {
			System.out.println("## BASE DE Conta corrente GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseTotalizadoresNaoFiscais() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection totalizadores = Fachada.getInstancia()
					.consultarTodosTotalizadores();
			session.clear();
			geraBaseTotalizadoresNaoFiscais(totalizadores);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE TOTALIZADORES GERADA OK!!");
			System.out.println("## REGISTROS => " + totalizadores.size());
		} catch (Exception e) {
			System.out.println("## BASE DE TOTALIZADORES GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseUsuario(Long loja) {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection usuarios = Fachada.getInstancia()
					.consultarTodosUsuario();
			Iterator i1 = usuarios.iterator();
			while (i1.hasNext()) {
				Usuario u = (Usuario) i1.next();
				Collection lojas = u.getLojas();
				Iterator i2 = lojas.iterator();
				boolean pertence = false;
				while (i2.hasNext()) {
					Loja l = (Loja) i2.next();
					if (l.getId().equals(loja)) {
						pertence = true;
					}
				}
				if (!pertence) {
					i1.remove();
				}
				u.setLojas(null);
			}
			session.clear();
			geraBaseUsuarios(usuarios);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE USUARIOS GERADA OK!!");
			System.out.println("## REGISTROS => " + usuarios.size());
		} catch (Exception e) {
			System.out.println("## BASE DE USUARIOS GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseProduto() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection produtos = Fachada.getInstancia()
					.consultarTodosProdutos();
			session.clear();
			geraBaseProsutos(produtos);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE PRODUTOS GERADA OK!!");
			System.out.println("## REGISTROS => " + produtos.size());
		} catch (Exception e) {
			System.out.println("## BASE DE PRODUTOS GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseImposto() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection impostos = Fachada.getInstancia()
					.consultarTodosImpostos();
			session.clear();
			geraBaseImposto(impostos);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE IMPOSTO GERADA OK!!");
			System.out.println("## REGISTROS => " + impostos.size());
		} catch (Exception e) {
			System.out.println("## BASE DE IMPOSTO GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseFabricante() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection fabricantes = Fachada.getInstancia()
					.consultarTodosFabricantees();
			session.clear();
			geraBaseFabricante(fabricantes);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE FABRICANTES GERADA OK!!");
			System.out.println("## REGISTROS => " + fabricantes.size());
		} catch (Exception e) {
			System.out.println("## BASE DE FABRICANTES GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseTipoProduto() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection tipos = Fachada.getInstancia()
					.consultarTodosTipoProduto();
			session.clear();
			geraBaseTipoProduto(tipos);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE TIPO PRODUTO GERADA OK!!");
			System.out.println("## REGISTROS => " + tipos.size());
		} catch (Exception e) {
			System.out.println("## BASE DE TIPO PRODUTO GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseUnidade() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection unidades = Fachada.getInstancia()
					.consultarTodasUnidades();
			session.clear();
			geraBaseUnidade(unidades);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE UNIDADE GERADA OK!!");
			System.out.println("## REGISTROS => " + unidades.size());
		} catch (Exception e) {
			System.out.println("## BASE DE UNIDADE GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBaseGrupoProduto() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection grupos = Fachada.getInstancia()
					.consultarTodosGruposProduto();
			session.clear();
			geraBaseGrupoProduto(grupos);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE GRUPO GERADA OK!!");
			System.out.println("## REGISTROS => " + grupos.size());
		} catch (Exception e) {
			System.out.println("## BASE DE GRUPO GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void geraBasePerfil() {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection<Perfil> perfis = Fachada.getInstancia().consultarTodosPerfil();
			for (Perfil object : perfis) {
				HashSet c = new HashSet(object.getOperacoes());
				object.setOperacoes(c);
			}
			session.clear();
			geraBasePerfil(perfis);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE PERFIL GERADA OK!!");
			System.out.println("## REGISTROS => " + perfis.size());
		} catch (Exception e) {
			System.out.println("## BASE DE PERFIL GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}


	private void geraBaseParametros(long idLoja) {
		try {
			Session session = RepositoryManagerHibernateUtil.getInstancia().currentSession();
			Collection<Parametro> Parametros = Fachada.getInstancia().consultarTodosParametro();
			session.clear();
			for (Parametro parametro : Parametros) {
				if(parametro.getChave().equalsIgnoreCase(ConcentradorParametro.LOJA)){
					parametro.setValor(idLoja + "");	
				}
			}
			geraBaseParametros(Parametros);
			RepositoryManagerHibernateUtil.getInstancia().closeSession();
			System.out.println("## BASE DE Parametros GERADA OK!!");
			System.out.println("## REGISTROS => " + Parametros.size());
		} catch (Exception e) {
			System.out.println("## BASE DE Parametros GERADA COM ERRO!!");
			System.out.println("## ERRO => " + e.getMessage());
			e.printStackTrace();
		}
	}

	protected abstract void geraBaseAcumuladorNaoFiscal(Collection col)
			throws Exception;

	protected abstract void geraBaseAutorizadora(Collection col)
			throws Exception;

	protected abstract void geraBaseComponente(Collection col) throws Exception;

	protected abstract void geraBaseFormaRecebimento(Collection col)
			throws Exception;

	protected abstract void geraBaseLoja(Collection col) throws Exception;

	protected abstract void geraBasePlanos(Collection col) throws Exception;

	protected abstract void geraBaseUsuarios(Collection col) throws Exception;

	protected abstract void geraBaseTotalizadoresNaoFiscais(Collection col)
			throws Exception;

	protected abstract void geraBaseProsutos(Collection col) throws Exception;

	protected abstract void geraBaseImposto(Collection col) throws Exception;

	protected abstract void geraBaseFabricante(Collection col) throws Exception;

	protected abstract void geraBaseTipoProduto(Collection col)
			throws Exception;

	protected abstract void geraBaseUnidade(Collection col) throws Exception;

	protected abstract void geraBaseGrupoProduto(Collection col)
			throws Exception;

	protected abstract void geraBasePerfil(Collection col) throws Exception;

	protected abstract void geraBaseMacroOperacao(Collection col)
			throws Exception;

	protected abstract void inicio(Long loja) throws Exception;

	protected abstract void geraBaseBancos(Collection col) throws Exception;

	protected abstract void geraBaseParametros(Collection col) throws Exception;

	protected abstract void geraBaseContaCorrente(Collection col) throws Exception;

	public static void main(String[] a) {
		GeradorBaseComponente gerador = (GeradorBaseComponente) ServiceLocator
				.getInstancia()
				.getObjectToIntancia(
						"com.infinity.datamarket.geradorbase.GeradorBaseComponenteHibernate");
		gerador.geraBase(new Long(1));
	}

}
