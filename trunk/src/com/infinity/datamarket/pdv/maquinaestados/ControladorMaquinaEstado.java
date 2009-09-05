package com.infinity.datamarket.pdv.maquinaestados;

import java.io.Serializable;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;

public interface ControladorMaquinaEstado {

	public Tecla consultaTeclaPorId(Long id) throws AppException;

	public MacroOperacao consultaMacroOperacao(IPropertyFilter filtro) throws AppException;
}