package com.infinity.datamarket.pdv.maquinaestados;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;

public abstract class Mic {

	public static final int ALTERNATIVA_1 = 1;
	public static final int ALTERNATIVA_2 = 2;
	public static final int ALTERNATIVA_3 = 3;
	public static final int ALTERNATIVA_4 = 4;
	public static final int ALTERNATIVA_5 = 5;
	public static final int ALTERNATIVA_6 = 6;
	public static final int ALTERNATIVA_7 = 7;
	public static final int ALTERNATIVA_8 = 8;
	public static final int ALTERNATIVA_9 = 9;
	public static final int ALTERNATIVA_10 = 10;


	public abstract int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param);

	public Fachada getFachadaPDV(){
		return Fachada.getInstancia();
	}
}
