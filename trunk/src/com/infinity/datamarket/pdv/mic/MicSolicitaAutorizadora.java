package com.infinity.datamarket.pdv.mic;

import com.infinity.datamarket.comum.pagamento.Autorizadora;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicSolicitaAutorizadora extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			do{
				gerenciadorPerifericos.getDisplay().setMensagem("Selecione o Cartão");
				EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 2);
				if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
					String valor = entrada.getDado();
					Long id = null;
					try{
						id = new Long(valor);
					}catch(NumberFormatException e){
						continue;
					}
					Autorizadora aut = null;
					try{
						aut =  getFachadaPDV().consultarAutorizadoraPorId(id);
					}catch(ObjectNotFoundException e){
						gerenciadorPerifericos.getDisplay().setMensagem("Opção Inválida");
						gerenciadorPerifericos.esperaVolta();
						continue;
					}
					if (aut == null){					
						gerenciadorPerifericos.getDisplay().setMensagem("Opção Inválida");
						gerenciadorPerifericos.esperaVolta();
						continue;
					}

					if (aut != null && aut.getSituacao().equals(Constantes.STATUS_INATIVO)){
						gerenciadorPerifericos.getDisplay().setMensagem("Opção Inválida");
						gerenciadorPerifericos.esperaVolta();
						continue;
					}
					gerenciadorPerifericos.getCmos().gravar(CMOS.AUTORIZADORA_CARTAO_OFF,aut.getId().toString());
					return ALTERNATIVA_1;
				}else{
					return ALTERNATIVA_2;
				}
			}while(true);
		}catch(AppException e){
			return ALTERNATIVA_2;
		}
	}

}
