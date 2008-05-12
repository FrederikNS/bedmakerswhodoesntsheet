package projectplanner;

public class FrozenException extends Exception {
	private static final long serialVersionUID = -1426935987944445541L;
	private Object src;
	public FrozenException(Object src) {
		super("Object " + src + " is frozen");
		this.src = src;
	}
	public Object getSrc() {
		return src;
	}
}
