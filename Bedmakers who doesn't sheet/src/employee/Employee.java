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
	ArrayList<Activity> assistingActivity;
	ArrayList<Project> assistingProject;

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

		nameInits+=nameSplitted[0];
		nameInits+=nameSplitted[nameSplitted.length-1];

		return nameInits;
	}

	public String getInitials(){
		return initials;
	}

	public void assignProject(Project project) {
		if(assignedProjects.contains(project)){
			throw
		} else {
			assignedProjects.add(project);	
		}
	}

	public void assignProjectLead(Project project) throws AlreadyAssignedToProjectAsLeaderException{
		if(assignedProjectsLead.contains(project));{
			throw new Exception();
		} else {
			if(!assignedProjects.contains(project)) {
				assignProject(project);
			} else {

			}
			assignedProjectsLead.add(project);
		}
	}

	public void assignActivity(Activity activity) {
		if(assignedActivities.contains(activity)){
			throw new AlreadyAssignedToActivityException();
		} else {
			assignedActivities.add(activity);
		}
	}

	public void relieveFromActivity(Activity activity) throws NotAssignedToActivityException {
		if(assignedProjects.contains(activity)){
			assignedActivities.remove(activity);
		} else {
			throw new NotAssignedToActivityException();
		}
	}

	public void assistingProject(Project project) throws AlreadyAssistingProjectException {
		if(assistingProject.contains(project)){
			throw new AlreadyAssistingProjectException();
			
		} else {
			if(assignedProjects.contains(project)){
				throw new AlreadyAssignedToProjectException();	
			} else {
				assistingProject.add(project);	
			}
		}
	}

	public void assistingActivity(Activity activity){
		if(assignedProjects.contains(activity)){
			throw new AlreadyAssignedToProjectsException();
		} else {
			assistingActivity.add(activity);
		}
	}
}

