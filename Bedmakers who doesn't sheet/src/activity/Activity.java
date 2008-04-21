package activity;

import java.util.ArrayList;

public class Activity {
	
	enum states{NOT_STARTED,IN_PROGRESS,COMPLETED};
	
	int startWeek;
	int endWeek;
	float workHours;
	String description;
	ArrayList<Employee> assignedEmployees;
	String name;
	int state;
	int completion;
}
