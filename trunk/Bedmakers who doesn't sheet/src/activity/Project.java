package activity;

import java.util.ArrayList;

import employee.Employee;

public class Project {
	String name;
	ArrayList<Activity> activities;
	Employee leader;
	
	void assignLeader(Employee leader)  {
		this.leader = leader;
	}
	
}
