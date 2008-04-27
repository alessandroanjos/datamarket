package com.infinity.datamarket.lote;

import java.util.Collection;

import javax.ejb.Stateless;

@Stateless
public class LoteServer implements LoteServerLocal, LoteServerRemote {
	public boolean verificaNovoLoteLiberado(int numeroLote){
		return false;
	}
	public Collection getLote(int numeroLote){
		return null;
	}
}
