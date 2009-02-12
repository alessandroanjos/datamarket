/*
 * Created on 09/07/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.report;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperRunManager;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.estoque.EntradaProduto;
import com.infinity.datamarket.comum.estoque.MovimentacaoEstoque;
import com.infinity.datamarket.comum.estoque.ProdutoEntradaProduto;
import com.infinity.datamarket.comum.estoque.ProdutoMovimentacaoEstoque;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.OperacaoDevolucao;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoPagamentoCartaoProprio;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.comum.util.JExtenso;
import com.infinity.datamarket.comum.util.Queries;
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

	public static final String EMPRESA;
	
	
	private static ResourceBundle rs = ResourceBundle.getBundle("relatorio");

	static {		
	
		EMPRESA = rs.getString("EMPRESA");
	
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

	public void gerarReciboVenda(TransacaoVenda transacao, OutputStream out) throws AppException{
		
		try{
			Map parametros = new HashMap();
			parametros.put("CAMINHO", "\\resources\\");

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

			BigDecimal quantidade = BigDecimal.ZERO;
			
			while(i.hasNext()){
				EventoTransacao ev = (EventoTransacao) i.next();
				if (ev instanceof EventoItemRegistrado){
					EventoItemRegistrado evir = (EventoItemRegistrado) ev;
					if (evir.getSituacao().equals(EventoItemRegistrado.ATIVO)){
						quantidade = quantidade.add(evir.getQuantidade());
						colItensRegistrados.add(ev);
					}					
				}else if (ev instanceof EventoItemPagamento){
					colPagamentos.add(ev);
				}
			}
			
			parametros.put("quantidade", quantidade);

			List resposta = new ArrayList();
			resposta.add(new Uniao(colPagamentos,colItensRegistrados));
            
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/ReciboVenda.jasper");
    		
			Iterator it = parametros.entrySet().iterator();

			while(it.hasNext()){
				System.out.println(it.next().toString());
			}
			
            JasperRunManager.runReportToPdfStream(input, out, parametros, new RelatorioDataSource (resposta));
		}catch(Exception e){
			throw new RelatorioException(e);
		}
	}

	public OutputStream gerarReciboPagamentoCliente(TransacaoPagamentoCartaoProprio transacao) throws AppException{
		
		OutputStream out  = new ByteArrayOutputStream();
		
		try{
			Map parametros = new HashMap();

			parametros.put("empresa", EMPRESA);	
			
			parametros.put("loja", transacao.getPk().getLoja()+"");
			parametros.put("componente", transacao.getPk().getComponente()+"");
			parametros.put("data", transacao.getPk().getDataTransacao());
			parametros.put("num_transacao", transacao.getPk().getNumeroTransacao()+"");
			parametros.put("operador", transacao.getOperador());
			
			StringBuffer textoRecibo = new StringBuffer();
			textoRecibo.append(Util.completaString("", " ", 15, true));
			textoRecibo.append(rs.getString("TEXTO_PAGAMENTO_CLIENTE_PARTE1"));
			
			if(transacao.getCPFCNPJ().length() == 11){
				textoRecibo.append(" " + transacao.getNome().toUpperCase());
				textoRecibo.append(", CPF N� ");
				textoRecibo.append(BackBean.formataCpfCnpj(transacao.getCPFCNPJ()));
			}else{
				textoRecibo.append(" " + transacao.getNome().toUpperCase());
				textoRecibo.append(", CNPJ N� ");
				textoRecibo.append(BackBean.formataCpfCnpj(transacao.getCPFCNPJ()));				
			}
			
			textoRecibo.append(", " + rs.getString("TEXTO_PAGAMENTO_CLIENTE_PARTE2"));
			
			textoRecibo.append((new DecimalFormat().format(Double.valueOf(transacao.getValor().toString()))));
			
			JExtenso valorPorExtenso = new JExtenso(transacao.getValor());
			
		    textoRecibo.append(" (" + valorPorExtenso.toString() + "), ");  
			textoRecibo.append(rs.getString("TEXTO_PAGAMENTO_CLIENTE_PARTE3"));
				
			parametros.put("textoRecibo", textoRecibo.toString());

			
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/ReciboPagamentoCliente.jasper");
            		
			Iterator it = parametros.entrySet().iterator();

			while(it.hasNext()){
				System.out.println(it.next().toString());
			}
			
			List lista = new ArrayList();
			
			lista.add("texto em branco");
			
			RelatorioDataSource rel = new RelatorioDataSource(lista);
			
            JasperRunManager.runReportToPdfStream(input, out, parametros, rel);
            
   		}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}
   		
   		return out;
	}
	
	public void gerarReciboMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque, OutputStream out) throws AppException{
		try{
			Map<String, String> parametros = new HashMap<String, String>();

			parametros.put("empresa", EMPRESA);	
			parametros.put("codigo", Util.completaString(movimentacaoEstoque.getId().toString(), "0", 4, false));
			parametros.put("lojaSaida", movimentacaoEstoque.getEstoqueSaida().getPk().getLoja().getNome());
			parametros.put("estoqueSaida", movimentacaoEstoque.getEstoqueSaida().getDescricao());
			parametros.put("lojaEntrada", movimentacaoEstoque.getEstoqueEntrada().getPk().getLoja().getNome());
			parametros.put("estoqueEntrada", movimentacaoEstoque.getEstoqueEntrada().getDescricao());
			
			DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
			
			parametros.put("dataMovimentacao", dt.format(movimentacaoEstoque.getDataMovimentacao()));
			
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/ReciboMovimentacaoEstoque.jasper");
            		
			Iterator it = parametros.entrySet().iterator();

			while(it.hasNext()){
				System.out.println(it.next().toString());
			}
			
			List<ProdutoMovimentacaoEstoque> listaItens = new ArrayList<ProdutoMovimentacaoEstoque>();
			
			Iterator itMovEstoque = movimentacaoEstoque.getProdutosMovimentacao().iterator();
			while(itMovEstoque.hasNext()){
				ProdutoMovimentacaoEstoque pme = (ProdutoMovimentacaoEstoque)itMovEstoque.next();
				pme.setProdutoMovimentacaoEstoque(pme);
				listaItens.add(pme);
			}
			
			RelatorioDataSource rel = new RelatorioDataSource(listaItens);
			
            JasperRunManager.runReportToPdfStream(input, out, parametros, rel);
   		}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}
	}
	
	public void gerarReciboEntradaProdutosEstoque(EntradaProduto entradaProduto, OutputStream out) throws AppException{
		try{
			
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/ReciboEntradaProdutosEstoque.jasper");
			
			Map<String, String> parametros = new HashMap<String, String>();
			
			DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

			parametros.put("empresa", EMPRESA);	
			parametros.put("numeroNota", Util.completaString(entradaProduto.getNumeroNota(), "0", 15, false));
			parametros.put("dataEmissao", dt.format(entradaProduto.getDataEmissaoNota()));
			parametros.put("dataEntrada", dt.format(entradaProduto.getDataEntrada()));
			Fornecedor fornecedor = null;
			if(entradaProduto.getFornecedor() != null){
				fornecedor = entradaProduto.getFornecedor();
			}else if(entradaProduto.getIdFornecedor() != null && !entradaProduto.getIdFornecedor().equals("0")){
				fornecedor = Fachada.getInstancia().consultaFornecedorPorId(new Long(entradaProduto.getIdFornecedor().toString()));
			}
			
			if(fornecedor != null){
				if(fornecedor.getTipoPessoa().equals(Fornecedor.PESSOA_FISICA)){
					parametros.put("fornecedor", fornecedor.getId().toString() + " - " + fornecedor.getNomeFornecedor());
					parametros.put("labelCpfCnpj", "CPF:");
					parametros.put("cpfCnpj", BackBean.formataCpfCnpj(fornecedor.getCpfCnpj().trim().replace(".", "").replace("/", "").replace("-", "")));
					parametros.put("pessoaContato", fornecedor.getPessoaContato());
					parametros.put("foneContato", fornecedor.getFoneContato());
				}else{
					parametros.put("fornecedor", fornecedor.getId().toString() + " - " + fornecedor.getRazaoSocial());
					parametros.put("labelCpfCnpj", "CNPJ:");
					parametros.put("cpfCnpj", BackBean.formataCpfCnpj(fornecedor.getCpfCnpj()));
					parametros.put("pessoaContato", fornecedor.getPessoaContato());
					parametros.put("foneContato", fornecedor.getFoneContato());
				}
			}else{
				parametros.put("fornecedor", "");
				parametros.put("labelCpfCnpj", "");
				parametros.put("labelCpfCnpj", "");
				parametros.put("pessoaContato", "");
				parametros.put("foneContato", "");
			}
			
			parametros.put("valorFrete", entradaProduto.getFrete() != null ? entradaProduto.getFrete().setScale(2).toString(): BigDecimal.ZERO.setScale(2).toString());
			parametros.put("valorICMS", entradaProduto.getIcms() != null ? entradaProduto.getIcms().setScale(2).toString(): BigDecimal.ZERO.setScale(2).toString());
			parametros.put("valorIPI", entradaProduto.getIpi() != null ? entradaProduto.getIpi().setScale(2).toString(): BigDecimal.ZERO.setScale(2).toString());
			parametros.put("valorDesconto", entradaProduto.getDesconto() != null ? entradaProduto.getDesconto().setScale(2).toString(): BigDecimal.ZERO.setScale(2).toString());
			parametros.put("valorTotal", entradaProduto.getValor() != null ? entradaProduto.getValor().setScale(2).toString(): BigDecimal.ZERO.setScale(2).toString());
			parametros.put("quantidadeTotal", entradaProduto.getQuantidadeTotal() != null ? entradaProduto.getQuantidadeTotal().setScale(3).toString(): BigDecimal.ZERO.setScale(3).toString());
			parametros.put("status", entradaProduto.getStatus().equals(Constantes.STATUS_ATIVO) ? "Ativa" : "Cancelada");
			
			Iterator it = parametros.entrySet().iterator();

			while(it.hasNext()){
				System.out.println(it.next().toString());
			}
			List<ProdutoEntradaProduto> listaItens = new ArrayList<ProdutoEntradaProduto>();
			
			Iterator itEntradaProdutos = entradaProduto.getProdutosEntrada().iterator();
			while(itEntradaProdutos.hasNext()){
				ProdutoEntradaProduto pep = (ProdutoEntradaProduto)itEntradaProdutos.next();
				pep.setProdutoEntradaProduto(pep);
				listaItens.add(pep);
			}
			
			RelatorioDataSource rel = new RelatorioDataSource(listaItens);
			
            JasperRunManager.runReportToPdfStream(input, out, parametros, rel);
   		}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}
	}

	
	public OutputStream gerarRelatorioAnaliticoVendas(int loja, Date data_inicio_movimento, Date data_fim_movimento) throws AppException{
		
		OutputStream out  = new ByteArrayOutputStream();
		ResultSet rs = null;
		PreparedStatement pstm = null;
		try{
			Map parametros = new HashMap();

			parametros.put("empresa", EMPRESA);				
						
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/RelatorioFechamentoVenda.jasper");
            					
			Connection con = getConnection();
			pstm = con.prepareStatement(Queries.RELATORIO_ANALITICO_VENDAS);
			
			//data inicio
			Calendar c = new GregorianCalendar();
			c.setTime(data_inicio_movimento);
			int d1_dia = c.get(Calendar.DAY_OF_MONTH);
			int d1_mes = c.get(Calendar.MONTH);
			int d1_ano = c.get(Calendar.YEAR);
			Date dataInicio= new GregorianCalendar(d1_ano,d1_mes,d1_dia).getTime();
			
			//dataFim
			c = new GregorianCalendar();
			c.setTime(data_fim_movimento);
			int d2_dia = c.get(Calendar.DAY_OF_MONTH);
			int d2_mes = c.get(Calendar.MONTH);
			int d2_ano = c.get(Calendar.YEAR);
			Date dataFim= new GregorianCalendar(d2_ano,d2_mes,d2_dia,23,59,59).getTime();
			
			pstm.setInt(1,loja);
			pstm.setDate(2,new java.sql.Date(dataInicio.getTime()));			
			pstm.setDate(3,new java.sql.Date(dataFim.getTime()));
			
			rs = pstm.executeQuery();
				
			JRResultSetDataSource jrRS = new JRResultSetDataSource( rs );
	
			
            JasperRunManager.runReportToPdfStream(input, out, parametros, jrRS);
            
   		}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}finally{
			try{
				if (rs != null){
					rs.close();
				}
				if (pstm != null){
					pstm.close();
				}
			}catch(Exception e){
				throw new RelatorioException(e);
			}
		}
   		
   		return out;
	}
	
public OutputStream gerarRelatorioAnaliticoEntradas(Date data_inicio_movimento, Date data_fim_movimento, String[] status) throws AppException{
		
		OutputStream out  = new ByteArrayOutputStream();
		ResultSet rs = null;
		PreparedStatement pstm = null;

		try{
			Map parametros = new HashMap();

			parametros.put("empresa", EMPRESA);				
						
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/RelatorioAnaliticoEntrada.jasper");
            					
			Connection con = getConnection();
			
			String sql = Queries.RELATORIO_ANALITICO_ENTRADAS;
			
			if(status.length == 2){
				String statusTmp = "(\'" + status[0] + "\',\'" + status[1] + "\')";
				sql = sql.replace("(?)", statusTmp);
//				sql = sql.replaceAll("(?)", statusTmp);
			}else{
				sql = sql.replace(" IN (?)", " = \'" + status[0] + "\'");
			}			

			
			pstm = con.prepareStatement(sql);
			
			//data inicio
			Calendar c = new GregorianCalendar();
			c.setTime(data_inicio_movimento);
			int d1_dia = c.get(Calendar.DAY_OF_MONTH);
			int d1_mes = c.get(Calendar.MONTH);
			int d1_ano = c.get(Calendar.YEAR);
			Date dataInicio= new GregorianCalendar(d1_ano,d1_mes,d1_dia).getTime();
			
			//dataFim
			c = new GregorianCalendar();
			c.setTime(data_fim_movimento);
			int d2_dia = c.get(Calendar.DAY_OF_MONTH);
			int d2_mes = c.get(Calendar.MONTH);
			int d2_ano = c.get(Calendar.YEAR);
			Date dataFim= new GregorianCalendar(d2_ano,d2_mes,d2_dia,23,59,59).getTime();
			
			pstm.setDate(1,new java.sql.Date(dataInicio.getTime()));			
			pstm.setDate(2,new java.sql.Date(dataFim.getTime()));
		
			System.out.println(sql);
			
			rs = pstm.executeQuery();
				
			JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
			
            JasperRunManager.runReportToPdfStream(input, out, parametros, jrRS);
            
   		}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}finally{
			try{
				if (rs != null){
					rs.close();
				}
				if (pstm != null){
					pstm.close();
				}
			}catch(Exception e){
				throw new RelatorioException(e);
			}
		}
   		
   		return out;
	}

	public OutputStream gerarRelatorioAnaliticoMovimentacaoEstoque(Date data_inicio_movimento, Date data_fim_movimento) throws AppException{
		
		OutputStream out  = new ByteArrayOutputStream();
		ResultSet rs = null;
		PreparedStatement pstm = null;
		try{
			Map parametros = new HashMap();
	
			parametros.put("empresa", EMPRESA);				
						
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/RelatorioAnaliticoMovimentacaoEstoque.jasper");
	        					
			Connection con = getConnection();
			pstm = con.prepareStatement(Queries.RELATORIO_ANALITICO_MOVIMENTACAO_ESTOQUE);
			
			//data inicio
			Calendar c = new GregorianCalendar();
			c.setTime(data_inicio_movimento);
			int d1_dia = c.get(Calendar.DAY_OF_MONTH);
			int d1_mes = c.get(Calendar.MONTH);
			int d1_ano = c.get(Calendar.YEAR);
			Date dataInicio= new GregorianCalendar(d1_ano,d1_mes,d1_dia).getTime();
			
			//dataFim
			c = new GregorianCalendar();
			c.setTime(data_fim_movimento);
			int d2_dia = c.get(Calendar.DAY_OF_MONTH);
			int d2_mes = c.get(Calendar.MONTH);
			int d2_ano = c.get(Calendar.YEAR);
			Date dataFim= new GregorianCalendar(d2_ano,d2_mes,d2_dia,23,59,59).getTime();
			
			pstm.setDate(1,new java.sql.Date(dataInicio.getTime()));			
			pstm.setDate(2,new java.sql.Date(dataFim.getTime()));
			
			rs = pstm.executeQuery();
				
			JRResultSetDataSource jrRS = new JRResultSetDataSource( rs );
	
			
	        JasperRunManager.runReportToPdfStream(input, out, parametros, jrRS);
	        
			}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}finally{
			try{
				if (rs != null){
					rs.close();
				}
				if (pstm != null){
					pstm.close();
				}
			}catch(Exception e){
				throw new RelatorioException(e);
			}
		}
			
			return out;
	}
	
public OutputStream gerarRelatorioAnaliticoOperacoesDevolucao(int loja, Date data_inicio_movimento, Date data_fim_movimento) throws AppException{
		
		OutputStream out  = new ByteArrayOutputStream();
		ResultSet rs = null;
		PreparedStatement pstm = null;
		try{
			Map parametros = new HashMap();
	
			parametros.put("empresa", EMPRESA);				
						
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/RelatorioAnaliticoOperacoesDevolucao.jasper");
	        					
			Connection con = getConnection();
			pstm = con.prepareStatement(Queries.RELATORIO_ANALITICO_OPERACAO_DEVOLUCAO);
			
			//data inicio
			Calendar c = new GregorianCalendar();
			c.setTime(data_inicio_movimento);
			int d1_dia = c.get(Calendar.DAY_OF_MONTH);
			int d1_mes = c.get(Calendar.MONTH);
			int d1_ano = c.get(Calendar.YEAR);
			Date dataInicio= new GregorianCalendar(d1_ano,d1_mes,d1_dia).getTime();
			
			//dataFim
			c = new GregorianCalendar();
			c.setTime(data_fim_movimento);
			int d2_dia = c.get(Calendar.DAY_OF_MONTH);
			int d2_mes = c.get(Calendar.MONTH);
			int d2_ano = c.get(Calendar.YEAR);
			Date dataFim= new GregorianCalendar(d2_ano,d2_mes,d2_dia,23,59,59).getTime();
			
			pstm.setDate(1,new java.sql.Date(dataInicio.getTime()));			
			pstm.setDate(2,new java.sql.Date(dataFim.getTime()));
			pstm.setInt(3,loja);
			
			rs = pstm.executeQuery();
				
			JRResultSetDataSource jrRS = new JRResultSetDataSource( rs );
	
			
	        JasperRunManager.runReportToPdfStream(input, out, parametros, jrRS);
	        
			}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}finally{
			try{
				if (rs != null){
					rs.close();
				}
				if (pstm != null){
					pstm.close();
				}
			}catch(Exception e){
				throw new RelatorioException(e);
			}
		}
			
			return out;
	}

	
	public static void main(String[] a){
		try{
			System.out.println(Util.completaString("25", "0", 4, false));
//			Date d1 = new Date();
//			d1.setDate(1);
//			Date d2 = new Date();
//			d2.setDate(16);
//		
//			
//			ByteArrayOutputStream out = (ByteArrayOutputStream) GerenciadorRelatorio.getInstancia().gerarRelatorioFechamentoCaixaGeral(1, d1, new Date());
//			
//			String caminho = "c:\\pdv\\temp\\";
//			File dir = new File(caminho);
//			if (!dir.exists()){
//				dir.mkdir();
//			}
//			String nomeArquivo = caminho+"DEVOLUCAO.pdf";						
//			FileOutputStream f = new FileOutputStream(nomeArquivo);
//			f.write(out.toByteArray());
//			f.flush();
//			f.close();
//			Runtime.getRuntime().exec("cmd /c"+nomeArquivo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	
	public void gerarReciboDevolucaoProdutos(OperacaoDevolucao operacaoDevolucao, OutputStream out) throws AppException{
		try{
			
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/ReciboDevolucaoProdutos.jasper");
			
			Map<String, String> parametros = new HashMap<String, String>();
			
			DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

			parametros.put("empresa", EMPRESA);	
			
			Iterator it = parametros.entrySet().iterator();

//			while(it.hasNext()){
//				String o = (String)it.next();
//				System.out.println(o);
//			}
			List<EventoOperacaoItemRegistrado> listaItens = new ArrayList<EventoOperacaoItemRegistrado>();
					
			RelatorioDataSource rel = new RelatorioDataSource(listaItens);
			
            JasperRunManager.runReportToPdfStream(input, out, parametros, rel);
   		}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}
	}
	
public void gerarReciboOperacaoDevolucao(OperacaoDevolucao devolucao, OutputStream out) throws AppException{
		
		try{
			Map parametros = new HashMap();

			parametros.put("empresa",EMPRESA);
			parametros.put("loja", devolucao.getPk().getLoja());			
			parametros.put("id", devolucao.getPk().getId());
			parametros.put("valor", devolucao.getValor());
			parametros.put("data", devolucao.getData());
			parametros.put("status", devolucao.getStatus());

			

			List resposta = new ArrayList(devolucao.getEventosOperacao());
            
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/ReciboOperacoesDevolucao.jasper");
    		
			Iterator it = parametros.entrySet().iterator();

			while(it.hasNext()){
				System.out.println(it.next().toString());
			}
			
            JasperRunManager.runReportToPdfStream(input, out, parametros, new RelatorioDataSource (resposta));
		}catch(Exception e){
			throw new RelatorioException(e);
		}
	}

	public OutputStream gerarRelatorioABCVendas(int loja, Date data_inicio_movimento, Date data_fim_movimento, String tipo) throws AppException{
		
		OutputStream out  = new ByteArrayOutputStream();
		ResultSet rs = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm1 = null;
		try{
			Map parametros = new HashMap();
	
			parametros.put("empresa", EMPRESA);				
						
			Connection con = getConnection();
			
			pstm1 = con.prepareStatement(Queries.RELATORIO_ABC_VENDAS_TOTAL);
			InputStream input = null;
			
			if (tipo.equals(Constantes.CONSTANTE_VALOR)){
				pstm = con.prepareStatement(Queries.RELATORIO_ABC_VENDAS_VALOR);
				input = GerenciadorRelatorio.class.getResourceAsStream("/resources/RelatorioABCVendasValor.jasper");
			}else if (tipo.equals(Constantes.CONSTANTE_QUANTIDADE)){
				pstm = con.prepareStatement(Queries.RELATORIO_ABC_VENDAS_QTD);
				input = GerenciadorRelatorio.class.getResourceAsStream("/resources/RelatorioABCVendasQuantidade.jasper");
			}			
			
			//data inicio
			Calendar c = new GregorianCalendar();
			c.setTime(data_inicio_movimento);
			int d1_dia = c.get(Calendar.DAY_OF_MONTH);
			int d1_mes = c.get(Calendar.MONTH);
			int d1_ano = c.get(Calendar.YEAR);
			Date dataInicio= new GregorianCalendar(d1_ano,d1_mes,d1_dia).getTime();
			
			//dataFim
			c = new GregorianCalendar();
			c.setTime(data_fim_movimento);
			int d2_dia = c.get(Calendar.DAY_OF_MONTH);
			int d2_mes = c.get(Calendar.MONTH);
			int d2_ano = c.get(Calendar.YEAR);
			Date dataFim= new GregorianCalendar(d2_ano,d2_mes,d2_dia,23,59,59).getTime();
			
			pstm.setInt(3,loja);
			pstm.setDate(1,new java.sql.Date(dataInicio.getTime()));			
			pstm.setDate(2,new java.sql.Date(dataFim.getTime()));
			
			pstm1.setInt(3,loja);
			pstm1.setDate(1,new java.sql.Date(dataInicio.getTime()));			
			pstm1.setDate(2,new java.sql.Date(dataFim.getTime()));
			
			ResultSet rTotal = pstm1.executeQuery();
			
			
			
			BigDecimal total = BigDecimal.ZERO; 
			BigDecimal qtd = BigDecimal.ZERO;
			if (rTotal.next()){
				total = rTotal.getBigDecimal("valor");
				total = total.setScale(2, BigDecimal.ROUND_DOWN);
				qtd = rTotal.getBigDecimal("qtd");
				qtd = qtd.setScale(3, BigDecimal.ROUND_DOWN);
			}
			parametros.put("total", total);
			parametros.put("qtd", qtd);
			
			rs = pstm.executeQuery();
			
			
			JRResultSetDataSource jrRS = new JRResultSetDataSource( rs );
				
	        JasperRunManager.runReportToPdfStream(input, out, parametros, jrRS);
	        
			}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}finally{
			try{
				if (rs != null){
					rs.close();
				}
				if (pstm != null){
					pstm.close();
				}
			}catch(Exception e){
				throw new RelatorioException(e);
			}
		}
			
		return out;
	}

	public OutputStream gerarRelatorioFechamentoCaixaOperador(int loja, Date data_inicio_movimento, Date data_fim_movimento, Integer operador) throws AppException{
		
		OutputStream out  = new ByteArrayOutputStream();
		ResultSet rs = null;
		PreparedStatement pstm = null;
		try{
			Map parametros = new HashMap();
	
			parametros.put("empresa", EMPRESA);				
						
			Connection con = getConnection();
			
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/RelatorioFechamentoCaixaOperador.jasper");
			
			if (operador != null && operador.intValue() != 0){
				pstm = con.prepareStatement(Queries.RELATORIO_FECHAMENTO_CAIXA_OPERADOR);
				pstm.setInt(4,operador.intValue());
			}else{
				pstm = con.prepareStatement(Queries.RELATORIO_FECHAMENTO_CAIXA_OPERADOR_GERAL);
			}			
			
			//data inicio
			Calendar c = new GregorianCalendar();
			c.setTime(data_inicio_movimento);
			int d1_dia = c.get(Calendar.DAY_OF_MONTH);
			int d1_mes = c.get(Calendar.MONTH);
			int d1_ano = c.get(Calendar.YEAR);
			Date dataInicio= new GregorianCalendar(d1_ano,d1_mes,d1_dia).getTime();
			
			//dataFim
			c = new GregorianCalendar();
			c.setTime(data_fim_movimento);
			int d2_dia = c.get(Calendar.DAY_OF_MONTH);
			int d2_mes = c.get(Calendar.MONTH);
			int d2_ano = c.get(Calendar.YEAR);
			Date dataFim= new GregorianCalendar(d2_ano,d2_mes,d2_dia,23,59,59).getTime();
			
			pstm.setDate(1,new java.sql.Date(dataInicio.getTime()));			
			pstm.setDate(2,new java.sql.Date(dataFim.getTime()));
			pstm.setInt(3,loja);
			
			rs = pstm.executeQuery();
			
			
			JRResultSetDataSource jrRS = new JRResultSetDataSource( rs );
				
	        JasperRunManager.runReportToPdfStream(input, out, parametros, jrRS);
	        
			}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}finally{
			try{
				if (rs != null){
					rs.close();
				}
				if (pstm != null){
					pstm.close();
				}
			}catch(Exception e){
				throw new RelatorioException(e);
			}
		}
			
		return out;
	}
	
	public OutputStream gerarRelatorioFechamentoCaixaGeral(int loja, Date data_inicio_movimento, Date data_fim_movimento) throws AppException{
		
		OutputStream out  = new ByteArrayOutputStream();
		ResultSet rs = null;
		PreparedStatement pstm = null;
		try{
			Map parametros = new HashMap();
	
			parametros.put("empresa", EMPRESA);				
						
			Connection con = getConnection();
			
			pstm = con.prepareStatement(Queries.RELATORIO_FECHAMENTO_CAIXA_GERAL);
			
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/RelatorioFechamentoCaixaGeral.jasper");
			
			//data inicio
			Calendar c = new GregorianCalendar();
			c.setTime(data_inicio_movimento);
			int d1_dia = c.get(Calendar.DAY_OF_MONTH);
			int d1_mes = c.get(Calendar.MONTH);
			int d1_ano = c.get(Calendar.YEAR);
			Date dataInicio= new GregorianCalendar(d1_ano,d1_mes,d1_dia).getTime();
			
			//dataFim
			c = new GregorianCalendar();
			c.setTime(data_fim_movimento);
			int d2_dia = c.get(Calendar.DAY_OF_MONTH);
			int d2_mes = c.get(Calendar.MONTH);
			int d2_ano = c.get(Calendar.YEAR);
			Date dataFim= new GregorianCalendar(d2_ano,d2_mes,d2_dia,23,59,59).getTime();
			
			pstm.setDate(1,new java.sql.Date(dataInicio.getTime()));			
			pstm.setDate(2,new java.sql.Date(dataFim.getTime()));
			pstm.setInt(3,loja);
			
			rs = pstm.executeQuery();
			
			
			JRResultSetDataSource jrRS = new JRResultSetDataSource( rs );
				
	        JasperRunManager.runReportToPdfStream(input, out, parametros, jrRS);
	        
			}catch(Exception e){
			e.printStackTrace();
			throw new RelatorioException(e);
		}finally{
			try{
				if (rs != null){
					rs.close();
				}
				if (pstm != null){
					pstm.close();
				}
			}catch(Exception e){
				throw new RelatorioException(e);
			}
		}
			
		return out;
	}

	
	
}
