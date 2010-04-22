package com.infinity.datamarket.socket.servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import com.infinity.datamarket.comum.util.Util;

public class ControladorServidorPedido {

	public synchronized static void getProximoNumero(Socket socket) {
		try {

			InputStream obj = socket.getInputStream();
			ObjectInputStream input = new ObjectInputStream(obj);
			Long[] codigos = (Long[])input.readObject();

			FileInputStream iput = new FileInputStream(new File(Util.getDirCorrente() +  "/ServidorPedido.properties"));
			Properties pr = new Properties();
			try {
				pr.load(iput);
			} finally {
				iput.close();
			}	
			
			long nsu = 0l;
			if (pr.get("NSU_PEDIDO_ATUAL") != null) {
				nsu = Long.parseLong(pr.get("NSU_PEDIDO_ATUAL").toString());
			}

			nsu++;

			pr.put("NSU_PEDIDO_ATUAL", "" + nsu);

			FileOutputStream oput = new FileOutputStream(new File(Util.getDirCorrente() + "/ServidorPedido.properties"));
			try {
				pr.store(oput, "");
			} finally {
				oput.close();
			}
			
			String codigoComponente = codigos[1] + "";
			if (codigoComponente.length() == 1) {
				codigoComponente = "0" + codigoComponente;
			}
			
			String codigoNsu = nsu + "";
			// verifica se o codigo eh menor que 6 
			if (codigoNsu.length() < 6 ) {
				//se o codigo for menor que 6 entao adiciona zero a esquerda em 6 
				codigoNsu = "00000" + codigoNsu;
				if (codigoNsu.length() > 6 ) {
					codigoNsu = codigoNsu.substring(codigoNsu.length() - 6,codigoNsu.length()); 
				}
			}
			
			
			ObjectOutputStream ouptu = new ObjectOutputStream(socket.getOutputStream());
			ouptu.writeObject(new Long(codigos[0] + "" + codigoComponente + "" + codigoNsu));
			ouptu.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
