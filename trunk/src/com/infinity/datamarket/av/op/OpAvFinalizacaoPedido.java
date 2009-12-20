package com.infinity.datamarket.av.op;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.operacao.ProdutoOperacaoItemRegistrado;
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
			
			Cliente cliente = (Cliente) gerenciadorPerifericos.getCmos().ler(CMOS.CLIENTE_AV);

			Collection collItensPedido = (List<EventoOperacaoItemRegistrado>)gerenciadorPerifericos.getCmos().ler(CMOS.COLL_EVENTO_OPERACAO_ITEM_REGISTRADO_PEDIDO);
	        int idLoja = ConcentradorParametro.getInstancia().getParametro(ConcentradorParametro.LOJA).getValorInteiro();
			long idPedido = carregarProximoIdPedido(idLoja);
			Usuario usu = (Usuario) gerenciadorPerifericos.getCmos().ler(CMOS.USUARIO_ATUAL);
			String codigoVendedor = usu.getId() + "";
			BigDecimal valorTotal = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_TOTAL_PEDIDO);
			BigDecimal balorTotalDesconto = (BigDecimal)gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_TOTAL_DESCONTO_PEDIDO);

			ClienteTransacao cli = null;
			if (cliente != null && cliente.getCpfCnpj() != null ) {
				cli = getFachadaPDV().consultarClienteTransacaoPorID(cliente.getCpfCnpj().replace(".", "").replace("/", "").replace("-", ""));
			}
			Iterator<EventoOperacaoItemRegistrado> it = collItensPedido.iterator();
			while(it.hasNext()) {
				EventoOperacaoItemRegistrado evento = it.next();
				evento.getPk().setId((int)idPedido);
				ProdutoOperacaoItemRegistrado prodOpItemReg = evento.getProdutoOperacaoItemRegistrado();
				prodOpItemReg.getPk().setId((int)idPedido);
			}
				
			getLogger(this.getClass()).info("inserir - INICIO");
			getLogger(this.getClass()).info("inserir:: vou preencher o objeto pedido");
			OperacaoPedido op = preencheOperacaoPedido(collItensPedido,cli,idLoja,idPedido,codigoVendedor,valorTotal,balorTotalDesconto);
			getLogger(this.getClass()).info("inserir:: preenchi o objeto pedido");
			getLogger(this.getClass()).info("inserir:: vou inserir o pedido");
			getFachadaPDV().inserirOperacaoES(op);
			getLogger(this.getClass()).info("inserir:: inseri o pedido");

			gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_PEDIDO, op);

			
			return ALTERNATIVA_1;

		} catch (AppException e) {
			return ALTERNATIVA_2;
		}
	}

	public OperacaoPedido preencheOperacaoPedido(Collection collItensPedido, ClienteTransacao cli , long idLoja, long idPedido, String codigoVendedor, BigDecimal valorTotal, BigDecimal balorTotalDesconto) throws AppException{
		OperacaoPedido pedido = new OperacaoPedido();
		OperacaoPK pk = new OperacaoPK();
		pk.setLoja((int)idLoja);
		pk.setId((int)idPedido);
		pedido.setPk(pk);
	
		pedido.setData(new Date());

		pedido.setStatus(new Integer(ConstantesOperacao.ABERTO));	

		pedido.setCliente(cli);

		pedido.setTipo(ConstantesOperacao.OPERACAO_PEDIDO);
		
		pedido.setCodigoUsuarioOperador(LoginBackBean.getCodigoUsuarioLogado());
		
		pedido.setCodigoUsuarioVendedor(codigoVendedor);
		
		pedido.setValor(valorTotal);
		
		pedido.setDesconto(balorTotalDesconto);
		
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