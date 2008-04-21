package schedule;

import java.util.ArrayList;

import activity.Activity;


public class Week {
	
	ArrayList<Activity> scheduledActivities;
	int availableHours;
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
