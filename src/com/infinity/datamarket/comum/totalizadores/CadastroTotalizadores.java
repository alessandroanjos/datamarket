package com.infinity.datamarket.comum.totalizadores;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.IRepositorio;

public class CadastroTotalizadores extends Cadastro{
	private static CadastroTotalizadores instancia;
	private CadastroTotalizadores(){}
	public static CadastroTotalizadores getInstancia(){
		if (instancia == null){
			instancia = new CadastroTotalizadores();
		}
		return instancia;
	}
	
	
	public IRepositorioTotalizadores getRepositorio() {

		return (IRepositorioTotalizadores) super.getRepositorio(IRepositorio.REPOSITORIO_TOTALIZADORES);
	}

	public void incrementarTotalizador(Long totalizador, BigDecimal valor) throws AppException{
		TotalizadorNaoFiscal tot = (TotalizadorNaoFiscal) getRepositorio().consultarTotalizadorPorPK(totalizador);
		tot.setContador(tot.getContador() + 1);
		tot.setValor(tot.getValor().add(valor));
		getRepositorio().atualizar(tot);
	}

	public void zerarTotalizador(Long totalizador) throws AppException{
		TotalizadorNaoFiscal tot = (TotalizadorNaoFiscal) getRepositorio().consultarTotalizadorPorPK(totalizador);
		tot.setContador(0);
		tot.setValor(new BigDecimal(0));
		getRepositorio().atualizar(tot);
	}

	public TotalizadorNaoFiscal consultarTotalizador(Long totalizador) throws AppException{
		return (TotalizadorNaoFiscal) getRepositorio().consultarTotalizadorPorPK(totalizador);
	}

	public void zerarTodosTotalizadores() throws AppException{
		Collection c = getRepositorio().consultarTodos();
		Iterator i = c.iterator();
		while(i.hasNext()){
			TotalizadorNaoFiscal tot = (TotalizadorNaoFiscal) i.next();
			tot.setContador(0);
			tot.setValor(new BigDecimal(0));
			getRepositorio().atualizar(tot);
		}
	}

}
