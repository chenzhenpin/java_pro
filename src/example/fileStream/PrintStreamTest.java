package example.fileStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class PrintStreamTest {
	public static void main(String[] args) {
		try {
			FileOutputStream fos=new FileOutputStream("src/example/fileStream/print.txt");
			PrintStream ps= new PrintStream(fos);
			{
				System.out.println("开始打印");
				ps.println("普通字符串");
				ps.println(new PrintStreamTest());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
