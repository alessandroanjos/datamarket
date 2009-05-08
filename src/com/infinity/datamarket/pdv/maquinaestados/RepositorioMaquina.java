package com.infinity.datamarket.pdv.maquinaestados;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Repositorio;

public class RepositorioMaquina extends Repositorio implements IRepositorioMaquina{
	private static RepositorioMaquina instancia;
	private RepositorioMaquina(){

	}
	public static RepositorioMaquina getInstancia(){
		if (instancia == null){
			instancia = new RepositorioMaquina();
		}
		return instancia;
	}

	public Collection consultaTecla(IPropertyFilter filtro) throws AppException{
		return  filter(filtro, true);
	}

	public Tecla consultaTeclaPorId(Long id) throws AppException{
		return (Tecla) findById(Tecla.class, id);
		
	}


	public Collection consultaMacroOperacao(IPropertyFilter filtro) throws AppException{
		return filter(filtro, true);
		
	}
	

}
