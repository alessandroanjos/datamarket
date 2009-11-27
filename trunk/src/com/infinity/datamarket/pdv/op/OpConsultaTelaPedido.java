package com.infinity.datamarket.pdv.op;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.infinity.datamarket.comum.operacao.ConstantesEventoOperacao;
import com.infinity.datamarket.comum.operacao.ConstantesOperacao;
import com.infinity.datamarket.comum.operacao.EventoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.operacao.EventoOperacaoPK;
import com.infinity.datamarket.comum.operacao.OperacaoPK;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.operacao.ProdutoOperacaoItemRegistrado;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.EventoTransacao;
import com.infinity.datamarket.comum.transacao.EventoTransacaoPK;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.Display;
import com.infinity.datamarket.pdv.gerenciadorperifericos.display.EntradaDisplay;
import com.infinity.datamarket.pdv.gui.telas.swing.ConsultaItemFrame;
import com.infinity.datamarket.pdv.gui.telas.swing.ConsultaPedidoFrame;
import com.infinity.datamarket.pdv.maquinaestados.Evento;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;
import com.infinity.datamarket.pdv.maquinaestados.Tecla;
import com.infinity.datamarket.pdv.util.ServerConfig;

public class OpConsultaTelaPedido extends Mic{

	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){		
//		Collection pedidos = new ArrayList();
//		OperacaoPK pk = new OperacaoPK();
//		OperacaoPedido p = new OperacaoPedido();
//		ClienteTransacao cli = new ClienteTransacao();
//		pk.setId(1);
//		pk.setLoja(1);
//		cli.setCpfCnpj("00919923463");
//		cli.setDataCadastro(new Date());
//		cli.setTipoPessoa(ClienteTransacao.PESSOA_FISICA);
//		cli.setNomeCliente("Wagner Medeiros");
//		p.setPk(pk);
//		p.setCliente(cli);
//		p.setData(new Date());
//		p.setValor(new BigDecimal(200));
//		pedidos.add(p);
//		p.setCodigoUsuarioOperador("1");
//		p.setCodigoUsuarioVendedor("1");
//		p.setStatus(ConstantesOperacao.ABERTO);
//		p.setTipo(ConstantesOperacao.OPERACAO_PEDIDO);
//		p.setValor(new BigDecimal(200));
//		p.setDesconto(BigDecimal.ZERO);
//		
//		EventoOperacaoPK eop = new EventoOperacaoPK();
//		eop.setId(1);
//		eop.setLoja(1);
//		eop.setNumeroEvento(1);
//		EventoOperacaoItemRegistrado eo = new EventoOperacaoItemRegistrado();
//		eo.setPk(eop);
//		eo.setDesconto(BigDecimal.ZERO);
//		eo.setDataHoraEvento(new Date());
//		eo.setPreco(new BigDecimal(200));
//		eo.setQuantidade(new BigDecimal(1));
//		eo.setTipoEvento(ConstantesEventoOperacao.EVENTO_OPERACAO_ITEM_REGISTRADO);		
//		ProdutoOperacaoItemRegistrado peo = new ProdutoOperacaoItemRegistrado();
//		peo.setCodigoExterno("100");
//		peo.setDescricaoCompleta("Bolo de Milho");
//		peo.setIdProduto(100);
//		peo.setImpostoImpressora("T1");
//		peo.setPercentual(new BigDecimal(17));
//		peo.setPk(eop);
//		peo.setPrecoPadrao(new BigDecimal(200));
//		peo.setPrecoPraticado(new BigDecimal(200));
//		peo.setTipoProduto(new Long(1));
//		peo.setUnidade("UN");
//		eo.setProdutoOperacaoItemRegistrado(peo);
//		HashSet eventos = new HashSet();
//		eventos.add(eo);
//		p.setEventosOperacao(eventos);
		
		Collection pedidos = null;
		
		try{
			URL urlCon = new URL("http://" +
					ServerConfig.HOST_SERVIDOR_ES +
					":" +
					ServerConfig.PORTA_SERVIDOR_ES +
					"/" +
					ServerConfig.CONTEXTO_SERVIDOR_ES +
					"/" +
					ServerConfig.ALTERAR_OPERACA_SERVLET +"status=" + ConstantesOperacao.ABERTO);
			URLConnection huc1 = urlCon.openConnection();
	
			huc1.setAllowUserInteraction(true);						
			
			ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
			Object obj = input.readObject();
			if (obj instanceof Collection) {
				pedidos = (Collection) obj;
			} else  if (obj instanceof Exception){
				gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
				gerenciadorPerifericos.esperaVolta();
				return ALTERNATIVA_2;		
			}
		}catch(Exception e){
			e.printStackTrace();
			gerenciadorPerifericos.getDisplay().setMensagem("Erro de Comunicação");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}
		
		if (pedidos != null && pedidos.size() > 0){
			ConsultaPedidoFrame c = new ConsultaPedidoFrame(gerenciadorPerifericos.getWindow().getFrame(),pedidos);
		
	    	c.setSize(800, 530);
	    	c.play();
	    	if (c.getRetornoTela() == c.BUTTON_OK){
	    		OperacaoPedido pedido = c.getValor();
	    		gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_PEDIDO, pedido);
	    		return ALTERNATIVA_1;
	    	}else{
	    		gerenciadorPerifericos.getCmos().gravar(CMOS.OPERACAO_PEDIDO, null);
	    		return ALTERNATIVA_2;
	    	}
		}else{
			gerenciadorPerifericos.getDisplay().setMensagem("Nenhum Pedido Encontrado");
			try {
				gerenciadorPerifericos.esperaVolta();
			} catch (AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return ALTERNATIVA_2;
		}
		
	}
}