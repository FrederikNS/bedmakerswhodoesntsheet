package schedule;

import java.util.ArrayList;

import activity.Activity;

public class Schedule {
	ArrayList<Week> weeks;
	public Schedule() { //Parm: Start date?
		weeks = new ArrayList<Week>();
	}
	
	public void assignActivityToWeek(Activity activity, int hours, Week week) {
		activity.addWeek(week, hours);
		week.addActivity(activity);
	}
	
	public void assignActivityToWeek(Activity activity, int hours, int weekIndex) {
		assignActivityToWeek(activity, hours, getWeek(weekIndex));
	}
	
	public void removeActivityFromWeek(Activity activity, Week week) {
		activity.removeWeek(week);
		week.removeActivity(activity);
	}
	
	public void removeActivity(Activity activity) {
		activity.remove();
	}
	
	public void removeActivityFromWeek(Activity activity, int weekIndex) {
		removeActivityFromWeek(activity, getWeek(weekIndex));
	}
	
	Week getWeek(int index) {
		return weeks.get(index);
	}
}
