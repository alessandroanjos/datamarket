package com.infinity.datamarket.pdv.op;

import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpInicioRecebimentoCartaoProprio extends OpInicioRecebimento{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		return execRegrasFormaPlano(gerenciadorPerifericos, param, ConstantesFormaRecebimento.CARTAO_PROPRIO);	
	}
}
