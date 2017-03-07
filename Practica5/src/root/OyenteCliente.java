package root;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Enumeration;

import root.Mensaje.Data;
import root.Servidor.Info;

public class OyenteCliente extends Thread{

	public OyenteCliente(Socket s, Servidor server){
		this.s = s;
		this.server = server;
	}
	
	@Override
	public void run(){
		try {
			fout = new ObjectOutputStream(
					s.getOutputStream()	);
			fin = new ObjectInputStream(
					s.getInputStream()	);
			try {
				this.execute();
			} catch (IOException e){
				MainClass.printCOy("Socket cerrado");
			}
			
			this.s.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void execute() throws IOException, ClassNotFoundException{
		while (true) {
			Mensaje m = (Mensaje) fin.readObject();
			Data data = m.getData();
			MainClass.printCOy("Mensaje recibido: " + m.getTipo());
			switch (m.getTipo()){
			case CONEX: 
				this.id = data.id;
				this.server.registrarOyente(data.id, this);
				this.server.registrarUsuario( data.id, 
						new Info(data.id, data.peliculas, data.puerto, data.ip)
						);
				fout.writeObject(new Mensaje(Tipo.CONF_CONEX, null));
				MainClass.printCOy("Mensaje enviado: " + Tipo.CONF_CONEX);
				break;
			case CONSUL_USERS: 
				Enumeration<Info> infolist = this.server.infoUsuarios();
				fout.writeObject(new Mensaje(Tipo.CONF_CONSUL_USERS, new Data(infolist)));	
				MainClass.printCOy("Mensaje enviado: " + Tipo.CONF_CONSUL_USERS);
				break;
			case PET_FICH:
				int id = server.buscarUsuario(data.pelicula);
				OyenteCliente oy = server.buscarOyente(id);
				oy.sendMessage(new Mensaje(Tipo.EMIT_FICH, new Data(data.ip, data.puerto)));
				MainClass.printCOy("Mensaje enviado: " + Tipo.EMIT_FICH);
				break;
			case COM_CS:
				fout.writeObject(new Mensaje(Tipo.COM_SC, m.getData()));
				MainClass.printCOy("Mensaje enviado: " + Tipo.COM_SC);
				break;
			case DESCONEX:
				server.desconectarOyente(this.id);
				fout.writeObject(new Mensaje(Tipo.CONF_DESCONEX, null));
				MainClass.printCOy("Mensaje enviado: " + Tipo.CONF_DESCONEX);
				break;
				
			default:
				MainClass.printCOy("***MENSAJE INVALIDO***");
				break;
			
			}
		}
	}
	
	public void sendMessage(Mensaje m) throws IOException{
		fout.writeObject(m);
		MainClass.printCOy("Mensaje enviado: " + m.getTipo());
	}
	
	private Socket s;
	private ObjectOutputStream fout;
	private ObjectInputStream fin;
	private Servidor server;
	private int id; 
}
