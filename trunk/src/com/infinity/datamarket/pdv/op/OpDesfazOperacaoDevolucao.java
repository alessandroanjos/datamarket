package com.infinity.datamarket.pdv.op;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Iterator;

import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class OpDesfazOperacaoDevolucao extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		
		
		
		try{
			Collection c = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.PK_OPERACOES);
//			OperacaoServerRemote remote = null;
			if (c != null && c.size() > 0){				
				Iterator i = c.iterator();
				while(i.hasNext()){
					Operacao op = (Operacao) i.next();
					if (op.getTipo() == ConstantesOperacao.OPERACAO_DEVOLUCAO){
//						if (remote == null){
							gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
//							remote = (OperacaoServerRemote) ServiceLocator.getJNDIObject(ServerConfig.OPERACAO_SERVER_JNDI);
//							if (remote == null){
//								gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
//								gerenciadorPerifericos.esperaVolta();
//								return ALTERNATIVA_2;
//							}
//						}
						try{
//							URL urlCon = new URL("http://" + loja.getNumeroIp() + ":" + loja.getNumeroPortaServlet() + "/EnterpriseServer/GerarBoletoServlet.servlet");
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
								return ALTERNATIVA_2;
							}
						} catch (Exception e) {
							gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
							gerenciadorPerifericos.esperaVolta();
							return ALTERNATIVA_2;
						}
//						remote.alteraStatusOperacao(op.getPk(),ConstantesOperacao.ABERTO);
						c.remove(op);
					}
				}
			}
			
			gerenciadorPerifericos.getCmos().gravar(CMOS.PK_OPERACOES,c);
								
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
