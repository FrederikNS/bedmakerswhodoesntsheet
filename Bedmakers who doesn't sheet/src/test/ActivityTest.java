package test;

import projectplanner.Activity;
import projectplanner.Week;
import junit.framework.TestCase;

/**
 * @author Kevin Rene Broloes
 *
 */
public class ActivityTest extends TestCase {
	public void testProgress() {
		try {
			Activity a = new Activity("", "test");
			Week w = new Week(0);
			a.addWeek(w, 10);
			w.addActivity(a);
			assertEquals(10.0f,a.getWorkloadForWeek(w));
			assertEquals(w.getAssignedHours(),a.getWorkloadForWeek(w));
		} catch (Exception e) {
			fail();
		}
	}
}
