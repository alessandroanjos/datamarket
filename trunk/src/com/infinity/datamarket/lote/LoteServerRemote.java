package com.infinity.datamarket.lote;

import java.util.Collection;

import javax.ejb.Remote;

@Remote
public interface LoteServerRemote {
	public boolean verificaNovoLoteLiberado(int numeroLote);
	public Collection getLote(int numeroLote);
}
