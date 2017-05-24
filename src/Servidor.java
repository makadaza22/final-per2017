import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Interfaz.NotifyClients;
import common.Camiseta;
import common.User;
import common.Equipo;
import processing.core.PApplet;


//Esta clase se encarga de la conexión por medio de TCP, extiende de hilo e implementa observer
public class Servidor extends Thread implements Observer {
	
	
	private ServerSocket socket;
	private ArrayList<Client> clients;

	private XMLusers xUsers;
	private XMLcamiseta xCamisetas;
	private XMLequipo xEquipos;
	
	private NotifyClients notifyclientes; //Esto es lo que nos va a comunicar cuantos clientes hay conectados...
	
	
	
	//ArrayList de los equipos, para tener guardada una copia local de ellos en el XML y no tener que estar leyendolo constantemente
	//private ArrayList<Equipo> equipos = new ArrayList<Equipo>();
	
	//Constructor del servidor
	public Servidor(PApplet app, NotifyClients notifyclientes) {
		
		//Iniciamos las variables
		clients = new ArrayList<Client>();
		xUsers = new XMLusers(app);
		xCamisetas = new XMLcamiseta(app);
		xEquipos = new XMLequipo();
		
		this.notifyclientes=notifyclientes;
		

		try {
			//Iniciamos el socket en el puerto 5000
			socket = new ServerSocket(1200);
			System.out.println("SERVER: " + socket.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		
		//Mantenemos el hilo constantemente corriendo
		while (true) {
			try {
				
//				if(!clients.isEmpty()){
//					clients.clear();
//				}

				
				if(clients.size() <= 2){
					//registra
					
				}
				
				
				//Cuando llega un cliente lo añadimos al arrayList de Clientes
				clients.add(new Client(socket.accept(), this));
				System.out.println("new client number: " + clients.get(clients.size() - 1).toString());
				System.out.println("Total clients: " + clients.size());
				notifyclientes.ShowClientes(clients.size());
				
				//se revisa por clientes cada 100 milisegundos
				sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public void update(Observable client, Object a) {
		
		//Si el objeto que llega es un usuario se realiza esto
		if (a instanceof User) {
			User b = (User) a;
			
				if (b.getId()==0) {
					//Si su id es  es el valor inicial, lo registra y sigue
					boolean result = xUsers.addUser(b.getName(), b.getEquipo(), 0);
					//y le respondemos con el resultado
					
					System.out.println("VALIDADO");
					if (result) {
						//Respondemos 1 si el registro ha sido exitoso
						((Client) client).send((int) 3);
					}
			} 
		}
		
	}

	
	

}
