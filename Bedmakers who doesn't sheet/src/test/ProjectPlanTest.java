package test;

import java.util.HashMap;

import junit.framework.TestCase;
import projectplanner.Activity;
import projectplanner.Employee;
import projectplanner.FrozenException;
import projectplanner.Project;
import projectplanner.ProjectPlan;
import projectplanner.UnknownIDException;
import projectplanner.Week;

public class ProjectPlanTest extends TestCase {

	private ProjectPlan projectPlan;
	private Activity active;
	private Project project;
	private Employee employee;
	private Week week;
	private HashMap<String,Activity> activities;
	private HashMap<String,Project> projects;
	private HashMap<String,Employee> employees;
	private HashMap<Integer,Week> weeks;

	protected void setUp() throws Exception {
		super.setUp();
		projectPlan = new ProjectPlan();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddActivity(){
		String activityName = "TestActivity";
		projectPlan.addActivity(activityName);
		activities = projectPlan.getActivities();
		active = activities.get((projectPlan.findActivityID(activityName)));
		assertTrue(active instanceof Activity);
		assertTrue(projectPlan.findActivity(activityName).contains(active));
	}
	
	public void testAddProject() {
		String projectName = "TestProject";
		projectPlan.addProject(projectName);
		projects = projectPlan.getProjects();
		project = projects.get(projectPlan.findProjectID(projectName));
		assertTrue(project instanceof Project);
		assertTrue(projectPlan.findProject(projectName).contains(project));
	}
	
	public void testAddEmployee1() {
		String employeeName = "TestEmployee";
		String initials = "TE";
		projectPlan.addEmployee(employeeName, initials);
		employees = projectPlan.getEmployees();
		employee = employees.get(initials);
		assertTrue(employee instanceof Employee);
		assertTrue(projectPlan.findEmployee(employeeName).contains(employee));
	}
	
	public void testAddEmployee2() {
		String employeeName = "Test Employee";
		String initials = "TeEm"; //What the initials should be, if successful.
		try {
			projectPlan.addEmployee(employeeName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Initials could not be created from name");
			e.printStackTrace();
			fail();
		}
		//Test to try and trigger the exception.
		String failName = "T e"; //Should fail, since it doesn't contain two letters in each name.
		try {
			projectPlan.addEmployee(failName);
			fail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Initials could not be created from name.\nTest Passed.");
		}
		employees = projectPlan.getEmployees();
		employee = employees.get(initials);
		String generatedInitials = projectPlan.findEmployeeID(employeeName);
		assertTrue(employee instanceof Employee);
		assertTrue(projectPlan.findEmployee(employeeName).contains(employee));
		assertEquals(initials, generatedInitials);
	}
	
	public void testAddProjectWithLeader() {
		String projectName = "TestProject";
		String employeeName = "TestEmployee";
		String initials = "TE";
		projectPlan.addEmployee(employeeName, initials);
		String leaderInit = initials;
		try {
			projectPlan.addProjectWithLeader(projectName, leaderInit);
		} catch (UnknownIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		projects = projectPlan.getProjects();
		project = projects.get(projectPlan.findProjectID(projectName));
		project.getLeader();
		employees = projectPlan.getEmployees();
		employee = employees.get(initials);
		assertTrue(project instanceof Project);
		assertTrue(projectPlan.findProject(projectName).contains(project));
		assertEquals(project.getLeader(), employee);
	}
	//TODO: Finish this testclass. And the rest, for that matter.
	public void testAssignActivityToWeek() {
		String activityName = "TestActivity";
		projectPlan.addActivity(activityName);
		try {
			projectPlan.assignActivityToWeek(projectPlan.findActivityID(activityName), 4, 15);
		} catch (FrozenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public void test
}
