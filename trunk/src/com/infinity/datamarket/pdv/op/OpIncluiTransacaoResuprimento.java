package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.transacao.TransacaoResuprimento;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpIncluiTransacaoResuprimento extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		Usuario autorizador = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.USUARIO_ATUAL);
		Usuario operador = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.OPERADOR_ATUAL);
		BigDecimal valor = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_RESUPRIMENTO);
		int numeroTransacao = gerenciadorPerifericos.incrementaNumeroTransacao();
		int loja = gerenciadorPerifericos.getCodigoLoja();
		int componente = gerenciadorPerifericos.getCodigoComponente();

		TransacaoPK pk = new TransacaoPK(loja,componente,numeroTransacao,new Date());
		TransacaoResuprimento trans = new TransacaoResuprimento(pk,ConstantesTransacao.TRANSACAO_RESUPRIMENTO,autorizador.getId().toString(),operador.getId().toString(),valor,"123");

		try{
			getFachadaPDV().inserirTransacao(trans);
		}catch(AppException e){
			e.printStackTrace();
		}

		return ALTERNATIVA_1;
	}
}
