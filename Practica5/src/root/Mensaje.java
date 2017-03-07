package root;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.LinkedList;

public class Mensaje implements Serializable{
	
	public Mensaje(Tipo tipo, Data data){
		this.tipo = tipo;
		this.data = data;
	}
	
	public Tipo getTipo(){
		return tipo;
	}
	
	public Data getData() {
		return data;
	}

	private Tipo tipo;
	private Data data;
	private static final long serialVersionUID = 1156787712560457417L;

	public static class Data{
		
		public Data(int id, LinkedList<String> peliculas, int ip, int puerto){
			this.id = id;
			this.peliculas = peliculas;
			this.ip = ip;
			this.puerto = puerto;
		}
		
		public Data( int ip, int puerto){

			this.ip = ip;
			this.puerto = puerto;
		}
		
		public Data( Enumeration<Servidor.Info> infolist){
			this.infolist = infolist;
		}
		
		public Data(int id, LinkedList<String> peliculas, Enumeration<Servidor.Info> infolist, int ip, int puerto){
			this.id = id;
			this.peliculas = peliculas;
			this.ip = ip;
			this.infolist = infolist;
			this.puerto = puerto;
		}
		
		public Data(String pelicula){
			this.pelicula = pelicula;
		}
		
		public int id;
		public LinkedList<String> peliculas;
		public String pelicula;
		public Enumeration<Servidor.Info> infolist;
		public int ip;
		public int puerto;
	}
}
