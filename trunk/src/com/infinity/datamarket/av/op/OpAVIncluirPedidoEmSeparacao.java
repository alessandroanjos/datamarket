package com.infinity.datamarket.av.op;


import com.infinity.datamarket.comum.operacao.ConstantesOperacao;

public class OpAVIncluirPedidoEmSeparacao extends OpAVIncluirPedido {
//
	
	public int getStatus() {
		return ConstantesOperacao.EM_SEPARACAO;
	}

}