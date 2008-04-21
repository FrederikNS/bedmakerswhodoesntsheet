package activity;

import java.util.ArrayList;
import java.util.HashMap;

import schedule.Week;

import employee.Employee;

public class Activity {
	
	enum states{NOT_STARTED,IN_PROGRESS,COMPLETED};
	
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
		return weeks.get(week);
	}
}
