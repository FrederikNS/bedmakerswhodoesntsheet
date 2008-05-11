package projectplanner;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity {
	private String name;
	private Project parentProject;
	private HashMap<Week, Float> weeklyWorkload;
	private ArrayList<Employee> assignedEmployees;
	private ArrayList<Employee> assistants;
	private HashMap<Employee, Float> progressByEmployee;
	private final String id;
	private boolean frozen;
	private boolean hasparent;

	public Activity(String id, String name) {
		hasparent = false;
		this.id = id;
		frozen = false;
		this.name = name;
		this.parentProject = null;
		weeklyWorkload = new HashMap<Week, Float>();
		assistants = new ArrayList<Employee>();
		assignedEmployees = new ArrayList<Employee>();
	}

	public Activity(String id, String name, Project parent) {
		hasparent = true;
		this.id = id;
		frozen = false;
		this.name = name;
		this.parentProject = parent;
		weeklyWorkload = new HashMap<Week, Float>();
		assistants = new ArrayList<Employee>();
		assignedEmployees = new ArrayList<Employee>();
		progressByEmployee = new HashMap<Employee, Float>();
	}

	public void setParent(Project parentProject) throws FrozenException {
		checkFreeze();
		if(hasparent) { } //FIXME: Exception her 
		this.parentProject = parentProject;
	}

	public void setName(String newName) throws FrozenException {
		checkFreeze();
		this.name = newName;
	}
	
	public void freeze() throws FrozenException {
		checkFreeze();
		frozen = true;
	}

	public void unfreeze() {
		frozen = false;
	}

	public void checkFreeze() throws FrozenException {
		if (frozen)
			throw new FrozenException(this);
	}	

	public boolean isFrozen() {
		return frozen;
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

	public void assignEmployeeAsAssistant(Employee e) throws FrozenException {
		checkFreeze();
		if (!assignedEmployees.contains(e))
			assistants.add(e);
	}

	public ArrayList<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}

	public ArrayList<Employee> getAssistants() {
		return assistants;
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

	public void removeAssistant(Employee employee) throws FrozenException {
		checkFreeze();
		assistants.remove(employee);
	}

	public void removeEmployee(Employee employee) throws FrozenException {
		checkFreeze();
		assignedEmployees.remove(employee);
	}

	public float getProgress() {
		float progress = 0;
		for (Employee e : progressByEmployee.keySet())
			progress += e.getProgresInActivity(this);
		return progress;
	}
	
	public HashMap<Employee,Float> getProgressByEmployee() {
		return progressByEmployee;
	}
	
	public float getWorkload() {
		float workload = 0;
		for (Week w : weeklyWorkload.keySet())
			workload += getHoursForWeek(w);
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
		if(frozen) out+=" [FROZEN]";
		return out;
	}
}
