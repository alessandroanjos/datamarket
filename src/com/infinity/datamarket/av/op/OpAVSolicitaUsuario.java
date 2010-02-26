package com.infinity.datamarket.av.op;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.AutorizacaoRecusadaException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.MacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpAVSolicitaUsuario extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{

			gerenciadorPerifericos.getDisplay().setMensagem("ID Usuário");
			EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 6);
			String senhaUsu = null;
			String idUsu = null;
			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
				idUsu = entrada.getDado();
			}else{

				gerenciadorPerifericos.getDisplay().setMensagem(OpAvEncerraIniciaAv.MENSAGEM_INICIAL );

				return ALTERNATIVA_2;
			}
			Usuario usu = null;
//			try{
//				MacroOperacao mo =(MacroOperacao) gerenciadorPerifericos.getCmos().ler(CMOS.MACRO_ATUAL);
//				usu = getFachadaPDV().consultarUsuarioPorId_IdMacro(new Long(idUsu), mo.getId());
//			}catch(ObjectNotFoundException ex){
//				gerenciadorPerifericos.getDisplay().setMensagem("Usuário Inválido");
//				try{
//					gerenciadorPerifericos.esperaVolta();
//					return ALTERNATIVA_2;
//				}catch(AppException e){
//
//				}
//			}catch(AutorizacaoRecusadaException ex){
//				gerenciadorPerifericos.getDisplay().setMensagem("Autorização Recusada");
//				try{
//					gerenciadorPerifericos.esperaVolta();
//					return ALTERNATIVA_2;
//				}catch(AppException e){
//
//				}
//			}
//			if (usu == null){
//				gerenciadorPerifericos.getDisplay().setMensagem("Usuário Inválido");
//				try{
//					gerenciadorPerifericos.esperaVolta();
//					return ALTERNATIVA_2;
//				}catch(AppException ex){
//
//				}
//			}

			gerenciadorPerifericos.getDisplay().setMensagem("Senha Usuário");
			entrada = gerenciadorPerifericos.lerDados(new int[]{10,27},Display.MASCARA_PASSWORD, 6);
			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
				senhaUsu = entrada.getDado();
			}else{
				return ALTERNATIVA_2;
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
			
			TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
			gerenciadorPerifericos.getDisplay().setUsuario(usu.getNome());
			gerenciadorPerifericos.atualizaTela(tela);


			gerenciadorPerifericos.getCmos().gravar(CMOS.OPERADOR_ATUAL, usu);
			gerenciadorPerifericos.getCmos().gravar(CMOS.AUTORIZADOR_ATUAL, usu);
			gerenciadorPerifericos.getCmos().gravar(CMOS.USUARIO_ATUAL, usu);
			gerenciadorPerifericos.getDisplay().setUsuario(usu.getNome());
		}catch (ObjectNotFoundException e){
			gerenciadorPerifericos.getDisplay().setMensagem("Usuário Inválido");
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}catch(Exception e){
			gerenciadorPerifericos.getDisplay().setMensagem("Usuário Inválido");
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}
		

		gerenciadorPerifericos.getDisplay().setMensagem(OpAVIniciaPedido.MENSAGEM_INICIAL);
		
		return ALTERNATIVA_1;
	}
}
