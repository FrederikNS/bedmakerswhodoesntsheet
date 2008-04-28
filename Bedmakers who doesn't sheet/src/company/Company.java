package company;

import java.util.ArrayList;
import employee.Employee;
import schedule.Schedule;

public class Company {
	Schedule schedule;
	ArrayList<Employee> employees;

	public Company() {
		schedule = new Schedule();
		employees = new ArrayList<Employee>();
	}

	public String createInitialsFromName(String name){
		String firstInitial = name.substring(0,2);
		int secondInitialIndex = name.lastIndexOf(" ") +1;
		String secondInitial = name.substring(secondInitialIndex,secondInitialIndex+2);
		String initials = firstInitial+secondInitial;
		System.out.println(initials);
		checkInitials(initials);
		return initials;
	}
//TODO Check if works.
	public String checkInitials(String initials){
		for(Employee emptemp: employees){
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
}
