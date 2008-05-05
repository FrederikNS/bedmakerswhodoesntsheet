package activity;

import java.util.ArrayList;
import java.util.HashMap;

import schedule.Week;

import employee.Employee;

public class Activity {
	
	//enum states{NOT_STARTED,IN_PROGRESS,COMPLETED};
	
	String name;
	Project parentProject;
	HashMap<Week,Float> weeks;
	String description;
	ArrayList<Employee> assignedEmployees;
	ArrayList<Employee> assistants;
	int workload;
	int progress;
	
	//int startWeek;
	//int endWeek;
	//int state;
	//int completion;
	
	public Activity(String name) {
		setName(name);
		weeks = HashMap<Week,Float>();
		assistants = new ArrayList<Employee>();
		assignedEmployees = new ArrayList<Employee>();
	}

	public Activity(String name, Project parent) {
		setParent(parent);
		Activity(name);
	}

	public void setParent(Project parentProject) {
		this.parentProject = parentProject;		
	}
	
	public void setName(String newName){
		this.name = newName;		
	}
	
	public String getName() {
		return name;
	}
	
	public Project getParentProject() {
		return parentProject;
	}
	
	public void remove() {
		parentProject.removeActivity(this);
		for(Week week : weeks.keySet()) week.removeActivity(this);
	}

	public void addWeek(Week week, float hours) {
		weeks.put(week,hours);
	}

	public void removeWeek(Week week) {
		weeks.remove(week);
	}

	public float getHoursForWeek(Week week) {
		return weeks.get(week); //FIXME: Hvad hvis ugen ikke er i array'et?
	}
}
