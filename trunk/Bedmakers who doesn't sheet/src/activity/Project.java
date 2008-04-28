package activity;

import java.util.ArrayList;

import employee.Employee;

public class Project {
	String name;
	ArrayList<Activity> activities;
	Employee leader;
	
	public Project(String name) {
		setName(name); //duplikeret kode
	}

	public Project(String name, Employee leader) {
		setName(name);
		assignLeader(leader);
	}
	
	void setName(String name) {
		setName(name);
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
