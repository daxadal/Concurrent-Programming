package multibuffer;

public class MainClass {

	/** Crea productos en paquetes de (id+1) hasta crear SeqMax o más
	 */
	private static class Producer extends Thread {
		public Producer(int id, int seqMax, MultiBuffer buff) {
			this.id = id;
			this.seqMax = seqMax;
			this.buff = buff;
		}
		
		@Override
		public void run() {
			Producto[] prods = new Producto[id+1];
			for(int seq=0; seq<seqMax;) {
				for (int i=0; i<this.id+1;i++) {
					prods[i] = new Producto(id,seq);
					seq++;
				}
				this.buff.almacenar(prods);
			}
		}
		
		private int id;
		private int seqMax;
		private MultiBuffer buff;
		
	}
	
	/** Consume productos en paquetes de (id+1) hasta crear SeqMax o más
	 */
	private static class Consumer extends Thread {
		public Consumer(int id, int seqMax, MultiBuffer buff) {
			this.id = id;
			this.seqMax = seqMax;
			this.buff = buff;
		}
		
		@Override
		public void run() {
			Producto[] prods;
			for(int seq=0; seq<seqMax; seq=seq+id+1) {
				prods = this.buff.extraer(id+1);
				for (int i=0; i<this.id+1;i++)
					prods[i].consumir(id, seq);
			}
		}
		
		private int id;
		private int seqMax;
		private MultiBuffer buff;
	}
	
	
	public static void main(String[] args) {
		MultiBuffer buff = new MultiBufferSync(10);
	//	MultiBuffer buff = new MultiBufferLC(10);
		Producer producers[] = new Producer[numPC];
		Consumer consumers[] = new Consumer[numPC];
		
		for(int id=0; id<numPC; id++) 
			producers[id] = new Producer(id, seqMax, buff);
		for(int id=0; id<numPC; id++) 
			consumers[id] = new Consumer(id, seqMax, buff);
		
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
	
	private static final int numPC = 5;
	private static final int seqMax = 10;

}
