package projectplanner;

import java.util.ArrayList;

public class Project extends Deprecateable {
	private String name;
	private String id;
	private ArrayList<Activity> activities;
	private ArrayList<Employee> assignedEmployees;
	private Employee leader;
	int startweek;
	int endweek;
	
	public Project(String id, String name) {
		startweek = 0;
		endweek = Integer.MAX_VALUE;
		undeprecate();
		leader = null;
		activities = new ArrayList<Activity>();
		assignedEmployees = new ArrayList<Employee>(); 
		this.name = name;
		this.id = id;
	}

	public Project(String id, String name, Employee leader) {
		startweek = 0;
		endweek = Integer.MAX_VALUE;
		undeprecate();
		activities = new ArrayList<Activity>();
		assignedEmployees = new ArrayList<Employee>();
		this.name = name;
		this.leader = leader;
		this.id = id;
	}
	
	public void setName(String name) {
		checkDeprecateAndDoNothing();
		this.name = name;
	}
	
	public String getName() {
		if(isDeprecated())
			return name + " [DEPRECATED]";
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
	
	public void setStartWeek(int v) {
		checkDeprecateAndDoNothing();
		startweek = v;
	}
	
	public void setEndWeek(int v) {
		checkDeprecateAndDoNothing();
		endweek = v;
	}
	
	public void assignLeader(Employee projectLeader) {
		checkDeprecateAndDoNothing();
		this.leader = projectLeader;
	}
	
	public void addActivity(Activity activity) throws ProjectException {
		checkDeprecateAndDoNothing();
		if(containsActivity(activity))
			throw new ProjectException("Activity already in project");
		activity.setParent(this);
		activities.add(activity);
	}

	public void freezeActivity(Activity activity) {
		checkDeprecateAndDoNothing();
		activity.deprecate();
	}
	
	public boolean containsEmployee(Employee e) {
		return assignedEmployees.contains(e);
	}
	
	public ArrayList<Employee> getEmployees() {
		return assignedEmployees;
	}
	
	public void addEmployee(Employee e) throws ProjectException {
		checkDeprecateAndDoNothing();
		if(containsEmployee(e))
			throw new ProjectException("Already contains employee e");
		assignedEmployees.add(e);
	}

	public void removeEmployee(Employee e) throws ProjectException {
		checkDeprecateAndDoNothing();
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
			if(!a.isDeprecated())
				workload+=a.getWorkload();
		return workload;
	}

	public float getProgress() {
		float progress = 0;
		for(Activity a : activities)
			if(!a.isDeprecated())
				progress+=a.getProgress();
		return progress;
	}

	public String toString() {
		return getName();
	}

	public void checkAssignEmployee(Employee e) throws ProjectException {
		if(containsEmployee(e)) throw new ProjectException("Already contains employee.");
	}

	public void checkRemoveEmployee(Employee e) throws ProjectException {
		if(!containsEmployee(e)) throw new ProjectException("Does not contain employee.");
	}
}
