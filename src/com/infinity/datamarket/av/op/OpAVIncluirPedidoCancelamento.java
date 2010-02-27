package com.infinity.datamarket.av.op;

import com.infinity.datamarket.comum.operacao.ConstantesOperacao;

public class OpAVIncluirPedidoCancelamento extends OpAVIncluirPedido {
	
	public int getStatus() {
		return ConstantesOperacao.CANCELADO;
	}

}