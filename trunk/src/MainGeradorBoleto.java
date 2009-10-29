import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.jboleto.JBoleto;
import org.jboleto.JBoletoBean;
import org.jboleto.control.Generator;
import org.jboleto.control.PDFGenerator;

import com.infinity.datamarket.comum.boleto.Boleto;
import com.infinity.datamarket.comum.util.Util;

public class MainGeradorBoleto {

	public static void main(String[] a) {

		try {
			//			
			// Class.forName("net.sourceforge.jtds.jdbc.Driver");
			// Connection con =
			// DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/ENTERPRISE",
			// "sa","1234");
			// System.out.println("foi");


			Date dataVencimento = Util.formatarStringParaData("10/08/2009");
			Date dataProcessamento = Util.formatarStringParaData("05/07/2009");
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
			BigDecimal valor = new BigDecimal("0.01");

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
			boleto.setValor(valor);// = "0.01";

			boleto.setNomeCliente(nomeCliente);// = "Fabio Souza";
			boleto.setEnderecoCliente(enderecoCliente);// = "Rua Geek 010101";
			boleto.setBairroCliente(bairroCliente);// = "Rua Geek 010101";
			boleto.setCidadeCliente(cidadeCliente);// = "Rio de Janeiro";
			boleto.setUFCliente(UFCliente);// = "RJ";
			boleto.setCpfCnpj(cpfCnpj);// = "00000000000";
			boleto.setCepCliente(cepCliente);// = "00000000000";
			boleto.setTipoBanco(Boleto.TIPO_BANCO_ITAU);// = "00000000000";
			
			JBoletoBean jBoletoBean = new JBoletoBean();

			jBoletoBean.setDataDocumento(Util.retornaDataFormatoDDMMYYYY(boleto.getDataProcessamento()));
			jBoletoBean.setDataProcessamento(Util.retornaDataFormatoDDMMYYYY(boleto.getDataProcessamento()));

			jBoletoBean.setCedente(boleto.getCedente());

			jBoletoBean.setNomeSacado(boleto.getNomeCliente());
			jBoletoBean.setEnderecoSacado(boleto.getEnderecoCliente());
			jBoletoBean.setBairroSacado(boleto.getBairroCliente());
			jBoletoBean.setCidadeSacado(boleto.getCidadeCliente());
			jBoletoBean.setUfSacado(boleto.getUFCliente());
			jBoletoBean.setCepSacado(boleto.getCepCliente());
			jBoletoBean.setCpfSacado(boleto.getCpfCnpj());

			jBoletoBean.setImagemMarketing("original_template_logo.gif");

			jBoletoBean.setDataVencimento(Util.retornaDataFormatoDDMMYYYY(boleto.getDataVencimento()));
			jBoletoBean.setInstrucao1(boleto.getInstrucao1());
			jBoletoBean.setInstrucao2(boleto.getInstrucao2());
			jBoletoBean.setInstrucao3(boleto.getInstrucao3());
			jBoletoBean.setInstrucao4(boleto.getInstrucao4());

			jBoletoBean.setCarteira(boleto.getCarteira());
			jBoletoBean.setAgencia(boleto.getAgencia());
			jBoletoBean.setContaCorrente(boleto.getContaCorrente());
			jBoletoBean.setDvContaCorrente(boleto.getDigitoContaCorrente());

			jBoletoBean.setNossoNumero(boleto.getNossoNumero(), 8);
			jBoletoBean.setNoDocumento(boleto.getNossoNumero());
			jBoletoBean.setValorBoleto(boleto.getValor().toString());

			Generator generator = new PDFGenerator(jBoletoBean, boleto.getTipoBanco());
			JBoleto jBoleto = new JBoleto(generator, jBoletoBean, boleto.getTipoBanco());

			String arquivoBoleto = System.currentTimeMillis() + "boleto.pdf"; // o ideal eh colocar loja + pdv + codigoOperador + timestemp;
			jBoleto.addBoleto();
			jBoleto.closeBoleto("c:\\temp\\"+ arquivoBoleto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
