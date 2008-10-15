/*
 * Created on 11/07/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.comum.util;

import java.util.ResourceBundle;

/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Queries {
	public static final String RELATORIO_ANALITICO_VENDAS;
	
	
	static {
		
			
			ResourceBundle rs = ResourceBundle.getBundle("queries");		
			
			RELATORIO_ANALITICO_VENDAS = rs.getString("RELATORIO_ANALITICO_VENDAS");			
				
		
	}
	
	
}
