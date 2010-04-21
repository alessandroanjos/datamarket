package com.infinity.datamarket.pdv.op;


import com.infinity.datamarket.comum.operacao.ConstantesOperacao;

public class OpConsultaTelaPedido extends OpConsultaTelaPedidoAbstrato{
	
	/**
	 * Qual o estado do pedido ficara no E.S apois a coonsulta
	 * Caso não queira separa retorna -1
	 * 
	 * @return
	 */
	public Integer getProximoEstadoOperacao(){
		return ConstantesOperacao.EM_PROCESSAMENTO;
	}

	/**
	 * Qual o estado do pedido ficara no E.S apois a coonsulta
	 * Caso não queira separa retorna -1
	 * 
	 * @return
	 */
	public Integer[]  getEstadoOperacaoConsulta(){
		Integer[] retorno = {ConstantesOperacao.SEPARADO};
		
		return retorno; 
	}

}