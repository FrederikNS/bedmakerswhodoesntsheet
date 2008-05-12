package projectplanner;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity extends Freezeable{
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
		unfreeze();
		this.name = name;
		this.parentProject = null;
		this.weeklyWorkload = new HashMap<Week, Float>();
		this.assignedEmployees = new ArrayList<Employee>();
		this.progressByEmployee = new HashMap<Employee, Float>();
	}

	public Activity(String id, String name, Project parent) {
		this.hasparent = true;
		this.id = id;
		unfreeze();
		this.name = name;
		this.parentProject = parent;
		this.weeklyWorkload = new HashMap<Week, Float>();
		this.assignedEmployees = new ArrayList<Employee>();
		this.progressByEmployee = new HashMap<Employee, Float>();
	}

	public void setParent(Project parentProject) throws FrozenException {
		checkFreeze();
		if(this.hasparent) { } //FIXME: Exception her 
		this.parentProject = parentProject;
	}

	public void setName(String newName) throws FrozenException {
		checkFreeze();
		this.name = newName;
	}

	public void checkFreeze() throws FrozenException {
		if(isFrozen()) throw new FrozenException(this);
		if(hasparent) {
			parentProject.checkFreeze();
		}
	}	

	
	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Project getParentProject() {
		return parentProject;
	}

	public void assignEmployee(Employee e) throws FrozenException, ActivityException {
		checkFreeze();
		assignedEmployees.add(e);
	}

	public ArrayList<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}

	public void addWeek(Week week, float hours) throws FrozenException {
		checkFreeze();
		weeklyWorkload.put(week, hours);
	}

	public void removeWeek(Week week) throws FrozenException {
		checkFreeze();
		weeklyWorkload.remove(week);
	}

	public float getHoursForWeek(Week week) throws ActivityException {
		if(!weeklyWorkload.containsKey(week))
			throw new ActivityException("No hours assigned in this week");
		return weeklyWorkload.get(week);
	}
	
	public float getWorkloadPerEmployeeForWeek(Week week) throws ActivityException {
		return getHoursForWeek(week) / numberOfNonFrozenEmployees();
	}

	public void removeEmployee(Employee employee) throws FrozenException, ActivityException {
		checkRemoveEmployee(employee);
		assignedEmployees.remove(employee);
	}

	public float getProgress() {
		float progress = 0;
		//for (Employee e : progressByEmployee.keySet())
		//	progress += e.getProgresInActivity(this);
		for(Float p : progressByEmployee.values()) progress += p;
		return progress;
	}

	public int numberOfNonFrozenEmployees() {
		int num = 0;
		for(Employee e : getAssignedEmployees()) if(!e.isFrozen()) num++;
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

	public void registerProgressFromEmployee(float hours, Employee employee)
			throws FrozenException {
		checkFreeze();
		if (progressByEmployee.containsKey(employee)) {
			hours += progressByEmployee.get(employee);
		}
		progressByEmployee.put(employee, hours);
	}

	public String toString() {
		String out = name;
		if(isFrozen()) out+=" [FROZEN]";
		return out;
	}
	
	public boolean containsEmployee(Employee e) {
		return assignedEmployees.contains(e);
	}

	public void checkAssignEmployee(Employee e) throws ActivityException, FrozenException {
		checkFreeze();
		if(containsEmployee(e))
			if(e.isAssignedToActivityAsEmployee(this))
				throw new ActivityException("Already contains this employee");
	}

	public void checkAssignEmployeeAsAssistant(Employee e) throws FrozenException, ActivityException {
		checkFreeze();
		if(containsEmployee(e))
			if(e.isAssignedToActivityAsAssistant(this))
				throw new ActivityException("Already contains this employee");
	}

	public void checkRemoveEmployee(Employee e) throws FrozenException, ActivityException {
		checkFreeze();
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
