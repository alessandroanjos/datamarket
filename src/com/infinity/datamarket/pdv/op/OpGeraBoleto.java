package com.infinity.datamarket.pdv.op;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.infinity.datamarket.comum.boleto.Boleto;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Util;
import com.infinity.datamarket.pdv.gerenciadorperifericos.GerenciadorPerifericos;
import com.infinity.datamarket.pdv.gerenciadorperifericos.cmos.CMOS;
import com.infinity.datamarket.pdv.maquinaestados.Mic;
import com.infinity.datamarket.pdv.maquinaestados.ParametroMacroOperacao;

public class OpGeraBoleto extends Mic{
	
	public int exec(GerenciadorPerifericos gerenciadorPerifericos, ParametroMacroOperacao param){
		try {

			BigDecimal valorPagamento = (BigDecimal) gerenciadorPerifericos.getCmos().ler(CMOS.VALOR_PAGAMENTO_ATUAL);

			Date dataVencimento = Util.formatarStringParaData("10/08/2009");
			Date dataProcessamento = new Date();
			String cedente = "JB fraudas"; // empresa que vai receber do sacado
			String nossoNumero = "00000001";
			String instrucao1 = "APOS O VENCIMENTO COBRAR MULTA DE 2%";
			String instrucao2 = "APOS O VENCIMENTO COBRAR R$ 0,50 POR DIA";
			String instrucao3 = "";
			String instrucao4 = "";
			String agencia = "2971";
			String carteira = "175";
			String contaCorrente = "08690";
			String digitoContaCorrente = "1";

			String nomeCliente = "Fabio Souza";
			String enderecoCliente = "Rua Geek 010101";
			String bairroCliente = "Rua Geek 010101";
			String cidadeCliente = "Rio de Janeiro";
			String UFCliente = "RJ";
			String cpfCnpj = "00000000000";
			String cepCliente = "00000000000";

			Boleto boleto = new Boleto();
			boleto.setDataVencimento(dataVencimento);// = "10/08/2009";
			boleto.setDataProcessamento(dataProcessamento);// = "05/07/2009";
			boleto.setCedente(cedente);// = "JB fraudas"; // empresa que vai receber do sacado
			boleto.setNossoNumero(nossoNumero);// = "00000001";
			boleto.setInstrucao1(instrucao1);// = "APOS O VENCIMENTO COBRAR MULTA DE 2%";
			boleto.setInstrucao2(instrucao2);// = "APOS O VENCIMENTO COBRAR R$ 0,50 POR DIA";
			boleto.setInstrucao3(instrucao3);// = "";
			boleto.setInstrucao4(instrucao4);// = "";
			boleto.setAgencia(agencia);// = "2971";
			boleto.setCarteira(carteira);// = "175";
			boleto.setContaCorrente(contaCorrente);// = "08690";
			boleto.setDigitoContaCorrente(digitoContaCorrente);// = "1";
			boleto.setValor(valorPagamento);// = "0.01";

			boleto.setNomeCliente(nomeCliente);// = "Fabio Souza";
			boleto.setEnderecoCliente(enderecoCliente);// = "Rua Geek 010101";
			boleto.setBairroCliente(bairroCliente);// = "Rua Geek 010101";
			boleto.setCidadeCliente(cidadeCliente);// = "Rio de Janeiro";
			boleto.setUFCliente(UFCliente);// = "RJ";
			boleto.setCpfCnpj(cpfCnpj);// = "00000000000";
			boleto.setCepCliente(cepCliente);// = "00000000000";
			boleto.setTipoBanco(Boleto.TIPO_BANCO_ITAU);// = "00000000000";

			getFachadaPDV().inserirBoleto(boleto);
			gerenciadorPerifericos.getCmos().gravar(CMOS.BOLETO,boleto);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ALTERNATIVA_2;
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ALTERNATIVA_2;
		}
		return ALTERNATIVA_1;
	}
}