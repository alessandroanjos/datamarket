package com.infinity.datamarket.socket.servidor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ControladorNumeroPedido {

	public static void getProximoNumero(Socket socket) {
		try {

			InputStream obj = socket.getInputStream();
			ObjectInputStream input = new ObjectInputStream(obj);
			Object object = input.readObject();
						    
			int retorno = (((int)new java.util.Date().getTime())/2) + 1;
			if (retorno < 0) {
				retorno = retorno * -1;
			}
			
			
			ObjectOutputStream ouptu = new ObjectOutputStream(socket.getOutputStream());
			ouptu.writeObject(new Integer(retorno));
			ouptu.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
