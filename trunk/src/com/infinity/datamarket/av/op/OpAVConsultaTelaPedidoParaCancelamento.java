package com.infinity.datamarket.av.op;


import com.infinity.datamarket.pdv.op.OpConsultaTelaPedido;

public class OpAVConsultaTelaPedidoParaCancelamento extends OpConsultaTelaPedido {

	public int getEstadoOperacaoAtualizada() {
		return -1;
	}
	
}