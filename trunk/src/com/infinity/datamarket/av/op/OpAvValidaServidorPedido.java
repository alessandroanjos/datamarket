package com.infinity.datamarket.av.op;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.MensagensAV;
import com.infinity.datamarket.socket.cliente.ClienteServidorPedido;

public class OpAvValidaServidorPedido extends Mic{


	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{

			if (!ClienteServidorPedido.servidorAtivo()) {
				gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Server Pedido OFF"));
				try{
					gerenciadorPerifericos.esperaVolta();
				}catch(AppException ex){

				}

				return ALTERNATIVA_2;
			}

			return ALTERNATIVA_1;

		}catch(Exception e){
			gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Erro"));
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}
	}
}
