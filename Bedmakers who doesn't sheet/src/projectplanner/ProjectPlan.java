package projectplanner;

import java.util.Calendar;
import java.util.HashMap;

public class ProjectPlan {
	private HashMap<String,Project> projects;
	private HashMap<Integer,Week> weeks;
	private HashMap<String,Employee> employees;
	private HashMap<Integer,Activity> activities;
	
	private int next_project_id;
	private int next_activity_id;

	public ProjectPlan() {
		next_project_id = 0;
		next_activity_id = 0;
		weeks = new HashMap<Integer,Week>();
		projects = new HashMap<String,Project>();
		employees = new HashMap<String,Employee>();
		activities = new HashMap<Integer,Activity>();
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

	private int generateNewActivityID() {
		return next_activity_id++;
	}

	/*
	 * WRAPPER FUNKTIONER MED KLASSE-PARAMETRE 
	 */

	private void addActivity(int id, String name) {
		activities.put(id,new Activity(id,name));
	}
	
	private void addProject(String id, String name) {
		projects.put(id,new Project(name));
	}
	
	private void addProject(String id, String name, String leader_initials) {
		projects.put(id,new Project(name, getEmployee(leader_initials)));
	}

	private void assignActivityToWeek(Activity activity, int hours, Week week) throws FrozenException {
		activity.addWeek(week, hours);
		week.addActivity(activity);
	}

	private void removeActivityFromWeek(Activity activity, Week week) throws FrozenException {
		//Week kalder activity.removeWeek()
		week.removeActivity(activity);
	}

	private void addActivityToProject(Activity activity, Project project) throws FrozenException {
		project.addActivity(activity);
	}

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

	/*
	 * WRAPPER FUNKTIONER MED ID V�RDIER 
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

	public void assignActivityToWeek(int activity_id, int hours, int weekIndex) throws FrozenException {
		assignActivityToWeek(getActivity(activity_id), hours, getWeek(weekIndex));
	}

	public void removeActivityFromWeek(int activity_id, int weekIndex) throws FrozenException {
		removeActivityFromWeek(getActivity(activity_id), getWeek(weekIndex));
	}

	public void addActivityToProject(int activity_id, int project_id) throws FrozenException {
		addActivityToProject(getActivity(activity_id),getProject(project_id));
	}

	public void removeActivityFromProject(int activity_id, int project_id) throws FrozenException {
		removeActivityFromProject(getActivity(activity_id),getProject(project_id));
	}

	public void freezeActivity(int activity_id) throws FrozenException {
		freezeActivity(getActivity(activity_id));
	}

	public void renameActivity(int activity_id, String newName) throws FrozenException{
		renameActivity(getActivity(activity_id), newName);
	}
	
	public void renameProject(int project_id, String newName) throws FrozenException{
		renameProject(getProject(project_id), newName);
	}
	
	public void assignLeaderToProject(String leader_initials, int project_id) throws FrozenException{
		assignLeaderToProject(getEmployee(leader_initials), getProject(project_id));
	}

	public void setProjectStartWeek(int project_id, int week) throws FrozenException, UnknownIDException {
		setProjectStartWeek(getProject(project_id), week);
	}

	public void setProjectEndWeek(int project_id, int week) throws FrozenException, UnknownIDException {
		setProjectEndWeek(getProject(project_id), week);
	}
	
	/*
	 * Container wrappers
	 */
	
	private Week getWeek(int index) {
		//Allok�r uger dynamisk. Kan sagtens crashe porgrammet hvis der bliver bedt om underlige uger 
		if(!weeks.containsKey(index))
			weeks.put(index,new Week(index));
		return weeks.get(index);
	}

	private Project getProject(int index) throws UnknownIDException {
		if(!projects.containsKey(index)) throw new UnknownIDException(ID_EXCEPTION_TYPES.PROJECT, index);
		return projects.get(index);
	}
	
	private Activity getActivity(int index) throws UnknownIDException {
		if(!activities.containsKey(index)) throw new UnknownIDException(ID_EXCEPTION_TYPES.ACTIVITY, index);
		return activities.get(index);
	}
	
	private Employee getEmployee(String initials) throws UnknownIDException {
		if(!employees.containsKey(initials)) throw new UnknownIDException(ID_EXCEPTION_TYPES.EMPLOYEE, initials);
		return employees.get(initials);
	}

	//FIXME: Udefra-kommende klasser burde ikke have rettigheder til at �ndre disse maps, og de ting som de peger p�.
	//Temp fix
	public HashMap<String,Project> getProjects() {
		return projects;
	}

	public HashMap<Integer,Activity> getActivities() {
		return activities;
	}

	public HashMap<String,Employee> getEmployees() {
		return employees;
	}
}
