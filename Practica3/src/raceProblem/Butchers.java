package raceProblem;

public class Butchers extends SoftLock{

	public Butchers(int numProcess, SoftLock mutex) {
		N = numProcess;
		this.mutex=mutex;
		this.next=1;
		this.number=1;
		this.turn = new int[N];
	}
	
	@Override
	public void lock(int id) {
		mutex.lock(id);
		turn[id]=number;
		number++;
		mutex.unlock(id);
		while (turn[id]!=next)
			wait(id);
		
	}

	@Override
	public void unlock(int id) {
		next++;
	}
	
	private int next;
	private int number;
	private int[] turn;
	private SoftLock mutex;
	private int N;

}
