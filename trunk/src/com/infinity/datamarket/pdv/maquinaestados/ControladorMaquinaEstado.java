package com.infinity.datamarket.pdv.maquinaestados;

import java.io.Serializable;
import java.util.Map;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;

public interface ControladorMaquinaEstado extends Serializable {

	public Tecla consultaTeclaPorId(Long id) throws AppException;

	public MacroOperacao consultaMacroOperacao(IPropertyFilter filtro) throws AppException;

	public Estado getEstado(Long id) throws AppException;

	/**
	 * Método que retorna todas as teclas e os macros que cada tecla leva, a partir de um estado
	 *  
	 * @param estado
	 * @return
	 * @throws AppException
	 */
	public Map<Tecla, MacroOperacao> getDescTeclasDescMacro(Long idEstadoAtual) throws AppException;

}