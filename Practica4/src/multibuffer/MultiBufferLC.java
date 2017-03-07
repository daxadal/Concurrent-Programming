package multibuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MultiBufferLC implements MultiBuffer {

	private static final Lock mutex = new ReentrantLock();
	private static final Condition tooMuchElements = mutex.newCondition();
	private static final Condition tooFewElements = mutex.newCondition();
			
	public MultiBufferLC(int N){
		this.prod = new Producto[N];
		this.proxCons = 0;
		this.proxProd = 0;
		this.nProd = 0;
		this.N = N;
	}
	
	@Override
	public void almacenar(Producto[] prods) {
		MultiBufferLC.mutex.lock();
		
		while(nProd + prods.length > N)
			try {
				System.out.println("Imposible almacenar " + prods.length
						+ ". buff: " + this.nProd + "/" +  this.N);
				MultiBufferLC.tooMuchElements.await();
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
		
		MultiBufferLC.tooFewElements.signalAll();
		MultiBufferLC.mutex.unlock();
	}

	@Override
	public synchronized Producto[] extraer(int n) {
		MultiBufferLC.mutex.lock();
		
		Producto[] prods = new Producto[n];	
		while(nProd < n)
			try {
				System.out.println("Imposible extraer " + n
						+ ". buff: " + this.nProd + "/" +  this.N);
				MultiBufferLC.tooFewElements.await();
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
		
		MultiBufferLC.tooMuchElements.signalAll();
		MultiBufferLC.mutex.unlock();
		return prods;
	}
	
	
	private Producto prod[];
	private int proxProd;
	private int proxCons;
	private int nProd;
	private int N;
}
