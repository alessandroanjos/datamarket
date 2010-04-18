package com.infinity.datamarket.comum.operacao;

import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Query;

import com.infinity.datamarket.comum.estoque.CadastroEstoqueProduto;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.EstoquePK;
import com.infinity.datamarket.comum.estoque.EstoqueProduto;
import com.infinity.datamarket.comum.estoque.EstoqueProdutoPK;
import com.infinity.datamarket.comum.estoque.IRepositorioEstoqueProduto;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.usuario.CadastroLoja;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;
import com.infinity.datamarket.comum.util.ValidationException;

public class CadastroOperacao extends Cadastro{
	private static CadastroOperacao instancia;
	private CadastroOperacao(){}
	public static CadastroOperacao getInstancia(){
		if (instancia == null){
			instancia = new CadastroOperacao();
		}
		return instancia;
	}
	
	public IRepositorioEstoqueProduto getRepositorioEstoqueProduto() {
		return (IRepositorioEstoqueProduto) super.getRepositorio(IRepositorio.REPOSITORIO_ESTOQUE_PRODUTO);
	}
		
	public IRepositorioOperacao getRepositorio() {
		return (IRepositorioOperacao) super.getRepositorio(IRepositorio.REPOSITORIO_OPERACAO);
	}

	public void inserirES(Operacao operacao) throws AppException{		
		getRepositorio().inserir(operacao);
		if (operacao instanceof OperacaoDevolucao){
			OperacaoDevolucao operacaoDevolucao = (OperacaoDevolucao) operacao;
			if(operacaoDevolucao.getStatus() != ConstantesOperacao.CANCELADO){
				Collection col = operacaoDevolucao.getEventosOperacao();
				if (col != null && col.size() > 0){
					Iterator i = col.iterator();
					while(i.hasNext()){
						EventoOperacao evt = (EventoOperacao) i.next();
						if (evt instanceof EventoOperacaoItemRegistrado){
							EventoOperacaoItemRegistrado evir = (EventoOperacaoItemRegistrado) evt;							
							Loja l = CadastroLoja.getInstancia().consultarPorId(new Long(evir.getPk().getLoja()));
							EstoqueProdutoPK pk = new EstoqueProdutoPK();
							EstoquePK epk = new EstoquePK();
							epk.setLoja(l);
							epk.setId(l.getIdEstoque());
							Estoque e = new Estoque();
							e.setPk(epk);
							pk.setEstoque(e);
							Produto p = new Produto();
							p.setId(new Long(evir.getProdutoOperacaoItemRegistrado().getIdProduto()));
							pk.setProduto(p);
							try{
								EstoqueProduto estoqueProduto = CadastroEstoqueProduto.getInstancia().consultarPorId(pk);
								estoqueProduto.adicionarQuantidade(evir.getQuantidade(),evir.getVencimento());
								getRepositorioEstoqueProduto().alterar(estoqueProduto);
							}catch(ObjectNotFoundException ex){
								EstoqueProduto estoqueProduto = new EstoqueProduto();
								estoqueProduto.setPk(pk);
								estoqueProduto.adicionarQuantidade(evir.getQuantidade(),evir.getVencimento());
								getRepositorioEstoqueProduto().inserir(estoqueProduto);
							}catch(Exception ex){
								System.out.println("Estoque da loja "+evir.getPk().getLoja()+" não foi atualizado");
								ex.printStackTrace();
							}			
						}
					}
				}
			}
		}
	}
	

	
	public void alterar(Operacao operacao)throws AppException{
		getRepositorio().alterar(operacao);
	}

	public void alterar(Operacao operacao, Collection<EventoOperacaoItemRegistrado> itensPedidoRemovidos)throws AppException{
		getRepositorio().alterar(operacao);
	}
	
	public Operacao consultarPorPK(OperacaoPK pk) throws AppException{
		Operacao operacao = (Operacao) getRepositorio().consultarPorPK(pk);
		return operacao;
	}
		
	public boolean existeOperacao(int idLoja, int idOperacao) throws AppException{

		StringBuffer sb = new StringBuffer();

		sb.append("select max(operacao.pk.id) from Operacao operacao where operacao.pk.loja = " + idLoja + " and operacao.pk.id = " + idOperacao);
		Query query = RepositoryManagerHibernateUtil.getInstancia().currentSession().createQuery(sb.toString());
		Integer qretorno = (Integer)query.uniqueResult(); 		
		if (qretorno == null) {
			return false;
		} else {
			return true;
		}
	}

	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public void alterarStatus(OperacaoPK pk, int status) throws AppException{		
		if (status == ConstantesOperacao.ABERTO || status == ConstantesOperacao.CANCELADO||
				status == ConstantesOperacao.FECHADO || status == ConstantesOperacao.EM_PROCESSAMENTO || status == ConstantesOperacao.EM_SEPARACAO
				 || status == ConstantesOperacao.EM_ALTERACAO){
			Operacao operacao = (Operacao) getRepositorio().consultarPorPK(pk);
			operacao.setStatus(status);
			getRepositorio().alterar(operacao);
		}else{
			throw new ValidationException("Status de Operação Inválido");
		}
	}

	public Integer retornaMaxIdOperacaoPorLoja(OperacaoPK pk) throws AppException{
		Integer retorno = new Integer(0);
		StringBuffer sb = new StringBuffer();
		sb.append("select max(operacao.pk.id) from Operacao operacao where loja = " + pk.getLoja());
		Query query = RepositoryManagerHibernateUtil.getInstancia().currentSession().createQuery(sb.toString());
		Integer qretorno = (Integer)query.uniqueResult(); 		
		if (qretorno != null){
			retorno = qretorno + 1;	
		}else{
			retorno++;	
		}		
		return retorno;
	}
	
	public void excluir(Operacao operacao)throws AppException{
		getRepositorio().excluir(operacao);
	}
	
	public void atualizaOperacaoEnviada(Operacao oper) throws AppException{
		oper.setStatus(ConstantesOperacao.ENVIADO);
		alterar(oper);
	}
	
	public void atualiza(Operacao oper) throws AppException{
		alterar(oper);
	}
}
