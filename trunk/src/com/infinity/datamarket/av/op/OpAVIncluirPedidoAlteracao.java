package com.infinity.datamarket.av.op;

import com.infinity.datamarket.comum.operacao.ConstantesOperacao;

public class OpAVIncluirPedidoAlteracao extends OpAVIncluirPedido {
	
	public int getStatus() {
		return ConstantesOperacao.EM_ALTERACAO;
	}

}