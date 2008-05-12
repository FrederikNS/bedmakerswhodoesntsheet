package test;


import junit.framework.TestCase;
import projectplanner.Activity;
import projectplanner.ActivityException;
import projectplanner.Employee;
import projectplanner.EmployeeException;
import projectplanner.FrozenException;
import projectplanner.Project;
import projectplanner.ProjectException;
import projectplanner.ProjectPlan;
import projectplanner.UnknownIDException;

public class ProjectPlanTest extends TestCase {

	
	/**
	 * This code is currently unreadable. I'm currently cleaning it up, but until then:
	 * Enjoy.
	 * Edit: The code is slightly more readable, but I'm going to bed now..
	 */
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
		//TODO: MARK
		activity = projectPlan.getActivities().get(activity_ID);
	}
	
	public void addProject() {
		project_ID = projectPlan.addProject(projectName);
		//TODO: MARK
		project = projectPlan.getProjects().get(project_ID);
	}
	
	public void addEmployee() {
		try {
			projectPlan.addEmployee(employeeName, initials);
		} catch (EmployeeException e) {
			print("FAIL");
			fail();
		}
		//TODO: MARK
		employee = projectPlan.getEmployees().get(initials);
	}

	/**
	 * In this simple test, we check if the activity is added correctly.
	 */
	public void testAddActivity(){		
		String activity_ID = projectPlan.addActivity(activityName);
		//TODO: MARK
		activity = projectPlan.getActivities().get(activity_ID);
		assertTrue(activity instanceof Activity);
		assertTrue(projectPlan.findActivity(activityName).contains(activity));
	}
	
	/**
	 * In this simple test, we check if the project is added correctly.
	 */
	public void testAddProject() {
		String project_ID = projectPlan.addProject(projectName);
		//TODO: MARK
		project = projectPlan.getProjects().get(project_ID);
		assertTrue(project instanceof Project);
		assertTrue(projectPlan.findProject(projectName).contains(project));
	}
	
	/**
	 * In this test, we check if the employee is added correctly.
	 * Afterwards, we try to bait the exception by adding the employee again.
	 */
	public void testAddEmployee() {
		try {
			projectPlan.addEmployee(employeeName, initials);
		} catch (EmployeeException e) {
			print("FAIL");
			fail();
		}
		//TODO: MARK
		employee = projectPlan.getEmployees().get(initials);
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
			//TODO: MARK
			project = projectPlan.getProjects().get(project_ID);
		} catch (UnknownIDException e) {
			print("The leader ID does not exist");
			fail();
		}		
		//TODO: MARK
		project.getLeader();
		assertTrue(project instanceof Project);
		assertTrue(projectPlan.findProject(projectName).contains(project));
		assertEquals(project.getLeader(), employee);
		
//Invalid leader initials. Testing if the exception catches fire.
		try {
			projectPlan.addProjectWithLeader(projectName, "12Kc92Q4werad45");
			fail();//Test fails if it succeeds to add an invalid leader to the project.
		} catch (UnknownIDException e) {
			print("The leader ID does not exist\nTest Passed.");
		}
		
	}
	
	/**
	* In this test, we test assigning activities to weeks, as well as assigning
	* activities that do not exist or are frozen. At the same time, we'll test if
	* the freeze function works.
	*/
	public void testAssignActivityToWeek() {
		addActivity();
		
//First, a regular activity that should work out fine.
		try {
			projectPlan.assignActivityToWeek(activity_ID, hours, weekIndex);
		} catch (FrozenException e) {
			print("Activity is frozen and unavailable");
			fail();
		} catch (UnknownIDException e) {
			print("Activity could not be found from ID");
			fail();
		}
//TODO: Not sure if this getWeekFromIndex I'm using is a good idea.. 
// I made it myself to get the week, but we might want to do it differently.
		int actHours = 0;
		try {
			actHours = (int)activity.getHoursForWeek(projectPlan.getWeekFromIndex(weekIndex));
		} catch (ActivityException e2) {
			e2.printStackTrace();
		}
		int weekHours = 0;
		try {
			//TODO: MARK
			weekHours = (int)((projectPlan.getWeekFromIndex(weekIndex)).getAssignedHours());
		} catch (ActivityException e1) {
			e1.printStackTrace();
		}
		//We assert that the week returned should contain the hours specified.
		assertEquals(hours, weekHours);
		assertEquals(weekHours, actHours);
		
//We then try to add an activity with an unknown ID
		try {
			projectPlan.assignActivityToWeek("42", hours, weekIndex);
			fail();
		} catch (FrozenException e) {
			print("Activity is frozen and unavailable");
			fail();
		} catch (UnknownIDException e) {
			// We should reach this exception, and it'll pass (by avoiding the fails specified above).
			print("Activity could not be found from ID\nTest Passed.");
		}
		
//We then try freezing a valid activity and then test to see if we can 
//assign it to a week. This serves to test both the freeze method and the freeze exception.
		try {
			projectPlan.freezeActivity(activity_ID);
		} catch (FrozenException e) {
			print("Activity is already frozen");
			fail();
		} catch (UnknownIDException e) {
			print("Activity could not be found from ID");
			fail();
		}
		//Adding the frozen activity.
		try {
			projectPlan.assignActivityToWeek(activity_ID, hours, weekIndex);
			fail();
		} catch (FrozenException e) {
			print("Activity is frozen and unavailable\nTest Passed.");
		} catch (UnknownIDException e) {
			print("Activity could not be found from ID");
			fail();
		}
	}
	
	/**
	 * In this test, we try to remove an activity,
	 * We expect the exception to trigger correctly if the activity is frozen,
	 * since this is tested in the previous tests. We expect the same of the ID exception.
	 */
	public void testRemoveActivityFromWeek() {
		addActivity();
		
//First, adding a regular activity that should work out fine.
		try {
			projectPlan.assignActivityToWeek(activity_ID, hours, weekIndex);
		} catch (FrozenException e) {
			print("Activity is frozen and unavailable");
			fail();
		} catch (UnknownIDException e) {
			print("Activity could not be found from ID");
			fail();
		}
		
//Then we remove it. Knowing from the last test that it adds correctly.
		try {
			projectPlan.removeActivityFromWeek(activity_ID, weekIndex);
		} catch (FrozenException e) {
			print("The activity is frozen and cannot be removed");
			fail();
		} catch (UnknownIDException e) {
			print("The activity ID cannot be found.");
			fail();
		}
//We then assert the week is empty after removing the activity, knowing we only added the one.
//This will assure us the activity was removed.
		//TODO: MARK
		assertTrue((projectPlan.getWeekFromIndex(weekIndex)).isEmpty());
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
		} catch (FrozenException e) {
			print("Activity or project is frozen");
			fail();
		} catch (UnknownIDException e) {
			print("Activity or project ID unknown");
			fail();
		} catch (ProjectException e) {
			print("Project already contains activity");
			fail();
		}
		//TODO: MARK
		assertTrue(project.containsActivity(activity));
		
