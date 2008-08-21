package com.infinity.datamarket.comum.pagamento;

public class ConstantesFormaRecebimento {
	public static final Long DINHEIRO = new Long(1);
	public static final Long CHEQUE = new Long(2);
	public static final Long CHEQUE_PRE = new Long(3);
	public static final Long CARTAO_OFF = new Long(4);
	public static final Long CARTAO_PROPRIO = new Long(5);
	public static final String DESCRICAO_DINHEIRO = "DINHEIRO";
	public static final String DESCRICAO_CHEQUE = "CHEQUE";
	public static final String DESCRICAO_CHEQUE_PRE = "CHEQUE-PRE";
	public static final String DESCRICAO_CARTAO_OFF = "CARTÃO OFF";
	public static final String DESCRICAO_CARTAO_PROPRIO = "CARTÃO PROPRIO";
	
	public static String retornaDescricaoFormaRecebimento(int codigoForma){
		String retorno = DESCRICAO_DINHEIRO;
		switch(codigoForma){
			case 1:
				retorno = DESCRICAO_DINHEIRO;
				break;
			case 2:
				retorno = DESCRICAO_CHEQUE;
				break;
			case 3:
				retorno = DESCRICAO_CHEQUE_PRE;
				break;
			case 4:
				retorno = DESCRICAO_CARTAO_OFF;
				break;
			case 5:
				retorno = DESCRICAO_CARTAO_PROPRIO;
				break;
		}
		return retorno;
	}
	
}
