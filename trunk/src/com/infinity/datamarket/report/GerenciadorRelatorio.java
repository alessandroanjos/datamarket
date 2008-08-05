/*
 * Created on 09/07/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.report;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

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
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;


/**
 * @author wagner.medeiros
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GerenciadorRelatorio {
	
	private Hashtable relatorios;

	public static final String CAMINHO_RELATORIO;
	
	public static final String RECIBO_VENDA;
	
	static {
		
			
			ResourceBundle rs = ResourceBundle.getBundle("relatorio");		
		
			CAMINHO_RELATORIO = rs.getString("CAMINHO_RELATORIO");
			
			RECIBO_VENDA = rs.getString("RECIBO_VENDA");
			
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
		if (relatorios == null){
			relatorios = new Hashtable();
		}
		if (relatorios.containsKey(layout)){
			return (JasperReport) relatorios.get(layout);
		}else{
			try{
				//gerando o jasper design
				JasperDesign desenho = JRXmlLoader.load( CAMINHO_RELATORIO+layout );
				   
				//compila o relatório
				JasperReport jr = JasperCompileManager.compileReport( desenho );
				relatorios.put(layout, jr);
				return jr;
			}catch(Exception e){
				throw new RelatorioException(e);
			}
		}
	}
	
	public JasperViewer gerarReciboVenda(TransacaoVenda transacao) throws AppException{
		JasperViewer viewer = null;
		try{
			JasperReport relatorio = getRelatorio(RECIBO_VENDA);
			

			Map parametros = new HashMap();

			parametros.put("empresa","Magia dos Pães");
			parametros.put("loja", ""+transacao.getPk().getLoja());
			parametros.put("componente", ""+transacao.getPk().getComponente());
			parametros.put("data", transacao.getPk().getDataTransacao());
			parametros.put("num_transacao", ""+transacao.getPk().getNumeroTransacao());
			parametros.put("cupom", transacao.getNumeroCupom());
			parametros.put("operador", transacao.getCodigoUsuarioOperador());
			parametros.put("vendedor", transacao.getCodigoUsuarioVendedor());
			parametros.put("desconto", transacao.getDescontoCupom());
			parametros.put("total", transacao.getValorCupom());
			
			JasperPrint impressao = JasperFillManager.fillReport( relatorio , parametros);
			
			//exibe o resultado
			viewer = new JasperViewer( impressao , false );
			return viewer;
		}catch(Exception e){
			throw new RelatorioException(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
}
