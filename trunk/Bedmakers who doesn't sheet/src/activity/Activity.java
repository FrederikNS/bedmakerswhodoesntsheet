package activity;

import java.util.ArrayList;
import java.util.HashMap;

import schedule.Week;

import employee.Employee;

public class Activity {
	
	//enum states{NOT_STARTED,IN_PROGRESS,COMPLETED};
	
	int id;
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
	
	public Activity(int id, String name) {
		this.name = name;
		this.id = id;
		//
	}
	
	public void setParent(Project parentProject) {
		this.parentProject = parentProject;		
	}
	
	public void renameActivity(String newName){
		this.name = newName;		
	}
	
	public int getId() {
		return id;
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
