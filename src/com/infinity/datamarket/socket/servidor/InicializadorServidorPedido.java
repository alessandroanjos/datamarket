package com.infinity.datamarket.socket.servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

import com.infinity.datamarket.comum.util.Util;


public class InicializadorServidorPedido {
	public static void main(String[] args) throws IOException {

		try {
			
			FileInputStream iput = new FileInputStream(new File(Util.getDirCorrente() +  "/ServidorPedido.properties"));
			Properties pr = new Properties();
			try {
				pr.load(iput);
			} finally {
				iput.close();
			}	
//
//			ResourceBundle rs = ResourceBundle.getBundle(
//					Util.getDirCorrente() +  "/ServidorPedido", Locale.getDefault());

			int porta = Integer.parseInt(pr.get("PORTA_SERVIDOR_PEDIDO") + "");
			
			ServerSocket serverSocket = serverSocket = new ServerSocket(porta);
			boolean listening = true;

			while (listening) {
				ControladorServidorPedido.getProximoNumero(serverSocket.accept());
			}

			serverSocket.close();

		} catch (IOException e) {
			System.err
					.println("Não foi possivel estabelecer conexão pela porta: 4444.");
			System.exit(-1);
		}
	}
}
