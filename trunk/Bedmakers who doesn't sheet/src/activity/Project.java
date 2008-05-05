package activity;

import java.util.ArrayList;

import employee.Employee;

public class Project {
	String name;
	ArrayList<Activity> activities;
	Employee leader;
	//FIXME: duplikeret kode i constructors
	public Project() {
		activities = new ArrayList<Activity>();
		setName("Unavngivet Projekt"); //FIXME: Static var til det her
	}
	
	public Project(String name) {
		activities = new ArrayList<Activity>();
		setName(name);
	}

	public Project(String name, Employee leader) {
		activities = new ArrayList<Activity>();
		setName(name);
		assignLeader(leader);
	}
	
	void setName(String name) {
		//EVENT
		setName(name);
	}
	
	void assignLeader(Employee l)  {
		//EVENT
		this.leader = l;
	}
	
	void addActivity(Activity activity) {
		//EVENT
		activities.add(activity);
	}

	void removeActivity(Activity activity) {
		//EVENT
		activities.add(activity);
	}
}

