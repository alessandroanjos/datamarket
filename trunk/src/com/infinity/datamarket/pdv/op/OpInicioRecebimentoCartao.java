package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.tef.ExcecaoTEF;
import com.infinity.datamarket.pdv.tef.GerenciadorTEF;
import com.infinity.datamarket.pdv.util.MensagensPDV;

public class OpInicioRecebimentoCartao extends OpInicioRecebimento{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		gerenciadorPerifericos.getDisplay().setMensagem(MensagensPDV.getMensagem(this, "AGUARDE ..."));

		return execRegrasFormaPlano(gerenciadorPerifericos, param, ConstantesFormaRecebimento.CARTAO);	
	}
}