
public enum Commands {
	ADDACTIVITY("addactivity"),
	NULL("");
	final String humanmessage;
	Commands(String humanmessage) {
		this.humanmessage = humanmessage;
	}
	public String toString() {
		return humanmessage;
	}
	public static Commands enumerateCommandWord(String ind[]) {
		for(Commands command : Commands.values()) {
			if(ind[0].equals(command)) return command;
		}
		return Commands.NULL;
	}
}

