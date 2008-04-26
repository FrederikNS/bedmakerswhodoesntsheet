package activity;

import java.util.ArrayList;

import employee.Employee;

public class Project {
	String name;
	ArrayList<Activity> activities;
	Employee leader;
	
	public Project(String name, Employee leader) {
		this.name = name;
		this.leader = leader;
	}
	
	public Project(String name) {
		Project(name, null);
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	void assignLeader(Employee leader)  {
		this.leader = leader;
	}
	
	void addActivity(Activity activity) {
		activities.add(activity);
	}

	void removeActivity(Activity activity) {
		activities.add(activity);
	}
}
