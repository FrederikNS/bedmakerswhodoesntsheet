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
	
	int next_project_id;
	int next_activity_id;

	public ProjectPlan() {
		next_project_id = 0;
		next_activity_id = 0;
		weeks = new HashMap<Integer,Week>();
		projects = new HashMap<Integer,Project>();
		employees = new HashMap<String,Employee>();
		activities = new HashMap<Integer,Activity>();
	}
	
	/*
	 * ID funktioner
	 */
	
	private int generateNewProjectID() {
		return next_project_id++;
	}

	private int generateNewActivityID() {
		return next_activity_id++;
	}

	/*
	 * WRAPPER FUNKTIONER MED KLASSE-PARAMTRE 
	 */

	private void addActivity(int id, String name) {
		activities.put(id,new Activity(name));
	}
	
	private void addProject(int id, String name) {
		projects.put(id,new Project(name));
	}
	
	private void addProject(int id, String name, String leader_initials) {
		projects.put(id,new Project(name, getEmployee(leader_initials)));
	}

	private void assignActivityToWeek(Activity activity, int hours, Week week) {
		activity.addWeek(week, hours);
		week.addActivity(activity);
	}

	private void removeActivityFromWeek(Activity activity, Week week) {
		//Week kalder activity.removeWeek()
		week.removeActivity(activity);
	}

	private void addActivityToProject(Activity activity, Project project) {
		project.addActivity(activity);
	}

	private void removeActivityFromProject(Activity activity, Project project) {
		project.removeActivity(activity);
	}

	private void removeActivity(Activity activity) {
		activity.remove();
	}
	
	private void renameActivity(Activity activity, String newName){
		activity.setName(newName);
	}
	
	private void renameProject(Project project, String newName){
		project.setName(newName);
	}
	
	private void assignLeaderToProject(Employee projectLeader, Project project){
		project.assignLeader(projectLeader);
	}

	/*
	 * WRAPPER FUNKTIONER MED ID VÆRDIER 
	 * (Bliver typisk kaldt udefra)
	 */
	
	public void addActivity(String name) {
		addActivity(generateNewActivityID(), name);
	}
	
	public void addProject(String name) {
		addProject(generateNewProjectID(), name);
	}
	
	public void addProject(String name, String leader_initials) {
		addProject(generateNewProjectID(), name, leader_initials);
	}
	
	public void addEmployee(String name, String initials) {
		employees.put(initials, new Employee(name, initials));
	}

	public void addEmployee(String name) throws Exception {
		String initials = Employee.generateInitialsFromName(name);
		employees.put(initials, new Employee(name, initials));
	}

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
		removeActivity(getActivity(activity_id));
	}

	public void renameActivity(int activity_id, String newName){
		renameActivity(getActivity(activity_id), newName);
	}
	
	public void renameProject(int project_id, String newName){
		renameProject(getProject(project_id), newName);
	}
	
	public void assignLeaderToProject(String leader_initials, int project_id){
		assignLeaderToProject(getEmployee(leader_initials), getProject(project_id));
	}
	
	//CON: Make public?
	private Week getWeek(int index) {
		//Allokér uger dynamisk 
		if(!weeks.containsKey(index))
			weeks.put(index,new Week(index));
		return weeks.get(index);
	}

	private Project getProject(int id) {
		//FIXME: Exception/contains
		return projects.get(id);
	}
	
	private Activity getActivity(int index) {
		//FIXME: Exception/contains
		return activities.get(index);
	}
	
	private Employee getEmployee(String initials) {
		return employees.get(initials);
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