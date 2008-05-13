package projectplanner;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity extends Deprecateable{
	private String name;
	private Project parentProject;
	private HashMap<Week, Float> weeklyWorkload;
	private HashMap<Employee, Float> progressByEmployee;
	private ArrayList<Employee> assignedEmployees;
	private final String id;
	private boolean hasparent;

	public Activity(String id, String name) {
		this.hasparent = false;
		this.id = id;
		undeprecate();
		this.name = name;
		this.parentProject = null;
		this.weeklyWorkload = new HashMap<Week, Float>();
		this.assignedEmployees = new ArrayList<Employee>();
		this.progressByEmployee = new HashMap<Employee, Float>();
	}

	public Activity(String id, String name, Project parent) {
		this.hasparent = true;
		this.id = id;
		undeprecate();
		this.name = name;
		this.parentProject = parent;
		this.weeklyWorkload = new HashMap<Week, Float>();
		this.assignedEmployees = new ArrayList<Employee>();
		this.progressByEmployee = new HashMap<Employee, Float>();
	}

	public void setParent(Project parentProject) {
		checkDeprecateAndDoNothing();
		if(this.hasparent) { } //FIXME: Exception her 
		this.parentProject = parentProject;
	}

	public void setName(String newName) {
		checkDeprecateAndDoNothing();
		this.name = newName;
	}
	
	public String getID() {
		return id;
	}

	public String getName() {
		if(isDeprecated())
			return name + " [DEPRECATED]";
		return name;
	}

	public Project getParentProject() {
		return parentProject;
	}

	public void assignEmployee(Employee e) throws ActivityException {
		checkDeprecateAndDoNothing();
		assignedEmployees.add(e);
	}

	public ArrayList<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}

	public void addWeek(Week week, float hours) {
		checkDeprecateAndDoNothing();
		weeklyWorkload.put(week, hours);
	}

	public void removeWeek(Week week) {
		checkDeprecateAndDoNothing();
		weeklyWorkload.remove(week);
	}

	public float getHoursForWeek(Week week) throws ActivityException {
		if(!weeklyWorkload.containsKey(week))
			throw new ActivityException("No hours assigned in this week");
		return weeklyWorkload.get(week);
	}
	
	public float getWorkloadPerEmployeeForWeek(Week week) throws ActivityException {
		return getHoursForWeek(week) / numberOfNonDeprecatedEmployees();
	}

	public void removeEmployee(Employee employee) throws ActivityException {
		checkRemoveEmployee(employee);
		assignedEmployees.remove(employee);
	}

	public float getProgress() {
		float progress = 0;
		for(Float p : progressByEmployee.values()) progress += p;
		return progress;
	}

	public int numberOfNonDeprecatedEmployees() {
		int num = 0;
		for(Employee e : getAssignedEmployees()) if(!e.isDeprecated()) num++;
		return num;
	}

	public int numberOfEmployees() {
		return assignedEmployees.size();
	}
	
	public HashMap<Employee,Float> getProgressByEmployee() {
		return progressByEmployee;
	}
	
	public float getWorkload() {
		float workload = 0;
		for (Float load : weeklyWorkload.values())
			workload += load;
		return workload;
	}

	public void registerProgressFromEmployee(float hours, Employee employee) {
		checkDeprecateAndDoNothing();
		if (progressByEmployee.containsKey(employee)) {
			hours += progressByEmployee.get(employee);
		}
		progressByEmployee.put(employee, hours);
	}

	public String toString() {
		return getName();
	}
	
	public boolean containsEmployee(Employee e) {
		return assignedEmployees.contains(e);
	}

	public void checkAssignEmployee(Employee e) throws ActivityException {
		checkDeprecateAndDoNothing();
		if(containsEmployee(e))
			if(e.isAssignedToActivityAsEmployee(this))
				throw new ActivityException("Already contains this employee");
	}

	public void checkAssignEmployeeAsAssistant(Employee e) throws ActivityException {
		checkDeprecateAndDoNothing();
		if(containsEmployee(e))
			if(e.isAssignedToActivityAsAssistant(this))
				throw new ActivityException("Already contains this employee");
	}

	public void checkRemoveEmployee(Employee e) throws ActivityException {
		checkDeprecateAndDoNothing();
		if(!containsEmployee(e))
			throw new ActivityException("Does not contain employee.");
	}

	public int getStartWeek() {
		int startweek = Integer.MAX_VALUE;
		for(Week week : weeklyWorkload.keySet()) startweek = Math.min(startweek, week.getIndex());
		return startweek;
	}

	public int getEndWeek() {
		int endweek = Integer.MIN_VALUE;
		for(Week week : weeklyWorkload.keySet()) endweek = Math.max(endweek, week.getIndex());
		return endweek;
	}
}
