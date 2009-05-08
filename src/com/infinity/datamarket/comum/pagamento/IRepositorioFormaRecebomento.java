package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.IRepositorio;

public interface IRepositorioFormaRecebomento extends IRepositorio{

	public FormaRecebimento consultarPorId(Long id) throws AppException;
	public Collection consultar(IPropertyFilter filter) throws AppException;
	public Collection consultarTodos() throws AppException;
	public void inserir(FormaRecebimento formaRecebimento) throws AppException;
	public void alterar(FormaRecebimento formaRecebimento) throws AppException;
	public void excluir(FormaRecebimento formaRecebimento) throws AppException;
	
}
