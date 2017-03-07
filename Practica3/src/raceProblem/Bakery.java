package raceProblem;

public class Bakery extends SoftLock{

	public Bakery(int numProcess) {
		N = numProcess;
		turn = new int[numProcess];
	}
	
	private int max(int[] array) {
		int max = 0;
		for( int num: array) {
			if (num > max) max = num;
		}
		return max;
	}
	
	@Override
	public void lock(int id) {
		turn[id] = 1;
		turn[id] = max(turn)+1;
		for(int j=0; j<N;j++) {
			while(j!=id && turn[j]!=0 && (turn[id]>turn[j] || ( turn[id]==turn[j] && id>j)))
				wait(id);
		}
		
	}

	@Override
	public void unlock(int id) {
		turn[id]=0;
	}
	
	private int[] turn;
	private int N;

}
