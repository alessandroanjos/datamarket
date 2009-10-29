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

public class Conexao {

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
			
			URL urlCon = new URL("http://localhost:8080/EnterpriseServer/GerarBoletoServlet.servlet");
			URLConnection huc1 = urlCon.openConnection();

			huc1.setAllowUserInteraction(true);
			huc1.setDoOutput(true);

			ObjectOutputStream output = new ObjectOutputStream(huc1.getOutputStream());
			output.writeObject(boleto);

//			ObjectInputStream input = new ObjectInputStream(huc1.getInputStream());
//			Object obj = input.readObject();
//			if (obj instanceof Exception) {
//				//throw (Exception) obj;
//				((Exception)obj).printStackTrace();
//			} else {
				InputStream in = huc1.getInputStream();
				FileOutputStream ouput = new FileOutputStream(new File("c:\\procenge\\temp.pdf"));
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0){
					ouput.write(buf, 0, len);
				}
//			}
//			input.close();
//			input = null;
			output.close();
			output = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
