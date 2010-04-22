package com.infinity.datamarket.socket.cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.infinity.datamarket.pdv.util.ServerConfig;

public class ClienteServidorPedido {

	public static void main(String[] a) {
		System.out.println(getProximoNumero(1l, 1l));
	}

	public static Long getProximoNumero(long loja, long componente) {

		Long retorno = 0l;

		try {
			int porta = Integer.parseInt(ServerConfig.PORTA_SERVIDOR_PEDIDO);
			String ip = ServerConfig.IP_SERVIDOR_PEDIDO;
			
			Socket Socket = new Socket(ip, porta);

			Long[] codigos = {loja, componente};
			
			ObjectOutputStream ouptu = new ObjectOutputStream(Socket.getOutputStream());
			ouptu.writeObject(codigos);
			
			ObjectInputStream input = new ObjectInputStream(Socket.getInputStream());
			Object object = input.readObject();
			input.close();
			ouptu.close();

			retorno = Long.parseLong(object + "");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println("Host não encontrado");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Host não encontrado");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retorno;
	}
}
