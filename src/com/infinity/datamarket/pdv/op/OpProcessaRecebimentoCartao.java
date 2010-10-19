package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.tef.ExcecaoTEF;
import com.infinity.datamarket.pdv.tef.GerenciadorTEF;
import com.infinity.datamarket.pdv.tef.RespostaOperacaoTEF;
import com.infinity.datamarket.pdv.tef.SolicitacaoOperacaoTEF;
import com.infinity.datamarket.pdv.tef.paygo.TEFPayGo;
import com.infinity.datamarket.pdv.util.MensagensPDV;

public class OpProcessaRecebimentoCartao extends OpInicioRecebimento{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try {
			Collection c = (Collection) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_AUTORIZACOES_CARTAO);
			if (c == null){
				c = new ArrayList();
			}
			
			PlanoPagamento plano = (PlanoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.PLANO_PAGAMENTO_ATUAL);

			
			BigDecimal valor = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);

			SolicitacaoOperacaoTEF solicitacao = new SolicitacaoOperacaoTEF();
			solicitacao.setIdentificacao(new Long(c.size() + 1));
			//pegar o numero da impressora
			solicitacao.setNumeroCOO(new Long(1));
			solicitacao.setValorOperacao(valor);
			solicitacao.setNomeRede(plano.getDescricao());
			solicitacao.setMoeda(TEFPayGo.MOEDA_REAL);

			gerenciadorPerifericos.getCmos().gravar("solicitacao",solicitacao);

			RespostaOperacaoTEF respostaTEF = GerenciadorTEF.getInstancia().getTef().solicitaOperacaoCartao(solicitacao);

			if (respostaTEF != null && respostaTEF.getStatusTransacao() != null && !respostaTEF.getStatusTransacao().equals("0")) {

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

			}
			
			if (respostaTEF.getLinhasComprovantePrincipal().length == 0) {
				gerenciadorPerifericos.getDisplay().setMensagem(MensagensPDV.getMensagem(this, "RESPOSTA SEM MENSAGEM"));
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			}

			respostaTEF.setPlano(plano);

			SolicitacaoOperacaoTEF solicitacaoConfirmacao = new SolicitacaoOperacaoTEF();
			solicitacaoConfirmacao.setIdentificacao(respostaTEF.getIdentificacao());
			solicitacaoConfirmacao.setNumeroCOO(respostaTEF.getNumeroCOO());
			solicitacaoConfirmacao.setNomeRede(respostaTEF.getNomeRede());
			solicitacaoConfirmacao.setNsuTEF(respostaTEF.getNsuTEF());
			solicitacaoConfirmacao.setChaveFinalizacao(respostaTEF.getChaveFinalizacao());
			gerenciadorPerifericos.getCmos().gravar("confirmacaoSolicitacao",solicitacaoConfirmacao);

			RespostaOperacaoTEF respostaConfirmacaoTEF = GerenciadorTEF.getInstancia().getTef().confirmaOperacao(solicitacaoConfirmacao);
			respostaConfirmacaoTEF.setPlano(respostaTEF.getPlano());

			gerenciadorPerifericos.getCmos().gravar("respostaConfirmacaoSolicitacao",respostaConfirmacaoTEF);

			gerenciadorPerifericos.getCmos().gravar("respostaSolicitacao",respostaTEF);
			//gerenciadorPerifericos.getCmos().gravar("respostaSolicitacao",respostaConfirmacaoTEF);

			c.add(respostaTEF);
			gerenciadorPerifericos.getCmos().gravar(CMOS.DADOS_AUTORIZACOES_CARTAO,c);

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
}