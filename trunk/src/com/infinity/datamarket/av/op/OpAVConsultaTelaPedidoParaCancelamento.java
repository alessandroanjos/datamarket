package com.infinity.datamarket.av.op;


import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.pdv.op.OpConsultaTelaPedidoAbstrato;

public class OpAVConsultaTelaPedidoParaCancelamento extends OpConsultaTelaPedidoAbstrato {


	
	/**
	 * Qual o estado do pedido ficara no E.S apois a coonsulta
	 * Caso não queira separa retorna -1
	 * 
	 * @return
	 */
	public Integer getProximoEstadoOperacao(){
		return ConstantesOperacao.CANCELADO;
	}

	/**
	 * Qual o estado do pedido ficara no E.S apois a coonsulta
	 * Caso não queira separa retorna -1
	 * 
	 * @return
	 */
	public Integer[]  getEstadoOperacaoConsulta(){
		Integer[] retorno = {ConstantesOperacao.ABERTO };

		return retorno; 
	}
}