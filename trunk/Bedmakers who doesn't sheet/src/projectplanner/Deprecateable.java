package projectplanner;

public abstract class Deprecateable {
	private boolean deprecated;
	public void deprecate() {
		this.deprecated = true;
	}

	public void undeprecate() {
		this.deprecated = false;
	}

	public void checkDeprecateAndDoNothing(){ // throws FrozenException {
//		if (deprecated)
//			throw new FrozenException(this);
	}	

	public boolean isDeprecated() {
		return deprecated;
	}
}
