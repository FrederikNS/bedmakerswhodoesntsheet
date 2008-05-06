package employee;

import java.util.ArrayList;

import exceptions.EmployeeException;

import activity.Activity;
import activity.Project;

//TODO: Lav enum's til Exceptions'ne i stedet for streng-beskeder

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

	public static String generateInitialsFromName(name) throws Exception{
		String firstInitial = name.substring(0,2);
		int secondInitialIndex = name.lastIndexOf(" ") +1;
		String secondInitial = name.substring(secondInitialIndex,secondInitialIndex+2);
		initials = firstInitial+secondInitial;
		System.out.println(initials);
		return initials;
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

	public void assignProject(Project project) throws EmployeeException {
		if(assignedProjects.contains(project)){
			throw new EmployeeException("Already assigned to project");
		} else {
			assignedProjects.add(project);	
		}
	}

	public void assignProjectLead(Project project) throws EmployeeException {
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

	public void assignActivity(Activity activity) throws EmployeeException {
		if(assignedActivities.contains(activity)){
			throw new EmployeeException("Already assigned to activity");
		} else {
			assignedActivities.add(activity);
		}
	}

	public void relieveFromActivity(Activity activity) throws EmployeeException {
		if(assignedProjects.contains(activity)){
			assignedActivities.remove(activity);
		} else {
			throw new EmployeeException("Not assigned to activity");
		}
	}

	public void assistProject(Project project) throws EmployeeException {
		if(assistingProject.contains(project)){
			throw new EmployeeException("Already assisting project");
		} else if(assignedProjects.contains(project)){
			throw new EmployeeException("Already assigned to project");
		} else {
			assistingProject.add(project);	
		}

	}

	public void assistingActivity(Activity activity) throws EmployeeException {
		if(assignedProjects.contains(activity)){
			throw new EmployeeException("Already assigned to project");
		} else {
			assistingActivity.add(activity);
		}
	}
}

