package com.infinity.datamarket.pdv.mic;

import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicInicioRecebimentoCartaoOff extends MicInicioRecebimento{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		return execRegrasFormaPlano(gerenciadorPerifericos, param, ConstantesFormaRecebimento.CARTAO_OFF );	
	}
}
