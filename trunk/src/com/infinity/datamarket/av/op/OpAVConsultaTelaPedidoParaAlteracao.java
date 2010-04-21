package com.infinity.datamarket.av.op;


import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.pdv.op.OpConsultaTelaPedidoAbstrato;

public class OpAVConsultaTelaPedidoParaAlteracao extends OpConsultaTelaPedidoAbstrato {

	
	/**
	 * Qual o estado do pedido ficara no E.S apois a coonsulta
	 * Caso não queira separa retorna -1
	 * 
	 * @return
	 */
	public Integer getProximoEstadoOperacao(){
		return ConstantesOperacao.EM_ALTERACAO;
	}

	/**
	 * Qual o estado do pedido ficara no E.S apois a coonsulta
	 * Caso não queira separa retorna -1
	 * 
	 * @return
	 */
	public Integer[]  getEstadoOperacaoConsulta(){
		Integer[] retorno = {ConstantesOperacao.ABERTO};
		
		return retorno; 
	}
}