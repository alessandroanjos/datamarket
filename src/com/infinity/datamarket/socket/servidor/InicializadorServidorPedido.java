package com.infinity.datamarket.socket.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Locale;
import java.util.ResourceBundle;


public class InicializadorServidorPedido {
	public static void main(String[] args) throws IOException {

		try {

			ResourceBundle rs = ResourceBundle.getBundle(
					"\\resources\\constantesJNDI", Locale.getDefault());

			int porta = Integer.parseInt(rs.getString("PORTA_SERVIDOR_PEDIDO"));
			
			ServerSocket serverSocket = serverSocket = new ServerSocket(porta);
			boolean listening = true;

			while (listening) {
				ControladorNumeroPedido.getProximoNumero(serverSocket.accept());
			}

			serverSocket.close();

		} catch (IOException e) {
			System.err
					.println("Não foi possivel estabelecer conexão pela porta: 4444.");
			System.exit(-1);
		}
	}
}
