package cli;

public enum Arguments {
	NAME("name="), LEADER("leader="), INITIALS("initials="), ID("id="), START("start="), END("end=");
	
	final String human;
	
	Arguments(String human2){
		this.human=human2;
	}
	
	public String toString() {
		return human;
	}
}