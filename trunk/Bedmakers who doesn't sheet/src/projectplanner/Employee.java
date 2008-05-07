package projectplanner;

import java.util.ArrayList;
import java.util.HashMap;

//TODO: Lav enum's til Exceptions'ne i stedet for streng-beskeder

public class Employee {

	private String name;
	private String initials;
	private ArrayList<Activity> assignedActivities;
	private ArrayList<Project> assignedProjects;
	private ArrayList<Project> assignedProjectsLead;
	private ArrayList<Activity> assistedActivities;
	private HashMap<Activity,Float> progress;
	private boolean frozen;
	//ArrayList<Project> assistingProject;

	public Employee(String name, String initials) {
		frozen = false;
		this.name = name;
		this.initials = initials;
		assignedActivities = new ArrayList<Activity>();
		assignedProjects = new ArrayList<Project>();
		assignedProjectsLead = new ArrayList<Project>();
	}

	public static String generateInitialsFromName(String name) throws Exception{
		String firstInitial = name.substring(0,2);
		int secondInitialIndex = name.lastIndexOf(" ") +1;
		String secondInitial = name.substring(secondInitialIndex,secondInitialIndex+2);
		String initials = firstInitial+secondInitial;
		//System.out.println(initials);
		return initials;
	}
	
	public void freeze() throws FrozenException {
		checkFreeze();
		frozen = true;
	}

	public void unfreeze() {
		frozen = false;
	}

	private void checkFreeze() throws FrozenException {
		if (frozen)
			throw new FrozenException(this);
	}	
	
	//public String CreateInitialsFromName() throws Exception{
	//	return CreateInitialsFromName(name);
	//}

	public String CreateInit() {
		String nameSplitted[] = name.split(" ");
		String nameInits = null;

		nameInits+=nameSplitted[0];
		nameInits+=nameSplitted[nameSplitted.length-1];

		return nameInits;
	}

	public String getInitials(){
		return initials;
	}

	public void assignProject(Project project) throws EmployeeException, FrozenException {
		checkFreeze();
		if(assignedProjects.contains(project)){
			throw new EmployeeException("Already assigned to project");
		} else {
			assignedProjects.add(project);	
		}
	}

	public void assignProjectLead(Project project) throws EmployeeException, FrozenException {
		checkFreeze();
		if(assignedProjectsLead.contains(project)) {
			throw new EmployeeException("Already assigned as project leader");
		} else {
			if(!assignedProjects.contains(project)) {
				assignProject(project);
			} else {

			}
			assignedProjectsLead.add(project);
		}
	}

	public void assignToActivity(Activity activity) throws EmployeeException, FrozenException {
		checkFreeze();
		if(assignedActivities.contains(activity)){
			throw new EmployeeException("Already assigned to activity");
		} else {
			assignedActivities.add(activity);
		}
	}

	public void relieveFromActivity(Activity activity) throws EmployeeException, FrozenException {
		checkFreeze();
		if(assignedProjects.contains(activity)){
			activity.removeEmployee(this);
			assignedActivities.remove(activity);
		} else {
			throw new EmployeeException("Not assigned to activity");
		}
	}
	
//	Ikke muligt, så vidt jeg ved - Jacob.
//	public void assistProject(Project project) throws EmployeeException {
//		if(assistingProject.contains(project)){
//			throw new EmployeeException("Already assisting project");
//		} else if(assignedProjects.contains(project)){
//			throw new EmployeeException("Already assigned to project");
//		} else {
//			assistingProject.add(project);	
//		}
//	}

	public void assistActivity(Activity activity) throws EmployeeException, FrozenException {
		checkFreeze();
		if(assignedProjects.contains(activity)){
			throw new EmployeeException("Already assigned to project");
		} else {
			assistedActivities.add(activity);
			activity.assignEmployeeAsAssistant(this);
		}
	}

	public void relieveFromAssistance(Activity activity) throws EmployeeException, FrozenException {
		checkFreeze();
		if(assistedActivities.contains(activity)){
			activity.removeAssistant(this);
			assistedActivities.remove(activity);
		} else {
			throw new EmployeeException("Not assigned to activity");
		}
	}	
	
	public void registerProgressInActivity(float hours, Activity a) throws FrozenException {
		checkFreeze();
		if(progress.containsKey(a)) {
			hours += progress.get(a);
		}
		progress.put(a, hours);
		a.registerProgressFromEmployee(hours, this);
	}
	
	public float getProgresInActivity(Activity a) {
		return progress.get(a);
	}
	
	public String toString() {
		String out = initials + ", " + name;
		if(frozen) out += " [FROZEN]";
		return out;
	}
}