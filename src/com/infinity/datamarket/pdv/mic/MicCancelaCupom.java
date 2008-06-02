package com.infinity.datamarket.pdv.mic;


import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicCancelaCupom extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try {
			gerenciadorPerifericos.getImpressoraFiscal().cancelaCupom();
		} catch (ImpressoraFiscalException e) {
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