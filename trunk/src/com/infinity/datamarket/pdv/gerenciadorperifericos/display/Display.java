package com.infinity.datamarket.pdv.gerenciadorperifericos.display;

import com.infinity.datamarket.comum.util.AppException;

public interface Display {
	public static final int MASCARA_MONETARIA = 1;
	public static final int MASCARA_PASSWORD = 2;
	public static final int MASCARA_NUMERICA = 3;
	public static final int MASCARA_PERCENTUAL = 4;
	public static final int MASCARA_QUANTIDADE = 5;

	public abstract void setMensagem(String mens);
	public abstract void limpaMensagem();
	public EntradaDisplay lerDados(int[] finalizadoras, int tipo, int tamanho) throws AppException;
	public void setUsuario(String usuario);
	public void setLoja(String loja);
	public void setComponente(String componente);
	public abstract void setValor(String valor);
}
