package producerConsumerProblem;

import java.util.concurrent.Semaphore;

public class AlmacenN implements Almacen{

	public AlmacenN(int N){
		this.ocupados = new Semaphore(0);
		this.libres = new Semaphore(N);
		this.mutexProd = new Semaphore(1);
		this.mutexCons = new Semaphore(1);
		this.prod = new Producto[N];
		this.proxCons = 0;
		this.proxProd = 0;
		this.N = N;
	}
	
	@Override
	public void almacenar(Producto producto) {
		try {
			this.libres.acquire();
			this.mutexProd.acquire();
			
			this.prod[proxProd] = producto;
			proxProd = (proxProd+1)%N;
			System.out.println("Producto "+ producto.toString() + " almacenado");
			
			this.mutexProd.release();
			this.ocupados.release();
		} catch (InterruptedException e) {}

	}

	@Override
	public Producto extraer() {
		Producto producto = null;
		try {
			this.ocupados.acquire();
			this.mutexCons.acquire();
			
			producto = this.prod[proxCons];
			this.prod[proxCons] = null;
			proxCons = (proxCons+1)%N;
			System.out.println("Producto "+ producto.toString() + " extraido");
			
			this.mutexCons.release();
			this.libres.release();
		} catch (InterruptedException e) {}

		return producto;
		
	}
	
	private Semaphore ocupados;
	private Semaphore libres;
	private Semaphore mutexProd;
	private Semaphore mutexCons;
	private Producto prod[];
	private int proxProd;
	private int proxCons;
	private int N;
}
