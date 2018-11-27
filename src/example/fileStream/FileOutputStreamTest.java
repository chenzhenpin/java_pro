package example.fileStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamTest {
	public static void main(String[] args) {
		try {
			FileOutputStream fos= new FileOutputStream("src/example/fileStream/newFile.txt");
			FileInputStream fis=new FileInputStream("src/example/fileStream/Test.java");
			
			byte[] bbuf= new byte[32];
			int hasRead=0;
			while ((hasRead=fis.read(bbuf))>0) {
				
				fos.write(bbuf,0,hasRead);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(new File("src/example/fileStream/newFile.txt").getAbsoluteFile());
		System.out.println(new File("src/example/fileStream/newFile.txt").getPath());
		System.out.println(new File("src/example/fileStream/newFile.txt").getParentFile());
		System.out.println(new File("src/example/fileStream/newFile.txt").getName());
		System.out.println(new File("src/example/fileStream/newFile.txt").getAbsolutePath());
	}

}
