package schedule;

import java.util.HashMap;

import employee.Employee;

import activity.Activity;
import activity.Project;

public class ProjectPlan {
	HashMap<Integer,Project> projects;
	HashMap<Integer,Week> weeks;
	HashMap<String,Employee> employees;
	HashMap<Integer,Activity> activities;

	public ProjectPlan() {
		weeks = new HashMap<Integer,Week>();
		projects = new HashMap<Integer,Project>();
		employees = new HashMap<String,Employee>();
		activities = new HashMap<Integer,Activity>();
	}
	
	//Funktioner med pointers
	public void assignActivityToWeek(Activity activity, int hours, Week week) {
		activity.addWeek(week, hours);
		week.addActivity(activity);
	}

	public void removeActivityFromWeek(Activity activity, Week week) {
		activity.removeWeek(week);
		week.removeActivity(activity);
	}

	public void addActivityToProject(Activity activity, Project project) {
		project.addActivity(activity);
	}

	public void removeActivityFromProject(Activity activity, Project project) {
		project.removeActivity(activity);
	}

	public void removeActivity(Activity activity) {
		activity.remove();
	}
	
	public void renameActivity(Activity activity, String newName){
		activity.renameActivity(newName);
	}
	
	public void renameProject(Project project, String newName){
		project.setName(newName);
	}
	
	public void assignProjectLeader(Project project, Employee projectLeader){
		project.assignLeader(projectLeader);
	}
	

	//Funktioner med id's
	public void assignActivityToWeek(int activity_id, int hours, int weekIndex) {
		assignActivityToWeek(getActivity(activity_id), hours, getWeek(weekIndex));
	}

	public void removeActivityFromWeek(int activity_id, int weekIndex) {
		removeActivityFromWeek(getActivity(activity_id), getWeek(weekIndex));
	}

	public void addActivityToProject(int activity_id, int project_id) {
		addActivityToProject(getActivity(activity_id),getProject(project_id));
	}

	public void removeActivityFromProject(int activity_id, int project_id) {
		removeActivityFromProject(getActivity(activity_id),getProject(project_id));
	}

	public void removeActivity(int activity_id) {
		getActivity(activity_id).remove();
	}

	public void renameActivity(int activity_id, String newName){
		renameActivity(getActivity(activity_id), newName);
	}
	
	public void renameProject(int project_id, String newName){
		renameProject(getProject(project_id), newName);
	}
	
	public void assignProjectLeader(int project_id, Employee projectLeader){
		assignProjectLeader(getProject(project_id), projectLeader);
	}
	
	//FIXME: ID's
	public void addActivity(int id, String name) {
		//FIXME: Fjern ID fra Activity
		activities.put(id, new Activity(id, name));
	}

	public void addProject(int id) {
		projects.put(id, new Project(id));
	}
	
	public void addProject(int id, String name) {
		projects.put(id,new Project(id, name));
	}
	
	public void addProject(int id, String name, Employee leader) {
		projects.put(id,new Project(id, name, leader));
	}
	
	private Project getProject(int id) {
		//FIXME: Exception/contains
		return projects.get(id);
	}
	
	//CON: Make public?
	private Week getWeek(int index) {
		//Allokér uger dynamisk 
		if(!weeks.containsKey(index))
			weeks.put(index,new Week(index));
		return weeks.get(index);
	}
	
	private Activity getActivity(int index) {
		//FIXME: Exception/contains
		return activities.get(index);
	}
}

/*
 * 	public String createInitialsFromName(String name){
		String firstInitial = name.substring(0,2);
		int secondInitialIndex = name.lastIndexOf(" ") +1;
		String secondInitial = name.substring(secondInitialIndex,secondInitialIndex+2);
		String initials = firstInitial+secondInitial;
		System.out.println(initials);
		checkInitials(initials);
		return initials;
	}

//TODO Check if works.
	public String checkInitials(String initials){
		for(Employee emptemp: employees.values()){
			if(emptemp.getInitials().equals(initials) && initials.length() == 4){
				initials = initials + "1";
				checkInitials(initials);
			} else if(emptemp.getInitials().equals(initials)){
				initials = initials.substring(0,4) + String.valueOf((Integer.parseInt((initials.substring(5,initials.length()))) +1));
				checkInitials(initials);
			}
		}
		return initials;
	}
	//Mixede funktioner (deprecate)
	public void assignActivityToWeek(Activity activity, int hours, int weekIndex) {
		assignActivityToWeek(activity, hours, getWeek(weekIndex));
	}

	public void removeActivityFromWeek(Activity activity, int weekIndex) {
		removeActivityFromWeek(activity, getWeek(weekIndex));
	}

	public void addActivityToProject(Activity activity, int project_id) {
		addActivityToProject(activity,getProject(project_id));
	}

*/