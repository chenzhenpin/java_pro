package example.demo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValue {
	int id() default 1;
	String msg() default "hello";
}
