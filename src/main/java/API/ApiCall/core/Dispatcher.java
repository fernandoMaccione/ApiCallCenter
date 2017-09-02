package API.ApiCall.core;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import API.ApiCall.configuracion.Configuracion;
import API.ApiCall.errors.LogError;

public class Dispatcher {
	private static ExecutorService threadPool;
	private static CallCenter callCenter;
	private static Dispatcher instance;
	
	public static synchronized Dispatcher getInstance(){
		if(instance == null){
			createInstance();
		}
		return instance;
	}
	
	private static void createInstance() {
		instance = new Dispatcher();
	}

	private Dispatcher() {
		Configuracion config = Configuracion.getInstance();
		threadPool = Executors.newFixedThreadPool(config.getPool());
		LogError.logInfo("Se inicio el pool con " + String.valueOf(config.getPool()));
		callCenter = CallCenter.getInstance();
	}
	
	public void dispatchCall(Socket cliente){
		try {
			InterlocutorHandler inter = new InterlocutorHandler(callCenter, cliente);
			threadPool.submit(inter);
		} catch (Exception ex) {
			LogError.logError("Ocurrieron problemas al tratar de establecer la comunicación con la llamada entrante", ex);
		}

	}

	public void close() {
		if(threadPool != null){
			threadPool.shutdown();
		}
		if(callCenter != null){
			callCenter.close();
		}
	}

	
}
