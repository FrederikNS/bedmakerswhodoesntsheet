package cli;

/**
 * Contains the prefixes for the different arguments
 * @author Frederik Nordahl Sabroe
 */
public enum Arguments {
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
	 * Contains an employee-id argument
	 */
	EMPLOYEEARG("employee="),
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
	WEEK("week=");
	
	final String human;
	
	Arguments(String human2){
		this.human=human2;
	}
	
	public String toString() {
		return human;
	}
}
