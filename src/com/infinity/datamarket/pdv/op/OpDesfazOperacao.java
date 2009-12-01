package com.infinity.datamarket.pdv.op;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class OpDesfazOperacao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
		
		try{
			Collection c = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.PK_OPERACOES);
			
			if (c != null && c.size() > 0){
				gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
//				OperacaoServerRemote remote = (OperacaoServerRemote) ServiceLocator.getJNDIObject(ServerConfig.OPERACAO_SERVER_JNDI);
//				if (remote == null){
//					gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
//					gerenciadorPerifericos.esperaVolta();
//					gerenciadorPerifericos.getDisplay().setMensagem("Deixar operação pendente?");
//					EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
//					if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
//						return ALTERNATIVA_1;
//					}else{
//						return ALTERNATIVA_2;
//					}					
//				}				
			
				Iterator i = c.iterator();
				while(i.hasNext()){
					Operacao op = (Operacao) i.next();					
					try{
//						URL urlCon = new URL("http://" + loja.getNumeroIp() + ":" + loja.getNumeroPortaServlet() + "/EnterpriseServer/GerarBoletoServlet.servlet");
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
						output.writeObject(op.getPk());
						ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
						Object obj = input.readObject();
						if (obj instanceof String && obj.equals("OK")) {

						} else  if (obj instanceof Exception){
							gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
							gerenciadorPerifericos.esperaVolta();
							gerenciadorPerifericos.getDisplay().setMensagem("Deixar operação pendente?");
							EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
							if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
								return ALTERNATIVA_1;
							}else{
								return ALTERNATIVA_2;
							}
						}
					} catch (Exception e) {
						gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
						gerenciadorPerifericos.esperaVolta();
						gerenciadorPerifericos.getDisplay().setMensagem("Deixar operação pendente?");
						EntradaDisplay entrada = gerenciadorPerifericos.lerDados(new int[]{Tecla.CODIGO_ENTER,Tecla.CODIGO_VOLTA},Display.MASCARA_NUMERICA, 0);
						if (entrada.getTeclaFinalizadora() == Tecla.CODIGO_ENTER){
							return ALTERNATIVA_1;
						}else{
							return ALTERNATIVA_2;
						}		
					}
				}
			}
			
			gerenciadorPerifericos.getCmos().gravar(CMOS.PK_OPERACOES,new ArrayList());
								
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
		return ALTERNATIVA_1;
	}
}
