package com.infinity.datamarket.pdv.op;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gui.telas.swing.ConsultaPedidoFrame;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.ServerConfig;

public abstract class OpConsultaTelaPedidoAbstrato extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){		

		gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
		
		Collection pedidos = null;
		
		try{
			URL urlCon = new URL("http://" +
					ServerConfig.HOST_SERVIDOR_ES +
					":" +
					ServerConfig.PORTA_SERVIDOR_ES +
					"/" +
					ServerConfig.CONTEXTO_SERVIDOR_ES +
					"/" +
					ServerConfig.CONSULTAR_OPERACOES_SERVLET);
			URLConnection huc1 = urlCon.openConnection();
	
			huc1.setAllowUserInteraction(true);	
			
			huc1.setDoOutput(true);

			ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
			output.writeObject(getEstadoOperacaoConsulta());
			
			ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
			Object obj = input.readObject();
			if (obj instanceof Collection) {
				pedidos = (Collection) obj;
			} else  if (obj instanceof Exception){
				gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;		
			}
		}catch(Exception e){
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}
		
		if (pedidos != null && pedidos.size() > 0){
			gerenciadorPerifericos.getDisplay().setMensagem("Selecione o Pedido");
			ConsultaPedidoFrame c = new ConsultaPedidoFrame(gerenciadorPerifericos.getWindow().getFrame(),pedidos);
		
	    	c.setSize(800, 530);
	    	c.play();
	    	if (c.getRetornoTela() == c.BUTTON_OK){
	    		OperacaoPedido pedido = c.getValor();
	    		int retorno = alteraOperacaoServlet(gerenciadorPerifericos, pedido);
	    		if (ALTERNATIVA_2 == retorno) {
	    			return retorno;
	    		}
	    		
	    		gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_PEDIDO, pedido);
	    		return ALTERNATIVA_1;
	    	}else{
	    		gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_PEDIDO, null);
	    		return ALTERNATIVA_2;
	    	}
		}else{
			gerenciadorPerifericos.getDisplay().setMensagem("Nenhum Pedido Encontrado");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}
		
	}

	private int alteraOperacaoServlet(GerenciadorPerifericos gerenciadorPerifericos, OperacaoPedido pedido) {
		Integer proximoEstado = getProximoEstadoOperacao();
		if (proximoEstado != null && proximoEstado != -1) {
			try{						

				URL urlCon = new URL("http://" +
						ServerConfig.HOST_SERVIDOR_ES +
						":" +
						ServerConfig.PORTA_SERVIDOR_ES +
						"/" +
						ServerConfig.CONTEXTO_SERVIDOR_ES +
						"/" +
						ServerConfig.ALTERAR_OPERACA_SERVLET +"?status=" + proximoEstado);
				URLConnection huc1 = urlCon.openConnection();

				huc1.setAllowUserInteraction(true);
				huc1.setDoOutput(true);

				ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
				output.writeObject(pedido.getPk());
				ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
				Object obj = input.readObject();
				if (obj instanceof String && obj.equals("OK")) {
					return ALTERNATIVA_1;
					
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
		}

		return ALTERNATIVA_1;
	}

	
	
	/**
	 * Qual o estado do pedido ficara no E.S apois a coonsulta
	 * Caso não queira separa retorna -1
	 * 
	 * @return
	 */
	public abstract Integer getProximoEstadoOperacao();	

	/**
	 * Qual o estado do pedido ficara no E.S apois a coonsulta
	 * Caso não queira separa retorna -1
	 * 
	 * @return
	 */
	public abstract Integer[]  getEstadoOperacaoConsulta();
	
}
