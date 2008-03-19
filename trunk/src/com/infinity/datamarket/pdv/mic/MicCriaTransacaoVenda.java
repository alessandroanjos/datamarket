package com.infinity.datamarket.pdv.mic;

import java.util.Date;

import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class MicCriaTransacaoVenda extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		Usuario operador = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.OPERADOR_ATUAL);
		String vendedor = (String) gerenciadorPerifericos.getCmos().ler(CMOS.VENDEDOR_ATUAL);
		String strOperador =""+operador.getId();
		Date dataInicio = new Date();

		int numeroTransacao = gerenciadorPerifericos.incrementaNumeroTransacao();
		int loja = gerenciadorPerifericos.getCodigoLoja();
		int componente = gerenciadorPerifericos.getCodigoComponente();

		TransacaoPK pk = new TransacaoPK(loja,componente,numeroTransacao,dataInicio);

		TransacaoVenda transVenda = new TransacaoVenda(pk,ConstantesTransacao.TRANSACAO_VENDA,
				vendedor,strOperador,dataInicio,null,null,null,null,null,TransacaoVenda.ATIVO);

		gerenciadorPerifericos.getCmos().gravar(CMOS.TRANSACAO_VENDA_ATUAL, transVenda);

		return ALTERNATIVA_1;
	}

}
