package root;

import java.io.IOException;
import java.util.Scanner;

public class MainClass {
	
	private static final Scanner cin = new Scanner(System.in);
	private static final int PORT = 1004;
	
	public static void main(String[] args){
		Servidor serv;
		Cliente client;
		try {
			serv = new Servidor(PORT);
			serv.start();
			client = new Cliente(1, null, PORT);
			
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static /*synchronized*/ void printC(int id, String msg){
		System.out.println("  User " + id + "// " + msg);
	}
	
	public static /*synchronized*/ void printS(String msg){
		System.out.println("  Server// " + msg);
	}
	
	public static /*synchronized*/ void printSOy(int id, String msg){
		System.out.println("OyUser " + id + "// " + msg);
	}
	
	public static /*synchronized*/ void printCOy(String msg){
		System.out.println("OyServer// " + msg);
	}
	
	public static /*synchronized*/ String readC(int id){
		System.out.print("  User " + id + "// >");
		return cin.nextLine();
	}
	
	public static /*synchronized*/ String readS(){
		System.out.print("  Server// >");
		return cin.nextLine();
	}
	
	
}
