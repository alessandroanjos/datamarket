package com.infinity.datamarket.comum.boleto;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
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
		getRepositorio().inserir(Boleto);
	}
}
