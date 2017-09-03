package API.ApiCall.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.Socket;

import API.ApiCall.configuracion.Configuracion;
import API.ApiCall.errors.LogError;
import API.ApiCall.model.operarios.Empleado;

public class InterlocutorHandler implements Runnable{

	private Empleado empl;
	private CallCenter call;
	private Writer out;
	private BufferedReader in;

	private Socket cliente;
	public InterlocutorHandler(CallCenter callCenter, Socket cliente) throws IOException{
		this.call = callCenter;
		this.cliente = cliente;
		openBuffers();
	}
	private void openBuffers() throws IOException {
		cliente.setSoTimeout(Configuracion.getInstance().getTimeOut());
		out = new PrintWriter(cliente.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

	}

	public void run() {
		try {
			
			empl = call.getOperador(this);
			LogError.logInfo("La llamada es atendia por: " + empl.getNombre());
			conversar();
		} catch (Exception e) {
			LogError.logError("Se cayó la conexión con el cliente", e);
		}finally {
			if (empl != null){
				call.liberarOperador(empl);
			}
			closeSocket();
		}
	}
	private void conversar() throws IOException, InterruptedException {
		escribirMensaje(empl.saludo());
		long espera = getEspera();
		escribirMensaje("Esta conversación va a durar " + String.valueOf(espera /1000)+" segundos...");
		Thread.sleep(espera);
		escribirMensaje ("Muchas gracias por comunicarse con nostros.");
		
	}
	
	private long getEspera() {
		return BigDecimal.valueOf((Math.random() * 5000 + 5000)).longValue(); //calculo un tiempo de duracion de conversación aleatorio según requerimiento del ejercicio
	}
	public void escribirMensaje(String mensaje) throws IOException{
		out.write(mensaje + "\n");
		out.flush();
	}
	
	private void closeSocket(){
		try {
			if(out != null)out.close();
			if(in != null )in.close();
			if(cliente != null)cliente.close();
		} catch (IOException e) {
			LogError.logError("Ocurrieron errores al cerrar el socket.", e);
		}
	}

}
