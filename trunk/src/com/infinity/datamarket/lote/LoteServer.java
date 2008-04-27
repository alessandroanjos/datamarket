package com.infinity.datamarket.lote;

import java.util.Collection;

import javax.ejb.Stateless;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;

@Stateless
public class LoteServer implements LoteServerLocal, LoteServerRemote {
	public boolean verificaNovoLoteLiberado(int numeroLote){
		return false;
	}
	public Collection getLote(int numeroLote) throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.addProperty("lote", numeroLote + 1);
		filter.addOrderByProperty("sequencial", filter.ASC);
		return Fachada.getInstancia().consultarDadosLote(filter);
	}
}
