package com.infinity.datamarket.av.op;

import com.infinity.datamarket.av.gui.telas.TelaAVInicial;
import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.componente.Componente;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.usuario.Loja;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.AutorizacaoRecusadaException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.ServiceLocator;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOSArquivo;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.ConstantesTela;
import com.infinity.datamarket.pdv.gui.telas.TelaMenssagem;
import com.infinity.datamarket.pdv.maquinaestados.MacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;

public class OpAVIniciaAV extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try{

			iniciaAv(gerenciadorPerifericos);
			gerenciadorPerifericos.getDisplay().setMensagem("");

		}catch(Exception e){
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Erro");
			try{
				gerenciadorPerifericos.esperaVolta();
			}catch(AppException ex){

			}
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}

	public static void iniciaAv(GerenciadorPerifericos gerenciadorPerifericos) {
		TelaAVInicial tela = (TelaAVInicial) ServiceLocator.getInstancia().getTela(ConstantesTela.TELA_AV_INICIAL);
		
//		String componente = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.COMPONENTE).toString();
//		int idLoja = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOJA).getValorInteiro();

        Loja loja = (Loja)gerenciadorPerifericos.getCmos().ler(CMOSArquivo.LOJA);
        Componente componente = (Componente)gerenciadorPerifericos.getCmos().ler(CMOSArquivo.COMPONENTE);

        tela.setCampoComponente(componente.getDescricao());
		tela.setCampoDesconto("");
		tela.setCampoLoja("" + loja.getNome());
		tela.setCampoUsuario("");
		tela.setCampoCliente("");
		tela.setCampoOperacao("");
		tela.setCampoCodigoProduto("");
		tela.setCampoDescricaoProduto("");
		tela.setCampoQuantidade("");

		gerenciadorPerifericos.atualizaTela(tela);
	}
}
