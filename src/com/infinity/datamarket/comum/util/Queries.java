/*
 * Created on 11/07/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Queries {
	public static final String RELATORIO_ANALITICO_VENDAS;
	public static final String RELATORIO_ANALITICO_ENTRADAS;
	public static final String RELATORIO_ANALITICO_MOVIMENTACAO_ESTOQUE;
	public static final String RELATORIO_ANALITICO_OPERACAO_DEVOLUCAO ;
	
	
	static {
			ResourceBundle rs = ResourceBundle.getBundle("/resources/Queries", Locale.getDefault());
						
			RELATORIO_ANALITICO_VENDAS = rs.getString("RELATORIO_ANALITICO_VENDAS");
			RELATORIO_ANALITICO_ENTRADAS = rs.getString("RELATORIO_ANALITICO_ENTRADAS");
			RELATORIO_ANALITICO_MOVIMENTACAO_ESTOQUE = rs.getString("RELATORIO_ANALITICO_MOVIMENTACAO_ESTOQUE");
			RELATORIO_ANALITICO_OPERACAO_DEVOLUCAO = rs.getString("RELATORIO_ANALITICO_OPERACAO_DEVOLUCAO");
	}
	
	
}
