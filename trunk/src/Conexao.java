import java.sql.Connection;
import java.sql.DriverManager;

import org.jboleto.JBoleto;
import org.jboleto.JBoletoBean;
import org.jboleto.control.Generator;
import org.jboleto.control.PDFGenerator;


public class Conexao {

	public static void main(String [] a) {

		try {
//			
//		   	Class.forName("net.sourceforge.jtds.jdbc.Driver");
//		   	Connection con = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1434/enterprise",
//		   				"sa","1234");
//		   	System.out.println("foi");
//		   	

            JBoletoBean jBoletoBean = new JBoletoBean();

            jBoletoBean.setDataDocumento("05/07/2009");
            jBoletoBean.setDataProcessamento("05/07/2009");

            jBoletoBean.setCedente("Notícias Geek");

            jBoletoBean.setNomeSacado("Fabio Souza");
            jBoletoBean.setEnderecoSacado("Rua Geek 010101");
            jBoletoBean.setBairroSacado("Freguesia");
            jBoletoBean.setCidadeSacado("Rio de Janeiro");
            jBoletoBean.setUfSacado("RJ");
            jBoletoBean.setCepSacado("22750-000");
            jBoletoBean.setCpfSacado("00000000000");

            jBoletoBean.setImagemMarketing("original_template_logo.gif");

            jBoletoBean.setDataVencimento("10/08/2009");
            jBoletoBean.setInstrucao1("APOS O VENCIMENTO COBRAR MULTA DE 2%");
            jBoletoBean.setInstrucao2("APOS O VENCIMENTO COBRAR R$ 0,50 POR DIA");
            jBoletoBean.setInstrucao3("");
            jBoletoBean.setInstrucao4("");

            jBoletoBean.setCarteira("175");
            jBoletoBean.setAgencia("2971");
            jBoletoBean.setContaCorrente("08690");
            jBoletoBean.setDvContaCorrente("1");

            jBoletoBean.setNossoNumero("34556",8);
            jBoletoBean.setNoDocumento("34556");
            jBoletoBean.setValorBoleto("300.00");

            Generator  generator = new PDFGenerator(jBoletoBean, JBoleto.ITAU);
            JBoleto jBoleto = new JBoleto(generator, jBoletoBean, JBoleto.ITAU);

            jBoleto.addBoleto();
            jBoleto.closeBoleto("itau.pdf");
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	   	

	
	}
	
}
