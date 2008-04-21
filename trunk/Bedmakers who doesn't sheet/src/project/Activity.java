package project;

import java.util.ArrayList;

import employee.Employee;

public class Activity {
	
	enum states{NOT_STARTED,IN_PROGRESS,COMPLETED};
	
	String name;
	Project partOfProject;
	int startWeek;
	int endWeek;
	float workHours;
	String description;
	ArrayList<Employee> assignedEmployees;
	int state;
	int completion;
	//woot=?
}
