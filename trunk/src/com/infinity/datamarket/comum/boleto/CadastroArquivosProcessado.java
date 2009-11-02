package com.infinity.datamarket.comum.boleto;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroArquivosProcessado extends Cadastro{
	
	private static CadastroArquivosProcessado instancia;
	
//	private static final Class CLASSE = ArquivosProcessado.class;
	
	public static CadastroArquivosProcessado getInstancia(){
		if (instancia == null){
			instancia = new CadastroArquivosProcessado();			
		}
		return instancia;
	}
	
	public IRepositorioArquivosProcessado getRepositorio() {
		// TODO Auto-generated method stub
		return (IRepositorioArquivosProcessado) super.getRepositorio(IRepositorio.REPOSITORIO_ARQUIVO_PROCESSADO);
	}
	
	public void inserir(ArquivosProcessado ArquivosProcessado) throws AppException{
		getRepositorio().inserir(ArquivosProcessado);
	}
	
	public void alterar(ArquivosProcessado ArquivosProcessado) throws AppException{
		getRepositorio().alterar(ArquivosProcessado);
	}
	
	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().consultar(filter);
	}
}