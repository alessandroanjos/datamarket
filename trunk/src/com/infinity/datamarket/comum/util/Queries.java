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
	public static final String RELATORIO_ABC_VENDAS_VALOR;
	public static final String RELATORIO_ABC_VENDAS_QTD;
	public static final String RELATORIO_ABC_VENDAS_TOTAL;
	public static final String RELATORIO_ANALITICO_ENTRADAS;
	public static final String RELATORIO_ANALITICO_MOVIMENTACAO_ESTOQUE;
	public static final String RELATORIO_ANALITICO_OPERACAO_DEVOLUCAO ;
	public static final String RELATORIO_FECHAMENTO_CAIXA_OPERADOR  ;
	public static final String RELATORIO_FECHAMENTO_CAIXA_OPERADOR_GERAL ;
	public static final String RELATORIO_FECHAMENTO_CAIXA_GERAL ;
	public static final String RELATORIO_COMISSAO_VENDEDOR ;
	
	
	static {
			ResourceBundle rs = ResourceBundle.getBundle("\\resources\\Queries", Locale.getDefault());
						
			RELATORIO_ANALITICO_VENDAS = rs.getString("RELATORIO_ANALITICO_VENDAS");
			RELATORIO_ABC_VENDAS_VALOR = rs.getString("RELATORIO_ABC_VENDAS_VALOR");
			RELATORIO_ABC_VENDAS_QTD = rs.getString("RELATORIO_ABC_VENDAS_QTD");
			RELATORIO_ABC_VENDAS_TOTAL = rs.getString("RELATORIO_ABC_VENDAS_TOTAL");
			RELATORIO_ANALITICO_ENTRADAS = rs.getString("RELATORIO_ANALITICO_ENTRADAS");			
			RELATORIO_ANALITICO_MOVIMENTACAO_ESTOQUE = rs.getString("RELATORIO_ANALITICO_MOVIMENTACAO_ESTOQUE");
			RELATORIO_ANALITICO_OPERACAO_DEVOLUCAO = rs.getString("RELATORIO_ANALITICO_OPERACAO_DEVOLUCAO");
			RELATORIO_FECHAMENTO_CAIXA_OPERADOR = rs.getString("RELATORIO_FECHAMENTO_CAIXA_OPERADOR");
			RELATORIO_FECHAMENTO_CAIXA_OPERADOR_GERAL = rs.getString("RELATORIO_FECHAMENTO_CAIXA_OPERADOR_GERAL");
			RELATORIO_FECHAMENTO_CAIXA_GERAL = rs.getString("RELATORIO_FECHAMENTO_CAIXA_GERAL");
			RELATORIO_COMISSAO_VENDEDOR = rs.getString("RELATORIO_COMISSAO_VENDEDOR");
	}
	
	
}
