package root;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class OyenteServidor extends Thread{
	
	public OyenteServidor(ObjectInputStream fin, ObjectOutputStream fout,
			int id, Cliente client) {
		this.id = id;
		this.client = client;
		this.fin = fin;
		this.fout = fout;
	}

	@Override
	public void run(){
		
		try {			
			try {
				this.execute();
			} catch (IOException e){
				MainClass.printCOy("Socket cerrado");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void execute() throws ClassNotFoundException, IOException{
		while (true) {
			Mensaje m = (Mensaje) fin.readObject();
			MainClass.printSOy(id, "Mensaje recibido: " + m.getTipo());
			switch (m.getTipo()){
				case CONF_CONEX:
					this.client.sendMessage(m);
					break;
				case CONF_CONSUL_USERS:
					this.client.sendMessage(m);
					break;
				case EMIT_FICH:
					fout.writeObject(new Mensaje(Tipo.COM_CS, null /*TODO data*/));
					MainClass.printSOy(id, "Mensaje enviado: " + Tipo.COM_CS);
					//TODO Crear clase Emisor ficher
					break;
				case COM_SC:
					//TODO Crear clase Receptor fichero
					/*XXX TEST*/ this.client.sendMessage(m);
					break;
				case CONF_DESCONEX:
					this.client.sendMessage(m);
					break;
				default:
					MainClass.printSOy(id,"***MENSAJE INVALIDO***");
					break;
			}
		}

	}
	
	private int id;
	private ObjectInputStream fin ;
	private ObjectOutputStream fout ;
	private Cliente client;
}
