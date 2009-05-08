package com.infinity.datamarket.transaction;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.Stateless;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.estoque.CadastroEstoque;
import com.infinity.datamarket.comum.estoque.Estoque;
import com.infinity.datamarket.comum.estoque.EstoquePK;
import com.infinity.datamarket.comum.estoque.EstoqueProduto;
import com.infinity.datamarket.comum.estoque.EstoqueProdutoPK;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.usuario.CadastroLoja;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;
import com.infinity.datamarket.comum.util.RepositorioHI;

@Stateless
public class TransactionServer implements TransactionServerRemote, TransactionServerLocal {
	public TransactionServer(){
		System.out.println("----------------------------------" );
		System.out.println("-- TRANSACTION SERVER INICIADO  --" );
		System.out.println("----------------------------------" );
		
	}
	
	public Fachada getFachada(){
		return Fachada.getInstancia();
	}
	public void inserirTransacao(Transacao transacao) throws AppException{
		getFachada().getInstancia().inserirTransacaoES(transacao);
		System.out.println("TRANSACAO INSERIDA >> "+transacao.getPk());
	}
	
}
