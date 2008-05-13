package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import projectplanner.Activity;
import projectplanner.ActivityException;
import projectplanner.Employee;
import projectplanner.EmployeeException;
import projectplanner.Project;
import projectplanner.ProjectException;
import projectplanner.ProjectPlan;
import projectplanner.UnknownIDException;

/**
 * The command line interface for using the program
 * @author Frederik Nordahl Sabroe
 */
public class CommandLineInterface {
	private BufferedReader keyboard;
	private ProjectPlan projectPlan = new ProjectPlan();
	private String command[] = null;
	private int commandInt[] = null;
	private String name;
	private String leader;
	private String initials;
	private int start;
	private int end;
	private boolean startSet = false;
	private boolean endSet = false;
	private String employee;
	private String assistant;
	private String project;
	private String activity;
	private int week;
	private boolean weekSet = false;
	private float hours;
	private boolean hoursSet = false;

	/**
	 * The constructor for the command line interface,  
	 */
	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	public CommandLineInterface() {
		keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(
				"+---------------------------------------+\n" +
				"|Welcome to the Project Planner! rev. 70|\n" +
				"+---------------------------------------+\n" +
				"\n" +
		"Type help for a list of commands");
		while(true){
			try {
				command = splitCommand(getInput());
			} catch (IOException e) {
				e.printStackTrace();
			}
			commandInt = new int[command.length];
			for(int i = 0;i<command.length;i++){
				commandInt[i] = converter(i);
				switch(Commands.values()[commandInt[i]]){
				case NAME:
					name = command[i].substring(5);
					break;
				case LEADER:
					leader = command[i].substring(7);
					break;
				case INITIALS:
					initials = command[i].substring(9);
					break;
				case ID:
					command[i].substring(3);
					break;
				case START:
					start = Integer.parseInt(command[i].substring(6));
					startSet = true;
					break;
				case END:
					end = Integer.parseInt(command[i].substring(4));
					endSet = true;
					break;
				case WEEK2:
					week = Integer.parseInt(command[i].substring(5));
					weekSet = true;
					break;
				case ASSISTANT:
					assistant = command[i].substring(10);
					break;
				case EMPLOYEEARG:
					employee = command[i].substring(9);
					break;
				case PROJECTARG:
					project = command[i].substring(8);
					break;
				case ACTIVITYARG:
					activity = command[i].substring(9);
					break;
				case PROGRESS:
					hours = Float.parseFloat(command[i].substring(9));
					hoursSet =true;
					break;
				}
			}
			if(commandInt[0]==Commands.QUIT.ordinal()){
				System.out.println("Bye!");
				return;
			}
			try {
				functionChooser();
			} catch (UnknownIDException e) {
				System.out.println("Unknown ID Error: "+e);
			} catch (ProjectException e) {
				System.out.println("Project Error: "+e);
			} catch (EmployeeException e) {
				System.out.println("Employee Error: "+e);
			} catch (ActivityException e) {
				System.out.println("Activity Error: "+e);
			}
			name = null;
			leader = null;
			initials = null;
			startSet = false;
			endSet = false;
			project=null;
			activity=null;
			employee=null;
			weekSet=false;
			hoursSet=false;
		}
	}

	private String getInput() throws IOException{
		System.out.print(">");
		return keyboard.readLine();
	}

	private static String[] splitCommand(String cmd) {
		String[] split = cmd.trim().split("\\s");
		for(int i = 0; i < split.length; i++) split[i]=split[i].replace('_', ' ');
		return split;
	}

