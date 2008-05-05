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
	//Ubrugte felter.
	//int startWeek;
	//int endWeek;
	//int state;
	//int completion;
	
	public Activity(int id, String name, Project parentProject) {
		this.name = name;
		this.id = id;
		this.parentProject = parentProject;
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
		for(Week week : weeks.keySet()) week.removeActivity(this);
	}

	public void addWeek(Week week, float hours) {
		weeks.put(week,hours);
	}

	public void removeWeek(Week week) {
		weeks.remove(week);
	}

	public float getHoursForWeek(Week week) {
		return weeks.get(week); //FIXME: Hvad hvis ugen ikke er i array'en?
	}
}
