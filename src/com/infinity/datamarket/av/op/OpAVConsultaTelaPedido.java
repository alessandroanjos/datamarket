package com.infinity.datamarket.av.op;


import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.pdv.op.OpConsultaTelaPedido;

public class OpAVConsultaTelaPedido extends OpConsultaTelaPedido {
	

	public int getEstadoOperacaoAtualizada() {
		return ConstantesOperacao.EM_SEPARACAO;
	}
	
}