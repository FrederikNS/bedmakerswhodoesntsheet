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
	 * Edit: The code is slightly more readable, but I'm going to bed now..
	 */
	private ProjectPlan projectPlan;
	private Activity activity;
	private Project project;
	private Employee employee;
	private HashMap<String,Activity> activities;
	private HashMap<String,Project> projects;
	private HashMap<String,Employee> employees;
	String activityName = "TestActivity";
	String projectName = "TestProject";
	String employeeName = "TestEmployee";
	String employeeName2 = "Test Employee";
	String initials = "TeEm";
	int weekIndex = 4;
	int weekend = 10;
	int hours = 15;
	String newName = "NewName";

	protected void setUp() throws Exception {
		super.setUp();
		projectPlan = new ProjectPlan();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	private Activity findActivity(String activityName) {
		activities = projectPlan.getActivities();
		activity = activities.get((projectPlan.findActivityID(activityName)));
		return activity;
	}
	private Project findProject(String projectName) {
		projects = projectPlan.getProjects();
		project = projects.get(projectPlan.findProjectID(projectName));
		return project;
	}
	private Employee findEmployee(String employeeName){
		employees = projectPlan.getEmployees();
		employee = employees.get(initials);
		return employee;
	}

	/**
	 * In this simple test, we check if the activity is added correctly.
	 */
	public void testAddActivity(){		
		projectPlan.addActivity(activityName);
		findActivity((activityName));
		assertTrue(activity instanceof Activity);
		assertTrue(projectPlan.findActivity(activityName).contains(activity));
	}
	
	/**
	 * In this simple test, we check if the project is added correctly.
	 */
	public void testAddProject() {
		projectPlan.addProject(projectName);
		findProject(projectName);
		assertTrue(project instanceof Project);
		assertTrue(projectPlan.findProject(projectName).contains(project));
	}
	
	/**
	 * In this simple test, we check if the employee is added correctly.
	 */
	public void testAddEmployee1() {
		projectPlan.addEmployee(employeeName, initials);
		findEmployee(employeeName);
		assertTrue(employee instanceof Employee);
		assertTrue(projectPlan.findEmployee(employeeName).contains(employee));
	}
	
	/**
	 * In this test, we try to add the employee with the feature to
	 * automatically generate the initials.
	 * Afterwards, we attempt to trigger the exception.
	 */
	public void testAddEmployee2() {
		try {
			projectPlan.addEmployee(employeeName2);
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
		findEmployee(employeeName2);
		String generatedInitials = projectPlan.findEmployeeID(employeeName2);
/* 
 * We first test if the employee object is added.
 * Then we see if it's in fact the correct employee, and finally, we check to see
 * if the initials match the expected result.
 */
		assertTrue(employee instanceof Employee);
		assertTrue(projectPlan.findEmployee(employeeName2).contains(employee));
		assertEquals(initials, generatedInitials);
	}
	
	/**
	 * In this test, we attempt to add projects with an employee set as a leader.
	 * We then try to make it catch the exception with an invalid leader.
	 */
	public void testAddProjectWithLeader() {
//First, we try assigning a valid leader.
		testAddEmployee1();
		try {
			projectPlan.addProjectWithLeader(projectName, initials);
		} catch (UnknownIDException e) {
			System.out.println("The leader ID does not exist");
			fail();
		}
		findProject(projectName);
		project.getLeader();
		findEmployee(employeeName);
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
		testAddActivity();
		
//First, a regular activity that should work out fine.
		try {
			projectPlan.assignActivityToWeek(findActivity(activityName).getID(), hours, weekIndex);
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
			projectPlan.freezeActivity(findActivity(activityName).getID());
		} catch (FrozenException e) {
			System.out.println("Activity is already frozen");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("Activity could not be found from ID");
			fail();
		}
		//Adding the frozen activity.
		try {
			projectPlan.assignActivityToWeek(findActivity(activityName).getID(), hours, weekIndex);
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
		testAddActivity();
		
//First, adding a regular activity that should work out fine.
		try {
			projectPlan.assignActivityToWeek(findActivity(activityName).getID(), hours, weekIndex);
		} catch (FrozenException e) {
			System.out.println("Activity is frozen and unavailable");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("Activity could not be found from ID");
			fail();
		}
		
//Then we remove it. Knowing from the last test that it adds correctly.
		try {
			projectPlan.removeActivityFromWeek(findActivity(activityName).getID(), weekIndex);
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
		testAddActivity();
		testAddProject();
		try {
			projectPlan.addActivityToProject(findActivity(activityName).getID(), projectPlan.findProjectID(projectName));
		} catch (FrozenException e) {
			System.out.println("The activity or project is frozen");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("The activity or project ID could not be found");
			fail();
		}
		findProject(projectName);
//Knowing we only added the one activity, we check to see if the project contains it.
		assertEquals(project.getActivities(), projectPlan.findActivity(activityName));
		
//We then try to remove it again.
		try {
			projectPlan.removeActivityFromProject(findActivity(activityName).getID(), projectPlan.findProjectID(projectName));
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
		testAddActivity();
		try {
			projectPlan.renameActivity(findActivity(activityName).getID(), newName);
		} catch (FrozenException e) {
			System.out.println("Activity is frozen");
		} catch (UnknownIDException e) {
			System.out.println("Activity not found from ID");
		}
		
		findActivity(newName);
		assertTrue(activity instanceof Activity);
		assertTrue(projectPlan.findActivity(newName).contains(activity));
		assertFalse(projectPlan.findActivity(activityName).contains(activity));
	}
	
	/**
	 * This test will test the following:
	 * Renaming a project, assigning a leader to it and setting a start and end week for it.
	 */
	public void testProjectFeatures() {
		testAddProject();
		
//First, we rename the project, and test if it's found in the project plan under that name
//And not the other.
		try {
			projectPlan.renameProject(findProject(projectName).getId(), newName);
		} catch (FrozenException e) {
			System.out.println("Project is frozen.");
			fail();
		} catch (UnknownIDException e) {
			System.out.println("Project ID doesn't correspond to a project");
			fail();
		}
		findProject(newName);
		String project_id = findProject(newName).getId();
		
		assertTrue(projectPlan.findProject(newName).contains(project));
		assertFalse(projectPlan.findProject(projectName).contains(project));
		
//We then Assign a leader to it, and test to see if he is assigned properly.
		testAddEmployee1();
		findEmployee(employeeName);
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
		try {
			projectPlan.setProjectStartWeek(project, weekIndex);
		} catch (FrozenException e) {
			System.out.println("Project Frozen");
			fail();
		}
		try {
			projectPlan.setProjectEndWeek(project, weekend);
		} catch (FrozenException e) {
			System.out.println("Project Frozen");
			fail();
		}
		assertTrue(weekIndex == project.getStartWeek());
		assertTrue(weekend == project.getEndWeek());
	}

}
