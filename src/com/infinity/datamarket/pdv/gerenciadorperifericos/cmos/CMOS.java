package com.infinity.datamarket.pdv.gerenciadorperifericos.cmos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.infinity.datamarket.comum.util.SistemaException;

public class CMOS implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private File cmos;
	private HashMap hash;

	private static CMOS instancia;

	public static final String TELA_ATUAL = "TELA_ATUAL";
	public static final String ESTADO_ATUAL = "ESTADO_ATUAL";
	public static final String MACRO_ATUAL = "MACRO_ATUAL";
	public static final String USUARIO_ATUAL = "USUARIO_ATUAL";
	public static final String PRODUTO_ATUAL = "PRODUTO_ATUAL";
	public static final String SUB_TOTAL = "SUB_TOTAL";
	public static final String TOTAL = "TOTAL";
	public static final String QUANTIDADE_ITEM = "QUANTIDADE_ITEM";
	public static final String OPERADOR_ATUAL = "OPERADOR_ATUAL";
	public static final String AUTORIZADOR_ATUAL = "AUTORIZADOR_ATUAL";
	public static final String FUNDO_TROCO = "FUNDO_TROCO";
	public static final String NUMERO_CUPOM = "NUMERO_CUPOM";
	public static final String NUMERO_TRANSACAO = "NUMERO_TRANSACAO";
	public static final String VENDEDOR_ATUAL = "VENDEDOR_ATUAL";
	public static final String TRANSACAO_VENDA_ATUAL = "TRANSACAO_VENDA_ATUAL";
	public static final String ITEM_REGISTRADO = "ITEM_REGISTRADO";
	public static final String ITEM_PAGAMENTO = "ITEM_PAGAMENTO";
	public static final String VALOR_SANGRIA = "VALOR_SANGRIA";
	public static final String VALOR_RESUPRIMENTO = "VALOR_RESUPRIMENTO";
	public static final String ITEM_CANCELADO = "ITEM_CANCELADO";
	public static final String FORMA_RECEBIMENTO_ATUAL = "FORMA_RECEBIMENTO_ATUAL";
	public static final String PLANO_PAGAMENTO_ATUAL = "PLANO_PAGAMENTO_ATUAL";
	public static final String VALOR_PAGAMENTO_ATUAL = "VALOR_PAGAMENTO_ATUAL";
	public static final String VALOR_TROCO_ATUAL = "VALOR_TROCO_ATUAL";
	public static final String CHAVE_ULTIMA_TRANSACAO = "CHAVE_ULTIMA_TRANSACAO";
	public static final String DADOS_CHEQUE = "DADOS_CHEQUE";
	public static final String DADOS_CHEQUE_PRE = "DADOS_CHEQUE_PRE";
	public static final String DADOS_CARTAO_OFF = "DADOS_CARTAO_OFF";
	public static final String DADOS_CARTAO_PROPRIO = "DADOS_CARTAO_PROPRIO";
	public static final String DADOS_CONSULTA_CARTAO_PROPRIO = "DADOS_CONSULTA_CARTAO_PROPRIO";
	public static final String AUTORIZADORA_CARTAO_OFF = "AUTORIZADORA_CARTAO_OFF";
	public static final String DADOS_AUTORIZACOES_CARTAO_PROPRIO = "DADOS_AUTORIZACOES_CARTAO_PROPRIO";
	public static final String DATA_ULTIMA_ABERTURA = "DATA_ULTIMA_ABERTURA";
	public static final String TIPO_TRANSACAO = "TIPO_TRANSACAO";
	public static final String DESCONTO = "DESCONTO";
	public static final String ACRESSIMO = "ACRESSIMO";
	public static final String TRANSACAO_PAGAMENTO_ATUAL = "TRANSACAO_PAGAMENTO_ATUAL";
	public static final String DATA_MOVIMENTO = "DATA_MOVIMENTO";
	public static final String OPERACAO_DEVOLUCAO = "OPERACAO_DEVOLUCAO";
	public static final String OPERACAO_PEDIDO = "OPERACAO_PEDIDO";
	public static final String PK_OPERACOES = "PK_OPERACOES";

	private CMOS(String nome){

		cmos = new File(nome);
		hash = (HashMap) lerCmos();
		

	}

	public synchronized static CMOS getInstancia(String nome){
		if (instancia == null){
			instancia = new CMOS(nome);
		}
		return instancia;
	}

	public Object ler(String chave){
		return hash.get(chave);
	}

	private synchronized Object lerCmos(){
		Object result;
		try{
			synchronized (cmos) {
				if (cmos.length() != 0){
					byte[] b = new byte[(int) cmos.length()];
					FileInputStream i = new FileInputStream(cmos);
					i.read(b);
					ByteArrayInputStream barr = new ByteArrayInputStream(b);
					ObjectInputStream objInp = new ObjectInputStream(barr);
					result = objInp.readObject();
					objInp.close();
					i.close();
				}else{
					return new HashMap();
				}
			}
		}catch(Exception e){
			throw new SistemaException(e);
		}
		return result;
	}

	public void gravar(String chave, Object valor){
		hash.put(chave, valor);
//		Thread t = new Thread(){
//			public void run(){
				gravaCmos(hash);
//			}
//		};
//		t.start();
		
	}

	private synchronized void gravaCmos(Object obj){
		try{
//			Map map = Collections.synchronizedMap((Map)obj);  
			synchronized (obj) {
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(bout);				
				out.writeObject(obj);
				out.close();
				byte[] bytes = bout.toByteArray();
				FileOutputStream fout = new FileOutputStream(cmos);
				fout.write(bytes);
				fout.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new SistemaException(e);
		}
	}

}
