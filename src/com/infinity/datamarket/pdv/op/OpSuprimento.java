package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;

import com.infinity.datamarket.comum.pagamento.ConstantesFormaRecebimento;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpSuprimento extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		BigDecimal fundoTroco = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_RESUPRIMENTO);
		try{
			gerenciadorPerifericos.getDisplay().setMensagem("Aguarde...");
			FormaRecebimento forma =  getFachadaPDV().consultarFormaRecebimentoPorId(ConstantesFormaRecebimento.DINHEIRO);
			gerenciadorPerifericos.getImpressoraFiscal().suprimento(fundoTroco, forma.getRecebimentoImpressora());
		}catch(ImpressoraFiscalException e){
			gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(Exception ex){
				return ALTERNATIVA_2;
			}
			return ALTERNATIVA_2;
		}catch(AppException e){
			e.printStackTrace();
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
}
