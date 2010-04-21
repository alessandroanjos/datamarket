package com.infinity.datamarket.socket.cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.infinity.datamarket.pdv.util.ServerConfig;

public class Cliente {

	public int getProximoNumero() {

		int retorno = 0;

		try {
			int porta = Integer.parseInt(ServerConfig.PORTA_SERVIDOR_PEDIDO);
			String ip = ServerConfig.IP_SERVIDOR_PEDIDO;
			
			Socket Socket = new Socket(ip, porta);

			ObjectOutputStream ouptu = new ObjectOutputStream(Socket.getOutputStream());
			ouptu.writeObject(new Integer("1"));
			

			ObjectInputStream input = new ObjectInputStream(Socket.getInputStream());
			Object object = input.readObject();
			input.close();
			ouptu.close();

			retorno = Integer.parseInt(object + "");
			
			System.out.println("" + object);

		} catch (UnknownHostException e) {
			System.err.println("Host não encontrado");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Host não encontrado");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retorno;
	}
}
