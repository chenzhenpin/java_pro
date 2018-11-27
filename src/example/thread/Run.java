package example.thread;

public class Run {
	public static void main(String[] args) {
		try {
			MyThread thread=new MyThread();
			thread.start();
			Thread.sleep(5000);
			thread.suspend();
			System.out.println("A="+System.currentTimeMillis()+"i="+thread.getI());
			Thread.sleep(5000);
			System.out.println("A="+System.currentTimeMillis()+"i="+thread.getI());
			thread.sleep(5000);
			thread.resume();
			thread.sleep(5000);
			thread.suspend();
			System.out.println("A="+System.currentTimeMillis()+"i="+thread.getI());
			thread.sleep(5000);
			System.out.println("A="+System.currentTimeMillis()+"i="+thread.getI());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
