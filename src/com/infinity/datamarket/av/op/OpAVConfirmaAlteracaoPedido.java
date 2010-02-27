package com.infinity.datamarket.av.op;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.util.MensagensAV;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class OpAVConfirmaAlteracaoPedido extends OpAVEncerraPedido {

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{
			OperacaoPedido pedido = (OperacaoPedido)gerenciadorPerifericos.getCmos().ler(CMOS.OPERACAO_PEDIDO);

			gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Confirma Pedido ?"));

			EntradaDisplay entrada;

			entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);

			if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_VOLTA){
				try{						

					URL urlCon = new URL("http://" +
							ServerConfig.HOST_SERVIDOR_ES +
							":" +
							ServerConfig.PORTA_SERVIDOR_ES +
							"/" +
							ServerConfig.CONTEXTO_SERVIDOR_ES +
							"/" +
							ServerConfig.ALTERAR_OPERACA_SERVLET +"?status=" + ConstantesOperacao.ABERTO);
					URLConnection huc1 = urlCon.openConnection();

					huc1.setAllowUserInteraction(true);
					huc1.setDoOutput(true);

					ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
					output.writeObject(pedido.getPk());
					ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
					Object obj = input.readObject();
					if (obj instanceof String && obj.equals("OK")) {

						
					} else  if (obj instanceof AppException){
						((Exception)obj).printStackTrace();
						gerenciadorPerifericos.getDisplay().setMensagem(((Exception)obj).getMessage());
						gerenciadorPerifericos.esperaVolta();
						return ALTERNATIVA_2;
					} else  if (obj instanceof Exception){
						((Exception)obj).printStackTrace();
						gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
						gerenciadorPerifericos.esperaVolta();
						return ALTERNATIVA_2;
					}
				} catch (Exception e) {
					gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
					try {
						gerenciadorPerifericos.esperaVolta();
					} catch (AppException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return ALTERNATIVA_2;
				}

				return ALTERNATIVA_2;
			} else {
				gerenciadorPerifericos.getDisplay().setMensagem(MensagensAV.getMensagem(this, "Codigo do Produto"));
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