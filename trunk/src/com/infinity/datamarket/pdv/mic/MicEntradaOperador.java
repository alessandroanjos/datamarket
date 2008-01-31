package com.infinity.datamarket.pdv.mic;

import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class MicEntradaOperador extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			gerenciadorPerifericos.getDisplay().setMensagem("ID Operador");
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 6);
			String senhaUsu = null;
			String idUsu = null;
			if (entrada.getTeclaFinalizadora() == 10){
				idUsu = entrada.getDado();
			}else{
				return ALTERNATIVA_3;
			}
			Usuario usu = null;
			try{
				usu = getFachadaPDV().consultarUsuarioPorId(new Long(idUsu));
			}catch(ObjectNotFoundException ex){
				gerenciadorPerifericos.getDisplay().setMensagem("Operador Inválido");
				try{
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}catch(AppException e){

				}
			}
			if (usu == null){
				gerenciadorPerifericos.getDisplay().setMensagem("Operador Inválido");
				try{
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}catch(AppException ex){

				}
			}

			gerenciadorPerifericos.getDisplay().setMensagem("Senha Operador");
			entrada = gerenciadorPerifericos.lerDados(new int[]{10,27},Display.MASCARA_PASSWORD, 6);
			if (entrada.getTeclaFinalizadora() == 10){
				senhaUsu = entrada.getDado();
			}else{
				return ALTERNATIVA_3;
			}

			usu = getFachadaPDV().loginUsuario(new Long(idUsu), senhaUsu);
			if (usu == null){
				gerenciadorPerifericos.getDisplay().setMensagem("Senha Inválida");
				try{
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}catch(AppException ex){

				}
			}
			gerenciadorPerifericos.getCmos().gravar(CMOS.OPERADOR_ATUAL, usu);
		}catch (ObjectNotFoundException e){
			gerenciadorPerifericos.getDisplay().setMensagem("Operador Inválido");
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}catch(Exception e){
			gerenciadorPerifericos.getDisplay().setMensagem("Operador Inválido");
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
}
