package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboleto.JBoleto;
import org.jboleto.JBoletoBean;
import org.jboleto.control.Generator;
import org.jboleto.control.PDFGenerator;

import com.infinity.datamarket.comum.boleto.Boleto;
import com.infinity.datamarket.comum.util.Util;

public class GerarBoletoServlet extends HttpServlet {

	public void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		
		try {
			
			InputStream obj = arg0.getInputStream();
			ObjectInputStream input = new ObjectInputStream(obj);
			Object object = input.readObject();
			Boleto boleto = (Boleto) object;
			input.close();
			
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
			
			InputStream in = new FileInputStream(new File("c:\\temp\\"+ arquivoBoleto));
			OutputStream out = arg1.getOutputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0){
			  out.write(buf, 0, len);
			}
			in.close();
			out.close();
		      

		} catch (Exception e) {
			ObjectOutputStream out = new ObjectOutputStream(arg1.getOutputStream());
			out.writeObject(e);// 
			out.close();

		}
	}
}