/*
 * Created on 09/07/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.util.AppException;


/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GerenciadorRelatorio {

	public static final String CAMINHO_RELATORIO;
	public static final String RELATORIO_ANALITICO_DESPESA;
	public static final String RELATORIO_ANALITICO_RECEITA;
	public static final String RELATORIO_DESPESA_TIPO;
	public static final String RELATORIO_RECEITA_FORMA;
	public static final String RELATORIO_RESUMO_DIARIO_RECEITA;
	public static final String RELATORIO_RESUMO_DIARIO_DESPESA;
	public static final String RELATORIO_RESUMO_DIARIO_DESPESA_X_RECEITA;

	static {
		
			
			ResourceBundle rs = ResourceBundle.getBundle("relatorio");		
		
			CAMINHO_RELATORIO = rs.getString("CAMINHO_RELATORIO");
			
			RELATORIO_ANALITICO_DESPESA = rs.getString("RELATORIO_ANALITICO_DESPESA");
			RELATORIO_ANALITICO_RECEITA = rs.getString("RELATORIO_ANALITICO_RECEITA");
			RELATORIO_DESPESA_TIPO = rs.getString("RELATORIO_DESPESA_TIPO");
			RELATORIO_RECEITA_FORMA = rs.getString("RELATORIO_RECEITA_FORMA");
			RELATORIO_RESUMO_DIARIO_RECEITA = rs.getString("RELATORIO_RESUMO_DIARIO_RECEITA");
			RELATORIO_RESUMO_DIARIO_DESPESA = rs.getString("RELATORIO_RESUMO_DIARIO_DESPESA");
			RELATORIO_RESUMO_DIARIO_DESPESA_X_RECEITA = rs.getString("RELATORIO_RESUMO_DIARIO_DESPESA_X_RECEITA");	
		
	}
	
	
	private static GerenciadorRelatorio instancia;
	
	public static GerenciadorRelatorio getInstancia(){
		if (instancia == null){
			instancia = new GerenciadorRelatorio();
		}
		return instancia;
	}

	
	private Connection getConnection() throws RelatorioException{
		Session session = null;
        try
        {
            session = RepositoryManagerHibernateUtil.currentSession();
            return session.connection();
        }
        catch(HibernateException e)
        {
            throw new RelatorioException(e);
        }
	}
	
	private JasperReport getRelatorio(String layout) throws RelatorioException{
		try{
			//gerando o jasper design
			JasperDesign desenho = JRXmlLoader.load( CAMINHO_RELATORIO+layout );
			   
			//compila o relatório
			return JasperCompileManager.compileReport( desenho );
		}catch(Exception e){
			throw new RelatorioException(e);
		}		
	}
	
	public void gerarRelatorioAnaliticaDespesa(Date dataInicial, Date dataFinal) throws AppException{
		
		try{
			Connection con = getConnection();
			JasperReport relatorio = getRelatorio(RELATORIO_ANALITICO_DESPESA);
			PreparedStatement pstm = con.prepareStatement(Queries.RELATORIO_ANALITICO_DESPESAS);
			pstm.setDate(1,new java.sql.Date(dataInicial.getTime()));
			pstm.setDate(2,new java.sql.Date(dataFinal.getTime()));
			ResultSet rs = pstm.executeQuery();
	
			//implementação da interface JRDataSource para DataSource ResultSet
			JRResultSetDataSource jrRS = new JRResultSetDataSource( rs );
			//executa o relatório
			Map parametros = new HashMap();
//			parametros.put("EMPRESA", Parametros.EMPRESA);
			parametros.put("DATA_INICIAL", dataInicial);
			parametros.put("DATA_FINAL", dataFinal);
			JasperPrint impressao = JasperFillManager.fillReport( relatorio , parametros,    jrRS );		
			//exibe o resultado
			JasperViewer viewer = new JasperViewer( impressao , false );
			viewer.show();
		}catch(Exception e){
			throw new RelatorioException(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
}
