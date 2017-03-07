package multibuffer;

public class MultiBufferSync implements MultiBuffer {

	public MultiBufferSync(int N){
		this.prod = new Producto[N];
		this.proxCons = 0;
		this.proxProd = 0;
		this.nProd = 0;
		this.N = N;
	}
	
	@Override
	public synchronized void almacenar(Producto[] prods) {
		while(nProd + prods.length > N)
			try {
				System.out.println("Imposible almacenar " + prods.length
						+ ". buff: " + this.nProd + "/" +  this.N);
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		for(int i=0; i< prods.length; i++){
			this.prod[proxProd] = prods[i];
			proxProd = (proxProd+1)%N;
		}
		
		this.nProd += prods.length;
		System.out.print("Productos almacenados: ");
		for(int i=0; i< prods.length; i++)
			System.out.print(prods[i].toString());
		System.out.println();
		
		this.notifyAll();
	}

	@Override
	public synchronized Producto[] extraer(int n) {
		Producto[] prods = new Producto[n];	
		while(nProd < n)
			try {
				System.out.println("Imposible extraer " + n
						+ ". buff: " + this.nProd + "/" +  this.N);
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		for(int i=0; i< prods.length; i++){
			prods[i] = this.prod[proxCons];
			this.prod[proxCons] = null;
			proxCons = (proxCons+1)%N;
		}
		
		this.nProd -= prods.length;
		System.out.print("Productos extraidos: ");
		for(int i=0; i< prods.length; i++)
			System.out.print(prods[i].toString());
		System.out.println();
		
		this.notifyAll();
		return prods;
	}
	
	private Producto prod[];
	private int proxProd;
	private int proxCons;
	private int nProd;
	private int N;
}
