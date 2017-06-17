# JUnitReflect
Reflections for JUnit  
A fast and simple way to access private fields and methods during JUnit testing.

## Usage
```
public class YourJUnitTestClass extends ClassParser<ClassToBeTested> {

	private ClassToBeTested tc = new ClassToBeTested("fistName", "lastName");

	@Before
	public void reset() {
		setInstance(tc);
	}
	
	@Test
	public void fieldTest() {
	
		String version = getField(String.class, "STATIC_VAR");
		String firstName = getField(String.class, "firstName");
		
		// do some testing
		// Assert ...
	}
	
	@Test
	public void setFieldTest() {
	
		// note: final fields cannot be changed
	
		setField("fieldName", Object newValue);
		
		// do some testing
		// Assert ...
	}
	
	@Test
	public void methodTest() {
	
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

