/*
 * Created on 09/07/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.infinity.datamarket.report;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.wsdl.Output;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.clientepagamento.ClientePagamento;
import com.infinity.datamarket.comum.estoque.EntradaProduto;
import com.infinity.datamarket.comum.estoque.MovimentacaoEstoque;
import com.infinity.datamarket.comum.estoque.ProdutoEntradaProduto;
import com.infinity.datamarket.comum.estoque.ProdutoMovimentacaoEstoque;
import com.infinity.datamarket.comum.fornecedor.Fornecedor;
import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.TransacaoPagamentoCartaoProprio;
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

	public static final String EMPRESA;
	
	public static final String CAMINHO_RELATORIO;

	public static final String RECIBO_VENDA;
	
	public static final String RECIBO_PAGAMENTO_CLIENTE;
	
	public static final String RECIBO_MOVIMENTACAO_ESTOQUE;
	
	public static final String RECIBO_ENTRADA_PRODUTOS_ESTOQUE;
	
	private static ResourceBundle rs = ResourceBundle.getBundle("relatorio");

	static {		
	
		EMPRESA = rs.getString("EMPRESA");
		
		CAMINHO_RELATORIO = rs.getString("CAMINHO_RELATORIO");
	
		RECIBO_VENDA = rs.getString("RECIBO_VENDA");
		
		RECIBO_PAGAMENTO_CLIENTE = rs.getString("RECIBO_PAGAMENTO_CLIENTE");
		
		RECIBO_MOVIMENTACAO_ESTOQUE = rs.getString("RECIBO_MOVIMENTACAO_ESTOQUE");
		
		RECIBO_ENTRADA_PRODUTOS_ESTOQUE = rs.getString("RECIBO_ENTRADA_PRODUTOS_ESTOQUE");
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

	public void gerarReciboVendaES(TransacaoVenda transacao, OutputStream out) throws AppException{
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
			parametros.put("codigo", Util.completaString(movimentacaoEstoque.getId().toString(), "0", 4, true));
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
			Map<String, String> parametros = new HashMap<String, String>();
			
			DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

			parametros.put("empresa", EMPRESA);	
			parametros.put("numeroNota", Util.completaString(entradaProduto.getNumeroNota(), "0", 4, true));
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
					parametros.put("labelCpfCnpj", BackBean.formataCpfCnpj(fornecedor.getCpfCnpj()));
					parametros.put("pessoaContato", fornecedor.getPessoaContato());
					parametros.put("foneContato", fornecedor.getFoneContato());
				}else{
					parametros.put("fornecedor", fornecedor.getId().toString() + " - " + fornecedor.getRazaoSocial());
					parametros.put("labelCpfCnpj", "CNPJ:");
					parametros.put("labelCpfCnpj", BackBean.formataCpfCnpj(fornecedor.getCpfCnpj()));
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
			
			InputStream input = GerenciadorRelatorio.class.getResourceAsStream("/resources/ReciboEntradaProdutosEstoque.jasper");
            		
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
}
