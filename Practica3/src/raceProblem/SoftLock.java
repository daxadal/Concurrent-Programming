package raceProblem;

public abstract class SoftLock {
	public abstract void lock(int id);
	
	public abstract void unlock(int id);
	
	public void wait(int id) {
		System.out.print("("+id+")"); //Espera activa
		if (style == 30) {
			System.out.println();
			style = 0;
		}
		else style++;
		Thread.yield();
	}
	
	private int style=0;
}
