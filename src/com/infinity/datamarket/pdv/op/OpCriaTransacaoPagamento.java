package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.autorizador.DadosConsultaCartaoProprio;
import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.transacao.TransacaoPagamento;
import com.infinity.datamarket.comum.transacao.TransacaoPagamentoCartaoProprio;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpCriaTransacaoPagamento extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		Usuario operador = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.OPERADOR_ATUAL);
		String strOperador =""+operador.getId();		
		Date dataInicio = new Date();
		
		int numeroTransacao = gerenciadorPerifericos.incrementaNumeroTransacao();
		int loja = gerenciadorPerifericos.getCodigoLoja();
		int componente = gerenciadorPerifericos.getCodigoComponente();

		
	
		TransacaoPK pk = new TransacaoPK(loja,componente,numeroTransacao,dataInicio);
		
		BigDecimal desconto = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.DESCONTO);
		BigDecimal acressimo = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.ACRESSIMO);
		BigDecimal valor = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.SUB_TOTAL);
		DadosConsultaCartaoProprio dados = (DadosConsultaCartaoProprio) gerenciadorPerifericos.getCmos().ler(CMOS.DADOS_CONSULTA_CARTAO_PROPRIO);

		TransacaoPagamentoCartaoProprio transPagamento = new TransacaoPagamentoCartaoProprio(pk,ConstantesTransacao.TRANSACAO_PAGAMENTO,
				strOperador,dataInicio,null,valor,desconto,acressimo,TransacaoPagamento.ATIVO,dados.getCPFCNPJ(),operador.getId().toString());
		transPagamento.setNome(dados.getNome());
		transPagamento.setOperador(operador.getNome());
		gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_PAGAMENTO_ATUAL, transPagamento);

		return ALTERNATIVA_1;
	}

}
