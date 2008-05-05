package activity;

import java.util.ArrayList;

import employee.Employee;

public class Project {
	int id;
	String name;
	ArrayList<Activity> activities;
	Employee leader;
	
	//FIXME: duplikeret kode i constructors
	public Project(int id) {
		setId(id);
		activities = new ArrayList<Activity>();
		setName("Unavngivet Projekt"); //FIXME: Static var til det her
	}
	
	public Project(int id, String name) {
		setId(id);
		activities = new ArrayList<Activity>();
		setName(name);
	}

	public Project(int id, String name, Employee leader) {
		setId(id);
		activities = new ArrayList<Activity>();
		setName(name);
		assignLeader(leader);
	}
	
	void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		setName(name);
	}
	
	public void assignLeader(Employee l)  {
		this.leader = l;
	}
	
	public void addActivity(Activity activity) {
		activities.add(activity);
	}

	public void removeActivity(Activity activity) {
		activities.add(activity);
	}
}

