package activity;

import java.util.ArrayList;

import employee.Employee;

public class Project {
	String name;
	ArrayList<Activity> activities;
	Employee leader;
	
	public Project(String name) {
		activities = new ArrayList<Activity>();
		setName(name);
	}

	public Project(String name, Employee leader) {
		activities = new ArrayList<Activity>();
		setName(name);
		assignLeader(leader);
	}
	
	public void setName(String name) {
		setName(name);
	}
	
	public void assignLeader(Employee projectLeader)  {
		this.leader = projectLeader;
	}
	
	public void addActivity(Activity activity) {
		activities.add(activity);
	}

	public void removeActivity(Activity activity) {
		activities.remove(activity);
	}
	
	public int getRemainingWork() {
		int load = 0;
		for(Activity activity : activities){
			load += activity.workload-activity.progress;
		}
		return load;
	}
	
	public ArrayList<Employee> getEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		for(Activity a : activities) {
			for(Employee e : a.getAssignedEmployees()) {
				employees.add(e);
			}
		}
		return employees;
	}
}

