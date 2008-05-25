package com.infinity.datamarket.cliente;

import java.math.BigDecimal;
import java.util.Collection;

import javax.ejb.Stateless;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;

@Stateless
public class ClienteServer implements ClienteServerLocal, ClienteServerRemote {
	
	public DadosCliente consiltarDadosCliente(String cpf_cnpj) throws AppException{
		DadosCliente dado = new DadosCliente();
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(Cliente.class);
		filter.addProperty("cpfCnpj", cpf_cnpj);
		Collection col = Fachada.getInstancia().consultarCliente(filter);
		Cliente cli = (Cliente) col.iterator().next();
		dado.setNome(cli.getNomeCliente());
		dado.setSaldoDisponivel(cli.getValorLimiteDisponivel());
		return dado;
	}
	
	public void debitar(String cpf_cnpj, BigDecimal valor) throws AppException{
		PropertyFilter filter = new PropertyFilter();
		filter.setTheClass(Cliente.class);
		filter.addProperty("cpfCnpj", cpf_cnpj);
		Collection col = Fachada.getInstancia().consultarCliente(filter);
		Cliente cli = (Cliente) col.iterator().next();
		BigDecimal novoSaldo = cli.getValorLimiteDisponivel().subtract(valor);
		cli.setValorLimiteDisponivel(novoSaldo);
		Fachada.getInstancia().alterarCliente(cli);
	}

}
