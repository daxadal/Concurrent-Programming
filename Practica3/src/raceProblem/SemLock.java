package raceProblem;

import java.util.concurrent.Semaphore;

public class SemLock extends SoftLock {

	public SemLock() {
		this.sem = new Semaphore(1);
	}
	@Override
	public void lock(int id) {
		try {
			this.sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unlock(int id) {
		this.sem.release();
	}

	private Semaphore sem;
}
