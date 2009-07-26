package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoFechamentoZ;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.impressorafiscal.ImpressoraFiscalException;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpIncluiTransacaoFechamentoZ extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		Usuario autorizador = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.USUARIO_ATUAL);
		BigDecimal gtFim = new BigDecimal(0);
		
		try {
			gtFim = gerenciadorPerifericos.getImpressoraFiscal().getGT();
		} catch (ImpressoraFiscalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int numeroTransacao = gerenciadorPerifericos.incrementaNumeroTransacao();
		int loja = gerenciadorPerifericos.getCodigoLoja();
		int componente = gerenciadorPerifericos.getCodigoComponente();

		TransacaoPK pk = new TransacaoPK(loja,componente,numeroTransacao,new Date());
		TransacaoFechamentoZ trans = new TransacaoFechamentoZ(pk,ConstantesTransacao.TRANSACAO_FECHAMENTO_Z,autorizador.getId().toString(),gtFim);

		try{
			getFachadaPDV().inserirTransacao(trans);
		}catch(AppException e){
			e.printStackTrace();
		}
		
		gerenciadorPerifericos.zeraNumeroTransacao();

		return ALTERNATIVA_1;
	}
}
