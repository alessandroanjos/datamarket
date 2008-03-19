package com.infinity.datamarket.pdv.mic;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoAbertura;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicIncluiTransacaoAbertura extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		Usuario autorizador = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.USUARIO_ATUAL);
		BigDecimal gtInicio = new BigDecimal(0);

		int numeroTransacao = gerenciadorPerifericos.incrementaNumeroTransacao();
		int loja = gerenciadorPerifericos.getCodigoLoja();
		int componente = gerenciadorPerifericos.getCodigoComponente();

		TransacaoPK pk = new TransacaoPK(loja,componente,numeroTransacao,new Date());
		TransacaoAbertura trans = new TransacaoAbertura(pk,ConstantesTransacao.TRANSACAO_ABERTURA,autorizador.getId().toString(),gtInicio);

		try{
			getFachadaPDV().inserirTransacao(trans);
		}catch(AppException e){
			e.printStackTrace();
		}

		return ALTERNATIVA_1;
	}
}
