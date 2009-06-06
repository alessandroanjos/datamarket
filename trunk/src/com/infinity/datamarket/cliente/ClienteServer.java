package com.infinity.datamarket.cliente;

import javax.ejb.Stateless;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.util.AppException;

@Stateless
public class ClienteServer implements ClienteServerLocal, ClienteServerRemote {

	public ClienteTransacao consultarClienteTransacaoPorID(String id) throws AppException{
		return Fachada.getInstancia().consultarClienteTransacaoPorID(id);
	}
}
