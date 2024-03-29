package test;


import junit.framework.TestCase;
import projectplanner.Activity;
import projectplanner.ActivityException;
import projectplanner.Employee;
import projectplanner.EmployeeException;
import projectplanner.Project;
import projectplanner.ProjectException;
import projectplanner.ProjectPlan;
import projectplanner.UnknownIDException;

/**
 * @author Kevin Rene Broloes
 *
 */
public class ProjectPlanTest extends TestCase {

	private ProjectPlan projectPlan;
	private Activity activity;
	private Project project;
	private Employee employee;

	//Variables used in the tests, pretty chronological. Saves redeclaring them in each test.
	String activityName = "TestActivity";
	String projectName = "TestProject";
	String employeeName = "TestEmployee";
	String employeeName2 = "Test Employee";
	String initials = "TeEm";
	String newName = "NewName";
	String activity_ID;
	String project_ID;
	int weekIndex = 4;
	int weekend = 10;
	int hours = 15;	

	protected void setUp() throws Exception {
		super.setUp();
		projectPlan = new ProjectPlan();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}


	public void print(String string) {
		System.out.println(string);
	}
	
	public void addActivity() {
		activity_ID = projectPlan.addActivity(activityName);
		activity = projectPlan.getActivities().get(activity_ID);
	}
	
	public void addProject() {
		project_ID = projectPlan.addProject(projectName);
		project = projectPlan.getProjects().get(project_ID);
	}
	
	public void addEmployee() {
		try {
			projectPlan.addEmployee(employeeName, initials);
			employee = projectPlan.getEmployees().get(initials.toLowerCase());
		} catch (EmployeeException e) {
			print("FAIL");
			fail();
		}
	}

	/**
	 * In this simple test, we check if the activity is added correctly.
	 */
	public void testAddActivity(){		
		addActivity();
		assertTrue(activity instanceof Activity);
		assertTrue(projectPlan.findActivity(activityName).contains(activity));
	}
	
	/**
	 * In this simple test, we check if the project is added correctly.
	 */
	public void testAddProject() {
		addProject();
		assertTrue(project instanceof Project);
		assertTrue(projectPlan.findProject(projectName).contains(project));
	}
	
	/**
	 * In this test, we check if the employee is added correctly.
	 * Afterwards, we try to bait the exception by adding the employee again.
	 */
	public void testAddEmployee() {
		addEmployee();
		assertTrue(employee instanceof Employee);
		assertTrue(projectPlan.findEmployee(employeeName).contains(employee));
		
		try {
			projectPlan.addEmployee(employeeName, initials);
			fail();
		} catch (EmployeeException e) {
			print("Test Passed");
		}
	}
	
	/**
	 * In this test, we attempt to add projects with an employee set as a leader.
	 * We then try to make it catch the exception with an invalid leader.
	 */
	public void testAddProjectWithLeader() {
//First, we try assigning a valid leader.
		testAddEmployee();
		try {
			String project_ID = projectPlan.addProjectWithLeader(projectName, initials);
			project = projectPlan.getProjects().get(project_ID);
			
			assertTrue(project.getLeader() == employee);
			assertTrue(project instanceof Project);
			assertTrue(projectPlan.findProject(projectName).contains(project));
			
			//Invalid leader initials. Testing if the exception catches fire.
			try {
				projectPlan.addProjectWithLeader(projectName, "12Kc92Q4werad45");
				fail();//Test fails if it succeeds to add an invalid leader to the project.
			} catch (UnknownIDException e) {
				print("The leader ID does not exist\nTest Passed.");
			}
		} catch (UnknownIDException e) {
			print("The leader ID does not exist");
			fail();
		} catch (EmployeeException e) {
			print("FAIL");
			fail();
		}
	}
	
