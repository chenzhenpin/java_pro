package example.demo;

@StringClass(cls="String_Class")
public class Form {
	
	@StringValue(id=4)
	public String name;
	@StringValue(msg="word")
	public int age;
	public String getName() {
		return name;
	}
	@StringMethod(method="POST")
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
