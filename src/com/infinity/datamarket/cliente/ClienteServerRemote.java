package com.infinity.datamarket.cliente;

import javax.ejb.Remote;

import com.infinity.datamarket.comum.util.AppException;

@Remote
public interface ClienteServerRemote {
	public DadosCliente consiltarDadosCliente(String cpf_cnpj) throws AppException;
}
