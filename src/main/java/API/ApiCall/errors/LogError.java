package API.ApiCall.errors;

public class LogError {
	public static void logError (String mensaje, Throwable ex){
		System.out.println(mensaje);
		ex.printStackTrace();
	}
	
	public static void logError (Throwable ex){
		ex.printStackTrace();
	}
	
	public static void logInfo (String mensaje){
		System.out.println(mensaje);
	}
}
