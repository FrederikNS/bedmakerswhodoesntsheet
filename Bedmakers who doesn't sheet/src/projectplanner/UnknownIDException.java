package projectplanner;

public class UnknownIDException extends Exception {
	private static final long serialVersionUID = 519195047702120049L;
	private ID_EXCEPTION_TYPES type; 
	int iKey;
	String sKey;

	public enum ID_EXCEPTION_TYPES{
		PROJECT,
		ACTIVITY,
		EMPLOYEE
	};
	
	public ID_EXCEPTION_TYPES getType() {
		return type;
	}
	
	public String getKey() {
		if(iKey != Integer.MIN_VALUE) return ""+iKey;
		return sKey;
	}
	
	public UnknownIDException(ID_EXCEPTION_TYPES type, String key) {
		super();
		sKey = key;
		iKey = Integer.MIN_VALUE;
	}
	
	public UnknownIDException(ID_EXCEPTION_TYPES type, int key) {
		super();
		iKey = key;
		sKey = "";
	}
}
