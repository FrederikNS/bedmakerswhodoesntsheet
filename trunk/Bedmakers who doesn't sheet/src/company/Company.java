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
}
