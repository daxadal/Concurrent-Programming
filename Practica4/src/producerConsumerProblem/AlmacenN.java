package producerConsumerProblem;


public class AlmacenN implements Almacen{

	public AlmacenN(int N){
		this.prod = new Producto[N];
		this.proxCons = 0;
		this.proxProd = 0;
		this.nProd = 0;
		this.N = N;
	}
	
	@Override
	public synchronized void almacenar(Producto producto) {
		while(nProd==N)
			try {
				System.out.println("Imposible almacenar");
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		this.prod[proxProd] = producto;
		proxProd = (proxProd+1)%N;
		this.nProd++;
		System.out.println("Producto "+ producto.toString() + " almacenado");
		this.notifyAll();
	}

	@Override
	public synchronized Producto extraer() {
		Producto producto = null;	
		while(nProd==0)
			try {
				System.out.println("Imposible extraer");
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		producto = this.prod[proxCons];
		this.prod[proxCons] = null;
		proxCons = (proxCons+1)%N;
		this.nProd--;
		this.notifyAll();
		
		System.out.println("Producto "+ producto.toString() + " extraido");
		return producto;
	}
	
	private Producto prod[];
	private int proxProd;
	private int proxCons;
	private int nProd;
	private int N;
}
