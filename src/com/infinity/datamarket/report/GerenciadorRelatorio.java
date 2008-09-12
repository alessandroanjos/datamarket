/*
 * Created on 09/07/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.report;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.clientepagamento.ClientePagamento;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.JExtenso;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.enterprise.gui.util.BackBean;


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
	
	public static final String RECIBO_PAGAMENTO_CLIENTE;
	
	public static final String EMPRESA;
	
	private static ResourceBundle rs = ResourceBundle.getBundle("relatorio");

	static {		
	
		CAMINHO_RELATORIO = rs.getString("CAMINHO_RELATORIO");
	
		RECIBO_VENDA = rs.getString("RECIBO_VENDA");
		
		EMPRESA = rs.getString("EMPRESA");
		
		RECIBO_PAGAMENTO_CLIENTE = rs.getString("RECIBO_PAGAMENTO_CLIENTE");
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
				JasperReport jr = (JasperReport) JRLoader.loadObject(new FileInputStream(CAMINHO_RELATORIO+layout));
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
			JasperReport jasperItens =  getRelatorio(RECIBO_VENDA);

			Map parametros = new HashMap();
			parametros.put("CAMINHO", this.CAMINHO_RELATORIO);

			parametros.put("empresa",EMPRESA);
			parametros.put("loja", transacao.getPk().getLoja()+"");
			parametros.put("componente", transacao.getPk().getComponente()+"");
			parametros.put("data", transacao.getPk().getDataTransacao());
			parametros.put("num_transacao", transacao.getPk().getNumeroTransacao()+"");
			parametros.put("cupom", transacao.getNumeroCupom());
			parametros.put("operador", transacao.getCodigoUsuarioOperador() + " - " + transacao.getOperador());
			parametros.put("vendedor", transacao.getCodigoUsuarioVendedor()==null?"":transacao.getCodigoUsuarioVendedor() + " - " + transacao.getVendedor());
			parametros.put("cliente", transacao.getCliente()==null?"":transacao.getCliente().getNomeCliente());
			parametros.put("desconto", transacao.getDescontoCupom()==null?BigDecimal.ZERO:transacao.getDescontoCupom());
			parametros.put("total", transacao.getValorCupom());
						

			ArrayList colItensRegistrados =  new ArrayList();
			ArrayList colPagamentos = new ArrayList();

			Collection coll = transacao.getEventosTransacao();
			Iterator i = coll.iterator();

			while(i.hasNext()){
				EventoTransacao ev = (EventoTransacao) i.next();
				if (ev instanceof EventoItemRegistrado){
					colItensRegistrados.add(ev);
				}else if (ev instanceof EventoItemPagamento){
					colPagamentos.add(ev);
				}
			}

			List resposta = new ArrayList();
			resposta.add(new Uniao(colPagamentos,colItensRegistrados));

            JasperPrint jasperPrintItemRegistrado = JasperFillManager.fillReport(jasperItens,
        			parametros, new RelatorioDataSource ( resposta) );

			//exibe o resultado
			viewer = new JasperViewer( jasperPrintItemRegistrado , false );
			return viewer;
		}catch(Exception e){
			throw new RelatorioException(e);
		}
	}
	
	public void gerarReciboPagamentoCliente(ClientePagamento clientePagamento) throws AppException{
		JasperViewer viewer = null;
		try{
			JasperReport jasperItens =  getRelatorio(RECIBO_PAGAMENTO_CLIENTE);

			Map parametros = new HashMap();
			parametros.put("CAMINHO", this.CAMINHO_RELATORIO);
			parametros.put("empresa", EMPRESA);		
			
			StringBuffer textoRecibo = new StringBuffer();
			textoRecibo.append(Util.completaString("", " ", 15, true));
			textoRecibo.append(rs.getString("TEXTO_PAGAMENTO_CLIENTE_PARTE1"));
			
			if(clientePagamento.getCliente().getTipoPessoa().equals(Cliente.PESSOA_FISICA)){
				textoRecibo.append(" " + clientePagamento.getCliente().getNomeCliente().toUpperCase());
				textoRecibo.append(", CPF Nº ");
				textoRecibo.append(BackBean.formataCpfCnpj(clientePagamento.getCliente().getCpfCnpj()));
			}else{
				textoRecibo.append(" " + clientePagamento.getCliente().getNomeFantasia().toUpperCase());
				textoRecibo.append(", CNPJ Nº ");
				textoRecibo.append(BackBean.formataCpfCnpj(clientePagamento.getCliente().getCpfCnpj()));				
			}
			
			textoRecibo.append(rs.getString("TEXTO_PAGAMENTO_CLIENTE_PARTE2"));
			
			textoRecibo.append((new DecimalFormat().format(Double.valueOf(clientePagamento.getValorPagamento().toString()))));
			
			JExtenso valorPorExtenso = new JExtenso(clientePagamento.getValorPagamento());
			
		    textoRecibo.append(" (" + valorPorExtenso.toString() + "), ");  
			textoRecibo.append(rs.getString("TEXTO_PAGAMENTO_CLIENTE_PARTE3"));
				
			parametros.put("textoRecibo", textoRecibo.toString());

			StringBuffer textoDataPagamento = new StringBuffer();
			
			Date dataPagamento = new Date(System.currentTimeMillis());
			
			textoDataPagamento.append(rs.getString("CIDADE") + ", ");
			String dia = dataPagamento.getDate()+"";
			if(dia.length() == 1){
				dia = "0" + dia;
			}
			textoDataPagamento.append(dia);
			textoDataPagamento.append(" de " + Util.retornaMesPorExtenso(dataPagamento.getMonth()) + " de ");
			textoDataPagamento.append(dataPagamento.getYear()+1900 + ".");
			
			parametros.put("dataPagamento", textoDataPagamento.toString());
						
//            JasperPrint jasperPrintReciboPagamento = JasperFillManager.fillReport(jasperItens, parametros);
            
            Connection connection = getConnection();
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
//            InputStream reportStream = context.getExternalContext().getResourceAsStream(CAMINHO_RELATORIO + File.separator +  "ReciboPagamentoCliente.jasper");
            
            InputStream reportStream = GerenciadorRelatorio.class.getResourceAsStream("/resources/ReciboPagamentoCliente.jasper");
            
            ServletOutputStream servletOutputStream = response.getOutputStream();
            JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parametros, connection);
//            connection.close();
            response.setContentType("application/pdf");
            servletOutputStream.flush();
            servletOutputStream.close();
			//exibe o resultado
//			viewer = new JasperViewer(jasperPrintReciboPagamento, false);
//			return viewer;
		}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}
	}
}
