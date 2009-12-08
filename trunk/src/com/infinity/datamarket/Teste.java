package com.infinity.datamarket;

import java.math.BigDecimal;
import java.util.Date;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.transacao.ClienteTransacao;
import com.infinity.datamarket.comum.transacao.ConstantesEventoTransacao;
import com.infinity.datamarket.comum.transacao.ConstantesTransacao;
import com.infinity.datamarket.comum.transacao.EventoItemPagamento;
import com.infinity.datamarket.comum.transacao.EventoItemRegistrado;
import com.infinity.datamarket.comum.transacao.EventoTransacaoPK;
import com.infinity.datamarket.comum.transacao.ProdutoItemRegistrado;
import com.infinity.datamarket.comum.transacao.Transacao;
import com.infinity.datamarket.comum.transacao.TransacaoPK;
import com.infinity.datamarket.comum.transacao.TransacaoVenda;
import com.infinity.datamarket.comum.util.ConjuntoEventoTransacao;
import com.infinity.datamarket.comum.util.Constantes;
import com.infinity.datamarket.pdv.maquinaestados.Evento;


public class Teste {

	public static void main(String[] args) {
//		Calendar c = new GregorianCalendar();
//		c.setTime(new Date(System.currentTimeMillis()));
//		int d1_dia = c.get(Calendar.DAY_OF_MONTH);
//		int d1_mes = c.get(Calendar.MONTH);
//		int d1_ano = c.get(Calendar.YEAR);
//		Date dataInicio = new GregorianCalendar(d1_ano,d1_mes,d1_dia).getTime();
//		DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
//		System.out.println(f.format(dataInicio));
		TransacaoVenda tv = new TransacaoVenda();
		TransacaoPK pk = new TransacaoPK();
		pk.setLoja(1);
		pk.setDataTransacao(new Date(System.currentTimeMillis()));
		pk.setComponente(1);
		pk.setNumeroTransacao(1);
		tv.setPk(pk);
		ClienteTransacao cli = new ClienteTransacao();
		cli.setCpfCnpj("02705132481");
		cli.setNomeCliente("CLIENTE TESTE");
		cli.setTipoPessoa(Cliente.PESSOA_FISICA);
		cli.setDataCadastro(new Date(System.currentTimeMillis()));
		tv.setCliente(cli);
		tv.setCodigoUsuarioOperador("1");
		tv.setCodigoUsuarioVendedor("1");
		tv.setComissaoUsuarioVendedor(new BigDecimal("10.00"));
		tv.setDataHoraInicio(new Date(System.currentTimeMillis()));
		tv.setDataHoraFim(new Date(System.currentTimeMillis()));
		tv.setDescontoCupom(new BigDecimal("10.00"));
		tv.setFormaTroco(null);
		tv.setValorCupom(new BigDecimal("100.00"));
		tv.setValorTroco(new BigDecimal("0.00"));
		tv.setNumeroCupom("123456");
		tv.setOperador("1");
		tv.setSituacao(Constantes.STATUS_ATIVO);
		tv.setStatus(Transacao.NAO_PROCESSADO);
		tv.setTipoTransacao(ConstantesTransacao.TRANSACAO_VENDA);
		ConjuntoEventoTransacao c = new ConjuntoEventoTransacao();
		EventoItemRegistrado eir = new EventoItemRegistrado();
		EventoTransacaoPK etPK = new EventoTransacaoPK();
		etPK.setLoja(1);
		etPK.setDataTransacao(new Date(System.currentTimeMillis()));
		etPK.setComponente(1);
		etPK.setNumeroTransacao(1);
//		etPK.setNumeroEvento(1);
		eir.setPk(etPK);
		eir.setProdutoItemRegistrado(new ProdutoItemRegistrado(etPK,2,"2","PRODUTO TESTE", new BigDecimal("0.00"), 
				new BigDecimal("20.00"), "T2", new BigDecimal("17.00"), new Long(1), "4"));
		eir.setQuantidade(new BigDecimal("1.00"));
		eir.setPreco(new BigDecimal("20.00"));
		eir.setDataHoraEvento(new Date(System.currentTimeMillis()));
		eir.setAcao("I");
		eir.setDesconto(new BigDecimal("0.00"));
		eir.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_REGISTRADO);
		eir.setLucro(new BigDecimal("20.00"));
		c.add(eir);
		
		EventoItemPagamento eip = new EventoItemPagamento();
		eip.setPk(etPK);
		eip.setCodigoForma(1);
		eip.setCodigoPlano(1);
		eip.setDescricaoForma("DINHEIRO");
		eip.setDescricaoPlano("A VISTA");
		eip.setDataHoraEvento(new Date(System.currentTimeMillis()));
		eip.setFormaImpressora("DINHEIRO");
		eip.setTipoEvento(ConstantesEventoTransacao.EVENTO_ITEM_PAGAMENTO);
		eip.setValorAcrescimo(new BigDecimal("0.00"));
		eip.setValorDesconto(new BigDecimal("0.00"));
		eip.setValorBruto(new BigDecimal("20.00"));
		c.add(eip);
		
		tv.setEventosTransacao(c);
		
		try {
			Fachada.getInstancia().
			inserirTransacao(tv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
