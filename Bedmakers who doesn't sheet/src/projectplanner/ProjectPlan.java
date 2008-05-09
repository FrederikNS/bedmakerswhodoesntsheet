package projectplanner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;

import projectplanner.UnknownIDException.ID_EXCEPTION_TYPES;

public class ProjectPlan {
	private HashMap<String,Project> projects;
	private HashMap<Integer,Week> weeks;
	private HashMap<String,Employee> employees;
	private HashMap<String,Activity> activities;
	
	private int next_project_id;
	private int next_activity_id;

	public ProjectPlan() {
		next_project_id = 0;
		next_activity_id = 0;
		weeks = new HashMap<Integer,Week>();
		projects = new HashMap<String,Project>();
		employees = new HashMap<String,Employee>();
		activities = new HashMap<String,Activity>();
	}
	
	/*
	 * ID funktioner
	 */
	
	private String generateNewProjectID() {
		next_project_id++;
		String currentYear = String.valueOf((Calendar.getInstance().get(Calendar.YEAR))%100);
		String numberPrefix = "";
		for(int i = 4; i > String.valueOf(next_project_id).length(); i--){
			numberPrefix += String.valueOf(0);
		}
		String issuedNumber = numberPrefix + String.valueOf(next_project_id);
		String projectID = currentYear + issuedNumber;
		return projectID;
	}

	private String generateNewActivityID() {
		next_activity_id++;
		return ""+next_activity_id;
	}

	/*
	 * WRAPPER FUNKTIONER MED KLASSE-PARAMETRE 
	 */

	private void addActivity(String id, String name) {
		activities.put(id,new Activity(id,name));
	}
	
	private void addProject(String id, String name) {
		projects.put(id,new Project(id, name));
	}
	
	private void addProjectWithLeader(String id, String name, String leader_initials) throws UnknownIDException {
		projects.put(id,new Project(id, name, getEmployee(leader_initials)));
	}

	private void assignActivityToWeek(Activity activity, int hours, Week week) throws FrozenException {
		activity.addWeek(week, hours);
		week.addActivity(activity);
	}

	private void removeActivityFromWeek(Activity activity, Week week) throws FrozenException {
		activity.removeWeek(week);
		week.removeActivity(activity);
	}

	private void addActivityToProject(Activity activity, Project project) throws FrozenException {
		project.addActivity(activity);
	}
	
	//Depreciate?
	private void removeActivityFromProject(Activity activity, Project project) throws FrozenException {
		project.freezeActivity(activity);
	}

	private void freezeActivity(Activity activity) throws FrozenException {
		activity.freeze();
	}
	
	private void renameActivity(Activity activity, String newName) throws FrozenException{
		activity.setName(newName);
	}
	
	private void renameProject(Project project, String newName) throws FrozenException{
		project.setName(newName);
	}
	
	private void assignLeaderToProject(Employee projectLeader, Project project) throws FrozenException{
		project.assignLeader(projectLeader);
	}

	public void setProjectStartWeek(Project project, int week_index) throws FrozenException {
		project.setStartWeek(week_index);
	}

	public void setProjectEndWeek(Project project, int week_index) throws FrozenException {
		project.setStartWeek(week_index);
	}
	
	public void assignEmployeeToActivity(Employee e, Activity a) throws FrozenException, EmployeeException {
		e.checkFreeze();
		a.checkFreeze();
		e.assignToActivity(a);
	}

	public void relieveEmployeeFromActivity(Employee e, Activity a) throws FrozenException, EmployeeException {
		e.checkFreeze();
		a.checkFreeze();
		e.relieveFromActivity(a);
	}

	public void assignEmployeeToAssistActivity(Employee e, Activity a) throws FrozenException, EmployeeException {
		e.checkFreeze();
		a.checkFreeze();
		e.assistActivity(a);
	}

