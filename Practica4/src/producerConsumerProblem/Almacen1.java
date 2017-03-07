package producerConsumerProblem;


public class Almacen1 implements Almacen{

	public Almacen1(){
		this.prod = null;
	}
	
	@Override
	public synchronized void almacenar(Producto producto) {
		while(this.prod != null)
			try {
				System.out.println("Imposible almacenar");
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		this.prod = producto;
		System.out.println("Producto "+ prod.toString() + " almacenado");
		this.notifyAll();
	}

	@Override
	public synchronized Producto extraer() {
		while(this.prod == null)
			try {
				System.out.println("Imposible extraer");
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Producto producto = this.prod;
		this.prod = null;
		System.out.println("Producto "+ producto.toString() + " extraido");
		this.notifyAll();
		return producto;
		
	}
	
	private Producto prod;
}
