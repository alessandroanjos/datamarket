package com.infinity.datamarket.lote;

import java.util.Collection;

import javax.ejb.Remote;

import com.infinity.datamarket.comum.util.AppException;

@Remote
public interface LoteServerRemote {
	public boolean verificaNovoLoteLiberado(int numeroLote);
	public Collection getLote(int numeroLote, int loja) throws AppException;
}
