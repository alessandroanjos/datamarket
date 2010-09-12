package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.pagamento.DadosCartaoOff;
import com.infinity.datamarket.comum.pagamento.DadosCartaoProprio;
import com.infinity.datamarket.comum.pagamento.PlanoPagamento;
import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.Transacao;
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

			BigDecimal valor = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);

			SolicitacaoOperacaoTEF solicitacao = new SolicitacaoOperacaoTEF();
			solicitacao.setIdentificacao(new Long(2));
			solicitacao.setNumeroCOO(new Long(1));
			solicitacao.setValorOperacao(valor);
			solicitacao.setNomeRede(TEFPayGo.VISANET);
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
			
			if (respostaTEF.getLinhasComprovantePrincipal().length > 0) {
				gerenciadorPerifericos.getDisplay().setMensagem(MensagensPDV.getMensagem(this, "RESPOSTA SEM MENSAGEM"));
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;
			}

			
			gerenciadorPerifericos.getCmos().gravar("respostaSolicitacao",respostaTEF);

//			SolicitacaoOperacaoTEF solicitacaoConfirmacao = new SolicitacaoOperacaoTEF();
//			solicitacaoConfirmacao.setIdentificacao(respostaTEF.getIdentificacao());
//			solicitacaoConfirmacao.setNumeroCOO(respostaTEF.getNumeroCOO());
//			solicitacaoConfirmacao.setNomeRede(respostaTEF.getNomeRede());
//			solicitacaoConfirmacao.setNsuTEF(respostaTEF.getNsuTEF());
//			solicitacaoConfirmacao.setChaveFinalizacao(respostaTEF.getChaveFinalizacao());
//			gerenciadorPerifericos.getCmos().gravar("confirmacaoSolicitacao",solicitacaoConfirmacao);
//
//			RespostaOperacaoTEF respostaConfirmacaoTEF = GerenciadorTEF.getInstancia().getTef().confirmaOperacao(solicitacao);
//
//			gerenciadorPerifericos.getCmos().gravar("respostaConfirmacaoSolicitacao",respostaConfirmacaoTEF);

			
			
//			DadosAutorizacaoCartaoProprio
//				private String autrizacao;
//				private String loja;
//				private String componente;
//				private Date data;
//				private BigDecimal valor;
//				private String nome;
			
//				para
//			DadosCartaoProprio dados = new DadosCartaoProprio();
//			dados.setAutorizacao(dadosAutorizacao.getAutrizacao());
//			dados.setCPFCNPJ(CPFCNPJ);
//
//
//			//plano.getForma().getId().intValue(),plano.getId().intValue(),plano.getForma().getRecebimentoImpressora()
//			DadosCartaoOff dados = (DadosCartaoOff) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CARTA);
//			BigDecimal valorPagamento = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);
//			PlanoPagamento plano = (PlanoPagamento) gerenciadorPerifericos.getCmos().ler(CMOS.PLANO_PAGAMENTO_ATUAL);
//			BigDecimal subTotal = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.SUB_TOTAL);
//
//			if (valorPagamento.compareTo(subTotal) > 0){
//				valorPagamento = subTotal;
//			}
//			Transacao transacao = null;
//			Integer tipoTransacao = (Integer) gerenciadorPerifericos.getCmos().ler(CMOS.TIPO_TRANSACAO);
//			if (tipoTransacao.intValue() == ConstantesTransacao.TRANSACAO_VENDA){
//				transacao = (Transacao) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_VENDA_ATUAL);
//			}else {
//				transacao = (Transacao) gerenciadorPerifericos.getCmos().ler(CMOS.TRANSACAO_PAGAMENTO_ATUAL);
//			}
//			//
//			
//			
//			
//			
//			
//			
//			

		} catch (AppException e) {
			e.printStackTrace();
			return ALTERNATIVA_2;
		} catch (ExcecaoTEF e) {
			e.printStackTrace();
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
}