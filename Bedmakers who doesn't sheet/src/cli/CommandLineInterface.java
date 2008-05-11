package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import projectplanner.*;

public class CommandLineInterface {
	private BufferedReader keyboard;
	ProjectPlan projectPlan = new ProjectPlan();
	String command[] = null;
	int commandInt[] = null;
	String name;
	String leader;
	String initials;
	String id;
	int start;
	int end;
	boolean startSet = false;
	boolean endSet = false;

	public CommandLineInterface(){
		keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(
				"+---------------------------------------+\n" +
				"|Welcome to the Project Planner! rev. 70|\n" +
				"+---------------------------------------+\n" +
				"\n" +
		"Type help for a list of commands");
		while(true){
			getInput();
			commandInt = new int[command.length];
			for(int i = 0;i<command.length;i++){
				String comm = command[i];
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
						end = Integer.parseInt(command[i].substring(6));
						endSet = true;
						break;
					}
				}
			}
			if(commandInt[0]==Commands.QUIT.ordinal()){
				System.out.println("Bye!");
				return;
			}
			functionChooser();
			name = null;
			leader = null;
			initials = null;
			id = null;
		}
	}

	public static String[] splitCommand(String cmd) {
		char[] in = cmd.trim().toCharArray();
		int num_cits = 0;

		//Konverter alle mellemrum i citationstegn til underscores
		// create project name="create project name=hej" leader="Hund"
		// ->
		// create project name="create_project_name=hej" leader="Hund"

		for(int i = 0; i < in.length; i++) {
			if(in[i]=='\"') {
				num_cits++;
			} else if(in[i]==' ' && num_cits % 2 == 1) {
				in[i] = '_';
			}
		}
		System.out.println(in);

		//Fjern alle citationstegn
		// create project name="create_project_name=hej" leader="Hund"
		// ->
		// create project name=create_project_name=hej leader=Hund

		char[] charstosplit = new char[in.length];
		int pos = 0;
		for(int i = 0; i < charstosplit.length; i++) {
			if(in[i]!='\"') charstosplit[pos++] = in[i];
		}
		System.out.println(charstosplit);

		//Split op!
		String[] split = new String(charstosplit).split("\\s");

		//Lav alle _ til mellemrum:
		for(String s : split) {
			s.replace('_', ' ');
		}
	}

	public int converter(int i){
		for(Commands comm:Commands.values())
			if(command[i].equals(comm.toString()))
				return comm.ordinal();
		for(Arguments args:Arguments.values())
			if(command[i].startsWith(args.toString()))
				return args.ordinal();
		return Commands.NULL.ordinal();
	}

	public void getInput(){
		System.out.println(
				"Please choose a function:\n");
		try {
			command = keyboard.readLine().split(" ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void functionChooser(){
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
		case RENAME:
			renameFunc();
			break;
		}
	}

	public void createFunc(){
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
					try{
						projectPlan.addEmployee(name);
					} catch(Exception e) {
						//TODO make exception handling
					}
				}
			} else {
				System.out.println("You must specify a name for the employee");
			}
			break;
		}
	}
	public void deleteFunc(){
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:
			/*if(name != null){

			} else */if(id != null){
				try{
					projectPlan.freezeProject(id);
				} catch(Exception e) {

				}
			}
			break;
		case ACTIVITY:
			if(id != null){
				try{
					projectPlan.freezeActivity(id);
				} catch(Exception e) {
					//TODO make exception handling
				}
			}
			break;
		case EMPLOYEE:
			/*if(name != null){

			} else*/ if(initials != null){
				try{
					projectPlan.freezeEmployee(initials);
				} catch(Exception e) {

				}
			}
			System.out.println("Employee cannot be removed at the moment");
			//FIXME Make employees freezeable
			break;
		}
	}
	public void editFunc(){
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:
			if(name != null){
				if(id != null){
					try {
						projectPlan.renameProject(id, name);
					} catch (FrozenException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnknownIDException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} if(startSet ==true){
				try {
					projectPlan.setProjectStartWeek(id, start);
				} catch (FrozenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownIDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} if(endSet ==true){
				try {
					projectPlan.setProjectEndWeek(id, end);
				} catch (FrozenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownIDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case ACTIVITY:
			if(name != null){
				if(id != null){
					try {
						projectPlan.renameActivity(id, name);
					} catch (FrozenException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnknownIDException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			break;
		}
	}
	public void findFunc(){
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:
			if(name != null)
				projectPlan.findProject(name);
			break;
		case ACTIVITY:
			if(name != null)
				projectPlan.findActivity(name);
			break;
		case EMPLOYEE:
			if(name != null)
				projectPlan.findEmployee(name);
			break;
		}
	}
	public void viewFunc(){
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
				System.out.println("Parent Project: "+projectPlan.getParentProjectToActivity(id));
				System.out.println("Start Week: "+projectPlan.getActivityStartWeek(id));
				System.out.println("End Week: "+projectPlan.getActivityEndWeek(id));
				System.out.println("Workload: "+projectPlan.getActivityWorkload(id));
				System.out.println("Progress: "+projectPlan.getActivityProgress(id));
			}
			break;
		case EMPLOYEE:
			if(initials!=null){
				System.out.println("Employee Name: "+projectPlan.getEmployeeName(id));
				System.out.println("Assigned Projects: "+projectPlan.getActivitiesAssignedToEmployee(id));
				System.out.println("Leading Projects: "+projectPlan.getProjectsBeingLeadByEmployee(id));
				System.out.println("Assigned Activities: "+projectPlan.getProjectsAssignedToEmployee(id));
				System.out.println("Assisting Activities: "+projectPlan.getActivitiesAssistedByEmployee(id));
			}
			break;
		case WEEK:
			if(id != null){
				int number = Integer.parseInt(id);
				System.out.println("Number of Projects: ");
				System.out.println("Number of Activities: ");
				System.out.println("Running Projects: ");
				System.out.println("Running Activities: ");
				System.out.println("Occupied Employees: ");
				System.out.println("Employees With Spare Time: ");
			}
		}
	}
	public void helpFunc(){
		System.out.println("");
	}
	public void assignFunc(){
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:

			break;
		case ACTIVITY:

			break;
		case EMPLOYEE:

			break;
		}

		switch(Commands.values()[commandInt[2]]){
		case PROJECT:

			break;
		case ACTIVITY:

			break;
		case EMPLOYEE:

			break;
		}
	}
	public void unassignFunc(){
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:

			break;
		case ACTIVITY:

			break;
		case EMPLOYEE:

			break;
		}

		switch(Commands.values()[commandInt[2]]){
		case PROJECT:

			break;
		case ACTIVITY:

			break;
		case EMPLOYEE:

			break;
		}
	}
	public void renameFunc(){
		switch(Commands.values()[commandInt[1]]){
		case PROJECT:

			break;
		case ACTIVITY:

			break;
		case EMPLOYEE:

			break;
		}
	}
}