	/**
	* In this test, we test assigning activities to weeks, as well as assigning
	* activities that do not exist or are frozen. At the same time, we'll test if
	* the freeze function works.
	*/
	public void testAssignActivityToWeek() {
		addActivity();
		int actHours = 0;
		int weekHours = 0;
		
//First, a regular activity that should work out fine.
		try {
			projectPlan.assignActivityToWeek(activity_ID, hours, weekIndex);
			actHours = (int)activity.getWorkloadForWeek(projectPlan.getWeekFromIndex(weekIndex));
			weekHours = (int)((projectPlan.getWeekFromIndex(weekIndex)).getAssignedHours());
			
			//We assert that the week returned should contain the hours specified.
			assertEquals(hours, weekHours);
			assertEquals(weekHours, actHours);
			
			//We then try to add an activity with an unknown ID
			try {
				projectPlan.assignActivityToWeek("42", hours, weekIndex);
				fail();
			} catch (UnknownIDException e) {
				// We should reach this exception, and it'll pass (by avoiding the fails specified above).
				print("Activity could not be found from ID\nTest Passed.");
			}	
			
			//We then try freezing a valid activity and then test to see if we can 
			//assign it to a week. This serves to test both the freeze method and the freeze exception.
			projectPlan.deprecateActivity(activity_ID);
			
		} catch (UnknownIDException e) {
			print("Activity could not be found from ID");
			fail();
		} catch (ActivityException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * In this test, we try to remove an activity.
	 */
	public void testRemoveActivityFromWeek() {
		addActivity();
		
//First, adding a regular activity that should work out fine.
		try {
			projectPlan.assignActivityToWeek(activity_ID, hours, weekIndex);
			projectPlan.removeActivityFromWeek(activity_ID, weekIndex);
//We then assert the week is empty after removing the activity, knowing we only added the one.
//This will assure us the activity was removed.
			assertTrue((projectPlan.getWeekFromIndex(weekIndex)).isEmpty());
		} catch (UnknownIDException e) {
			print("Activity could not be found from ID");
			fail();
		}
	}

	/**
	 * This test will attempt to add an activity to an existing project.
	 * Then we try to bait the project exception by adding the same activity twice.
	 */
	public void testAddActivityToProject(){
		addActivity();
		addProject();
		
		try {
			projectPlan.addActivityToProject(activity_ID, project_ID);
			assertTrue(project.containsActivity(activity));
			
			//Baiting the exception:
			try {
				projectPlan.addActivityToProject(activity_ID, project_ID);
				fail();
			} catch (ProjectException e) {
				print("Project already contains activity\nTest Passed.");
			}
			
		} catch (UnknownIDException e) {
			print("Activity or project ID unknown");
			fail();
		} catch (ProjectException e) {
			print("Project already contains activity");
			fail();
		}
	}	
	
	/**
	 * This test will test the following:
	 * Assigning a leader to it and setting a start and end week for it.
	 */
	public void testProjectFeatures() {
		addProject();
		addEmployee();
		
//We assign a leader to the project, and test to see if he is assigned properly.
//Then the project is assigned to a start- and endweek.
		try {
			projectPlan.assignLeaderToProject(initials, project_ID);
			assertEquals(project.getLeader(), employee);
			
			projectPlan.setProjectStartWeek(project_ID, weekIndex);				
			projectPlan.setProjectEndWeek(project_ID, weekend);
			assertTrue(weekIndex == project.getStartWeek());
			assertTrue(weekend == project.getEndWeek());
		} catch (UnknownIDException e) {
			print("ID unknown");
			fail();
		}		
	}

	/**
	 * This test will assign an employee to a project.
	 * Afterwards, we'll test relieving the employee from the project.
	 */
	public void testAssignEmployeeToProject() {
		addEmployee();
		addProject();
		
		try {
			projectPlan.assignEmployeeToProject(initials, project_ID);
			assertTrue(project.containsEmployee(employee));
			projectPlan.relieveEmployeeFromProject(initials, project_ID, false);
			assertFalse(project.containsEmployee(employee));
		} catch (EmployeeException e) {
			print("FAIL");
			fail();
		} catch (UnknownIDException e) {
			print("Unknown ID");
			fail();
		} catch (ProjectException e) {
			print("FAIL");
			fail();
		}		
	}
	
	/**
	 * In this test, we'll assign an employee to an activity, and then remove it.
	 */
	
	public void testAssignEmployeeToActivity() {
		addActivity();
		addEmployee();
		addProject();
		
		try {
			projectPlan.assignEmployeeToProject(initials, project_ID);
			projectPlan.addActivityToProject(activity_ID, project_ID);
			projectPlan.assignEmployeeToActivity(initials, activity_ID);
			
			assertTrue(projectPlan.isEmployeeAssignedToActivity(initials, activity_ID));
		} catch (EmployeeException e1) {
			e1.printStackTrace();
		} catch (UnknownIDException e1) {
			e1.printStackTrace();
		} catch (ProjectException e1) {
			e1.printStackTrace();
		} catch (ActivityException e) {
			print("lol");
		}
		
		
// Relieving the employee
		try {
			projectPlan.relieveEmployeeFromActivity(initials, activity_ID);
		} catch (EmployeeException e) {
			e.printStackTrace();
		} catch (UnknownIDException e) {
			e.printStackTrace();
		} catch (ActivityException e) {
			e.printStackTrace();
		}
		assertFalse(employee.isAssignedToActivity(activity));
	}
	
	/**
	 * In this test, we will test running a test, testing assigning an employee to assist an activity.
	 * Afterwards, we will remove the employee.
	 */
	
	public void testAssignEmployeeToAssistActivity() {
		addActivity();
		addEmployee();
		
		try {
			projectPlan.assignEmployeeToAssistActivity(initials, activity_ID);
			assertTrue(projectPlan.isEmployeeAssignedToActivityAsAssistant(initials, activity_ID));
			projectPlan.relieveEmployeeFromAssistingActivity(initials, activity_ID);
			assertFalse(projectPlan.isEmployeeAssignedToActivityAsAssistant(initials, activity_ID));
		} catch (EmployeeException e) {
			e.printStackTrace();
		} catch (UnknownIDException e) {
			e.printStackTrace();
		} catch (ActivityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * In this test, we will register employee progress to an activity.
	 */
	
	public void testRegisterEmployeeProgressInActivity() {
		addEmployee();
		addActivity();
		addProject();
		
		try {
			projectPlan.addActivityToProject(activity_ID, project_ID);
			projectPlan.assignEmployeeToProject(initials, project_ID);
			projectPlan.assignEmployeeToActivity(initials, activity_ID);
			projectPlan.registerEmployeeProgressInActivity(initials, hours, activity_ID);
			assertTrue(projectPlan.getActivityProgress(activity_ID) == hours);
			print("Everything has passed! We win x-mas!");
		} catch (EmployeeException e) {
			print("FAIL!");
			fail();
		} catch (UnknownIDException e) {
			print("Unknown ID");
			fail();
		} catch (ActivityException e) {
			print("FAIL2!");
			fail();
		} catch (ProjectException e) {
			print("FAIL3");
			fail();
		}
	}
}
