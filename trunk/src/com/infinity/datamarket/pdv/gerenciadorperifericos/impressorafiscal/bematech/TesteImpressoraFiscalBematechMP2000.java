package com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.bematech;

import java.math.BigDecimal;

import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscal;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;

public class TesteImpressoraFiscalBematechMP2000 {
	public static void main(String[] a){
		
		try{
			ImpressoraFiscal imp = new ImpressoraFiscalBematechMP2000();
//	        imp.inicioDia();
//	        imp.inicioOperador(new BigDecimal(20.00), "DINHEIRO");
//	        imp.inicioCupomFiscal("");
//	        imp.vendeItem("123", "Caneta", "17.00", "I", new BigDecimal("1"),"UN" ,new BigDecimal("15"),ImpressoraFiscal.DESCONTO_VALOR,new BigDecimal("0.3"));
//	        imp.cancelaItem("002");
//	        imp.iniciaFechamentoCupom(null, null, null);
//			imp.efetuaPagamento(new BigDecimal("10.00"), "DINHEIRO");
	        imp.efetuaPagamento(new BigDecimal("30.00"), "CHEQUE");
//	        imp.cancelaCupom();
//	        imp.terminaFechaCupom("Obrigado. Volte Sempre!");
//	        imp.cancelaCupom();
//	        imp.leituraX();
//			imp.imprimeRelatorioGerencial("2345234523452345234234523452345234523452345234523452345234523452345234523452345234523452342345234523452345234523423452345234523452345asdfasdfasdfqwerqwerqwer2345234ut534u32p4unv234unv2938475vn20938745vn202398452093857209384750293847502938750293875029384750293847502938475029387502938750293845072938570293874502938750293847502389570293845072938750293845072938750238975029387502938475029384750293875029387502938570238942345234523452345234234523452345234523452345234523452345234523452345234523452345234523452342345234523452345234523423452345234523452345asdfasdfasdfqwerqwerqwer2345234ut534u32p4unv234unv2938475vn20938745vn202398452093857209384750293847502938750293875029384750293847502938475029387502938750293845072938570293874502938750293847502389570293845072938750293845072938750238975029387502938475029384750293875029387502938570238942345234523452345234234523452345234523452345234523452345234523452345234523452345234523452342345234523452345234523423452345234523452345asdfasdfasdfqwerqwerqwer2345234ut534u32p4unv234unv2938475vn20938745vn20239845209385720938475029384750293875029387502938475029384750293847502938750293875029384507293857029387450293875029384750238957029384507293875029384507293875023897502938750293847502938475029387502938750293857023894");
//	        imp.finalizaRelatorioGerencial();
			System.out.println("OK");
		}catch(ImpressoraFiscalException e){
			e.printStackTrace();
		}
	}
}
