package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.pagamento.DadosCartaoOff;
import com.infinity.datamarket.comum.pagamento.DadosCartaoProprio;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.StringUtil;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.tef.ExcecaoTEF;
import com.infinity.datamarket.pdv.tef.GerenciadorTEF;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF;
import com.infinity.datamarket.pdv.tef.SolicitacaoOperacaoTEF;
import com.infinity.datamarket.pdv.tef.paygo.TEFPayGo;
import com.infinity.datamarket.pdv.util.MensagensPDV;

public class OpAdministrarTEF extends OpInicioRecebimento{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try {

			SolicitacaoOperacaoTEF solicitacao = new SolicitacaoOperacaoTEF();
			solicitacao.setIdentificacao(new Long(1));

			RespostaOperacaoTEF respostaTEF = GerenciadorTEF.getInstancia().getTef().solicitaOperacaoAdministracao(solicitacao);
				
			if (respostaTEF.getTextoEspecialCliente() != null && !"".equals(respostaTEF.getTextoEspecialCliente())) {
				gerenciadorPerifericos.getDisplay().setMensagem(respostaTEF.getTextoEspecialCliente());
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			}

			if (respostaTEF.getTextoEspecialOperador()  != null && !"".equals(respostaTEF.getTextoEspecialOperador())) {
				gerenciadorPerifericos.getDisplay().setMensagem(respostaTEF.getTextoEspecialOperador());
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			}
			
			if (respostaTEF.getLinhasComprovantePrincipal().length == 0) {
				gerenciadorPerifericos.getDisplay().setMensagem(MensagensPDV.getMensagem(this, "RESPOSTA SEM MENSAGEM"));
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			} else if(respostaTEF.getLinhasComprovantePrincipal().length > 0){
				System.out.println("Imprimindo Linhas Comprovante Principal");

				imprimeComprovante(respostaTEF.getLinhasComprovantePrincipal(), gerenciadorPerifericos);
//				for (int i = 0; i < respostaTEF.getLinhasComprovantePrincipal().length; i++) {
//					String linha = respostaTEF.getLinhasComprovantePrincipal()[i];
//					System.out.println(linha);
//
//					
//				}
			}

		} catch (AppException e) {
			e.printStackTrace();
			try {
				gerenciadorPerifericos.getDisplay().setMensagem("ERRO .... ");
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			} catch (Exception e2) {
			}
			return ALTERNATIVA_2;
		} catch (ExcecaoTEF e) {
			e.printStackTrace();
			try {
				gerenciadorPerifericos.getDisplay().setMensagem(e.getMessage());
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			} catch (Exception e2) {
			}

			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
	
	
	private void imprimeComprovante(String[] linhas, GerenciadorPerifericos ger) throws ImpressoraFiscalException{
		StringBuffer buffer = new StringBuffer();
		System.out.println("Imprime cupom");
		for (int i = 0; i < linhas.length; i++) {
			System.out.println(linhas[i]);
			buffer.append(StringUtil.centraliza("CARTÃO PRÓPRIO", 48));
		}
		System.out.println("FIM - Imprime cupom");

		ger.getImpressoraFiscal().imprimeRelatorioGerencial(buffer.toString());
		ger.getImpressoraFiscal().finalizaRelatorioGerencial();
	}

}