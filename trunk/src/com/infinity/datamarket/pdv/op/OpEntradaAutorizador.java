package com.infinity.datamarket.pdv.op;

import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.AutorizacaoRecusadaException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.MacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpEntradaAutorizador extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			gerenciadorPerifericos.getDisplay().setMensagem("ID Autorizador");
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 6);
			String senhaUsu = null;
			String idUsu = null;
			if (entrada.getTeclaFinalizadora() == 10){
				idUsu = entrada.getDado();
			}else{
				return ALTERNATIVA_2;
			}
			Usuario usu = null;
			try{
				MacroOperacao mo =(MacroOperacao) gerenciadorPerifericos.getCmos().ler(CMOS.MACRO_ATUAL);
				usu = getFachadaPDV().consultarUsuarioPorId_IdMacro(new Long(idUsu), mo.getId(), com.infinity.datamarket.pdv.maquinaestados.MacroOperacao.TIPO_COMPONENTE_PDV);
			}catch(ObjectNotFoundException ex){
				gerenciadorPerifericos.getDisplay().setMensagem("Autorizador Inv�lido");
				try{
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}catch(AppException e){

				}
			}catch(AutorizacaoRecusadaException ex){
				gerenciadorPerifericos.getDisplay().setMensagem("Autoriza��o Recusada");
				try{
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}catch(AppException e){

				}
			}
			if (usu == null){
				gerenciadorPerifericos.getDisplay().setMensagem("Autorizador Inv�lido");
				try{
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}catch(AppException ex){

				}
			}



			gerenciadorPerifericos.getDisplay().setMensagem("Senha Autorizador");
			entrada = gerenciadorPerifericos.lerDados(new int[]{10,27},Display.MASCARA_PASSWORD, 6);
			if (entrada.getTeclaFinalizadora() == 10){
				senhaUsu = entrada.getDado();
			}else{
				return ALTERNATIVA_2;
			}

			usu = getFachadaPDV().loginUsuario(new Long(idUsu), senhaUsu);
			if (usu == null){
				gerenciadorPerifericos.getDisplay().setMensagem("Senha Inv�lida");
				try{
					gerenciadorPerifericos.esperaVolta();
					return ALTERNATIVA_2;
				}catch(AppException ex){

				}
			}
			gerenciadorPerifericos.getCmos().gravar(CMOS.AUTORIZADOR_ATUAL, usu);
		}catch (ObjectNotFoundException e){
			gerenciadorPerifericos.getDisplay().setMensagem("Autorizador Inv�lido");
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}catch(Exception e){
			gerenciadorPerifericos.getDisplay().setMensagem("Autorizador Inv�lido");
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
}
