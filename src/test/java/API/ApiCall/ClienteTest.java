package API.ApiCall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

public class ClienteTest implements Runnable{
	
	private int cliente;
	private Vector<String> mensajes;
	public ClienteTest (int numeroCliente){
		cliente = numeroCliente;
	}

	@Override
	public void run() {
		Socket socketAlServer = null;
		
		/* Se crea el socket cliente */
        try {
			socketAlServer = new Socket("localhost", 1000);
	        InputStream entrada = socketAlServer.getInputStream();
	    //    OutputStream salida = socketAlServer.getOutputStream();

	        BufferedReader d = new BufferedReader(new InputStreamReader(entrada));
	        String mensajeRecibido = d.readLine();
	        mensajes = new Vector<String>();
	        while (mensajeRecibido != null) {
	        	mensajes.add(mensajeRecibido);
		        System.out.println ("CLIENTE " + String.valueOf(cliente) +" - El server respondió:  " + mensajeRecibido);
		        mensajeRecibido = d.readLine();
	        }
        } catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Vector<String> getMensajes(){
		return mensajes;
	}

}
