package raceProblem;

public class Xmonitor {

	private int x;
	
	public Xmonitor(int value){
		this.x = value;
	}
	
	public synchronized void increment(){
		this.x++;
	}
	
	public synchronized void decrement(){
		this.x--;
	}
	
	public synchronized int getX(){
		return this.x;
	}
	
}
