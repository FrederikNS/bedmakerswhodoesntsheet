package projectplanner;

import java.util.HashSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;

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
		if(currentYear.length() == 1) {
			currentYear = "0"+currentYear;
		}
		String numberPrefix = "";
		for(int i = 4; i > String.valueOf(next_project_id).length(); i--){
			numberPrefix += "0";
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
	
	private void addProjectWithLeader(String id, String name, String leader_initials) throws UnknownIDException, EmployeeException {
		Employee employee = getEmployee(leader_initials);
		Project project = new Project(id, name, employee);
		employee.assignProjectLead(project);
		projects.put(id,project);
	}

	private void assignActivityToWeek(Activity activity, float hours, Week week) {
		activity.addWeek(week, hours);
		week.addActivity(activity);
	}

	private void removeActivityFromWeek(Activity activity, Week week) {
		activity.removeWeek(week);
		week.removeActivity(activity);
	}

	private void addActivityToProject(Activity activity, Project project) throws ProjectException {
		project.addActivity(activity);
	}
	
	private void renameActivity(Activity activity, String newName) {
		activity.setName(newName);
	}
	
	private void renameProject(Project project, String newName) {
		project.setName(newName);
	}
	
	private void assignLeaderToProject(Employee projectLeader, Project project) {
		project.assignLeader(projectLeader);
	}

	private void setProjectStartWeek(Project project, int week_index) {
		project.setStartWeek(week_index);
	}

	private void setProjectEndWeek(Project project, int week_index) {
		project.setEndWeek(week_index);
	}
	
	private void assignEmployeeToProject(Employee e, Project p) throws EmployeeException, ProjectException {
		e.checkAssignToProject(p);
		p.checkAssignEmployee(e);
		p.addEmployee(e);
		e.assignToProject(p);
	}

	private void relieveEmployeeFromProject(Employee e, Project p, boolean reassignasassistant) throws EmployeeException, ProjectException {
		e.checkRelieveFromProject(p);
		p.checkRemoveEmployee(e);
		e.relieveFromProject(p, reassignasassistant);
		p.removeEmployee(e);		
	}
	
	private void assignEmployeeToActivity(Employee e, Activity a) throws EmployeeException, ActivityException {
		e.checkAssignToActivity(a);
		a.checkAssignEmployee(e);
		e.assignToActivity(a);
		a.assignEmployee(e);
	}

	private void relieveEmployeeFromActivity(Employee e, Activity a) throws EmployeeException, ActivityException {
		e.checkRelieveFromActivity(a);
		a.checkRemoveEmployee(e);
		e.relieveFromActivity(a);
		a.removeEmployee(e);
	}

	private void assignEmployeeToAssistActivity(Employee e, Activity a) throws EmployeeException, ActivityException {
		e.checkAssistActivity(a);
		a.checkAssignEmployeeAsAssistant(e);
		e.assistActivity(a);
		a.assignEmployee(e);
	}

	private void relieveEmployeeFromAssitingActivity(Employee e, Activity a) throws EmployeeException, ActivityException {
		e.checkRelieveFromAssistance(a);
		a.checkRemoveEmployee(e);
		e.relieveFromAssistance(a);
		a.removeEmployee(e);
	}	
	
	private void registerEmployeeProgressInActivity(Employee e, float hours, Activity a) throws EmployeeException {
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

	private HashSet<Employee> getEmployeesAssignedToActivity(Activity a) {
		return a.getAssignedEmployees();
	}

	/*private HashSet<Employee> getAssistantsToActivity(Activity a) {
		return a.getAssistants();
	}*/
	
	private Project getActivityParentProject(Activity a) {
		return a.getParentProject();
	}
	
	private boolean isActivityDeprecated(Activity a) {
		return a.isDeprecated();
	}

	private boolean isProjectDeprecated(Project p) {
		return p.isDeprecated();
	}

	private boolean isEmployeeDeprecated(Employee e) {
		return e.isDeprecated();
	}
	
	private HashSet<Activity> getActivitiesInProject(Project p) {
		return p.getActivities();
	}
	
	private HashSet<Employee> getEmployeesAssignedToProject(Project p) {
		return p.getEmployees();
	}
	
	private Employee getProjectLeader(Project p) {
		return p.getLeader();
	}
	
	private HashMap<Activity, Boolean> getActivitiesAssignedToEmployee(Employee e) {
		return e.getAssignedActivities();
	}

	//private HashSet<Activity> getActivitiesAssistedByEmployee(Employee e) {
	//	return e.getAssistedActivities();
	//}
	
	private HashSet<Project> getProjectsAssignedToEmployee(Employee e) {
		return e.getAssignedProjects();
	}
	
	private HashSet<Project> getProjectsBeingLeadByEmployee(Employee e) {
		return e.getProjectsBeingLead();
	}
	
	private void freeze(Deprecateable f) {
		f.deprecate();
	}
	
	private HashMap<Activity, Float> getWorkDoneByEmployee(Employee e) {
		return e.getWorkDone();
	}
	
	private float getWorkloadForWeek(Week week) throws ActivityException {
		return week.getAssignedHours();
	}
	
	private String getProjectName(Project project) {
		return project.getName();
	}
	
	private int getProjectStartWeek(Project project) {
		return project.getStartWeek();
	}
	
	private int getProjectEndWeek(Project project) {
		return project.getEndWeek();
	}
	
	private float getProjectWorkload(Project project) {
		return project.getWorkload();
	}
	
	private float getProjectProgress(Project project) {
		return project.getProgress();
	}
	
	private String getActivityName(Activity activity) {
		return activity.getName();	
	}
	
	private int getActivityStartWeek(Activity activity) {
		return activity.getStartWeek();
	}

	private int getActivityEndWeek(Activity activity) {
		return activity.getEndWeek();
	}
	
	private String getEmployeeName(Employee employee) {
		return employee.getName();
	}
	
	private boolean isEmployeeAssignedToActivity(Employee employee, Activity activity) {
		return employee.isAssignedToActivity(activity);
	}

	private boolean isEmployeeAssignedToActivityAsEmployee(Employee employee, Activity activity) {
		return employee.isAssignedToActivityAsEmployee(activity);
	}

	private boolean isEmployeeAssignedToActivityAsAssistant(Employee employee, Activity activity) {
		return employee.isAssignedToActivityAsAssistant(activity);
	}

	private HashSet<Activity> getActivitiesInWeek(Week week) {
		return week.getScheduledActivities();
	}
	
	private int getNumberOfActivitiesInWeek(Week week) {
		return week.getNumberOfScheduledActivities();
	}

	/*
	 * WRAPPER FUNKTIONER MED ID VAERDIER 
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
	
	public String addProjectWithLeader(String name, String leader_initials) throws UnknownIDException, EmployeeException {
		String id = generateNewProjectID();
		addProjectWithLeader(id, name, leader_initials);
		return id;
	}
	
	public void addEmployee(String name, String initials) throws EmployeeException {
		initials = initials.toLowerCase();
		if(employees.containsKey(initials)) throw new EmployeeException("An employee with the initials " + initials + " already exists.");
		employees.put(initials, new Employee(name, initials));
	}

	public void addEmployee(String name) throws EmployeeException {
		String initials = Employee.generateInitialsFromName(name).toLowerCase();
		if(employees.containsKey(initials)) throw new EmployeeException("An employee with the initials " + initials + " already exists.");
		employees.put(initials, new Employee(name, initials));
	}

	public void assignActivityToWeek(String activity_id, float hours, int weekIndex) throws UnknownIDException {
		assignActivityToWeek(getActivity(activity_id), hours, getWeek(weekIndex));
	}

	public void removeActivityFromWeek(String activity_id, int weekIndex) throws UnknownIDException {
		removeActivityFromWeek(getActivity(activity_id), getWeek(weekIndex));
	}

	public void addActivityToProject(String activity_id, String project_id) throws UnknownIDException, ProjectException {
		addActivityToProject(getActivity(activity_id),getProject(project_id));
	}

	public void freezeActivity(String activity_id) throws UnknownIDException {
		freeze(getActivity(activity_id));
	}

	public void renameActivity(String activity_id, String newName) throws UnknownIDException{
		renameActivity(getActivity(activity_id), newName);
	}
	
	public void renameProject(String project_id, String newName) throws UnknownIDException{
		renameProject(getProject(project_id), newName);
	}
	
	public void assignLeaderToProject(String leader_initials, String project_id) throws UnknownIDException{
		assignLeaderToProject(getEmployee(leader_initials), getProject(project_id));
	}

	public void setProjectStartWeek(String project_id, int week) throws UnknownIDException {
		setProjectStartWeek(getProject(project_id), week);
	}

	public void setProjectEndWeek(String project_id, int week) throws UnknownIDException {
		setProjectEndWeek(getProject(project_id), week);
	}
	
	public void assignEmployeeToProject(String emp_id, String project_id) throws EmployeeException, UnknownIDException, ProjectException {
		assignEmployeeToProject(getEmployee(emp_id), getProject(project_id));
	}

	public void relieveEmployeeFromProject(String emp_id, String project_id, boolean reassignasassistant) throws EmployeeException, UnknownIDException, ProjectException {
		relieveEmployeeFromProject(getEmployee(emp_id), getProject(project_id), reassignasassistant);
	}
	
	public void assignEmployeeToActivity(String emp_id, String act_id) throws EmployeeException, UnknownIDException, ActivityException {
		assignEmployeeToActivity(getEmployee(emp_id), getActivity(act_id));
	}

	public void relieveEmployeeFromActivity(String emp_id, String act_id) throws EmployeeException, UnknownIDException, ActivityException {
		relieveEmployeeFromActivity(getEmployee(emp_id), getActivity(act_id));
	}

	public void assignEmployeeToAssistActivity(String emp_id, String act_id) throws EmployeeException, UnknownIDException, ActivityException {
		assignEmployeeToAssistActivity(getEmployee(emp_id), getActivity(act_id));
	}

	public void relieveEmployeeFromAssistingActivity(String emp_id, String act_id) throws EmployeeException, UnknownIDException, ActivityException {
		relieveEmployeeFromAssitingActivity(getEmployee(emp_id), getActivity(act_id));
	}	
	
	public void registerEmployeeProgressInActivity(String emp_id, float hours, String act_id) throws EmployeeException, UnknownIDException {
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
	
	public HashSet<Employee> getEmployeesAssignedToActivity(String act_id) throws UnknownIDException {
		return getEmployeesAssignedToActivity(getActivity(act_id));
	}

	/*public HashSet<Employee> getAssistantsToActivity(String act_id) throws UnknownIDException {
		return getAssistantsToActivity(getActivity(act_id));
	}*/
	
	public Project getActivityParentProject(String act_id) throws UnknownIDException {
		return getActivityParentProject(getActivity(act_id));
	}
	
	public boolean isActivityDeprecated(String act_id) throws UnknownIDException {
		return isActivityDeprecated(getActivity(act_id));
	}

	public boolean isProjectDeprecated(String project_id) throws UnknownIDException {
		return isProjectDeprecated(getProject(project_id));
	}

	public boolean isEmployeeDeprecated(String emp_id) throws UnknownIDException {
		return isEmployeeDeprecated(getEmployee(emp_id));
	}
	
	public HashSet<Activity> getActivitiesInProject(String project_id) throws UnknownIDException {
		return getActivitiesInProject(getProject(project_id));
	}
	
	public HashSet<Employee> getEmployeesAssignedToProjcet(String project_id) throws UnknownIDException {
		return getEmployeesAssignedToProject(getProject(project_id));
	}
	
	public Employee getProjectLeader(String project_id) throws UnknownIDException {
		return getProjectLeader(getProject(project_id));
	}
	
	public HashMap<Activity,Boolean> getActivitiesAssignedToEmployee(String emp_id) throws UnknownIDException {
		return getActivitiesAssignedToEmployee(getEmployee(emp_id));
	}

	public HashSet<Project> getProjectsAssignedToEmployee(String emp_id) throws UnknownIDException {
		return getProjectsAssignedToEmployee(getEmployee(emp_id));
	}
	
	public HashSet<Project> getProjectsBeingLeadByEmployee(String emp_id) throws UnknownIDException {
		return getProjectsBeingLeadByEmployee(getEmployee(emp_id));
	}
	
	public void freezeProject(String project_id) throws UnknownIDException {
		freeze(getProject(project_id));
	}

	public void freezeEmployee(String emp_id) throws UnknownIDException {
		freeze(getEmployee(emp_id));
	}
	
	public HashMap<Activity,Float> getWorkDoneByEmployee(String emp_id) throws UnknownIDException {
		return getWorkDoneByEmployee(getEmployee(emp_id));
	}
	
	public float getWorkloadForWeek(int index) throws ActivityException {
		return getWorkloadForWeek(getWeek(index));
	}

	public String getProjectName(String project_id) throws UnknownIDException {
		return getProjectName(getProject(project_id));
	}
	
	public int getProjectStartWeek(String project_id) throws UnknownIDException {
		return getProjectStartWeek(getProject(project_id));
	}
	
	public int getProjectEndWeek(String project_id) throws UnknownIDException {
		return getProjectEndWeek(getProject(project_id));
	}
	
	public float getProjectWorkload(String project_id) throws UnknownIDException {
		return getProjectWorkload(getProject(project_id));
	}
	
	public float getProjectProgress(String project_id) throws UnknownIDException {
		return getProjectProgress(getProject(project_id));
	}
	
	public String getActivityName(String activity_id) throws UnknownIDException {
		return getActivityName(getActivity(activity_id));	
	}
	
	public int getActivityStartWeek(String activity_id) throws UnknownIDException {
		return getActivityStartWeek(getActivity(activity_id));
	}

	public int getActivityEndWeek(String activity_id) throws UnknownIDException {
		return getActivityEndWeek(getActivity(activity_id));
	}
	
	public String getEmployeeName(String employee_initials) throws UnknownIDException {
		return getEmployeeName(getEmployee(employee_initials));
	}

	public boolean isEmployeeAssignedToActivity(String employee_initials, String activity_id) throws UnknownIDException {
		return isEmployeeAssignedToActivity(getEmployee(employee_initials),getActivity(activity_id));
	}

	public boolean isEmployeeAssignedToActivityAsEmployee(String employee_initials, String activity_id) throws UnknownIDException {
		return isEmployeeAssignedToActivityAsEmployee(getEmployee(employee_initials),getActivity(activity_id));
	}

	public boolean isEmployeeAssignedToActivityAsAssistant(String employee_initials, String activity_id) throws UnknownIDException {
		return isEmployeeAssignedToActivityAsAssistant(getEmployee(employee_initials),getActivity(activity_id));
	}
		
	public HashSet<Activity> getActivitiesInWeek(int week_index) {
		return getActivitiesInWeek(getWeek(week_index));
	}
	
	public int getNumberOfActivitiesInWeek(int week_index) {
		return getNumberOfActivitiesInWeek(getWeek(week_index));
	}


	
	/*
	 * lazypeons <- Liste over alle ansatte
	 * For alle aktiviter i ugen
	 *   Fjern alle ansatte i aktiviteten fra lazypeons
	 * For alle ansatte i lazypeons
	 *   Fjern alle uarbejdsdygtige (frosne) ansatte
	 */
	/*
	public HashSet<Employee> getLazyEmployeesForWeek(int index) {
		HashSet<Employee> lazypeons = new HashSet<Employee>(employees.values());
		Week week = getWeek(index);
		for(Activity activity : week.getScheduledActivities()) {
			for(Employee employee : activity.getAssignedEmployees())
				lazypeons.remove(employee);
		}
		for(Employee e : lazypeons)
			if(e.isDeprecated())
				lazypeons.remove(e);
		return lazypeons;
	}*/
		
	public HashMap<Employee, Float> getWorkloadByEmployeeForWeek(int index) {
		//Snup ugen fra vores uge-container med den givne indeks vaerdi.
		Week week = getWeek(index);

		//Opret en ny HashMap. Til hver ansat (key/employee) hører der en maengde arbejdstid (value/float).
		HashMap<Employee,Float> workloadByEmployee = new HashMap<Employee,Float>();
		
		//Initialiser hashmappen saaledes at den indeholder alle ansatte,
		//og at de alle har en arbejdstid for ugen paa 0.
		for(Employee e : employees.values()) {
			workloadByEmployee.put(e,0.0f);
		}
		
		//Loeb gennem alle aktiviteter i ugen.
		for(Activity activity : week.getScheduledActivities()) {
			//Faa den gennemsnitlige arbejdsbyrde for en ansat i aktiviteten for ugen.  
			//Der er taget hoejde for nedlagte ansatte.
			float workload_per_employee = 0;
			try {
				workload_per_employee = activity.getWorkloadPerEmployeeForWeek(week);
			} catch (ActivityException e) {
				System.out.println("Program Sync Error between week and activity");
			}
			//Loeb gennem alle ansatte i aktiviteten og laeg den beregende byrde 
			//til deres samlede byrde for ugen.
			for(Employee employee : activity.getAssignedEmployees()) {
				float total_employee_workload = 0;
				//Hent den allerede beregnede byrde for den ansatte for ugen.
				total_employee_workload = workloadByEmployee.get(employee);
				//Laeg aktivitetens byrde til.
				total_employee_workload += workload_per_employee;
				//Gem den i listen på den ansattes plads.
				workloadByEmployee.put(employee, total_employee_workload);
			}
		}
		
		//Fjern alle nedlagte ansatte
		for(Employee employee : workloadByEmployee.keySet())
			if(employee.isDeprecated())
				workloadByEmployee.remove(employee);
		
		return workloadByEmployee;
	}
	
	/*
	 * Container wrappers
	 */
	
	private Week getWeek(int index) {
		//Alloker uger dynamisk.
		if(!weeks.containsKey(index))
			weeks.put(index,new Week(index));
		return weeks.get(index);
	}
	public Week getWeekFromIndex(int index) {
		return getWeek(index);
	}

	private Project getProject(String index) throws UnknownIDException {
		if(!projects.containsKey(index)) throw new UnknownIDException("Unknown project index:"+ index);
		return projects.get(index);
	}
	
	private Activity getActivity(String index) throws UnknownIDException {
		if(!activities.containsKey(index)) throw new UnknownIDException("Unknown activity index:" + index);
		return activities.get(index);
	}
	
	private Employee getEmployee(String initials) throws UnknownIDException {
		initials = initials.toLowerCase();
		if(!employees.containsKey(initials)) throw new UnknownIDException("Unknown initials:" + initials);
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
	
	public HashSet<Activity> findActivity(String pattern) {
		HashSet<Activity> listout = new HashSet<Activity>();
		Pattern p = Pattern.compile(pattern);
		for(Activity a : activities.values()) {
			if(p.matcher(a.getName()).matches()) {
				listout.add(a);
			}
		}
		return listout;
	}
	
	public HashSet<Employee> findEmployee(String pattern) {
		HashSet<Employee> listout = new HashSet<Employee>();
		Pattern p = Pattern.compile(pattern);
		for(Employee employee : employees.values()) {
			if(p.matcher(employee.getName()).matches()) {
				listout.add(employee);
			}
		}
		return listout;
	}	

	public HashSet<Project> findProject(String pattern) {
		HashSet<Project> listout = new HashSet<Project>();
		Pattern p = Pattern.compile(pattern);
		for(Project project : projects.values()) {
			if(p.matcher(project.getName()).matches()) {
				listout.add(project);
			}
		}
		return listout;
	}
}
