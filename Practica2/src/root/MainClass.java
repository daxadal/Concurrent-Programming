package root;

public class MainClass {
	
	private static class IncrDecr extends Thread {
	
		public IncrDecr(int id, int incr, SoftLock mutex) {
			this.id = id;
			this.incr = incr;
			this.mutex = mutex;
			System.out.println("Proceso "+id+"("+incr+") creado");
		}
		
		public void run() {
			for(int i=0; i<MID; i++) {
				mutex.lock(id);
				//<---- CS ---->
				System.out.println();
				System.out.println("-------------------["+id+" lock]------------------------");
				x += incr;
				Thread.yield();
				System.out.println();
				System.out.println("-------------------["+id+" unlock]----------------------");
				//<---- CS ---->
				mutex.unlock(id);
			}
		}
		private int id;
		private int incr;
		private SoftLock mutex;
	}

	public static void main(String[] args) {
		//declaraciones
		SoftLock mutex = new TieBreaker(2*NP);
	//	SoftLock mutex = new Butchers(2*NP, new Bakery(2*NP));
	//	SoftLock mutex = new Bakery(2*NP);
		IncrDecr incrs[] = new IncrDecr[NP];
		IncrDecr decrs[] = new IncrDecr[NP];
		
		for(int i=0; i<NP; i++) 
			incrs[i] = new IncrDecr(i, +1, mutex);
		for(int i=0; i<NP; i++) 
			decrs[i] = new IncrDecr(NP+i, -1, mutex);
		
		//creacion
		x=0;
		for(int i=0; i<NP; i++) {
			incrs[i].start();
			decrs[i].start();
		}
		
		//espera y resultado
		for(int i=0; i<NP; i++) {	
			try {
				incrs[i].join();
				decrs[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println();
		System.out.println("X="+ x + " (0 expected)");
	}
	
	private static final int NP = 5;
	private static final int MID = 5;
	private static volatile int x;

}
