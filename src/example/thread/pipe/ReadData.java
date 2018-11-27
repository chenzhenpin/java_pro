package example.thread.pipe;

import java.io.IOException;
import java.io.PipedInputStream;

public class ReadData {
	public void readMethod(PipedInputStream input) {
		try {
			System.out.println("read:");
			byte[] byteAarry=new byte[20];
			int readLine=input.read(byteAarry);
			while (readLine!=-1) {
				String newData=new String(byteAarry, 0, readLine);
				System.out.print(newData);
				readLine=input.read(byteAarry);
			}
			System.out.println();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
