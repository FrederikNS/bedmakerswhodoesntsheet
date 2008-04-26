package schedule;

import java.util.ArrayList;

import activity.Activity;

public class Schedule {
	ArrayList<Week> weeks;

	public Schedule(int length) { //CON: Start date?
		weeks = new ArrayList<Week>(length);
	}

	public Schedule() {
		weeks = new ArrayList<Week>(20 * 52); // FIXME: Allocate dynamically
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

	//CON: Make public?
	Week getWeek(int index) {
		return weeks.get(index);
	}
}
