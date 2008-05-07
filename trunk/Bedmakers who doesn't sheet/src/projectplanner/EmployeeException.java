package projectplanner;

public class EmployeeException extends Exception {
	private static final long serialVersionUID = -6195913310986553082L;

	public EmployeeException() {
		super();
	}
	public EmployeeException(String reason) {
		super(reason);
	}	
}
