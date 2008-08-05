package com.infinity.datamarket.cliente;

import java.util.Collection;

import javax.ejb.Remote;

import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.util.AppException;

@Remote
public interface ClienteServerRemote {
	public ClienteTransacao consultarClienteTransacaoPorID(String id) throws AppException;
}
