package root;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

public class Servidor extends Thread{
	public Servidor(int port) throws IOException{
		this.listen = new ServerSocket(port);
	}
	
	public void run() {
		try {
			this.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void execute() throws IOException {
		Socket s;
		while (true){
			s = listen.accept();
			(new OyenteCliente(s, this)).start();
		}
	}
	
	public void registrarOyente(int idCliente, OyenteCliente oyente){
		this.tOyentes.put(idCliente, oyente);
	}
	
	public void registrarUsuario(int idCliente, Info info){
		this.tUsuarios.put(idCliente, info);
	}
	
	public OyenteCliente buscarOyente(int idCliente){
		return this.tOyentes.get(idCliente);
	}
	
	public Enumeration<Info> infoUsuarios(){
		return this.tUsuarios.elements();
	}
	
	public void desconectarOyente(int idCliente){
		this.tOyentes.remove(idCliente);
		if (this.tOyentes.isEmpty())
			this.interrupt();
	}
	
	public int buscarUsuario(String pelicula){
		Enumeration<Info> infolist = tUsuarios.elements();
		Info info;
		while (infolist.hasMoreElements()){
			info = infolist.nextElement();
			if (info.peliculas.contains(pelicula))
				return info.id;
		}
		return -1;
	}
	
	private ServerSocket listen;
	private Hashtable<Integer, Info> tUsuarios;
	private Hashtable<Integer, OyenteCliente> tOyentes;
	
	public static class Info{
		
		public Info (int id, LinkedList<String> peliculas, int puerto, int ip){
			this.peliculas = peliculas;
			this.puerto = puerto;
			this.ip = ip;
			this.id = id;
		}
		
		public LinkedList<String> peliculas;
		public int puerto;
		public int ip;
		public int id;
	}
}
