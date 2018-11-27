package example.fileStream;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterTest {
	public static void main(String[] args) {
		try {
			FileWriter fw= new FileWriter("src/example/fileStream/writer.txt");
			fw.write("大家好\r\n");
			fw.write("你好\r\n");
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
