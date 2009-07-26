package com.infinity.datamarket.pdv.op;

import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpExibeOperadorDisplay extends Mic{



	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		Usuario usu = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.OPERADOR_ATUAL);
		if (usu == null){
			gerenciadorPerifericos.getDisplay().setUsuario("");
		}else{
			gerenciadorPerifericos.getDisplay().setUsuario(usu.getNome());
		}		
		return ALTERNATIVA_1;
	}
}
