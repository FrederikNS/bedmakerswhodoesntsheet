package cli;

public enum Arguments {
	NAME("name="), LEADER("leader="), INITIALS("initials="), ID("id="), START("start="), END("end="), EMPLOYEEARG("employee="), PROJECTARG("project="), ACTIVITYARG("activity="), WORKLOAD("workload=");
	
	final String human;
	
	Arguments(String human2){
		this.human=human2;
	}
	
	public String toString() {
		return human;
	}
}
