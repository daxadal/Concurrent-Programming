package producerConsumerProblem;

public class MainClass {

	private static class Producer extends Thread {
		public Producer(int id, int seqMax, Almacen almacen) {
			this.id = id;
			this.seqMax = seqMax;
			this.almacen = almacen;
		}
		
		@Override
		public void run() {
			Producto prod;
			for(int seq=0; seq<seqMax; seq++) {
				prod = new Producto(id,seq);
				this.almacen.almacenar(prod);
			}
		}
		
		private int id;
		private int seqMax;
		private Almacen almacen;
		
	}
	
	private static class Consumer extends Thread {
		public Consumer(int id, int seqMax, Almacen almacen) {
			this.id = id;
			this.seqMax = seqMax;
			this.almacen = almacen;
		}
		
		@Override
		public void run() {
			Producto prod;
			for(int seq=0; seq<seqMax; seq++) {
				prod = this.almacen.extraer();
				prod.consumir(id, seq);
			}
		}
		
		private int id;
		private int seqMax;
		private Almacen almacen;
	}
	
	
	public static void main(String[] args) {
	//	Almacen almacen = new Almacen1();
		Almacen almacen = new AlmacenN(2);
		Producer producers[] = new Producer[numPC];
		Consumer consumers[] = new Consumer[numPC];
		
		for(int id=0; id<numPC; id++) 
			producers[id] = new Producer(id, seqMax, almacen);
		for(int id=0; id<numPC; id++) 
			consumers[id] = new Consumer(id, seqMax, almacen);
		
		//creacion
		for(int i=0; i<numPC; i++) {
			producers[i].start();
			consumers[i].start();
		}
		
		//espera
		for(int i=0; i<numPC; i++) {	
			try {
				producers[i].join();
				consumers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	private static final int numPC = 10;
	private static final int seqMax = 10;

}
