package example.sys;

import java.io.IOException;

public class RunTimeTest {
	public static void main(String[] args) throws IOException {
		Runtime rt =Runtime.getRuntime();
		System.out.println("处理器数量："+rt.availableProcessors());
		System.out.println("空闲内存数："+rt.freeMemory());
		System.out.println("总内存数："+rt.totalMemory());
		System.out.println("可用最大内存数："+rt.maxMemory());
		rt.exec("notepad.exe");
	}
}
