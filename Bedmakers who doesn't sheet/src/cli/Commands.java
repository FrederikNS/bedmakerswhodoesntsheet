package cli;

/**
 * Contains the different user commands
 * @author frederikns
 *
 */
public enum Commands {
	/**
	 * When no input is detected
	 */
	NULL(""),
	/**
	 * When command is create
	 */
	CREATE("create"),
	/**
	 * When command is delete
	 */
	DELETE("delete"),
	/**
	 * When command is view
	 */
	VIEW("view"),
	/**
	 * When command is help
	 */
	HELP("help"),
	/**
	 * When command is employee
	 */
	EMPLOYEE("employee"),
	/**
	 * When command is project
	 */
	PROJECT("project"),
	/**
	 * When command is activity
	 */
	ACTIVITY("activity"),
	/**
	 * When command is edit
	 */
	EDIT("edit"),
	/**
	 * When command is find
	 */
	FIND("find"),
	/**
	 * When command is assign
	 */
	ASSIGN("assign"),
	/**
	 * When command is unassign
	 */
	UNASSIGN("unassign"),
	/**
	 * When command is rename
	 */
	RENAME("rename"),
	/**
	 * When command is quit
	 */
	QUIT("quit"),
	/**
	 * When command is week
	 */
	WEEK("week"),
	/**
	 * When command is progress
	 */
	PROGRESS("progress");
	
	final String humanmessage;
	
	Commands(String humanmessage) {
		this.humanmessage = humanmessage;
	}
	
	public String toString() {
		return humanmessage;
	}
}

