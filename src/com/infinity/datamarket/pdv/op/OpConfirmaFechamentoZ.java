 package com.infinity.datamarket.pdv.op;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpConfirmaFechamentoZ extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			Date dataMov = (Date) gerenciadorPerifericos.getCmos().ler(CMOS.DATA_MOVIMENTO);
			Calendar c = new GregorianCalendar();
			c.setTime(dataMov);
			int dia = c.get(Calendar.DAY_OF_MONTH);
			int mes = c.get(Calendar.MONTH) + 1;
			int ano = c.get(Calendar.YEAR);
			
			gerenciadorPerifericos.getDisplay().setMensagem("Fechamento Z     "+dia+"/"+mes+"/"+ano);
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
				return ALTERNATIVA_1;
			}else{
				return ALTERNATIVA_2;
			}
		} catch (AppException e) {
			e.printStackTrace();
			return ALTERNATIVA_2;
		}
	}
}
