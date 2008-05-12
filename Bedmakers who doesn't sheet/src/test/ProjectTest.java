package test;

import projectplanner.Activity;
import projectplanner.Employee;
import projectplanner.Project;
import projectplanner.Week;
import junit.framework.TestCase;

public class ProjectTest extends TestCase {
	Activity activity1;
	Activity activity2;
	Project project1;
	Employee employee1;
	
	public void failmsg(String msg) {
		System.out.println(msg);
		fail();
	}
	
	public void setUp() {
		project1 = new Project("","testproject");
		activity1 = new Activity("", "Testactivity");
		activity2 = new Activity("", "Testactivity2");
		employee1 = new Employee("Peon 2", "PEON");
		try {
			project1.addActivity(activity1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testActivityAssignment() {
		try {
			//already assigned in setUP, so should fail
			project1.addActivity(activity1);
			fail();
		} catch (Exception e) { }
		try {
			assertTrue(activity1.getParentProject()==project1);
			assertTrue(project1.getActivities().contains(activity1));
		} catch (Exception e) { fail(); }
		try { project1.addActivity(activity1); fail(); } catch (Exception e) {}
		try {
			activity1.addWeek(new Week(1), 10);
			activity1.registerProgressFromEmployee(3, employee1);
			assertTrue(project1.getProgress()==3);
			assertFalse(project1.getWorkload()==0);
			assertTrue(project1.getWorkload()==10);
			project1.addActivity(activity2);
			activity2.addWeek(new Week(2), 7);
			activity2.registerProgressFromEmployee(6, employee1);
			assertTrue(project1.getProgress()==9);
			assertTrue(project1.getWorkload()==17);
			activity1.freeze();
			assertTrue(project1.getProgress()==6);
			assertTrue(project1.getWorkload()==7);
		} catch (Exception e) { e.printStackTrace(); fail(); }
	}
}
