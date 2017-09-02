package API.ApiCall.model.operarios;

import API.ApiCall.model.Cliente;

public abstract class Empleado {
	protected String nombre;
	private int id;
	public abstract String tipo ();
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void atenderLlamada (Cliente cliente){
		
	}
	
	public String saludo (){
		return "Buen dia, soy el " + tipo () + " " + nombre + ", en que podría ayudarlo?";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
