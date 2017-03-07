package raceProblem;

public class TieBreaker extends SoftLock {

	public TieBreaker(int numProcess) {
		N = numProcess;
		in = new int[numProcess];
		for (int i=0;i<N;i++) in[i]=-1;
		last = new int[numProcess];
	}
	
	@Override
	public void lock(int id) {
		for(int j=0; j<N; j++) {
			in[id]=j;
			last[j]=id;
			for (int k=0; k<N; k++) {
				while (k!=id && last[j]==id && in[k]>=in[id]) 
					wait(id);
			}
		}
		
	}

	@Override
	public void unlock(int id) {
		in[id]=-1;
	}
	
	private int[] in;
	private int[] last;
	private int N;
}
