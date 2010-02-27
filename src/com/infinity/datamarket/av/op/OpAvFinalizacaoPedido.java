package com.infinity.datamarket.av.op;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.Operacao;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.operacao.ProdutoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.usuario.Usuario;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.ConcentradorParametro;
import com.infinity.datamarket.comum.util.ConjuntoEventoOperacao;
import com.infinity.datamarket.enterprise.gui.login.LoginBackBean;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpAvFinalizacaoPedido extends Mic{
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){

		try {

			OperacaoPedido op = (OperacaoPedido)gerenciadorPerifericos.getCmos().ler(CMOS.OPERACAO_PEDIDO);

			
			ClienteTransacao cli = (ClienteTransacao) gerenciadorPerifericos.getCmos().ler(CMOS.CLIENTE_AV);

			Collection collItensPedido = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);
	        int idLoja = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOJA).getValorInteiro();
	        long idPedido = 0;
			if (op != null) {
				idPedido = op.getPk().getId();
			} else {
				idPedido = carregarProximoIdPedido(idLoja);
			}
			Usuario usu = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.USUARIO_ATUAL);
			String codigoVendedor = usu.getId() + "";
			BigDecimal valorTotal = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_TOTAL_PEDIDO);
			BigDecimal valorTotalDesconto = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_TOTAL_DESCONTO_PEDIDO);

			Iterator<EventoOperacaoItemRegistrado> it = collItensPedido.iterator();
			while(it.hasNext()) {
				EventoOperacaoItemRegistrado evento = it.next();
				evento.getPk().setId((int)idPedido);
				ProdutoOperacaoItemRegistrado prodOpItemReg = evento.getProdutoOperacaoItemRegistrado();
				prodOpItemReg.getPk().setId((int)idPedido);
			}
				
			getLogger(this.getClass()).info("inserir - INICIO");
			getLogger(this.getClass()).info("inserir:: vou preencher o objeto pedido");
			op = preencheOperacaoPedido(collItensPedido,cli,idLoja,idPedido,codigoVendedor,valorTotal,valorTotalDesconto);
			getLogger(this.getClass()).info("inserir:: preenchi o objeto pedido");
			getLogger(this.getClass()).info("inserir:: vou inserir o pedido");
			
			
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Operacao.class);
			filter.addProperty("pk.id", op.getPk().getId());
			filter.addProperty("pk.loja", op.getPk().getLoja());
			
			Collection coll = Fachada.getInstancia().consultarOperacao(filter);
			
			if (coll != null && coll.size() != 0) {
				OperacaoPedido pedidoJaCadastrado = (OperacaoPedido) coll.iterator().next(); 
				getFachadaPDV().excluirOperacao(pedidoJaCadastrado);	
			}

			getFachadaPDV().inserirOperacaoES(op);

			getLogger(this.getClass()).info("inserir:: inseri o pedido");

			gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_PEDIDO, op);

			
			return ALTERNATIVA_1;

		} catch (AppException e) {
			return ALTERNATIVA_2;
		}
	}

	public OperacaoPedido preencheOperacaoPedido(Collection collItensPedido, ClienteTransacao cli , long idLoja, long idPedido, String codigoVendedor, BigDecimal valorTotal, BigDecimal valorTotalDesconto) throws AppException{
		OperacaoPedido pedido = new OperacaoPedido();
		OperacaoPK pk = new OperacaoPK();
		pk.setLoja((int)idLoja);
		pk.setId((int)idPedido);
		pedido.setPk(pk);
	
		pedido.setData(new Date());

		pedido.setStatus(new Integer(ConstantesOperacao.PARA_ENVIAR));

		pedido.setCliente(cli);

		pedido.setTipo(ConstantesOperacao.OPERACAO_PEDIDO);
		
		pedido.setCodigoUsuarioOperador(LoginBackBean.getCodigoUsuarioLogado());
		
		pedido.setCodigoUsuarioVendedor(codigoVendedor);
		
		pedido.setValor(valorTotal);
		
		pedido.setDesconto(valorTotalDesconto);
		
		ConjuntoEventoOperacao ceo = new ConjuntoEventoOperacao();
		
		Iterator it = collItensPedido.iterator();
		while(it.hasNext()){
			EventoOperacaoItemRegistrado evOpItReg = (EventoOperacaoItemRegistrado)it.next();
			evOpItReg.getPk().setId(pedido.getPk().getId());
			ceo.add(evOpItReg);
		}
		
		pedido.setEventosOperacao(ceo);

		pedido.setUsaEnderecoEntrega("");
		pedido.setLogradouro("");
		pedido.setNumero("");
		pedido.setComplemento("");
		pedido.setBairro("");
		pedido.setCidade("");
		pedido.setEstado("");
		pedido.setCep("");
		pedido.setPontoReferencia("");

		if (cli != null) {
			
			pedido.setUsaEnderecoEntrega("S");
			if (cli.getLogradouro() != null) pedido.setLogradouro(cli.getLogradouro());
			if (cli.getNumero() != null) pedido.setNumero(cli.getNumero());
			if (cli.getComplemento() != null) pedido.setComplemento(cli.getComplemento());
			if (cli.getBairro() != null) pedido.setBairro(cli.getBairro());
			if (cli.getCidade() != null) pedido.setCidade(cli.getCidade());
			if (cli.getEstado() != null) pedido.setEstado(cli.getEstado());
			if (cli.getCep() != null) pedido.setCep(cli.getCep());
		}
	
		return pedido;
	}
	
	public long carregarProximoIdPedido(long idLoja){
//		if(idLoja != 0){
//			OperacaoPK pk = new OperacaoPK();
//			pk.setLoja((int) idLoja);
//			try {
//				return (long)(getFachadaPDV().retornaMaxIdOperacaoPorLoja(pk));
//			} catch (AppException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return 1;
//			}
//		}
//		return 1;
		int retorno = (((int)new java.util.Date().getTime())/2) + 1;
		if (retorno < 0) {
			retorno = retorno * -1;
		}
		return retorno;
	}
}