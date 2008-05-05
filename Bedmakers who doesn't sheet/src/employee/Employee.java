package employee;

import java.util.ArrayList;

import activity.Activity;
import activity.Project;

public class Employee {

	String name;
	String initials;
	ArrayList<Activity> assignedActivities;
	ArrayList<Project> assignedProjects;
	ArrayList<Project> assignedProjectsLead;

	public Employee(String name, String initials) {
		this.name = name;
		this.initials = initials;
		assignedActivities = new ArrayList<Activity>();
		assignedProjects = new ArrayList<Project>();
		assignedProjectsLead = new ArrayList<Project>();
	}

	public String CreateInitialsFromName() throws Exception{
		String firstInitial = name.substring(0,2);
		int secondInitialIndex = name.lastIndexOf(" ") +1;
		String secondInitial = name.substring(secondInitialIndex,secondInitialIndex+2);
		initials = firstInitial+secondInitial;
		System.out.println(initials);
		return initials;
	}

	public String CreateInit() {
		String nameSplitted[] = name.split(" ");
		String nameInits = null;
		if(nameSplitted[0].length() > 2 && nameSplitted[nameSplitted.length-1].length() > 2){
			nameInits+=nameSplitted[0];
			nameInits+=nameSplitted[nameSplitted.length-1];
			return nameInits;
		}
		return null;
	}

	public String getInitials(){
		return initials;
	}

	public void assignProject(Project project) {
		assignedProjects.add(project);
	}

	public void assignProjectLead(Project project) {
		if(!assignedProjects.contains(project)) assignProject(project);
		assignedProjectsLead.add(project);
	}

	public void assignActivity(Activity activity) {
		assignedActivities.add(activity);
	}

	public void relieveFromActivity(Activity activity) {
		assignedActivities.remove(activity);
	}

}
