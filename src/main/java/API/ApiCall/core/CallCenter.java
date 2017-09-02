package API.ApiCall.core;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import API.ApiCall.configuracion.Configuracion;
import API.ApiCall.model.operarios.Director;
import API.ApiCall.model.operarios.Empleado;
import API.ApiCall.model.operarios.Operador;
import API.ApiCall.model.operarios.Supervisor;

public class CallCenter {
	private Queue<Empleado> operariosLibres;
	private Queue<Empleado> supervisoresLibres;
	private Queue<Empleado> directoresLibres;
	
	private static CallCenter instance;
	
	public void close(){
		instance = null;
	}
	public static CallCenter getInstance(){
		if(instance == null){
			createInstance();
		}
		return instance;
	}
	
	private CallCenter(Configuracion conf){
		operariosLibres = new LinkedList<Empleado>();
		for (int i=0; i < conf.getTotalOperarios(); i++){
			Operador oper = new Operador();
			oper.setId(i);
			oper.setNombre("OperNumero " + String.valueOf(i));
			operariosLibres.add(oper);
		}
		
		supervisoresLibres = new LinkedList<Empleado>();
		for (int i=0; i < conf.getTotalSupervisores(); i++){
			Supervisor oper = new Supervisor();
			oper.setId(i);
			oper.setNombre("SuperNumero " + String.valueOf(i));
			supervisoresLibres.add(oper);
		}
		
		directoresLibres = new LinkedList<Empleado>();
		for (int i=0; i < conf.getTotalDirectores(); i++){
			Director oper = new Director();
			oper.setId(i);
			oper.setNombre("DirectorNumero " + String.valueOf(i));
			directoresLibres.add(oper);
		}
	}

	private static void createInstance() {
		Configuracion conf = Configuracion.getInstance();
		
		instance = new CallCenter(conf);
	}

	public synchronized Empleado getOperador(InterlocutorHandler inter) throws InterruptedException, IOException{
		while (true){
			if (!operariosLibres.isEmpty()){
				return operariosLibres.poll();
			}else if (!supervisoresLibres.isEmpty()){
				return supervisoresLibres.poll();
			}else if (!directoresLibres.isEmpty()){
				return directoresLibres.poll();
			}else{
				inter.escribirMensaje("Todos nuestros operarios se encuentran ocupados, aguarde por favor...");
				this.wait(); 				
			}
		}
	}

	public synchronized void liberarOperador(Empleado empleado){

		if (empleado instanceof Operador){
			operariosLibres.add(empleado);
		}else if (empleado instanceof Supervisor){
			supervisoresLibres.add(empleado);
		}else {
			directoresLibres.add(empleado);
		}
		this.notify();
	}
}
