package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.tef.ExcecaoTEF;
import com.infinity.datamarket.pdv.tef.GerenciadorTEF;
import com.infinity.datamarket.pdv.util.MensagensPDV;

public class OpVerificaServidorTEFAtivo extends OpInicioRecebimento{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try {
			System.out.println("TEF -- verificar atividade do tef");
			if (!GerenciadorTEF.getInstancia().getTef().validarServicoAtivo()){
				System.out.println("TEF -- INATIVO");
				gerenciadorPerifericos.getDisplay().setMensagem(MensagensPDV.getMensagem(this, "TEF NAO ESTA ATIVO"));
				try{
					gerenciadorPerifericos.esperaVolta();
				}catch(AppException ex){}
				return ALTERNATIVA_2;
			} else {
				System.out.println("TEF -- ATIVO");
			}
		} catch (ExcecaoTEF e) {
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
}