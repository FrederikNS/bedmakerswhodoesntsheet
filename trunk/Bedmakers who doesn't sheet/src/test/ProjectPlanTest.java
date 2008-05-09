package test;

import java.util.HashMap;

import junit.framework.TestCase;
import projectplanner.Activity;
import projectplanner.ProjectPlan;

public class ProjectPlanTest extends TestCase {

	private ProjectPlan projectPlan;
	private Activity active;
	private HashMap<String,Activity> activities;

	protected void setUp() throws Exception {
		super.setUp();
		projectPlan = new ProjectPlan();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddActivity(){
		String projectName = "TestActivity";
		projectPlan.addActivity(projectName);
		activities = projectPlan.getActivities();
		active = activities.get((projectPlan.findActivityID(projectName)));
		assertTrue(active instanceof Activity);
	}
}
