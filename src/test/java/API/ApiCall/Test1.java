package API.ApiCall;

import java.util.Collection;
import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

import API.ApiCall.core.Server;
import static org.junit.Assert.*;

public class Test1 {

    @BeforeClass
    public static void runOnceBeforeClass() {
        System.out.println("Iniciando el servicio de Call Center para el inicio del test");
        new Server();
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Test
	public void test() {
		System.out.println("Iniciando el test");
		
		Collection<ClienteTest> clientes = new Vector<ClienteTest>();
		Collection<Thread> tClientes = new Vector<Thread>();
		/*
		 *El test se basa en tirar 10 threads concurrentes al callcenter.
		 *El servicio del callcenter está configurado para atender hasta 10 llamadas a la vez (configuración del temaño del pool). 
		 *En caso de entrar mas, se encolaran.
		 *Adicionalmente a esto, solo tiene en total 8 recursos para que atiendan el telefono (5 operarios, 2 supervisores, 1 director) Igualmente se pueden configurar los que se deseen.
		 *Con lo que al ejecutar el test, a 2 de los 10 que entren se los van a dejar en espera con la leyenda "Nuestros operarios están ocupados, espere por favor".
		 *
		 */
		for (int i =0; i<10 ; i++){
			ClienteTest cl = new ClienteTest(i);
			clientes.add(cl);
			Thread tCl = new Thread(cl);
			tClientes.add(tCl);
			tCl.start();
		}
		for (Thread t:tClientes){
			try {
				t.join();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
				
				fail("Falló la ejecición de los test");
			}
		}
		
		/*
		 * Chequeo las respuesta del servidor por cada cliente conectado. Deberian ser 3:
		 * -"Buen dia, soy el Operador x, en que podría ayudarlo?
		 * -"Esta conversación va a durar x segundos..."
		 * -"Muchas gracias por comunicarse con nostros."
		 * Si están esas 3 respuestas, significa que la comunicación se establecio correctamente.
		 * 
		 * Para el caso de que no hayan habido operadores disponibles, al cliente le debería haber llegado el siguiente mensaje:
		 * -"Todos nuestros operarios se encuentran ocupados, aguarde por favor..."
		 */
		
		for (ClienteTest cl:clientes){
			Vector<String> mensajes = cl.getMensajes();
			if (mensajes.size()>3){
				if (!mensajes.get(0).contains("Todos nuestros operarios se encuentran ocupados, aguarde por favor..."))
					fail("No llegó el mensaje: Todos nuestros operarios se encuentran ocupados, aguarde por favor...");
				mensajes.remove(0);
			}
			
			if (!mensajes.get(0).contains("Buen dia, soy el"))
				fail("No llegó el mensaje: Buen dia, soy el");
			if (!mensajes.get(1).contains("Esta conversación va a durar"))
				fail("No llegó el mensaje: Esta conversación va a durar");
			if (!mensajes.get(2).contains("Muchas gracias por comunicarse con nostros."))
				fail("No llegó el mensaje: Muchas gracias por comunicarse con nostros.");
		}
		
		assertTrue("El test pasó exitosamente", true);

	}

}
