/*
 * Created on 11/07/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.report;

import java.util.ResourceBundle;

/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Queries {
	public static final String RELATORIO_ANALITICO_DESPESAS;
	public static final String RELATORIO_ANALITICO_RECEITA;
	public static final String RELATORIO_RESUMO_DIARIO_RECEITA;
	public static final String RELATORIO_RESUMO_DIARIO_DESPESA;
	public static final String RELATORIO_RESUMO_DIARIO_DESPESA_X_RECEITA;
	public static final String RELATORIO_DESPESA_POR_TIPO;
	public static final String RELATORIO_RECEITA_POR_FORMA;
	
	static {
		
			
			ResourceBundle rs = ResourceBundle.getBundle("queries");		
			
			RELATORIO_ANALITICO_DESPESAS = rs.getString("RELATORIO_ANALITICO_DESPESAS");
			RELATORIO_ANALITICO_RECEITA = rs.getString("RELATORIO_ANALITICO_RECEITA");
			RELATORIO_RESUMO_DIARIO_RECEITA = rs.getString("RELATORIO_RESUMO_DIARIO_RECEITA");
			RELATORIO_RESUMO_DIARIO_DESPESA = rs.getString("RELATORIO_RESUMO_DIARIO_DESPESA");
			RELATORIO_RESUMO_DIARIO_DESPESA_X_RECEITA = rs.getString("RELATORIO_RESUMO_DIARIO_DESPESA_X_RECEITA");
			RELATORIO_DESPESA_POR_TIPO = rs.getString("RELATORIO_DESPESA_POR_TIPO");
			RELATORIO_RECEITA_POR_FORMA = rs.getString("RELATORIO_RECEITA_POR_FORMA");
				
		
	}
	
	
}
