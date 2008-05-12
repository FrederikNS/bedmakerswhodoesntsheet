package projectplanner;

import java.util.ArrayList;

public class Project extends Freezeable {
	private String name;
	private String id;
	private ArrayList<Activity> activities;
	private ArrayList<Employee> assignedEmployees;
	private Employee leader;
	boolean frozen;
	int startweek;
	int endweek;
	
	public Project(String id, String name) {
		startweek = 0;
		endweek = Integer.MAX_VALUE;
		unfreeze();
		leader = null;
		activities = new ArrayList<Activity>();
		assignedEmployees = new ArrayList<Employee>(); 
		this.name = name;
		this.id = id;
	}

	public Project(String id, String name, Employee leader) {
		startweek = 0;
		endweek = Integer.MAX_VALUE;
		unfreeze();
		activities = new ArrayList<Activity>();
		assignedEmployees = new ArrayList<Employee>();
		this.name = name;
		this.leader = leader;
		this.id = id;
	}
	
	public void setName(String name) throws FrozenException {
		checkFreeze();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public int getStartWeek() {
		return startweek;
	}
	
	public int getEndWeek() {
		return endweek;
	}
	
	public Employee getLeader() {
		return leader;
	}
	
	public void setStartWeek(int v) throws FrozenException {
		checkFreeze();
		startweek = v;
	}
	
	public void setEndWeek(int v) throws FrozenException {
		checkFreeze();
		endweek = v;
	}
	
	public void assignLeader(Employee projectLeader) throws FrozenException  {
		checkFreeze();
		this.leader = projectLeader;
	}
	
	public void addActivity(Activity activity) throws FrozenException, ProjectException {
		checkFreeze();
		if(containsActivity(activity))
			throw new ProjectException("Activity already in project");
		activity.setParent(this);
		activities.add(activity);
	}

	public void freezeActivity(Activity activity) throws FrozenException {
		checkFreeze();
		activity.freeze();
	}
	
	public boolean containsEmployee(Employee e) {
		return assignedEmployees.contains(e);
	}
	
	public ArrayList<Employee> getEmployees() {
		return assignedEmployees;
	}
	
	public void addEmployee(Employee e) throws FrozenException, ProjectException {
		checkFreeze();
		if(containsEmployee(e))
			throw new ProjectException("Already contains employee e");
		assignedEmployees.add(e);
	}

	public void removeEmployee(Employee e) throws FrozenException, ProjectException {
		checkFreeze();
		if(!containsEmployee(e))
			throw new ProjectException("Does not contain employee e");
		assignedEmployees.remove(e);
	}
	
	public boolean containsActivity(Activity activity) {
		return activities.contains(activity);
	}

	public ArrayList<Activity> getActivities() {
		return activities;
	}
	
	public float getWorkload() {
		float workload = 0;
		for(Activity a : activities)
			if(!a.isFrozen())
				workload+=a.getWorkload();
		return workload;
	}

	public float getProgress() {
		float progress = 0;
		for(Activity a : activities)
			if(!a.isFrozen())
				progress+=a.getProgress();
		return progress;
	}

	public String toString() {
		String out = name;
		out += ". Start week: " + startweek + ", end week: " + endweek + ".";
		if(frozen) out += " [FROZEN]";
		out += "\n* Leader: ";
		if(leader==null) out += "none";
		else out += leader;
		out += "\n* Activities (" + activities.size() +"):\n";
		for(Activity a : activities) {
			out += "\t" + a + "\n";
		}
		return out;
	}

	public void checkAssignEmployee(Employee e) {
		// TODO Auto-generated method stub
		
	}

	public void checkRemoveEmployee(Employee e) {
		// TODO Auto-generated method stub
		
	}
}
