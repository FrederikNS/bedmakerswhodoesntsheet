package schedule;

import java.util.ArrayList;

import exceptions.FrozenException;

import activity.Activity;

public class Week {
	private int index;
	private ArrayList<Activity> scheduledActivities;
	
	public Week(int index) {
		this.index = index;
		this.scheduledActivities = new ArrayList<Activity>();
	}
	
	public int getIndex() {
		return index;
	}
	
	public void addActivity(Activity activity) {
		scheduledActivities.add(activity);
	}
	
	public void removeActivity(Activity activity) throws FrozenException {
		scheduledActivities.remove(activity);
		activity.removeWeek(this);
	}
	
	public float getAssignedHours() {
		float hoursum = 0;
		for(Activity activity : scheduledActivities) {
			hoursum += activity.getHoursForWeek(this);
		}
		return hoursum;
	}
	
	public boolean isEmpty() {
		return scheduledActivities.isEmpty();
	}
}
