package com.infinity.datamarket.autorizador;

import java.math.BigDecimal;

import javax.ejb.Remote;

@Remote
public interface AutorizadorServerRemote {
	public DadosAutorizacaoCartaoProprio autorizaTransacaoCartaoProprio(String cpfCnpj, BigDecimal valor) throws AutorizacaoException;
	public void confirmaTransacaoCartaoProprio(Long id) throws AutorizacaoException;
	public void desfazTransacaoCartaoProprio(Long id) throws AutorizacaoException;
}
