package com.infinity.datamarket.comum.usuario;

import java.util.Iterator;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.AutorizacaoRecusadaException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.pdv.maquinaestados.MacroOperacao;

public class CadastroUsuario extends Cadastro{
	private static CadastroUsuario instancia;
	private static Class CLASSE = Usuario.class;
	private CadastroUsuario(){}
	public static CadastroUsuario getInstancia(){
		if (instancia == null){
			instancia = new CadastroUsuario();
		}
		return instancia;
	}

	public Usuario consultarPorId(Long id) throws AppException{
		return (Usuario) getRepositorio().findById(CLASSE, id);
	}

	public Usuario consultarPorId_IdMacro(Long id,Long idMacro) throws AppException{
		Usuario usu = (Usuario) getRepositorio().findById(CLASSE, id);
		if (verificaAutorizacao(usu, idMacro)){
			return usu;
		}else{
			throw new AutorizacaoRecusadaException("Autorizacao Recusada");
		}
	}

	private boolean verificaAutorizacao(Usuario usu, Long idMacro){
		Iterator i = usu.getPerfil().getOperacoes().iterator();
		while(i.hasNext()){
			MacroOperacao mo = (MacroOperacao) i.next();
			if (idMacro.equals(mo.getId())){
				return true;
			}
		}
		return false;

	}

	public Usuario login(Long id, String senha) throws AppException{
		Usuario usu = (Usuario) getRepositorio().findById(CLASSE, id);
		if (usu.getSenha().equals(senha)){
			return usu;
		}
		return null;
	}

}
