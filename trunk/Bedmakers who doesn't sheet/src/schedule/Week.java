package schedule;

import java.util.ArrayList;

import activity.Activity;


public class Week {
	int index;
	ArrayList<Activity> scheduledActivities;
	public Week(int index) {
		this.index = index;
		this.scheduledActivities = new ArrayList<Activity>();
	}
	public void addActivity(Activity activity) {
		scheduledActivities.add(activity);
	}
	public void removeActivity(Activity activity) {
		scheduledActivities.remove(activity);
	}
	public float getAssignedHours() {
		float hoursum = 0;
		for(Activity activity : scheduledActivities) {
			hoursum += activity.getHoursForWeek(this);
		}
		return hoursum;
	}
}
