package example.base;

public class BaseTest {
	public static void main(String[] args) {
		Integer inta=128;
		Integer intb=128;
		int a=128;
		int b=128;
		System.out.println("inta=intb:"+(inta==intb));
		System.out.println("inta=a:"+(inta==a));		
		System.out.println("a==b:"+(a==b));
		System.out.println("Integer.compare(inta, intb):"+Integer.compare(inta, intb));
		
	}
}
