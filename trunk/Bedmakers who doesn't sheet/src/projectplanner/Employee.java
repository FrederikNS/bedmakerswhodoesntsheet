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
		//TODO: Check om den ansatte er assistent i nogle af projects activities og konverter.
		//if(assistingProject.contains(project)){
		//	throw new EmployeeException("Already assisting project");
		//}
		if(assignedProjects.contains(project)){
			throw new EmployeeException("Already assigned to project");
		}
		assignedProjects.add(project);
	}

	public void assignProjectLead(Project project) throws EmployeeException, FrozenException {
		checkFreeze();
		if(assignedProjectsLead.contains(project)) {
			throw new EmployeeException("Already assigned as project leader");
		}
		if(!assignedProjects.contains(project)) {
			assignToProject(project);
		}
		assignedProjectsLead.add(project);
	}

	public void assignToActivity(Activity activity) throws EmployeeException, FrozenException {
		checkFreeze();
		if(assignedActivities.contains(activity)){
			throw new EmployeeException("Already assigned to activity");
		}
		if(!assignedProjects.contains(activity.getParentProject())) {
			throw new EmployeeException("Not assigned to project");
		}
		if(assistedActivities.contains(activity)) {
			throw new EmployeeException("Already assigned to activity as assistant");
		}
		assignedActivities.add(activity);
	}

	public void relieveFromActivity(Activity activity) throws EmployeeException, FrozenException {
		checkFreeze();
		if(!assignedActivities.contains(activity)){
			throw new EmployeeException("Not assigned to activity");
		}
		assignedActivities.remove(activity);
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
		}
		if(assistedActivities.contains(activity)) {
			throw new EmployeeException("Already assisting project");
		}
		assistedActivities.add(activity);
	}

	public void relieveFromAssistance(Activity activity) throws EmployeeException, FrozenException {
		checkFreeze();
		if(!assistedActivities.contains(activity)){
			throw new EmployeeException("Not assigned to activity as assistant");
		}
		assistedActivities.remove(activity);
	}	
	
	public void registerProgressInActivity(float hours, Activity a) throws FrozenException, EmployeeException {
		checkFreeze();
		if(!assignedProjects.contains(a) && !assistedActivities.contains(a)) {
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
			if(assignedActivities.contains(activity)) {
				relieveFromActivity(activity);
				if(reassignasassistant) {
					assistActivity(activity);
				}
			}
		}
	}
}