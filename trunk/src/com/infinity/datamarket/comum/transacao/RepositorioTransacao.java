package com.infinity.datamarket.comum.transacao;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.totalizadores.CadastroTotalizadores;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioTransacao extends Repositorio implements IRepositorioTransacao{
	
	private static RepositorioTransacao instancia;
	private RepositorioTransacao(){}
	public static RepositorioTransacao getInstancia(){
		if (instancia == null){
			instancia = new RepositorioTransacao();
		}
		return instancia;
	}

	private CadastroTotalizadores getCadastroTotalizadores(){
		return CadastroTotalizadores.getInstancia();
	}

	public void inserir(Transacao trans) throws AppException{		
		insert(trans);
	}
	
	public Transacao consultarPorPK(TransacaoPK pk) throws AppException{
		return (Transacao) findById(Transacao.class, pk);
	}
	
	public void atualizar(Transacao trans) throws AppException{
		update(trans);
	}

	public void atualizar(Transacao trans, Collection<EventoItemRegistrado> itensRegistradosRemovidos) throws AppException{
		if(itensRegistradosRemovidos != null){
			Iterator<EventoItemRegistrado> it = itensRegistradosRemovidos.iterator();
			while(it.hasNext()){
				EventoTransacaoPK evTransPK = it.next().getPk();
				removeById(EventoItemRegistrado.class, evTransPK);
			}
		}
		update(trans);
	}

	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}

	public void inserirCliente(ClienteTransacao cli) throws AppException{
		insert(cli);
	}
	
	public void atualizarCliente(ClienteTransacao cli) throws AppException{
		update(cli);
	}
	
	public ClienteTransacao consultarClienteTransacaoPorID(String id) throws AppException{
		return (ClienteTransacao) findById(ClienteTransacao.class, id);
	}

	public Collection consultarClienteTransacao(IPropertyFilter filter) throws AppException{
		return filter(filter, false);
	}
	
}
