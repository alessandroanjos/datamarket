package com.infinity.datamarket.comum.cliente;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroCliente extends Cadastro {
	private static CadastroCliente instancia;
	private static Class CLASSE = Cliente.class;
	private CadastroCliente(){}
	
	public static CadastroCliente getInstancia(){
		if (instancia == null){
			instancia = new CadastroCliente();
		}
		return instancia;
	}
	
	public Cliente consultarPorId(Long id) throws AppException{
		return (Cliente) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	
	public Cliente consultarPorPK(Long id) throws AppException{
		return (Cliente) getRepositorio().findById(CLASSE, id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Cliente cliente) throws AppException{
		getRepositorio().insert(cliente);
	}
	
	public void alterar(Cliente cliente) throws AppException{
		getRepositorio().update(cliente);
		IPropertyFilter filter = new PropertyFilter();
		filter.setTheClass(ClienteTransacao.class);
		filter.addProperty("cpfCnpj", cliente.getCpfCnpj());
		Collection colecaoClienteTransacao = consultar(filter);
		if(colecaoClienteTransacao != null && colecaoClienteTransacao.size() > 0){
			Iterator itClienteTransacao = colecaoClienteTransacao.iterator();
			while(itClienteTransacao.hasNext()){
				ClienteTransacao cliTrans = (ClienteTransacao) itClienteTransacao.next();
				if(cliente.getTipoPessoa().equals(cliTrans.getTipoPessoa()) &&
						cliente.getCpfCnpj().equals(cliTrans.getCpfCnpj())){
					if(cliente.getTipoPessoa().equals(Cliente.PESSOA_FISICA)){
						cliTrans.setNomeCliente(cliente.getNomeCliente());	
					}else{
						cliTrans.setNomeCliente(cliente.getRazaoSocial());
						cliTrans.setInscricaoEstadual(cliente.getInscricaoEstadual());
						cliTrans.setInscricaoMunicipal(cliente.getInscricaoMunicipal());
					}
					cliTrans.setLogradouro(cliente.getLogradouro());
					cliTrans.setNumero(cliente.getNumero());
					cliTrans.setComplemento(cliente.getComplemento());
					cliTrans.setBairro(cliente.getBairro());
					cliTrans.setCep(cliente.getCep());
					cliTrans.setCidade(cliente.getCidade());
					cliTrans.setEstado(cliente.getEstado());
					cliTrans.setFone(cliente.getFoneContato());	
					cliTrans.setCelular(cliente.getFoneCelular());
			    	cliTrans.setPessoaContato(cliente.getPessoaContato());
			    	cliTrans.setReferenciaBancaria(cliente.getReferenciaComercial());
			    	getRepositorio().update(cliTrans);
				}
			}
		}
	}
	
	public void excluir(Cliente cliente) throws AppException{
		getRepositorio().remove(cliente);
	}
}
