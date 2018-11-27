package example.thread;

public class MyThread extends Thread {

	private long i;

	public long getI() {
		return i;
	}

	public void setI(long i) {
		this.i = i;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			i++;
		}
	}
	
	
}