//Baiting the exception:
		try {
			projectPlan.addActivityToProject(activity_ID, project_ID);
			fail();
		} catch (FrozenException e) {
			print("Activity or project is frozen");
			fail();
		} catch (UnknownIDException e) {
			print("Activity or project ID unknown");
			fail();
		} catch (ProjectException e) {
			print("Project already contains activity\nTest Passed.");
		}
	}	
	
	/**
	 * This test will test the following:
	 * Assigning a leader to it and setting a start and end week for it.
	 */
	public void testProjectFeatures() {
		addProject();
		addEmployee();
		
//We then Assign a leader to it, and test to see if he is assigned properly.
		
		try {
			projectPlan.assignLeaderToProject(initials, project_ID);
		} catch (FrozenException e) {
			print("Project frozen");
			fail();
		} catch (UnknownIDException e) {
			print("ID unknown");
			fail();
		}
		//TODO: MARK
		assertEquals(project.getLeader(), employee);
		
//Then the project is assigned to a start- and endweek.
		try {
				projectPlan.setProjectStartWeek(project_ID, weekIndex);
		} catch (UnknownIDException e) {
			print("ID unknown");
			fail();
		} catch (FrozenException e) {
			print("Project Frozen");
			fail();
		}
		try {
			projectPlan.setProjectEndWeek(project_ID, weekend);
		} catch (FrozenException e) {
			print("Project Frozen");
			fail();
		} catch (UnknownIDException e) {
			print("ID unknown");
			fail();
		}
		//TODO: MARK
		assertTrue(weekIndex == project.getStartWeek());
		assertTrue(weekend == project.getEndWeek());
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
		} catch (FrozenException e) {
			print("Project or Employee frozen");
			fail();
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
		//TODO: MARK
		assertTrue(project.containsEmployee(employee));
		
//We then try to relieve the employee from the project:
		try {
			projectPlan.relieveEmployeeFromProject(initials, project_ID, false);
		} catch (FrozenException e) {
			print("Project is frozen");
			fail();
		} catch (EmployeeException e) {
			print("FAIL");
			fail();
		} catch (UnknownIDException e) {
			print("Project or Employee ID not found");
			fail();
		} catch (ProjectException e) {
			print("FAIL");
			fail();
		}
		//MARK: TODO
		assertFalse(project.containsEmployee(employee));
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
		} catch (FrozenException e1) {
			e1.printStackTrace();
		} catch (EmployeeException e1) {
			e1.printStackTrace();
		} catch (UnknownIDException e1) {
			e1.printStackTrace();
		} catch (ProjectException e1) {
			e1.printStackTrace();
		}
		
		try {
			projectPlan.assignEmployeeToActivity(initials, activity_ID);
		} catch (FrozenException e) {
			e.printStackTrace();
		} catch (EmployeeException e) {
			e.printStackTrace();
		} catch (UnknownIDException e) {
			e.printStackTrace();
		} catch (ActivityException e) {
			print("lol");
		}
		//TODO: MARK
		assertTrue(employee instanceof Employee);
		assertTrue(employee.isAssignedToActivity(activity));
		
// Relieving the employee
		try {
			projectPlan.relieveEmployeeFromActivity(initials, activity_ID);
		} catch (FrozenException e) {
			e.printStackTrace();
		} catch (EmployeeException e) {
			e.printStackTrace();
		} catch (UnknownIDException e) {
			e.printStackTrace();
		} catch (ActivityException e) {
			e.printStackTrace();
		}
		//TODO: MARK
		assertFalse(employee.isAssignedToActivity(activity));
	}
	
	/**
	 * In this test, we will test running a test, testing assigning and employee to assist an activity.
	 */
	
	public void testAssignEmployeeToAssistActivity() {
		
	}
}
