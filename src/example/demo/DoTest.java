package example.demo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DoTest {
	public static void main(String[] args) {
		 System.out.println("Start:");
		 boolean hasAnnotation= Form.class.isAnnotationPresent(StringClass.class);
		 if (hasAnnotation) {
			 StringClass sAnnotation= Form.class.getAnnotation(StringClass.class);
			 System.out.println(sAnnotation.cls());
		 }
		 Method[] methods=Form.class.getDeclaredMethods();
		 for (int i = 0; i < methods.length; i++) {
			 
			 Boolean hasm=methods[i].isAnnotationPresent(StringMethod.class);
			 if (hasm) {
				 System.out.println(methods[i].getName());
				 StringMethod stringMethod=methods[i].getAnnotation(StringMethod.class);
				 System.out.println(stringMethod.method());
			}
		}
		 Field[] fields =Form.class.getFields();
		 System.out.println(fields.length);
		 for (int i = 0; i < fields.length; i++) {
			 StringValue v=fields[i].getAnnotation(StringValue.class);
			 System.out.println(fields[i].getName());
			 System.out.println(v.id());
			 System.out.println(v.msg());
		}
	}
}
