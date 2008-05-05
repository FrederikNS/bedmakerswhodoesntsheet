package company;

import java.util.HashMap;

import schedule.Schedule;
import activity.Project;
import employee.Employee;

public class Company {
	HashMap<Integer,Project> projects;
	Schedule schedule;
	HashMap<String,Employee> employees;

	public Company() {
		schedule = new Schedule();
		projects = new HashMap<Integer,Project>();
		employees = new HashMap<String,Employee>();
	}

	/*public String createInitialsFromName(String name){
		String firstInitial = name.substring(0,2);
		int secondInitialIndex = name.lastIndexOf(" ") +1;
		String secondInitial = name.substring(secondInitialIndex,secondInitialIndex+2);
		String initials = firstInitial+secondInitial;
		System.out.println(initials);
		checkInitials(initials);
		return initials;
	}*/

//TODO Check if works.
	public String checkInitials(String initials){
		for(Employee emptemp: employees.values()){
			if(emptemp.getInitials().equals(initials) && initials.length() == 4){
				initials = initials + "1";
				checkInitials(initials);
			} else if(emptemp.getInitials().equals(initials)){
				initials = initials.substring(0,4) + String.valueOf((Integer.parseInt((initials.substring(5,initials.length()))) +1));
				checkInitials(initials);
			}
		}
		return initials;
	}
	
	public void addProject(int id) {
		projects.put(id, new Project(id));
	}
	
	public void addProject(int id, String name) {
		projects.put(id,new Project(id, name));
	}
	
	public void addProject(int id, String name, Employee leader) {
		projects.put(id,new Project(id, name, leader));
	}
}
