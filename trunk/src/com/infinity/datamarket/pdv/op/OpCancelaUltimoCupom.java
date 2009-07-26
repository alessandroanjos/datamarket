package com.infinity.datamarket.pdv.op;

import java.util.Date;

import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoCancelamento;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;


public class OpCancelaUltimoCupom extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		TransacaoPK pkUltimaTransacao = (TransacaoPK) gerenciadorPerifericos.getCmos().ler(CMOS.CHAVE_ULTIMA_TRANSACAO);
		if (pkUltimaTransacao != null){
			Usuario autorizador = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.USUARIO_ATUAL);
	
			int numeroTransacao = gerenciadorPerifericos.incrementaNumeroTransacao();
			int loja = gerenciadorPerifericos.getCodigoLoja();
			int componente = gerenciadorPerifericos.getCodigoComponente();
	
			TransacaoPK pk = new TransacaoPK(loja,componente,numeroTransacao,new Date());
			TransacaoCancelamento transCanc = new TransacaoCancelamento(pk,ConstantesTransacao.TRANSACAO_CANCELAMENTO,pkUltimaTransacao.getLoja(),pkUltimaTransacao.getComponente(),pkUltimaTransacao.getNumeroTransacao(),pkUltimaTransacao.getDataTransacao(),"2",autorizador.getId().toString());
			try {
				getFachadaPDV().inserirTransacao(transCanc);
			} catch (AppException e) {
				e.printStackTrace();
				return ALTERNATIVA_1;
			}
		}else{
			
			gerenciadorPerifericos.getDisplay().setMensagem("Cancelamento Inválido");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ALTERNATIVA_1;
		}
		return ALTERNATIVA_1;
	}
}
