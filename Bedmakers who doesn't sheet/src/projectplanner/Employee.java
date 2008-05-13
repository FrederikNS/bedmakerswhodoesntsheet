package projectplanner;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * PotKortet assignedActivities er en liste over tildelte aktiviteter.
 * Den value, der hører til hver key, bestemmer om den er assistent eller ægte ansat.
 * False: Ansat
 * True: Assistent
 */

public class Employee extends Deprecateable{

	private String name;
	private String initials;
	private HashMap<Activity,Boolean> assignedActivities;
	private ArrayList<Project> assignedProjects;
	private ArrayList<Project> assignedProjectsLead;
	private HashMap<Activity,Float> progress;

	public Employee(String name, String initials) {
		undeprecate();
		this.name = name;
		this.initials = initials;
		assignedActivities = new HashMap<Activity,Boolean>();
		assignedProjects = new ArrayList<Project>();
		assignedProjectsLead = new ArrayList<Project>();
		progress = new HashMap<Activity,Float>();
	}

	public static String generateInitialsFromName(String name) throws EmployeeException{
		try {
			String nameSplitted[] = name.split(" ");
			String nameInits = "";
	
			nameInits+=nameSplitted[0].substring(0, 2);
			nameInits+=nameSplitted[nameSplitted.length-1].substring(0, 2);
	
			return nameInits;
		} catch (Exception e) {
			throw new EmployeeException("Error creating initials from name: " + name + ".");
		}
	}

	public String getInitials(){
		return initials;
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

	public void assignToProject(Project project) throws EmployeeException {
		checkAssignToProject(project);
		assignedProjects.add(project);
		for(Activity a : assignedActivities.keySet()) {
			if(a.getParentProject()==project) {
				relieveFromAssistance(a);
				assignToActivity(a);
			}
		}
	}

	public void relieveFromProject(Project project, boolean reassignasassistant) throws EmployeeException {
		checkRelieveFromProject(project);
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

	public void assignProjectLead(Project project) throws EmployeeException {
		if(isLeaderOfProject(project))
			throw new EmployeeException("Already assigned as project leader");

		if(!isAssignedToProject(project)) 
			assignToProject(project);
		
		assignedProjectsLead.add(project);
	}
	
	
	public void removeFromProjectLead(Project project) throws EmployeeException{
		if(!isLeaderOfProject(project))
			throw new EmployeeException("Not leader of project");
		assignedProjectsLead.remove(project);
	}

	public void assignToActivity(Activity activity) throws EmployeeException {
		checkAssignToActivity(activity);
		assignedActivities.put(activity, false);
	}

	public void relieveFromActivity(Activity activity) throws EmployeeException {
		assignedActivities.remove(activity);
	}
	
	public void assistActivity(Activity activity) throws EmployeeException {
		checkAssistActivity(activity);
		assignedActivities.put(activity,true);
	}

	public void relieveFromAssistance(Activity activity) throws EmployeeException {
		checkRelieveFromAssistance(activity);
		assignedActivities.remove(activity);
	}	
	
	public void registerProgressInActivity(float hours, Activity a) throws EmployeeException {
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
		return getName();
	}

	public String getName() {
		if(isDeprecated())
			return name + " [DEPRECATED]";
		return name;
	}
	
	public HashMap<Activity,Boolean> getAssignedActivities() {
		return assignedActivities;
	}

	public ArrayList<Project> getAssignedProjects() {
		return assignedProjects;
	}
	
	public ArrayList<Project> getProjectsBeingLead() {
		return assignedProjectsLead;
	}
	
	public HashMap<Activity, Float> getWorkDone() {
		return progress;
	}

	public void checkAssignToProject(Project project) throws EmployeeException {
		if(assignedProjects.contains(project)){
			throw new EmployeeException("Already assigned to project");
		}
	}

	public void checkRelieveFromProject(Project project) throws EmployeeException {
		if(!assignedProjects.contains(project))
			throw new EmployeeException("Not assigned to project");
		if(isLeaderOfProject(project))
			throw new EmployeeException("Assigned to project as leader - unassign first");
	}

	public void checkAssignToActivity(Activity activity) throws EmployeeException {
		if(isAssignedToActivityAsEmployee(activity))
			throw new EmployeeException("Already assigned to activity");

		if(isAssignedToActivityAsAssistant(activity))
			throw new EmployeeException("Already assigned to activity as assistant");

		if(!isAssignedToProject(activity.getParentProject()))
			throw new EmployeeException("Not assigned to project");		
	}

	public void checkRelieveFromActivity(Activity activity) throws EmployeeException {
		if(!isAssignedToActivity(activity))
			throw new EmployeeException("Not assigned to activity");
	}

	public void checkAssistActivity(Activity activity) throws EmployeeException {
		if(isAssignedToProject(activity.getParentProject())) {
			throw new EmployeeException("Assigned to project, so cannot be assigned as assitant");
		}
		if(isAssignedToActivityAsEmployee(activity)){
			throw new EmployeeException("Already assigned to activity as employee");
		}
		if(isAssignedToActivityAsAssistant(activity)) {
			throw new EmployeeException("Already assisting project");
		}		
	}

	public void checkRelieveFromAssistance(Activity activity) throws EmployeeException {
		if(!isAssignedToActivityAsAssistant(activity)){
			throw new EmployeeException("Not assigned to activity as assistant");
		}
	}
}