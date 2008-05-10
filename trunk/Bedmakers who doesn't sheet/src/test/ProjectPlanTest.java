package test;

import java.util.HashMap;

import junit.framework.TestCase;
import projectplanner.Activity;
import projectplanner.Employee;
import projectplanner.FrozenException;
import projectplanner.Project;
import projectplanner.ProjectPlan;
import projectplanner.UnknownIDException;

public class ProjectPlanTest extends TestCase {

	
	/**
	 * This code is currently unreadable. I'm currently cleaning it up, but until then:
	 * Enjoy.
	 */
	private ProjectPlan projectPlan;
	private Activity active;
	private Project project;
	private Employee employee;
	private HashMap<String,Activity> activities;
	private HashMap<String,Project> projects;
	private HashMap<String,Employee> employees;

	protected void setUp() throws Exception {
		super.setUp();
		projectPlan = new ProjectPlan();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * In this simple test, we check if the activity is added correctly.
	 */
	public void testAddActivity(){
		String activityName = "TestActivity";
		projectPlan.addActivity(activityName);
		activities = projectPlan.getActivities();
		active = activities.get((projectPlan.findActivityID(activityName)));
		assertTrue(active instanceof Activity);
		assertTrue(projectPlan.findActivity(activityName).contains(active));
	}
	
	/**
	 * In this simple test, we check if the project is added correctly.
	 */
	public void testAddProject() {
		String projectName = "TestProject";
		projectPlan.addProject(projectName);
		projects = projectPlan.getProjects();
		project = projects.get(projectPlan.findProjectID(projectName));
		assertTrue(project instanceof Project);
		assertTrue(projectPlan.findProject(projectName).contains(project));
	}
	
	/**
	 * In this simple test, we check if the employee is added correctly.
	 */
	public void testAddEmployee1() {
		String employeeName = "TestEmployee";
		String initials = "TE";
		projectPlan.addEmployee(employeeName, initials);
		employees = projectPlan.getEmployees();
		employee = employees.get(initials);
		assertTrue(employee instanceof Employee);
		assertTrue(projectPlan.findEmployee(employeeName).contains(employee));
	}
	
	/**
	 * In this test, we try to add the employee with the feature to
	 * automatically generate the initials.
	 * Afterwards, we attempt to trigger the exception.
	 */
	public void testAddEmployee2() {
		String employeeName = "Test Employee";
		String initials = "TeEm"; //What the initials should be, if successful.
		try {
			projectPlan.addEmployee(employeeName);
		} catch (Exception e) {
			System.out.println("Initials could not be created from name");
			fail();
		}
//Test to try and trigger the exception.
		String failName = "T e"; //Should fail, since it doesn't contain two letters in each name.
		try {
			projectPlan.addEmployee(failName);
			fail(); //The test fails if it succeeds to add an employee with the invalid name.
		} catch (Exception e) {
			System.out.println("Initials could not be created from name.\nTest Passed.");
		}
		employees = projectPlan.getEmployees();
		employee = employees.get(initials);
		String generatedInitials = projectPlan.findEmployeeID(employeeName);
/* 
 * We first test if the employee object is added.
 * Then we see if it's in fact the correct employee, and finally, we check to see
 * if the initials match the expected result.
 */
		assertTrue(employee instanceof Employee);
		assertTrue(projectPlan.findEmployee(employeeName).contains(employee));
		assertEquals(initials, generatedInitials);
	}
	
	/**
	 * In this test, we attempt to add projects with an employee set as a leader.
	 * We then try to make it catch the exception with an invalid leader.
	 */
	public void testAddProjectWithLeader() {
//First, we try assigning a valid leader.
		String projectName = "TestProject";
		String employeeName = "TestEmployee";
		String initials = "TE";
		projectPlan.addEmployee(employeeName, initials);
		String leaderInit = initials;
		try {
			projectPlan.addProjectWithLeader(projectName, leaderInit);
		} catch (UnknownIDException e) {
			System.out.println("The leader ID does not exist");
			fail();
		}
		projects = projectPlan.getProjects();
		project = projects.get(projectPlan.findProjectID(projectName));
		project.getLeader();
		employees = projectPlan.getEmployees();
		employee = employees.get(initials);
		assertTrue(project instanceof Project);
		assertTrue(projectPlan.findProject(projectName).contains(project));
		assertEquals(project.getLeader(), employee);
//Invalid leader initials. Testing if the exception catches fire.
		try {
			projectPlan.addProjectWithLeader(projectName, "12Kc92Q4werad45");
			fail();//Test fails if it succeeds to add an invalid leader to the project.
		} catch (UnknownIDException e) {
			System.out.println("The leader ID does not exist\nTest Passed.");
		}
		
	}
	
	/**
	* In this test, we test assigning activities to weeks, as well as assigning
	* activities that do not exist or are frozen. At the same time, we'll test if
	* the freeze function works.
	**/
	public void testAssignActivityToWeek() {
		String activityName = "TestActivity";
		int weekIndex = 4;
		int hours = 15;
		projectPlan.addActivity(activityName);
		
//First, a regular activity that should work out fine.
		try {
			projectPlan.assignActivityToWeek(projectPlan.findActivityID(activityName), hours, weekIndex);
		} catch (FrozenException e) {
			System.out.println("Activity is frozen and unavailable");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("Activity could not be found from ID");
			fail();
		}
//TODO: Not sure if this getWeekFromIndex I'm using is a good idea.. 
// I made it myself to get the week, but we might want to do it differently.
		int temp = (int)((projectPlan.getWeekFromIndex(weekIndex)).getAssignedHours());
		//We assert that the week returned should contain the hours specified.
		assertEquals(hours, temp);
		
//We then try to add an activity with an unknown ID
		try {
			projectPlan.assignActivityToWeek("50", hours, weekIndex);
			fail();
		} catch (FrozenException e) {
			System.out.println("Activity is frozen and unavailable");
			fail();
		} catch (UnknownIDException e) {
			// We should reach this exception, and it'll pass (by avoiding the fails specified above).
			System.out.println("Activity could not be found from ID\nTest Passed.");
		}
		
//We then try freezing a valid activity and then test to see if we can 
//assign it to a week. This serves to test both the freeze method and the freeze exception.
		try {
			projectPlan.freezeActivity(projectPlan.findActivityID(activityName));
		} catch (FrozenException e) {
			System.out.println("Activity is already frozen");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("Activity could not be found from ID");
			fail();
		}
		//Adding the frozen activity.
		try {
			projectPlan.assignActivityToWeek(projectPlan.findActivityID(activityName), hours, weekIndex);
			fail();
		} catch (FrozenException e) {
			System.out.println("Activity is frozen and unavailable\nTest Passed.");
		} catch (UnknownIDException e) {
			System.out.println("Activity could not be found from ID");
			fail();
		}
	}
	
	/**
	 * In this test, we try to remove an activity,
	 * We expect the exception to trigger correctly if the activity is frozen,
	 * since this is tested in the previous tests. We expect the same of the ID exception.
	 */
	public void testRemoveActivityFromWeek() {
		String activityName = "TestActivity";
		int weekIndex = 4;
		int hours = 15;
		projectPlan.addActivity(activityName);
		
//First, adding a regular activity that should work out fine.
		try {
			projectPlan.assignActivityToWeek(projectPlan.findActivityID(activityName), hours, weekIndex);
		} catch (FrozenException e) {
			System.out.println("Activity is frozen and unavailable");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("Activity could not be found from ID");
			fail();
		}
//Then we remove it. Knowing from the last test that it adds correctly.
		try {
			projectPlan.removeActivityFromWeek(projectPlan.findActivityID(activityName), weekIndex);
		} catch (FrozenException e) {
			System.out.println("The activity is frozen and cannot be removed");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("The activity ID cannot be found.");
			fail();
		}
//We then assert the week is empty after removing the activity, knowing we only added the one.
//This will assure us the activity was removed.
		assertTrue((projectPlan.getWeekFromIndex(weekIndex)).isEmpty());
	}
	
	/**
	 * In this test, we try to add an existing activity to a project.
	 * Afterwards, we try to remove it.
	 * The exceptions are tested in previous tests.
	 */
	public void testAddRemoveActivityToProject() {
//First, we add an activity and a project. We know this works.
		String activityName = "TestActivity";
		String projectName = "TestProject";
		projectPlan.addActivity(activityName);
		projectPlan.addProject(projectName);
		try {
			projectPlan.addActivityToProject(projectPlan.findActivityID(activityName), projectPlan.findProjectID(projectName));
		} catch (FrozenException e) {
			System.out.println("The activity or project is frozen");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("The activity or project ID could not be found");
			fail();
		}
		projects = projectPlan.getProjects();
		project = projects.get(projectPlan.findProjectID(projectName));
//Knowing we only added the one activity, we check to see if the project contains it.
		assertEquals(project.getActivities(), projectPlan.findActivity(activityName));
		
//We then try to remove it again.
		try {
			projectPlan.removeActivityFromProject(projectPlan.findActivityID(activityName), projectPlan.findProjectID(projectName));
		} catch (FrozenException e) {
			System.out.println("The activity or project is frozen");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("The activity or project ID could not be found");
			fail();
		}
//We assert it should now not contain the activity.
		assertNotSame(project.getActivities(), projectPlan.findActivity(activityName));
		assertFalse(project.getActivities().contains(projectPlan.findActivity(activityName)));
	}

	/**
	 * This test will attempt to rename an activity.
	 * Exceptions tested in previous tests.
	 */
	public void testRenameActivity() {
		String activityName = "TestActivity";
		String newName = "NewName";
		projectPlan.addActivity(activityName);
		try {
			projectPlan.renameActivity(projectPlan.findActivityID(activityName), newName);
		} catch (FrozenException e) {
			System.out.println("Activity is frozen");
		} catch (UnknownIDException e) {
			System.out.println("Activity not found from ID");
		}
		activities = projectPlan.getActivities();
		active = activities.get((projectPlan.findActivityID(newName)));
		assertTrue(active instanceof Activity);
		assertTrue(projectPlan.findActivity(newName).contains(active));
		assertFalse(projectPlan.findActivity(activityName).contains(active));
	}
	
	/**
	 * This test will test the following:
	 * Renaming a project, assigning a leader to it and setting a start and end week for it.
	 */
	public void testProjectFeatures() {
		String projectName = "TestProject";
		String newName = "NewName";
		projectPlan.addProject(projectName);
		
//First, we rename the project, and test if it's found in the project plan under that name
//And not the other.
		try {
			projectPlan.renameProject(projectPlan.findProjectID(projectName), newName);
		} catch (FrozenException e) {
			System.out.println("Project is frozen.");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("Project ID doesn't correspond to a project");
			fail();
		}
		projects = projectPlan.getProjects();
		String project_id = projectPlan.findProjectID(newName);
		project = projects.get(project_id);
		assertTrue(projectPlan.findProject(newName).contains(project));
		assertFalse(projectPlan.findProject(projectName).contains(project));
		
//We then Assign a leader to it, and test to see if he is assigned properly.
		String employeeName = "TestEmployee";
		String initials = "TE";
		projectPlan.addEmployee(employeeName, initials);
		employees = projectPlan.getEmployees();
		employee = employees.get(initials);
		try {
			projectPlan.assignLeaderToProject(initials, project_id);
		} catch (FrozenException e) {
			System.out.println("Project frozen");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("ID unknown");
			fail();
		}
		assertEquals(project.getLeader(), employee);
		
//Then the project is assigned to a start- and endweek.
		int week_index = 4;
		int week_end = 10;
		try {
			projectPlan.setProjectStartWeek(project, week_index);
		} catch (FrozenException e) {
			System.out.println("Project Frozen");
			fail();
		}
		try {
			projectPlan.setProjectEndWeek(project, week_end);
		} catch (FrozenException e) {
			System.out.println("Project Frozen");
			fail();
		}
		assertTrue(week_index == project.getStartWeek());
		assertTrue(week_end == project.getEndWeek());
	}

}
