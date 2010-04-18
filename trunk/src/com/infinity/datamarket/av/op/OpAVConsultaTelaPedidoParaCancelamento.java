package com.infinity.datamarket.av.op;


import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.pdv.op.OpConsultaTelaPedido;

public class OpAVConsultaTelaPedidoParaCancelamento extends OpConsultaTelaPedido {


	public int getEstadoOperacaoAtualizada() {
		return ConstantesOperacao.CANCELADO;
	}
	
	public int getEstadoOperacaoConsulta() {
		return -1;
	}

	
}