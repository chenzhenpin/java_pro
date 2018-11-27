package example.thread.wait2;

public class Subtract {
	private String lock;
	public Subtract(String lock) {
		super();
		this.lock=lock;
	}
	public void subtract() {
		try {
			synchronized (lock) {
				while(ValueObject.list.size()==0) {
					System.out.println("wait begin threadName="+Thread.currentThread().getName());
					lock.wait();
					System.out.println("wait end threadName="+Thread.currentThread().getName());

				}
				ValueObject.list.remove(0);
				System.out.println("list size="+ValueObject.list.size());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
