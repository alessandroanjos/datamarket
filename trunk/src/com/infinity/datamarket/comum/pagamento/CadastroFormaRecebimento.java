package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroFormaRecebimento extends Cadastro{
	private static CadastroFormaRecebimento instancia;
	
	private CadastroFormaRecebimento(){}
	public static CadastroFormaRecebimento getInstancia(){
		if (instancia == null){
			instancia = new CadastroFormaRecebimento();
		}
		return instancia;
	}

	public IRepositorioFormaRecebomento getRepositorio() {
		return (IRepositorioFormaRecebomento) super.getRepositorio(IRepositorio.REPOSITORIO_FORMA_RECEBIMENTO);
	}
	
	public FormaRecebimento consultarPorId(Long id) throws AppException{
		return (FormaRecebimento) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(FormaRecebimento formaRecebimento) throws AppException{
		getRepositorio().inserir(formaRecebimento);
	}
	
	public void alterar(FormaRecebimento formaRecebimento) throws AppException{
		getRepositorio().alterar(formaRecebimento);
	}
	
	public void excluir(FormaRecebimento formaRecebimento) throws AppException{
		getRepositorio().excluir(formaRecebimento);
	}

}
