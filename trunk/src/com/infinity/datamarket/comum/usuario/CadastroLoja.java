package com.infinity.datamarket.comum.usuario;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroLoja extends Cadastro{
	private static CadastroLoja instancia;
	private static Class CLASSE = Loja.class;
	private CadastroLoja(){}
	public static CadastroLoja getInstancia(){
		if (instancia == null){
			instancia = new CadastroLoja();
		}
		return instancia;
	}

	public Loja consultarPorId(Long id) throws AppException{
		return (Loja) getRepositorio().findById(CLASSE, id);
	}
}
