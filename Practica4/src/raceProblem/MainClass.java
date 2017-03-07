package raceProblem;

public class MainClass {
	
	private static class IncrDecr extends Thread {
	
		public IncrDecr(int id, int incr, Xmonitor xm) {
			this.incr = incr;
			this.xm = xm;
			System.out.println("Proceso "+id+"("+incr+") creado");
		}
		
		public void run() {
			if (incr == +1)
				for(int i=0; i<MID; i++)
					xm.increment();
			else
				for(int i=0; i<MID; i++)
					xm.decrement();
		}
		private int incr;
		private Xmonitor xm;
	}

	public static void main(String[] args) {
		IncrDecr incrs[] = new IncrDecr[NP];
		IncrDecr decrs[] = new IncrDecr[NP];
		Xmonitor xm = new Xmonitor(0);
		
		for(int i=0; i<NP; i++) 
			incrs[i] = new IncrDecr(i, +1, xm);
		for(int i=0; i<NP; i++) 
			decrs[i] = new IncrDecr(NP+i, -1, xm);
		
		//creacion
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
		System.out.println("X="+ xm.getX() + " (0 expected)");
	}
	
	private static final int NP = 5;
	private static final int MID = 5;

}
