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

	private void addActivityToProject(Activity activity, Project project) throws FrozenException, ProjectException {
		project.addActivity(activity);
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

	private void setProjectStartWeek(Project project, int week_index) throws FrozenException {
		project.setStartWeek(week_index);
	}

	private void setProjectEndWeek(Project project, int week_index) throws FrozenException {
		project.setEndWeek(week_index);
	}
	
	private void assignEmployeeToProject(Employee e, Project p) throws FrozenException, EmployeeException, ProjectException {
		e.checkFreeze();
		p.checkFreeze();
		p.addEmployee(e);
		e.assignToProject(p);
	}

	private void relieveEmployeeFromProject(Employee e, Project p, boolean reassignasassistant) throws FrozenException, EmployeeException, ProjectException {
		e.checkFreeze();
		p.checkFreeze();
		e.relieveFromProject(p, reassignasassistant);
		p.removeEmployee(e);		
	}
	
	private void assignEmployeeToActivity(Employee e, Activity a) throws FrozenException, EmployeeException {
		e.checkFreeze();
		a.checkFreeze();
		e.assignToActivity(a);
		a.assignEmployee(e);
	}

	private void relieveEmployeeFromActivity(Employee e, Activity a) throws FrozenException, EmployeeException {
		e.checkFreeze();
		a.checkFreeze();
		e.relieveFromActivity(a);
		a.removeEmployee(e);
	}

	private void assignEmployeeToAssistActivity(Employee e, Activity a) throws FrozenException, EmployeeException {
		e.checkFreeze();
		a.checkFreeze();
		e.assistActivity(a);
		a.assignEmployee(e);
	}

	private void relieveEmployeeFromAssitingActivity(Employee e, Activity a) throws FrozenException, EmployeeException {
		e.checkFreeze();
		a.checkFreeze();
		e.relieveFromAssistance(a);
		a.removeEmployee(e);
	}	
	
	private void registerEmployeeProgressInActivity(Employee e, float hours, Activity a) throws FrozenException, EmployeeException {
		a.checkFreeze();
		e.checkFreeze();
		e.registerProgressInActivity(hours, a);
		a.registerProgressFromEmployee(hours, e);		
	}
	
	private Float getActivityWorkload(Activity a) {
		return a.getWorkload();
	}
	
	private Float getActivityProgress(Activity a) {
		return a.getProgress();
	}
	
	private HashMap<Employee,Float> getActivityProgressByEmployee(Activity a) {
		return a.getProgressByEmployee();
	}

	private ArrayList<Employee> getEmployeesAssignedToActivity(Activity a) {
		return a.getAssignedEmployees();
	}

	/*private ArrayList<Employee> getAssistantsToActivity(Activity a) {
		return a.getAssistants();
	}*/
	
	private Project getParentProjectToActivity(Activity a) {
		return a.getParentProject();
	}
	
	private boolean isActivityFrozen(Activity a) {
		return a.isFrozen();
	}

	private boolean isProjectFrozen(Project p) {
		return p.isFrozen();
	}

	private boolean isEmployeeFrozen(Employee e) {
		return e.isFrozen();
	}
	
	private ArrayList<Activity> getActivitiesInProject(Project p) {
		return p.getActivities();
	}
	
	private ArrayList<Employee> getEmployeesAssignedToProject(Project p) {
		return p.getEmployees();
	}
	
	private Employee getProjectLeader(Project p) {
		return p.getLeader();
	}
	
	private ArrayList<Activity> getActivitiesAssignedToEmployee(Employee e) {
		return e.getAssignedActivities();
	}

	//private ArrayList<Activity> getActivitiesAssistedByEmployee(Employee e) {
	//	return e.getAssistedActivities();
	//}
	
	private ArrayList<Project> getProjectsAssignedToEmployee(Employee e) {
		return e.getAssignedProjects();
	}
	
	private ArrayList<Project> getProjectsBeingLeadByEmployee(Employee e) {
		return e.getProjectsBeingLead();
	}
	
	private void freeze(Freezeable f) throws FrozenException {
		f.freeze();
	}
	
	private HashMap<Activity, Float> getWorkDoneByEmployee(Employee e) {
		return e.getWorkDone();
	}
	
	private float getAssignedHoursForWeek(Week week) {
		return week.getAssignedHours();
	}

	/*
	 * WRAPPER FUNKTIONER MED ID VÆRDIER 
	 * (Bliver typisk kaldt udefra)
	 */
	
	public String addActivity(String name) {
		String id =  generateNewActivityID();
		addActivity(id, name);
		return id;
	}
	
	public String addProject(String name) {
		String id = generateNewProjectID();
		addProject(id, name);
		return id;
	}
	
	public String addProjectWithLeader(String name, String leader_initials) throws UnknownIDException {
		String id = generateNewProjectID();
		addProjectWithLeader(id, name, leader_initials);
		return id;
	}
	
	public void addEmployee(String name, String initials) throws EmployeeException {
		if(employees.containsKey(initials)) throw new EmployeeException("An employee with the initials " + initials + " already exists.");
		employees.put(initials, new Employee(name, initials));
	}

	/*public void addEmployee(String name) throws GayException {
		String initials = Employee.generateInitialsFromName(name);
		employees.put(initials, new Employee(name, initials));
	}*/

	public void assignActivityToWeek(String activity_id, int hours, int weekIndex) throws FrozenException, UnknownIDException {
		assignActivityToWeek(getActivity(activity_id), hours, getWeek(weekIndex));
	}

	public void removeActivityFromWeek(String activity_id, int weekIndex) throws FrozenException, UnknownIDException {
		removeActivityFromWeek(getActivity(activity_id), getWeek(weekIndex));
	}

	public void addActivityToProject(String activity_id, String project_id) throws FrozenException, UnknownIDException, ProjectException {
		addActivityToProject(getActivity(activity_id),getProject(project_id));
	}

	public void freezeActivity(String activity_id) throws FrozenException, UnknownIDException {
		freeze(getActivity(activity_id));
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
	
	public void assignEmployeeToProject(String emp_id, String project_id) throws FrozenException, EmployeeException, UnknownIDException, ProjectException {
		assignEmployeeToProject(getEmployee(emp_id), getProject(project_id));
	}

	public void relieveEmployeeFromProject(String emp_id, String project_id, boolean reassignasassistant) throws FrozenException, EmployeeException, UnknownIDException, ProjectException {
		relieveEmployeeFromProject(getEmployee(emp_id), getProject(project_id), reassignasassistant);
	}
	
	public void assignEmployeeToActivity(String emp_id, String act_id) throws FrozenException, EmployeeException, UnknownIDException {
		assignEmployeeToActivity(getEmployee(emp_id), getActivity(act_id));
	}

	public void relieveEmployeeFromActivity(String emp_id, String act_id) throws FrozenException, EmployeeException, UnknownIDException {
		relieveEmployeeFromActivity(getEmployee(emp_id), getActivity(act_id));
	}

	public void assignEmployeeToAssistActivity(String emp_id, String act_id) throws FrozenException, EmployeeException, UnknownIDException {
		assignEmployeeToAssistActivity(getEmployee(emp_id), getActivity(act_id));
	}

	public void relieveEmployeeFromAssitingActivity(String emp_id, String act_id) throws FrozenException, EmployeeException, UnknownIDException {
		relieveEmployeeFromAssitingActivity(getEmployee(emp_id), getActivity(act_id));
	}	
	
	public void registerEmployeeProgressInActivity(String emp_id, float hours, String act_id) throws FrozenException, EmployeeException, UnknownIDException {
		registerEmployeeProgressInActivity(getEmployee(emp_id), hours, getActivity(act_id));
	}
	
	public Float getActivityWorkload(String act_id) throws UnknownIDException {
		return getActivityWorkload(getActivity(act_id));
	}
	
	public Float getActivityProgress(String act_id) throws UnknownIDException {
		return getActivityProgress(getActivity(act_id));
	}
	
	public HashMap<Employee,Float> getActivityProgressByEmployee(String act_id) throws UnknownIDException {
		return getActivityProgressByEmployee(getActivity(act_id));
	}
	
	public ArrayList<Employee> getEmployeesAssignedToActivity(String act_id) throws UnknownIDException {
		return getEmployeesAssignedToActivity(getActivity(act_id));
	}

	/*public ArrayList<Employee> getAssistantsToActivity(String act_id) throws UnknownIDException {
		return getAssistantsToActivity(getActivity(act_id));
	}*/
	
	public Project getParentProjectToActivity(String act_id) throws UnknownIDException {
		return getParentProjectToActivity(getActivity(act_id));
	}
	
	public boolean isActivityFrozen(String act_id) throws UnknownIDException {
		return isActivityFrozen(getActivity(act_id));
	}

	public boolean isProjectFrozen(String project_id) throws UnknownIDException {
		return isProjectFrozen(getProject(project_id));
	}

	public boolean isEmployeeFrozen(String emp_id) throws UnknownIDException {
		return isEmployeeFrozen(getEmployee(emp_id));
	}
	
	public ArrayList<Activity> getActivitiesInProject(String project_id) throws UnknownIDException {
		return getActivitiesInProject(getProject(project_id));
	}
	
	public ArrayList<Employee> getEmployeesAssignedToProjcet(String project_id) throws UnknownIDException {
		return getEmployeesAssignedToProject(getProject(project_id));
	}
	
	public Employee getProjectLeader(String project_id) throws UnknownIDException {
		return getProjectLeader(getProject(project_id));
	}
	
	public ArrayList<Activity> getActivitiesAssignedToEmployee(String emp_id) throws UnknownIDException {
		return getActivitiesAssignedToEmployee(getEmployee(emp_id));
	}

	public ArrayList<Project> getProjectsAssignedToEmployee(String emp_id) throws UnknownIDException {
		return getProjectsAssignedToEmployee(getEmployee(emp_id));
	}
	
	public ArrayList<Project> getProjectsBeingLeadByEmployee(String emp_id) throws UnknownIDException {
		return getProjectsBeingLeadByEmployee(getEmployee(emp_id));
	}
	
	public void freezeProject(String project_id) throws FrozenException, UnknownIDException {
		freeze(getProject(project_id));
	}

	public void freezeEmployee(String emp_id) throws FrozenException, UnknownIDException {
		freeze(getEmployee(emp_id));
	}
	
	public HashMap<Activity,Float> getWorkDoneByEmployee(String emp_id) throws UnknownIDException {
		return getWorkDoneByEmployee(getEmployee(emp_id));
	}
	
	/*
	 * lazypeons <- Liste over alle ansatte
	 * For alle aktiviter i ugen
	 *   Fjern alle ansatte i aktiviteten fra lazypeons
	 * For alle ansatte i lazypeons
	 *   Fjern alle uarbejdsdygtige (frosne) ansatte
	 */
	
	public ArrayList<Employee> getLazyEmployeesForWeek(int index) {
		ArrayList<Employee> lazypeons = new ArrayList<Employee>(employees.values());
		Week week = getWeek(index);
		for(Activity activity : week.getScheduledActivities()) {
			for(Employee employee : activity.getAssignedEmployees())
				lazypeons.remove(employee);
		}
		for(Employee e : lazypeons)
			if(e.isFrozen())
				lazypeons.remove(e);
		return lazypeons;
	}
	
	public float getAssignedHoursForWeek(int index) {
		return getAssignedHoursForWeek(getWeek(index));
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
	public Week getWeekFromIndex(int index) {
		return getWeek(index);
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
}
