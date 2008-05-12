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

	public void assignEmployee(Employee e) throws FrozenException {
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

	public float getHoursForWeek(Week week) {
		 // FIXME: Burde den throwe exception?
		if(weeklyWorkload.containsKey(week)) return 0;
		return weeklyWorkload.get(week);
	}

	public void removeEmployee(Employee employee) throws FrozenException {
		checkFreeze();
		assignedEmployees.remove(employee);
	}

	public float getProgress() {
		float progress = 0;
		//for (Employee e : progressByEmployee.keySet())
		//	progress += e.getProgresInActivity(this);
		for(Float p : progressByEmployee.values()) progress += p;
		return progress;
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
}
