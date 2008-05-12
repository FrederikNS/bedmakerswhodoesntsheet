package test;

import projectplanner.*;
import junit.framework.TestCase;

public class EmployeeTest extends TestCase {
	Activity activity1;
	Project project1;
	Employee employee1;
	
	public void setUp() {
		project1 = new Project("","testproject");
		activity1 = new Activity("", "Testactivity", project1);
		employee1 = new Employee("Peon 2", "PEON");
		try {
			project1.addActivity(activity1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testProjectLeaderFunctions() {
		try {
			employee1.assignProjectLead(project1);
			assertTrue(employee1.isAssignedToProject(project1));
			assertTrue(employee1.isLeaderOfProject(project1));
			employee1.removeFromProjectLead(project1);
			assertTrue(employee1.isAssignedToProject(project1));
			assertFalse(employee1.isLeaderOfProject(project1));
		} catch (Exception e) {
			fail();
		}
		try {
			employee1.removeFromProjectLead(project1);
			fail();
		} catch (Exception e) {	}
		try {
			employee1.relieveFromProject(project1, true);
		} catch (Exception e) {	fail(); }
		assertFalse(employee1.isAssignedToProject(project1));
		try {
			employee1.relieveFromProject(project1, true);
			fail();
		} catch (Exception e) {	}
		try {
		} catch (Exception e) { fail(); }
	}
	
	public void testActivityAssigment() {
		try { employee1.assignToProject(project1); } catch (Exception e) { fail(); }
		try { employee1.assistActivity(activity1); fail(); } catch (Exception e) { }
		try {
			employee1.assignToActivity(activity1);
			assertTrue(employee1.isAssignedToActivity(activity1));
			assertTrue(employee1.isAssignedToActivityAsEmployee(activity1));
			assertFalse(employee1.isAssignedToActivityAsAssistant(activity1));
		} catch (Exception e) { fail(); }
		try { employee1.assistActivity(activity1); fail(); } catch (Exception e) { }
		try {
			employee1.relieveFromProject(project1, true);
			assertTrue(employee1.isAssignedToActivityAsAssistant(activity1));
			employee1.assignToProject(project1);
			assertTrue(employee1.isAssignedToActivityAsEmployee(activity1));
			employee1.relieveFromActivity(activity1);
			assertFalse(employee1.isAssignedToActivity(activity1));
		} catch (Exception e) { fail(); }
	}
	
	public void testFreeze() {
		assertFalse(employee1.isFrozen());
		try { employee1.freeze(); } catch (Exception e) { fail(); }
		assertTrue(employee1.isFrozen());
		try { employee1.assignProjectLead(project1); fail(); } catch (Exception e) {}
		try { employee1.removeFromProjectLead(project1); fail(); } catch (Exception e) {}
		try { employee1.assignToActivity(activity1); fail(); } catch (Exception e) {}
		try { employee1.unfreeze(); } catch (Exception e) { fail(); }
		try { employee1.assignProjectLead(project1); } catch (Exception e) { fail(); }
		try { employee1.removeFromProjectLead(project1); } catch (Exception e) { fail(); }
		try { employee1.assignToActivity(activity1); } catch (Exception e) { fail(); }
	}
}
