package API.ApiCall.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import API.ApiCall.configuracion.Configuracion;
import API.ApiCall.errors.LogError;

public class Server implements Runnable {
	
	public Server(){
		Thread ThreadServerSocketCallCenter = new Thread(this, "ThreadServerSocketCallCenter");
		ThreadServerSocketCallCenter.start();
	}
	
	@Override
	public void run() {
		while(true){
			startServer();
			LogError.logInfo("Se detuvo el servicio. Se iniciará automaticamente.");
		}
	}

	private void startServer(){
        System.out.println( "Iniciando servicio de call center..." );
        Dispatcher dispatch = Dispatcher.getInstance();
        ServerSocket serverSocket = null;
		try {			
			
			Configuracion config = Configuracion.getInstance();
			serverSocket = new ServerSocket(config.getPuerto());
			LogError.logInfo("Servidor iniciado en el puerto: " + String.valueOf(config.getPuerto()));
			while(true){
				LogError.logInfo("-----------------------------------------------------------------------");
				LogError.logInfo("Esperando conexiones!");
				Socket cliente = serverSocket.accept();
				LogError.logInfo("Conectando al cliente...");
				dispatch.dispatchCall(cliente);
			}
			
		} catch (Exception ex) {
			LogError.logError("ErrorAL INICIAR EL THREAD DE SERVER SOCKET.", ex);
		} finally{
			if (dispatch != null){
				dispatch.close();
			}
			if (serverSocket != null){
				try {
					serverSocket.close();
				} catch (IOException e) {
					LogError.logError(e);
				}
			}
		}
	}
}
