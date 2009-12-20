package com.infinity.datamarket.comum.operacao;

public class ConstantesOperacao {
	public static final int OPERACAO_DEVOLUCAO = 10;
	public static final int OPERACAO_PEDIDO = 11;
	
	
	public static final int ABERTO = 1;
	public static final int EM_PROCESSAMENTO = 2;	
	public static final int FECHADO = 3;
	public static final int CANCELADO = 4;

	//quando eh criado no av e nao foi enviado ao E.S.
	public static final int PARA_ENVIAR = 5;

	//Quando foi criando no av e foi enviado ao E. S.
	public static final int ENVIADO = 6;

}
