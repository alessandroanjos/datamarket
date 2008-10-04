package com.infinity.datamarket.pdv.mic;

import java.util.Date;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicInicioDia extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		Date dataMov = (Date) gerenciadorPerifericos.getCmos().ler(CMOS.DATA_MOVIMENTO);
		Date dataAtual = new Date();
		
		if (dataMov != null){
			
			if (Util.comparaDatasSemHora(dataMov, dataAtual) >= 0){
				gerenciadorPerifericos.getDisplay().setMensagem("Movimento Dia Fechado");
				try {
					gerenciadorPerifericos.esperaVolta();
				} catch (AppException e) {
					return ALTERNATIVA_2;
				}
				return ALTERNATIVA_2;
			}
			
		}
		
		try{
			gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
			gerenciadorPerifericos.getImpressoraFiscal().inicioDia();
			gerenciadorPerifericos.getCmos().gravar(CMOS.DATA_MOVIMENTO, dataAtual);	
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
