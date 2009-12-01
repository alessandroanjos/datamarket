package com.infinity.datamarket.enterprise.gui.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.transacao.Transacao;

public class ReceptorTransacaoServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			InputStream obj = request.getInputStream();
			ObjectInputStream input = new ObjectInputStream(obj);
			Object object = input.readObject();
			Transacao transacao = (Transacao) object;
			input.close();
			
			Fachada.getInstancia().inserirTransacaoES(transacao);

			ObjectOutputStream ouptu = new ObjectOutputStream(response.getOutputStream());
			ouptu.writeObject("OK");
			ouptu.close();

//			JBoletoBean jBoletoBean = new JBoletoBean();
//
//			jBoletoBean.setDataDocumento(Util.retornaDataFormatoDDMMYYYY(boleto.getDataProcessamento()));
//			jBoletoBean.setDataProcessamento(Util.retornaDataFormatoDDMMYYYY(boleto.getDataProcessamento()));
//
//			jBoletoBean.setCedente(boleto.getCedente());
//
//			jBoletoBean.setNomeSacado(boleto.getNomeCliente());
//			jBoletoBean.setEnderecoSacado(boleto.getEnderecoCliente());
//			jBoletoBean.setBairroSacado(boleto.getBairroCliente());
//			jBoletoBean.setCidadeSacado(boleto.getCidadeCliente());
//			jBoletoBean.setUfSacado(boleto.getUFCliente());
//			jBoletoBean.setCepSacado(boleto.getCepCliente());
//			jBoletoBean.setCpfSacado(boleto.getCpfCnpj());
//
//			jBoletoBean.setImagemMarketing("original_template_logo.gif");
//
//			jBoletoBean.setDataVencimento(Util.retornaDataFormatoDDMMYYYY(boleto.getDataVencimento()));
//			jBoletoBean.setInstrucao1(boleto.getInstrucao1());
//			jBoletoBean.setInstrucao2(boleto.getInstrucao2());
//			jBoletoBean.setInstrucao3(boleto.getInstrucao3());
//			jBoletoBean.setInstrucao4(boleto.getInstrucao4());
//
//			jBoletoBean.setCarteira(boleto.getCarteira());
//			jBoletoBean.setAgencia(boleto.getAgencia());
//			jBoletoBean.setContaCorrente(boleto.getNumeroContaCorrente());
//			jBoletoBean.setDvContaCorrente(boleto.getDigitoContaCorrente());
//
//			jBoletoBean.setNossoNumero(boleto.getNossoNumero(), 8);
//			jBoletoBean.setNoDocumento(boleto.getNossoNumero());
//			jBoletoBean.setValorBoleto(boleto.getValor().toString());
//
//			Generator generator = new PDFGenerator(jBoletoBean, boleto.getTipoBanco());
//			JBoleto jBoleto = new JBoleto(generator, jBoletoBean, boleto.getTipoBanco());
//
//			String arquivoBoleto = System.currentTimeMillis() + "boleto.pdf"; // o ideal eh colocar loja + pdv + codigoOperador + timestemp;
//			jBoleto.addBoleto();
//			jBoleto.closeBoleto("c:\\temp\\"+ arquivoBoleto);
//			
//			InputStream in = new FileInputStream(new File("c:\\temp\\"+ arquivoBoleto));
//			OutputStream out = arg1.getOutputStream();
//			byte[] buf = new byte[1024];
//			int len;
//			while ((len = in.read(buf)) > 0){
//			  out.write(buf, 0, len);
//			}
//			in.close();
//			out.close();
		      

		} catch (Exception e) {
			ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
			out.writeObject(e);// 
			out.close();

		}
	}
}