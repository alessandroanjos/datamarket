package com.infinity.datamarket.pdv.mic;

import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicInicioDia extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
			gerenciadorPerifericos.getImpressoraFiscal().inicioDia();
		}catch(ImpressoraFiscalException e){
			gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(Exception ex){
				return ALTERNATIVA_2;
			}
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}

}
