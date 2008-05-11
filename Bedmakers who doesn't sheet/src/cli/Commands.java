package cli;

public enum Commands {
	ADDACTIVITY("addactivity"), NULL(""), CREATE("create"), DELETE("delete"), VIEW("view"), HELP("help"), EMPLOYEE("employee"), PROJECT("project"), ACTIVITY("activity"), EDIT("edit"), FIND("find"), ASSIGN("assign"), UNASSIGN("unassign"), RENAME("rename"), QUIT("quit"), WEEK("week");
	
	final String humanmessage;
	
	Commands(String humanmessage) {
		this.humanmessage = humanmessage;
	}
	
	public String toString() {
		return humanmessage;
	}
}

