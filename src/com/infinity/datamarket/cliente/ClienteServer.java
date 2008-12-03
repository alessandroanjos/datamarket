package com.infinity.datamarket.cliente;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.ejb.Stateless;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.lote.DadoLote;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;

@Stateless
public class ClienteServer implements ClienteServerLocal, ClienteServerRemote {

	public ClienteTransacao consultarClienteTransacaoPorID(String id) throws AppException{
		return Fachada.getInstancia().consultarClienteTransacaoPorID(id);
	}
}
