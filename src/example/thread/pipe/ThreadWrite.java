package example.thread.pipe;

import java.io.PipedOutputStream;

public class ThreadWrite extends Thread{
	private WriteData writeData;
	private PipedOutputStream out;
	
	public  ThreadWrite(WriteData writeData,PipedOutputStream out) {
		this.writeData=writeData;
		this.out=out;
	}
	@Override
	public void run() {
		writeData.WriteMethod(out);
	}
}
