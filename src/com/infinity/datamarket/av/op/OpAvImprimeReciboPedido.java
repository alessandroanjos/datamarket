package com.infinity.datamarket.av.op;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvImprimeReciboPedido extends Mic{
	
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try {

			int idLoja = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOJA).getValorInteiro();
			Usuario usu = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.USUARIO_ATUAL);
	        int idComponente = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.COMPONENTE).getValorInteiro();
	
			
			OperacaoPedido op = (OperacaoPedido) gerenciadorPerifericos.getCmos().ler(CMOS.OPERACAO_PEDIDO);
	
	
			String arquivo =  idLoja + "_" + idComponente + "_" +  usu.getId() + "_" +   System.currentTimeMillis() + "_" + "pedido.pdf"; // o ideal eh colocar loja + pdv + codigoOperador + timestemp;
	
			String caminho = Util.getDirCorrente();
			File file = new File(caminho, arquivo);
			
			FileOutputStream output = new FileOutputStream(file);

			getFachadaPDV().gerarReciboOperacaoPedido(op,output);			
	
			output.close();
			
			Runtime.getRuntime().exec("cmd /c" + file.toString());
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (AppException e) {

			e.printStackTrace();
		}
		
		
		
		return ALTERNATIVA_1;
	}
}