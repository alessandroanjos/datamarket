package com.infinity.datamarket.pdv.mic;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicZeraTotalizadoresNaoFiscais extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try{
			getFachadaPDV().zerarTodosTotalizadores();
		}catch(AppException e){
			e.printStackTrace();
		}

		return ALTERNATIVA_1;
	}
}