	public void relieveEmployeeFromAssitingActivity(Employee e, Activity a) throws FrozenException, EmployeeException {
		e.checkFreeze();
		a.checkFreeze();
		e.relieveFromAssistance(a);
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
	
	public void addProjectWithLeader(String name, String leader_initials) throws UnknownIDException {
		addProjectWithLeader(generateNewProjectID(), name, leader_initials);
	}
	
	public void addEmployee(String name, String initials) {
		employees.put(initials, new Employee(name, initials));
	}

	public void addEmployee(String name) throws Exception {
		String initials = Employee.generateInitialsFromName(name);
		employees.put(initials, new Employee(name, initials));
	}

	public void assignActivityToWeek(String activity_id, int hours, int weekIndex) throws FrozenException, UnknownIDException {
		assignActivityToWeek(getActivity(activity_id), hours, getWeek(weekIndex));
	}

	public void removeActivityFromWeek(String activity_id, int weekIndex) throws FrozenException, UnknownIDException {
		removeActivityFromWeek(getActivity(activity_id), getWeek(weekIndex));
	}

	public void addActivityToProject(String activity_id, String project_id) throws FrozenException, UnknownIDException {
		addActivityToProject(getActivity(activity_id),getProject(project_id));
	}

	public void removeActivityFromProject(String activity_id, String project_id) throws FrozenException, UnknownIDException {
		removeActivityFromProject(getActivity(activity_id),getProject(project_id));
	}

	public void freezeActivity(String activity_id) throws FrozenException, UnknownIDException {
		freezeActivity(getActivity(activity_id));
	}

	public void renameActivity(String activity_id, String newName) throws FrozenException, UnknownIDException{
		renameActivity(getActivity(activity_id), newName);
	}
	
	public void renameProject(String project_id, String newName) throws FrozenException, UnknownIDException{
		renameProject(getProject(project_id), newName);
	}
	
	public void assignLeaderToProject(String leader_initials, String project_id) throws FrozenException, UnknownIDException{
		assignLeaderToProject(getEmployee(leader_initials), getProject(project_id));
	}

	public void setProjectStartWeek(String project_id, int week) throws FrozenException, UnknownIDException {
		setProjectStartWeek(getProject(project_id), week);
	}

	public void setProjectEndWeek(String project_id, int week) throws FrozenException, UnknownIDException {
		setProjectEndWeek(getProject(project_id), week);
	}
	
	/*
	 * Container wrappers
	 */
	
	private Week getWeek(int index) {
		//Allokér uger dynamisk.
		if(!weeks.containsKey(index))
			weeks.put(index,new Week(index));
		return weeks.get(index);
	}

	private Project getProject(String index) throws UnknownIDException {
		if(!projects.containsKey(index)) throw new UnknownIDException(ID_EXCEPTION_TYPES.PROJECT, index);
		return projects.get(index);
	}
	
	private Activity getActivity(String index) throws UnknownIDException {
		if(!activities.containsKey(index)) throw new UnknownIDException(ID_EXCEPTION_TYPES.ACTIVITY, index);
		return activities.get(index);
	}
	
	private Employee getEmployee(String initials) throws UnknownIDException {
		if(!employees.containsKey(initials)) throw new UnknownIDException(ID_EXCEPTION_TYPES.EMPLOYEE, initials);
		return employees.get(initials);
	}

	public HashMap<String,Project> getProjects() {
		return projects;
	}

	public HashMap<String,Activity> getActivities() {
		return activities;
	}

	public HashMap<String,Employee> getEmployees() {
		return employees;
	}
	
	/*
	 * Search
	 */
	
	public ArrayList<Activity> findActivity(String pattern) {
		ArrayList<Activity> listout = new ArrayList<Activity>();
		Pattern p = Pattern.compile(pattern);
		for(Activity a : activities.values()) {
			if(p.matcher(a.getName()).matches()) {
				listout.add(a);
			}
		}
		return listout;
	}
	
	public String findActivityID(String pattern) {
		Pattern p = Pattern.compile(pattern);
		String id = "";
		for(Activity a: activities.values()) {
			if(p.matcher(a.getName()).matches()) {
				id = a.getID();
			}
		}
		return id;
	}

	public ArrayList<Employee> findEmployee(String pattern) {
		ArrayList<Employee> listout = new ArrayList<Employee>();
		Pattern p = Pattern.compile(pattern);
		for(Employee employee : employees.values()) {
			if(p.matcher(employee.getName()).matches()) {
				listout.add(employee);
			}
		}
		return listout;
	}	
	
	public String findEmployeeID(String pattern) {
		Pattern p = Pattern.compile(pattern);
		String id = "";
		for(Employee employee : employees.values()) {
			if(p.matcher(employee.getName()).matches()) {
				id = employee.getInitials();
			}
		}
		return id;
	}	
	
	public ArrayList<Project> findProject(String pattern) {
		ArrayList<Project> listout = new ArrayList<Project>();
		Pattern p = Pattern.compile(pattern);
		for(Project project : projects.values()) {
			if(p.matcher(project.getName()).matches()) {
				listout.add(project);
			}
		}
		return listout;
	}
	public String findProjectID(String pattern) {
		Pattern p = Pattern.compile(pattern);
		String id = "";
		for(Project project : projects.values()) {
			if(p.matcher(project.getName()).matches()) {
				id = project.getId();
			}
		}
		return id;
	}
}
