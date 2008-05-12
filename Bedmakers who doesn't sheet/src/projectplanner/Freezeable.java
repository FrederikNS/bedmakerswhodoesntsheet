package projectplanner;

public abstract class Freezeable {
	private boolean frozen;
	public void freeze() throws FrozenException {
		checkFreeze();
		this.frozen = true;
	}

	public void unfreeze() {
		this.frozen = false;
	}

	public void checkFreeze() throws FrozenException {
		if (frozen)
			throw new FrozenException(this);
	}	

	public boolean isFrozen() {
		return frozen;
	}
}