	private int converter(int i){
		for(Commands comm:Commands.values()){
			if(comm.toString().contains("=")){
				if(command[i].startsWith(comm.toString())){
					return comm.ordinal();
				}
			} else
				if(command[i].equals(comm.toString())){ 
					return comm.ordinal();
				}
		}
		return Commands.NULL.ordinal();
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void functionChooser() throws UnknownIDException, ProjectException, EmployeeException, ActivityException{
		switch(Commands.values()[commandInt[0]]){
		case CREATE:
			createFunc();
			break;
		case DELETE:
			deleteFunc();
			break;
		case EDIT:
			editFunc();
			break;
		case FIND:
			findFunc();
			break;
		case VIEW:
			viewFunc();
			break;
		case HELP:
			helpFunc();
			break;
		case ASSIGN:
			assignFunc();
			break;
		case UNASSIGN:
			unassignFunc();
			break;
		case PROGRESS:
			progressFunc();
			break;
		}
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void createFunc() throws EmployeeException{
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:
			if(name != null){
				if(leader != null) {
					try{
						projectPlan.addProjectWithLeader(name,leader);
					} catch(Exception e) {

					}
				} else {
					projectPlan.addProject(name);
				}
			} else {
				System.out.println("You must specify a name for the project");
			}
			break;
		case ACTIVITY:
			if(name != null){
				projectPlan.addActivity(name);
			} else {
				System.out.println("You must specify a name for the activity");
			}
			break;
		case EMPLOYEE:
			if(name != null){
				if(initials != null) {
					projectPlan.addEmployee(name,initials);
				} else {
					projectPlan.addEmployee(name);
				}
			} else {
				System.out.println("You must specify a name for the employee");
			}
			break;
		}
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void deleteFunc() throws UnknownIDException{
		if(project!=null){
			projectPlan.freezeProject(project);
		} if(activity!=null){
			projectPlan.freezeActivity(activity);
		} if(employee!=null){
			projectPlan.freezeEmployee(employee);
		}
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void editFunc() throws UnknownIDException{
		if(project!=null){
			if(name!=null){
				projectPlan.renameProject(project, name);
			}if(startSet==true){
				projectPlan.setProjectStartWeek(project, start);
			}if(endSet==true){
				projectPlan.setProjectEndWeek(project, end);
			}
		} else if(activity!=null){
			if(name!=null){
				projectPlan.renameActivity(activity, name);
			}
		}
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void findFunc(){
		if(project!=null){
			for(Project proj:projectPlan.findProject(project)) {
				System.out.println(proj.getName()+", "+proj.getId());
			}
		}if(activity!=null){
			for(Activity act:projectPlan.findActivity(activity)) {
				System.out.println(act.getName()+", "+act.getID());
			}
		}if(employee!=null){
			for(Employee emp:projectPlan.findEmployee(employee)) {
				System.out.println("*"+emp.getName()+", "+emp.getInitials());
			}
		}
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void viewFunc() throws UnknownIDException{
		if(project!=null){
			System.out.println("Project Name: "+projectPlan.getProjectName(project));
			System.out.println("Leader: "+(projectPlan.getProjectLeader(project)!=null?projectPlan.getProjectLeader(project).getInitials()+", "+projectPlan.getProjectLeader(project).getName():"No Leader"));
			System.out.println("Start Week: "+projectPlan.getProjectStartWeek(project));
			System.out.println("End Week: "+projectPlan.getProjectEndWeek(project));
			System.out.println("Workload: "+projectPlan.getProjectWorkload(project));
			System.out.println("Progress: "+projectPlan.getProjectProgress(project));
			System.out.println("Assigned Employees:");
			for(Employee key:projectPlan.getEmployeesAssignedToProjcet(project)){
				System.out.println(key.getInitials()+", "+key.getName());
			}
			System.out.println("Activities:");
			for(Activity key:projectPlan.getActivitiesInProject(project)){
				System.out.println(key.getID()+", "+key.getName());
			}
			System.out.println();
		}else if(activity!=null){
			System.out.println("Activity Name: "+projectPlan.getActivityName(activity));
			System.out.println("Parent Project: "+(projectPlan.getActivityParentProject(activity)!=null?projectPlan.getActivityParentProject(activity).getId()+", "+projectPlan.getActivityParentProject(activity).getName():"No Parent Project"));
			System.out.println("Start Week: "+projectPlan.getActivityStartWeek(activity));
			System.out.println("End Week: "+projectPlan.getActivityEndWeek(activity));
			System.out.println("Assigned Employees:");
			for(Employee key:projectPlan.getEmployeesAssignedToActivity(activity)){
				System.out.println(key.getInitials()+", "+key.getName());
			}
			System.out.println("Workload: "+projectPlan.getActivityWorkload(activity));
			System.out.println("Progress: "+projectPlan.getActivityProgress(activity));
			System.out.println();
		}else if(employee!=null){
			System.out.println("Employee Name: "+projectPlan.getEmployeeName(employee));
			System.out.println("Initials: "+employee);
			System.out.println("Work Done:");
			for(Activity key:projectPlan.getWorkDoneByEmployee(employee).keySet()){
				System.out.println(key.getID()+", "+key.getName()+", "+projectPlan.getWorkDoneByEmployee(employee).get(key));
			}
			System.out.println("Assigned Activities:");
			for(Activity key:projectPlan.getActivitiesAssignedToEmployee(employee).keySet()){
				System.out.println(key.getID()+", "+key.getName()+(projectPlan.getActivitiesAssignedToEmployee(employee).get(key)?", (Assisting)":""));
			}
			System.out.println("Leading Projects:");
			for(Project key:projectPlan.getProjectsBeingLeadByEmployee(employee)){
				System.out.println(key.getId()+", "+key.getName());
			}
			System.out.println("Assigned Projects:");
			for(Project key:projectPlan.getProjectsAssignedToEmployee(employee)){
				System.out.println(key.getId()+", "+key.getName());
			}
			System.out.println();
		}else if(commandInt[1]==Commands.ACTIVITY.ordinal()){
			for(Activity act:projectPlan.getActivities().values())
				System.out.println(act.getID()+", "+act.getName());
			System.out.println();
		}else if(commandInt[1]==Commands.PROJECT.ordinal()){
			for(Project act:projectPlan.getProjects().values())
				System.out.println(act.getId()+", "+act.getName());
			System.out.println();
		}else if(commandInt[1]==Commands.EMPLOYEE.ordinal()){
			for(Employee act:projectPlan.getEmployees().values())
				System.out.println(act.getInitials()+", "+act.getName());
			System.out.println();
		}else if(commandInt[1]==Commands.WEEK.ordinal()){
			if(weekSet ==true){
				System.out.println("Number of Activities: "+projectPlan.getNumberOfActivitiesInWeek(week));
				System.out.println("Running Activities:");
				for(Activity key:projectPlan.getActivitiesInWeek(week)){
					System.out.println(key.getID()+", "+key.getName());
				}
				System.out.println("Occupied Employees: "+projectPlan.getWorkloadByEmployeeForWeek(week));
				for(Employee key:projectPlan.getWorkloadByEmployeeForWeek(week).keySet()){
					System.out.println(key.getInitials()+", "+key.getName()+", "+projectPlan.getWorkloadByEmployeeForWeek(week).get(key));
				}
				System.out.println();
			}
		}
	}

	private void helpFunc(){
		System.out.println(
				"+----+\n" +
				"|Help|\n" +
				"+----+\n" +
				"\n" +
				"The program is operated by Commands and the corresponding arguments\n" +
				"\n" +
				"Creating:\n" +
				"A project can be added with f.x. the name \"Sleep\" by using the command:\n" +
				"\"create project name=Sleep\"\n" +
				"A project will be created and assigned an autoegenerated id\n" +
				"NB! All spaces should be replaced by underscores in the commands, they will be\n" +
				"reinterpreted as spaces by the program as spaces\n" +
				"\n" +
				"Lookup:\n" +
				"Looking up a projects id can be done by using the command:\n" +
				"\"find project name=Sleep\"\n" +
				"Where Sleep is the name of the project to lookup for\n" +
				"This also works for activities and employees\n" +
				"\n" +
				"Viewing:\n" +
				"The command \"view project\" shows all the projects (projects can be replaced by\n" +
				"employee or activity)\n" +
				"Details for a specific activity, week, project or employee can also be viewed by\nadding an equals sign and the identifier, f.x. \"view project=080001\"\n" +
				"\n" +
				"Assigning:\n" +
				"An activity can be assigned to a project with the following command:\n" +
				"\"assign activity=1 project=080001\"\n" +
				"The numbers should be replaced by the corresponding IDs\n" +
				"This also goes for assigning activities to emloyees (though the ID should be\n" +
				"replaced with the employees Initials) and assigning employees to projects\n" +
				"Assigning activities is a little bit different, and should be done as follows:\n" +
				"\"assign activity=1 week=4 hours=20\"\n" +
				"where 1 is the id of the activity, 4 is the number of the week, and 20 is the\n" +
				"amount of hours the activity is expected to take\n" +
				"\n" +
				"Unassign:\n" +
				"Unassign uses the same syntax as assigning.\n" +
				"\n" +
				"Editing:\n" +
				"It is possible to edit the name of a project and an activity, as well as the\n" +
				"start-week and end-week of a project.\n" +
				"For example all of them can be edited by using the following command\n" +
				"\"edit project=1 name=newproject start=4 end=5\"\n" +
				"Any of the arguments can be left out, except the project of activity ID\n" +
				"\n" +
				"Registering Work Progress:\n" +
				"Employees can register how much work they have done on a particular activity by\n" +
				"using the following command:\n" +
				"\"progress employee=LaHa hours=10 activity=1\"\n" +
				"Where LaHa is the employee's initials, 10 is the amount of hours, and 1 is the\n" +
				"activity ID");
	}

	@SuppressWarnings("incomplete-switch") //Switches intentionally left incomplete
	private void assignFunc() throws UnknownIDException, ProjectException, EmployeeException, ActivityException{
		if(project!= null){
			if(activity!=null){
				projectPlan.addActivityToProject(activity, project);
			}else if(employee!=null){
				projectPlan.assignEmployeeToProject(employee, project);
			}
		} else if(activity!=null){
			if(employee!=null){
				projectPlan.assignEmployeeToActivity(employee, activity);
			} else if(assistant!=null) {
				projectPlan.assignEmployeeToAssistActivity(assistant, activity);
			} else if(weekSet==true){
				if(hoursSet==true){
					projectPlan.assignActivityToWeek(activity, hours, week);	
				}
			}
		}
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void unassignFunc() throws EmployeeException, UnknownIDException, ProjectException, ActivityException{
		if(project!=null){
			if(employee!=null){
				projectPlan.relieveEmployeeFromProject(employee, project, true);	
			}
		} else if(activity!=null){
			if(employee!=null){
				projectPlan.relieveEmployeeFromActivity(employee, activity);
			} else if(assistant!= null){
				projectPlan.relieveEmployeeFromAssistingActivity(assistant, activity);
			} else if(weekSet==false){
				projectPlan.removeActivityFromWeek(activity, week);
			}
		}
	}

	private void progressFunc() throws EmployeeException, UnknownIDException {
		if(hoursSet == true){
			if(employee != null){
				if(activity != null){
					projectPlan.registerEmployeeProgressInActivity(employee, hours, activity);
				}
			}
		}
	}
}