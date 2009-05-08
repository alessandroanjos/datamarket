package com.infinity.datamarket.comum.cliente;

import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.IRepositorioTransacao;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroCliente extends Cadastro {
	private static CadastroCliente instancia;
	private CadastroCliente(){}
	
	public static CadastroCliente getInstancia(){
		if (instancia == null){
			instancia = new CadastroCliente();
		}
		return instancia;
	}
	
	
	public IRepositorioCliente getRepositorio() {
		return (IRepositorioCliente) super.getRepositorio(IRepositorio.REPOSITORIO_CLIENTE);
	}
	
	public IRepositorioTransacao getRepositorioTransacao() {
		return (IRepositorioTransacao) super.getRepositorio(IRepositorio.REPOSITORIO_TRANSACAO);
	}
	
	public Cliente consultarPorId(Long id) throws AppException{
		return (Cliente) getRepositorio().consultarPorId(id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
	
	public Cliente consultarPorPK(Long id) throws AppException{
		return (Cliente) getRepositorio().consultarPorPK(id);
	}
	
	public Collection consultarTodos() throws AppException{
		return getRepositorio().consultarTodos();
	}
	public void inserir(Cliente cliente) throws AppException{
		getRepositorio().inserir(cliente);
	}
	
	public void alterar(Cliente cliente) throws AppException{
		getRepositorio().alterar(cliente);
		IPropertyFilter filter = new PropertyFilter();
		filter.setTheClass(ClienteTransacao.class);
		filter.addProperty("cpfCnpj", cliente.getCpfCnpj());
		Collection colecaoClienteTransacao = getRepositorioTransacao().consultarClienteTransacao(filter);
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
			    	getRepositorioTransacao().atualizarCliente(cliTrans);
				}
			}
		}
	}
	
	public void excluir(Cliente cliente) throws AppException{
		getRepositorio().excluir(cliente);
	}
}
