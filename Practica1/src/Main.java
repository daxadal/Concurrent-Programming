
public class Main {
	
	private static class MyThread extends Thread {
		
		public MyThread(int id, int sleepTime) {
			this.id = id;
			this.sleepTime = sleepTime;
		}
		
		@Override
		public void run() {
			System.out.print("Init " + this.id +"\t");
			try {
				MyThread.sleep(this.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print("Exit " + this.id +"\t");
		}
		
		private int id;
		private int sleepTime;
	}
	
private static class MyThread2 extends Thread {
		
		public MyThread2(int increment) {
			this.increment = increment;
		}
		
		@Override
		public void run() {
			for (int i=0; i<Main.M2; i++) {
				Main.conflictive = Main.conflictive + this.increment;
			}
		}
		
		private int increment;
	}

	public static void main(String[] args) {
		MyThread[] threads = new MyThread[N1];
		MyThread2[] incrThreads = new MyThread2[N2];
		MyThread2[] decrThreads = new MyThread2[N2];
		
		//-------------Parte 1--------------
		for (int i=0; i<N1; i++) {
			threads[i] = new MyThread(i, T1);
			threads[i].start();
		}
		
		for (int i=0; i<N1; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.print("\nThreads joined!");
		
		//-------------Parte 2--------------
		for (int i=0; i<N1; i++) {
			incrThreads[i] = new MyThread2(+1);
			decrThreads[i] = new MyThread2(-1);
			incrThreads[i].start();
			decrThreads[i].start();
		}
		
		for (int i=0; i<N1; i++) {
			try {
				incrThreads[i].join();
				decrThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.print("\nConflictive value:" + Main.conflictive + " (0 expected)");

	}
	
	private static final int N1 = 20; //n procesos 1
	private static final int T1 = 500; //tiempo de espera 1
	private static final int N2 = 20; //n procesos 2
	private static final int M2 = 20; //num incr/decr 2
	
	private static int conflictive = 0;

}
