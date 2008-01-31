package com.infinity.datamarket.comum.pagamento;

import java.util.Collection;
import java.util.List;

import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroFormaRecebimento extends Cadastro{
	private static CadastroFormaRecebimento instancia;
	private static Class CLASSE = FormaRecebimento.class;
	private CadastroFormaRecebimento(){}
	public static CadastroFormaRecebimento getInstancia(){
		if (instancia == null){
			instancia = new CadastroFormaRecebimento();
		}
		return instancia;
	}

	public FormaRecebimento consultarPorId(Long id) throws AppException{
		return (FormaRecebimento) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultarPorDescricao(String descricao, boolean preciso) throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(CLASSE);
		filter.addProperty("descricao", descricao);
		filter.setIgnoreCase(true);
		List l = getRepositorio().filter(filter, preciso);
		return l;
	}

}
