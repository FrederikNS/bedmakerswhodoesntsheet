package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import projectplanner.*;

/**
 * @author Frederik Nordahl Sabroe
 *
 */
public class CommandLineInterface {
	private BufferedReader keyboard;
	private ProjectPlan projectPlan = new ProjectPlan();
	private String command[] = null;
	private int commandInt[] = null;
	private String name;
	private String leader;
	private String initials;
	private String id;
	private int start;
	private int end;
	private boolean startSet = false;
	private boolean endSet = false;
	private String employee;
	private String project;
	private String activity;
	private int week;
	private boolean weekSet;
	private float progress;
	private boolean progressSet;

	/**
	 * The constructor for the command line interface,  
	 */
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
				if(i>1){
					switch(Arguments.values()[commandInt[i]]){
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
						id = command[i].substring(3);
						break;
					case START:
						start = Integer.parseInt(command [i].substring(6));
						startSet = true;
						break;
					case END:
						end = Integer.parseInt(command[i].substring(4));
						endSet = true;
						break;
					case WEEK:
						week = Integer.parseInt(command[i].substring(5));
						weekSet = true;
					case EMPLOYEEARG:
						employee = command[i].substring(9);
						break;
					case PROJECTARG:
						project = command[i].substring(8);
						break;
					case ACTIVITYARG:
						activity = command[i].substring(9);
						break;
					}
				}
			}
			if(commandInt[0]==Commands.QUIT.ordinal()){
				System.out.println("Bye!");
				return;
			}
			try {
				functionChooser();
			} catch (FrozenException e) {
				System.out.println("Frozen Error: "+e);
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
			id = null;
			startSet = false;
			endSet = false;
		}
	}

	private String getInput() throws IOException{
		System.out.print(">");
		return keyboard.readLine();
	}

	private static String[] splitCommand(String cmd) {
		String[] split = cmd.trim().split("\\s");
		for(int i = 0; i < split.length; i++)  split[i].replace('_', ' ');
		return split;
	}

	private int converter(int i){
		for(Commands comm:Commands.values())
			if(command[i].equals(comm.toString()))
				return comm.ordinal();
		for(Arguments args:Arguments.values())
			if(command[i].startsWith(args.toString()))
				return args.ordinal();
		return Commands.NULL.ordinal();
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void functionChooser() throws FrozenException, UnknownIDException, ProjectException, EmployeeException, ActivityException{
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
		case LIST:
			listFunc();
			break;
		case ASSIGN:
			assignFunc();
			break;
		case UNASSIGN:
			unassignFunc();
			break;
		case RENAME:
			renameFunc();
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
	private void deleteFunc() throws FrozenException, UnknownIDException{
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:
			if(id != null){
				projectPlan.freezeProject(id);
			}
			break;
		case ACTIVITY:
			if(id != null){
				projectPlan.freezeActivity(id);
			}
			break;
		case EMPLOYEE:
			if(initials != null){
				projectPlan.freezeEmployee(initials);
			}
			break;
		}
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void editFunc() throws FrozenException, UnknownIDException{
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:
			if(name != null){
				if(id != null){
					projectPlan.renameProject(id, name);
				}
			} if(startSet ==true){
				projectPlan.setProjectStartWeek(id, start);
			} if(endSet ==true){
				projectPlan.setProjectEndWeek(id, end);
			}
			break;
		case ACTIVITY:
			if(id != null){
				if(name != null){
					projectPlan.renameActivity(id, name);
				}
				/*if(workloadSet ==true){
					projectPlan.
				}*/
			}
			break;
		}
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void findFunc(){
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:
			if(name != null)
				for(Project proj:projectPlan.findProject(name)) {
					System.out.println(proj.getName()+", "+proj.getId());
				}
			break;
		case ACTIVITY:
			if(name != null)
				for(Activity act:projectPlan.findActivity(name)) {
					System.out.println(act.getName()+", "+act.getID());
				}
			break;
		case EMPLOYEE:
			if(name != null)
				for(Employee emp:projectPlan.findEmployee(name)) {
					System.out.println("*"+emp.getName()+", "+emp.getInitials());
				}
			break;
		}
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void listFunc(){
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:
			for(Project proj:projectPlan.getProjects().values())
			System.out.println(proj.getId()+", "+proj.getName());
			break;
		case ACTIVITY:
			for(Activity act:projectPlan.getActivities().values())
				System.out.println(act.getID()+", "+act.getName());
			break;
		case EMPLOYEE:
			for(Employee emp:projectPlan.getEmployees().values())
				System.out.println(emp.getInitials()+", "+emp.getName());
			break;
		}
	}
	
	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void viewFunc() throws UnknownIDException{
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:
			if(id != null){
				System.out.println("Project Name: "+projectPlan.getProjectName(id));
				System.out.println("Leader: "+projectPlan.getProjectLeader(id));
				System.out.println("Start Week: "+projectPlan.getProjectStartWeek(id));
				System.out.println("End Week: "+projectPlan.getProjectEndWeek(id));
				System.out.println("Workload: "+projectPlan.getProjectWorkload(id));
				System.out.println("Progress: "+projectPlan.getProjectProgress(id));
				System.out.println("Activities: "+projectPlan.getActivitiesInProject(id));
			}
			break;
		case ACTIVITY:
			if(id!=null){
				System.out.println("Activity Name: "+projectPlan.getActivityName(id));
				System.out.println("Parent Project: "+projectPlan.getActivityParentProject(id));
				System.out.println("Start Week: "+projectPlan.getActivityStartWeek(id));
				System.out.println("End Week: "+projectPlan.getActivityEndWeek(id));
				System.out.println("Assigned Employees: "+projectPlan.getEmployeesAssignedToActivity(id));
				System.out.println("Workload: "+projectPlan.getActivityWorkload(id));
				System.out.println("Progress: "+projectPlan.getActivityProgress(id));
			}
			break;
		case EMPLOYEE:
			if(initials!=null){
				System.out.println("Employee Name: "+projectPlan.getEmployeeName(initials));
				System.out.println("Work Done: "+projectPlan.getWorkDoneByEmployee(initials));
				System.out.println("Assigned Projects: "+projectPlan.getActivitiesAssignedToEmployee(initials));
				System.out.println("Leading Projects: "+projectPlan.getProjectsBeingLeadByEmployee(initials));
				System.out.println("Assigned Activities: "+projectPlan.getProjectsAssignedToEmployee(initials));
			}
			break;
		case WEEK:
			if(weekSet ==true){
				System.out.println("Number of Activities: "+projectPlan.getNumberOfActivitiesInWeek(week));
				System.out.println("Running Activities: "+projectPlan.getActivitiesInWeek(week));
				System.out.println("Occupied Employees: "+projectPlan.getWorkloadByEmployeeForWeek(week));
				System.out.println("Employees With Spare Time: "+projectPlan.getWorkloadByEmployeeForWeek(week));
			}
		}
	}

	private void helpFunc(){
		System.out.println("");
	}

	@SuppressWarnings("incomplete-switch") //Switches intentionally left incomplete
	private void assignFunc() throws FrozenException, UnknownIDException, ProjectException, EmployeeException, ActivityException{
		switch(Arguments.values()[commandInt[1]]){
		case PROJECTARG:
			switch(Arguments.values()[commandInt[2]]){
			case PROJECTARG:
				System.out.println("Can not assign project to project");
				break;
			case ACTIVITYARG:
				projectPlan.addActivityToProject(activity, project);
				break;
			case EMPLOYEEARG:
				projectPlan.assignEmployeeToProject(employee, project);
				break;
			}
			break;
		case ACTIVITYARG:
			switch(Arguments.values()[commandInt[2]]){
			case PROJECTARG:
				projectPlan.addActivityToProject(activity, project);
				break;
			case ACTIVITYARG:
				System.out.println("Can not assign activity to activity");
				break;
			case EMPLOYEEARG:
				projectPlan.assignEmployeeToActivity(employee, activity);
				break;
			}
			break;
		case EMPLOYEEARG:
			switch(Arguments.values()[commandInt[2]]){
			case PROJECTARG:
				projectPlan.assignEmployeeToProject(employee, project);
				break;
			case ACTIVITYARG:
				projectPlan.assignEmployeeToActivity(employee, activity);
				break;
			case EMPLOYEEARG:
				System.out.println("Can not assign employee to employee");
				break;
			}
			break;
		}
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void unassignFunc() throws FrozenException, EmployeeException, UnknownIDException, ProjectException, ActivityException{
		switch(Arguments.values()[commandInt[1]]){
		case PROJECTARG:
			switch(Arguments.values()[commandInt[2]]){
			case PROJECTARG:
				System.out.println("Can not unassign project from project");
				break;
				/*case ACTIVITYARG:
				projectPlan.
				break;*/
			case EMPLOYEEARG:
				projectPlan.relieveEmployeeFromProject(employee, project, true);
				break;
			}
			break;
		case ACTIVITYARG:
			switch(Arguments.values()[commandInt[2]]){
			/*case PROJECTARG:

				break;*/
			case ACTIVITYARG:
				System.out.println("Can not unassign activity from activity");
				break;
			case EMPLOYEEARG:
				projectPlan.relieveEmployeeFromActivity(employee, activity);
				break;
			case WEEK:
				projectPlan.removeActivityFromWeek(activity, week);
				break;
			}
			break;
		case EMPLOYEEARG:
			switch(Arguments.values()[commandInt[2]]){
			case PROJECTARG:
				projectPlan.relieveEmployeeFromProject(employee, project, true);
				break;
			case ACTIVITYARG:
				projectPlan.relieveEmployeeFromActivity(employee, activity);
				break;
			case EMPLOYEEARG:
				System.out.println("Can not unassign employee from employee");
				break;
			}
			break;
		}
	}

	@SuppressWarnings("incomplete-switch") //Switch intentionally left incomplete
	private void renameFunc() throws FrozenException, UnknownIDException{
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:
			if(name!=null){
				if(id!=null){
					projectPlan.renameProject(id, name);
				}
			}
			break;
		case ACTIVITY:
			if(name!=null){
				if(id!=null){
					projectPlan.renameProject(id, name);
				}
			}
			break;
		}
	}

	private void progressFunc() throws FrozenException, EmployeeException, UnknownIDException {
		if(progressSet == true){
			if(employee != null){
				if(activity != null){
					projectPlan.registerEmployeeProgressInActivity(employee, progress, activity);
				}
			}
		}
	}
}