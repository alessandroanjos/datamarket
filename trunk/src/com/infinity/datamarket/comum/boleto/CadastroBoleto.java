package com.infinity.datamarket.comum.boleto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.CadastroControleId;
import com.infinity.datamarket.comum.util.Controle;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroBoleto extends Cadastro{
	
	private static CadastroBoleto instancia;
	
//	private static final Class CLASSE = Boleto.class;
	
	public static CadastroBoleto getInstancia(){
		if (instancia == null){
			instancia = new CadastroBoleto();			
		}
		return instancia;
	}
	
	
	public IRepositorioBoleto getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioBoleto) super.getRepositorio(IRepositorio.REPOSITORIO_BOLETO);
	}
	
	public void inserir(Boleto Boleto) throws AppException{
		Controle controle = CadastroControleId.getInstancia().getControle(ArquivoProcessado.class);
		
		Boleto.setId(controle.getValor());
		getRepositorio().inserir(Boleto);
	}
	
	public void alterar(Boleto Boleto) throws AppException{
		getRepositorio().alterar(Boleto);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
}