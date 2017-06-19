# JUnitReflect
Reflections for JUnit  
A fast and simple way to access private fields and methods during JUnit testing.

## Usage
```
public class YourJUnitTestClass extends ClassParser<ClassToBeTested> {
	
	@Test
	public void fieldTest() {
	
		ClassToBeTested tc = new ClassToBeTested("fistName", "lastName");
		setInstance(tc);
		
		String version = getField(String.class, "STATIC_VAR");
		String firstName = getField(String.class, "firstName");
		
		// do some testing
		// Assert ...
	}
	
	@Test
	public void setFieldTest() {
	
		// note: final fields cannot be changed
		
		ClassToBeTested tc = new ClassToBeTested("fistName", "lastName");
		setInstance(tc);
	
		setField("fieldName", Object newValue);
		
		// do some testing
		// Assert ...
	}
	
	@Test
	public void methodTest() {
	
		ClassToBeTested tc = new ClassToBeTested("fistName", "lastName");
		setInstance(tc);
	
		// method with no args or return
		method("methodName");
	
		// method with args but no return
		method_args("methodName", arg0, arg1 ...);
	
		// method with args and return
		Integer sqrt = T_method_args(Integer.class, "sqrt", args ...);
		
		// do some testing
		// Assert ...
	}
}
```

