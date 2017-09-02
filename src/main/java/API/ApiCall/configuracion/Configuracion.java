package API.ApiCall.configuracion;

public class Configuracion {
	private int puerto;
	private int totalOperarios;
	private int totalSupervisores;
	private int totalDirectores;
	private int pool;
	private int timeOut;
	
	private static Configuracion instance;
	
	
	public int getPuerto() {
		return puerto;
	}
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	public int getTotalOperarios() {
		return totalOperarios;
	}
	public void setTotalOperarios(int totalOperarios) {
		this.totalOperarios = totalOperarios;
	}
	public int getTotalSupervisores() {
		return totalSupervisores;
	}
	public void setTotalSupervisores(int totalSupervisores) {
		this.totalSupervisores = totalSupervisores;
	}
	public int getTotalDirectores() {
		return totalDirectores;
	}
	public void setTotalDirectores(int totalDirectores) {
		this.totalDirectores = totalDirectores;
	}
	
	public static Configuracion getInstance(){
		if(instance == null){
			createInstance();
		}
		return instance;
	}

	private static void createInstance() {
		//Para segunda version estos datos de configuración se guardarían en algún archivo.
		instance = new Configuracion();
		instance.puerto = 1000;
		instance.totalDirectores = 1;
		instance.totalSupervisores = 2;
		instance.totalOperarios = 5;
		instance.timeOut = 10000;
		instance.pool = 10; //hasta 10 llamadas concurrentes, segun consigna
	}
	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	public int getPool() {
		return pool;
	}
	public void setPool(int pool) {
		this.pool = pool;
	}
	
}
