package cli;

/**
 * Contains the different user commands
 * @author Frederik Nordahl Sabroe
 *
 */
public enum Commands {
	/**
	 * Contains a name argument
	 */
	NAME("name="),
	/**
	 * Contains a leader-id argument
	 */
	LEADER("leader="),
	/**
	 * Contains an initial argument
	 */
	INITIALS("initials="),
	/**
	 * Contains an id argument
	 */
	ID("id="),
	/**
	 * Contains a start week number argument
	 */
	START("start="),
	/**
	 * Contains an end week number argument
	 */
	END("end="),
	/**
	 * Contains an hour argument
	 */
	HOURS("hours="),
	/**
	 * When command is progress
	 */
	PROGRESS("progress"),
	/**
	 * Contains an employee-id argument
	 */
	EMPLOYEEARG("employee="),
	/**
	 * Contains an assistant-id
	 */
	ASSISTANT("assistant="),
	/**
	 * Contains a project-id argument
	 */
	PROJECTARG("project="),
	/**
	 * Contains an activity-id argument
	 */
	ACTIVITYARG("activity="),
	/**
	 * Contains an employee-id argument
	 */
	WEEK2("week="),
	/**
	 * When command is create
	 */
	CREATE("create"),
	/**
	 * When command is delete
	 */
	DEPRECATE("deprecate"),
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
	 * When command is quit
	 */
	QUIT("quit"),
	/**
	 * When command is week
	 */
	WEEK("week"),
	/**
	 * When no input is detected
	 */
	NULL("");
	
	final String humanmessage;
	
	Commands(String humanmessage) {
		this.humanmessage = humanmessage;
	}
	
	public String toString() {
		return humanmessage;
	}
}

