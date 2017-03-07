package producerConsumerProblem;

import java.util.concurrent.Semaphore;

public class Almacen1 implements Almacen{

	public Almacen1(){
		this.ocupados = new Semaphore(0);
		this.libres = new Semaphore(1);
		this.prod = null;
	}
	
	@Override
	public void almacenar(Producto producto) {
		try {
			this.libres.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.prod = producto;
		this.ocupados.release();
		System.out.println("Producto "+ prod.toString() + " almacenado");
	}

	@Override
	public Producto extraer() {
		try {
			this.ocupados.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Producto producto = this.prod;
		this.libres.release();
		System.out.println("Producto "+ prod.toString() + " extraido");
		return producto;
		
	}
	
	private Semaphore ocupados;
	private Semaphore libres;
	private Producto prod;
}
