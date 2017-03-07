package root;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import root.Mensaje.Data;

public class Cliente extends Thread{

	public Cliente(int id, String host, int port) throws UnknownHostException, IOException{
		this.s = new Socket(host, port);
		this.id = id;
		this.newMessages = false;
		this.port = port;
	}
	
	public void run(){
		
		try {
			
			while (!s.isBound())
				MainClass.printC(id, "No asociado");
			while (!s.isConnected())
				MainClass.printC(id, "No conectado");
			this.fout = new ObjectOutputStream(
					s.getOutputStream()	);
			this.fin = new ObjectInputStream(
					s.getInputStream()	);
			
			this.oy = new OyenteServidor(fin, fout, id, this);
			this.oy.start();
			
			this.execute();
			
			this.oy.interrupt();
			this.s.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void execute() throws ClassNotFoundException, IOException{
		LinkedList<String> peliculas;
		//TODO leer info de pelis del cliente
		
		fout.writeObject(new Mensaje(Tipo.CONEX, new Data(id, peliculas, id, port)));
		MainClass.printC(id, "Mensaje enviado: " + Tipo.CONEX);
		this.readInbox();
		MainClass.printC(id, "Conexion establecida");

		int opcion = askAction();
		while (opcion != 3){
			if (opcion == 1) {
				fout.writeObject(new Mensaje(Tipo.CONSUL_USERS, null));
				MainClass.printC(id, "Mensaje enviado: " + Tipo.CONSUL_USERS);
				this.readInbox();
				MainClass.printC(id, "Usuarios:");
				//TODO imprimir info usuarios
			}
			else /*opcion == 2*/ {
				String peli = askFilm();
				fout.writeObject(new Mensaje(Tipo.PET_FICH, new Data(peli)));
				MainClass.printC(id, "Mensaje enviado: " + Tipo.PET_FICH);
				this.readInbox();
				MainClass.printC(id, "Fichero recibido");
			}
			opcion = askAction();
		}
		
		fout.writeObject(new Mensaje(Tipo.DESCONEX, null));
		MainClass.printC(id, "Mensaje enviado: " + Tipo.DESCONEX);
		this.readInbox();
		MainClass.printC(id, "Desconexión con éxito");
	}
	
	private int askAction(){
		MainClass.printC(id, "Escoja una opcion:");
		MainClass.printC(id, "1 - Pedir lista de usuarios");
		MainClass.printC(id, "2 - Pedir pelicula");
		MainClass.printC(id, "3 - Desconectar");
		String opcion = MainClass.readC(id);
		switch (opcion){
			case "1": return 1;
			case "2": return 2;
			case "3": return 3;
			default: return askAction();
		}
	}
	
	private String askFilm(){
		MainClass.printC(id, "Escriba la pelicula que desea:");
		return  MainClass.readC(id);
	}
	
	public synchronized Mensaje readInbox() {
		while (!this.newMessages)
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		this.newMessages = false;
		return this.inbox;
	}
	
	public synchronized void sendMessage(Mensaje m){
		this.inbox = m;
		this.newMessages = true;
		this.notify();
	}
	
	private Socket s;
	private ObjectInputStream fin ;
	private ObjectOutputStream fout ;
	private OyenteServidor oy;
	private int id;
	
	private int port;
	private boolean newMessages; 
	private Mensaje inbox;
}
