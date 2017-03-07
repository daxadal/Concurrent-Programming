package root;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Test {
	
	public static void main(String[] args){
		try {
			execute(null, 1004);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void execute(String serverHost, int port) throws IOException, ClassNotFoundException{
		ServerSocket l = new ServerSocket(port);
		Socket sc = new Socket(serverHost, port);
		Socket ss = l.accept();
		if (sc.isBound())
			System.out.println("Socket client bound");
		else
			System.out.println("Socket client NOT bound");
		
		if (ss.isBound())
			System.out.println("Socket server bound");
		else
			System.out.println("Socket server NOT bound");
		
		if (sc.isConnected())
			System.out.println("Socket client connected");
		else
			System.out.println("Socket client NOT connected");
		
		if (ss.isConnected())
			System.out.println("Socket server connected");
		else
			System.out.println("Socket server NOT connected");
		
		OutputStream souts = ss.getOutputStream();
		OutputStream soutc = sc.getOutputStream();
		InputStream sins = ss.getInputStream();
		InputStream sinc = sc.getInputStream();
		
		
		System.out.println("Streams created");
		
		
		ObjectOutputStream fouts = new ObjectOutputStream(souts);
		ObjectOutputStream foutc = new ObjectOutputStream(soutc);
		
		ObjectInputStream finc = new ObjectInputStream(sinc);
		ObjectInputStream fins = new ObjectInputStream(sins);
		
		System.out.println("Wrappers created");
		
		Mensaje m = new Mensaje(Tipo.CONEX, null);
		foutc.writeObject(m);
		System.out.println("CLIENT // Mensaje enviado. Tipo: " + m.getTipo());
		m = (Mensaje) fins.readObject();
		System.out.println("SERVER // Mensaje recibido. Tipo: " + m.getTipo());
		
		m = new Mensaje(Tipo.CONF_CONEX, null);
		fouts.writeObject(m);
		System.out.println("SERVER // Mensaje enviado. Tipo: " + m.getTipo());
		m = (Mensaje) finc.readObject();
		System.out.println("CLIENT // Mensaje recibido. Tipo: " + m.getTipo());
		
		l.close();
		sc.close();
		ss.close();
		
		if (sc.isClosed())
			System.out.println("Socket client closed");
		else
			System.out.println("Socket client NOT closed");
		
		if (ss.isClosed())
			System.out.println("Socket server closed");
		else
			System.out.println("Socket server NOT closed");
		
		if (l.isClosed())
			System.out.println("ServerSocket listener closed");
		else
			System.out.println("ServerSocket listener NOT closed");
		
		
	}

}
