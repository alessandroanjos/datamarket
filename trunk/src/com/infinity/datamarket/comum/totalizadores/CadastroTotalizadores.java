package com.infinity.datamarket.comum.totalizadores;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;

public class CadastroTotalizadores extends Cadastro{
	private static CadastroTotalizadores instancia;
	private CadastroTotalizadores(){}
	public static CadastroTotalizadores getInstancia(){
		if (instancia == null){
			instancia = new CadastroTotalizadores();
		}
		return instancia;
	}

	public void incrementarTotalizador(Long totalizador, BigDecimal valor) throws AppException{
		TotalizadorNaoFiscal tot = (TotalizadorNaoFiscal) getRepositorio().findById(TotalizadorNaoFiscal.class, totalizador);
		tot.setContador(tot.getContador() + 1);
		tot.setValor(tot.getValor().add(valor));
		getRepositorio().update(tot);
	}

	public void zerarTotalizador(Long totalizador) throws AppException{
		TotalizadorNaoFiscal tot = (TotalizadorNaoFiscal) getRepositorio().findById(TotalizadorNaoFiscal.class, totalizador);
		tot.setContador(0);
		tot.setValor(new BigDecimal(0));
		getRepositorio().update(tot);
	}

	public TotalizadorNaoFiscal consultarTotalizador(Long totalizador) throws AppException{
		return (TotalizadorNaoFiscal) getRepositorio().findById(TotalizadorNaoFiscal.class, totalizador);
	}

	public void zerarTodosTotalizadores() throws AppException{
		Collection c = getRepositorio().findAll(TotalizadorNaoFiscal.class);
		Iterator i = c.iterator();
		while(i.hasNext()){
			TotalizadorNaoFiscal tot = (TotalizadorNaoFiscal) i.next();
			tot.setContador(0);
			tot.setValor(new BigDecimal(0));
			getRepositorio().update(tot);
		}
	}

}
