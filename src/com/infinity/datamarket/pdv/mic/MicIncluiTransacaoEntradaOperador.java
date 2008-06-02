package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoEntradaOperador;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicIncluiTransacaoEntradaOperador extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		Usuario autorizador = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.AUTORIZADOR_ATUAL);
		Usuario operador = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.OPERADOR_ATUAL);
		BigDecimal fundoTroco = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_RESUPRIMENTO);
		int numeroTransacao = gerenciadorPerifericos.incrementaNumeroTransacao();
		int loja = gerenciadorPerifericos.getCodigoLoja();
		int componente = gerenciadorPerifericos.getCodigoComponente();

		TransacaoPK pk = new TransacaoPK(loja,componente,numeroTransacao,new Date());
		TransacaoEntradaOperador trans = new TransacaoEntradaOperador(pk,ConstantesTransacao.TRANSACAO_ENTRADA_OPERADOR,autorizador.getId().toString(),operador.getId().toString(),fundoTroco);

		try{
			getFachadaPDV().inserirTransacao(trans);
		}catch(AppException e){
			e.printStackTrace();
		}

		return ALTERNATIVA_1;
	}
}
