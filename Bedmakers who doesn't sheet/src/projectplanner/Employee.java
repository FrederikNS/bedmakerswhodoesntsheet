package projectplanner;

import java.util.ArrayList;
import java.util.HashMap;

//TODO: Lav enum's til Exceptions'ne i stedet for streng-beskeder

public class Employee {

	private String name;
	private String initials;
	private HashMap<Activity,Boolean> assignedActivities;
	private ArrayList<Project> assignedProjects;
	private ArrayList<Project> assignedProjectsLead;
	//private ArrayList<Activity> assistedActivities;
	private HashMap<Activity,Float> progress;
	private boolean frozen;
	//ArrayList<Project> assistingProject;

	public Employee(String name, String initials) {
		frozen = false;
		this.name = name;
		this.initials = initials;
		assignedActivities = new HashMap<Activity,Boolean>();
		assignedProjects = new ArrayList<Project>();
		assignedProjectsLead = new ArrayList<Project>();
		//assistedActivities = new ArrayList<Activity>();
	}

	public static String generateInitialsFromName(String name) throws Exception{
		String nameSplitted[] = name.split(" ");
		String nameInits = "";

		nameInits+=nameSplitted[0].substring(0, 2);
		nameInits+=nameSplitted[nameSplitted.length-1].substring(0, 2);

		return nameInits;
	}

	public boolean isFrozen() {
		return frozen;
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
	
	public String getInitials(){
		return initials;
	}

	public void assignToProject(Project project) throws EmployeeException, FrozenException {
		checkFreeze();
		if(assignedProjects.contains(project)){
			throw new EmployeeException("Already assigned to project");
		}
		assignedProjects.add(project);
		for(Activity a : assignedActivities.keySet()) {
			if(a.getParentProject()==project) {
				relieveFromAssistance(a);
				assignToActivity(a);
			}
		}
	}
	
	public boolean isLeaderOfProject(Project p) {
		return assignedProjectsLead.contains(p);
	}
	
	public boolean isAssignedToProject(Project p) {
		return assignedProjects.contains(p);
	}
	
	public boolean isAssignedToActivity(Activity a) {
		return assignedActivities.keySet().contains(a);
	}

	public boolean isAssignedToActivityAsEmployee(Activity a) {
		if(!isAssignedToActivity(a)) return false;
		return !assignedActivities.get(a);
	}

	public boolean isAssignedToActivityAsAssistant(Activity a) {
		if(!isAssignedToActivity(a)) return false;
		return assignedActivities.get(a);
	}

	public void assignProjectLead(Project project) throws EmployeeException, FrozenException {
		checkFreeze();
		if(isLeaderOfProject(project))
			throw new EmployeeException("Already assigned as project leader");

		if(!isAssignedToProject(project)) 
			assignToProject(project);
		
		assignedProjectsLead.add(project);
	}

	public void assignToActivity(Activity activity) throws EmployeeException, FrozenException {
		checkFreeze();
		if(isAssignedToActivityAsEmployee(activity))
			throw new EmployeeException("Already assigned to activity");

		if(isAssignedToActivityAsAssistant(activity))
			throw new EmployeeException("Already assigned to activity as assistant");

		if(!isAssignedToProject(activity.getParentProject()))
			throw new EmployeeException("Not assigned to project");

		assignedActivities.put(activity, false);
	}

	public void relieveFromActivity(Activity activity) throws EmployeeException, FrozenException {
		checkFreeze();
		if(!isAssignedToActivity(activity))
			throw new EmployeeException("Not assigned to activity");

		assignedActivities.remove(activity);
	}
	
	public void assistActivity(Activity activity) throws EmployeeException, FrozenException {
		checkFreeze();
		if(isAssignedToActivityAsEmployee(activity)){
			throw new EmployeeException("Already assigned to activity as employee");
		}
		if(isAssignedToActivityAsAssistant(activity)) {
			throw new EmployeeException("Already assisting project");
		}
		assignedActivities.put(activity,true);
	}

	public void relieveFromAssistance(Activity activity) throws EmployeeException, FrozenException {
		checkFreeze();
		if(!isAssignedToActivityAsAssistant(activity)){
			throw new EmployeeException("Not assigned to activity as assistant");
		}
		assignedActivities.remove(activity);
	}	
	
	public void registerProgressInActivity(float hours, Activity a) throws FrozenException, EmployeeException {
		checkFreeze();
		if(!isAssignedToActivity(a)) {
			throw new EmployeeException("Not assigned/assisting project");
		}
		if(progress.containsKey(a)) {
			hours += progress.get(a);
		}
		progress.put(a, hours);
	}
	
	public float getProgresInActivity(Activity a) {
		return progress.get(a);
	}
	
	public String toString() {
		String out = initials + ", " + name;
		if(frozen) out += " [FROZEN]";
		return out;
	}

	public String getName() {
		return name;
	}

	public void relieveFromProject(Project project, boolean reassignasassistant) throws EmployeeException, FrozenException {
		checkFreeze();
		if(!assignedProjects.contains(project)) {
			throw new EmployeeException("Not assigned to project");
		}
		assignedProjects.remove(project);
		for(Activity activity : project.getActivities()) {
			if(isAssignedToActivity(activity)) {
				relieveFromActivity(activity);
				if(reassignasassistant) {
					assistActivity(activity);
				}
			}
		}
	}
	
	public ArrayList<Activity> getAssignedActivities() {
		//Late hack, works fine but is unoptimal
		return new ArrayList<Activity>(assignedActivities.keySet());
	}
	/*
	public ArrayList<Activity> getAssistedActivities() {
		return assistedActivities;
	}*/
	
	public ArrayList<Project> getAssignedProjects() {
		return assignedProjects;
	}
	
	public ArrayList<Project> getProjectsBeingLead() {
		return assignedProjectsLead;
	}
	
	public HashMap<Activity, Float> getWorkDone() {
		return progress;
	}
}