package projectplanner;

import java.util.ArrayList;

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
	
	public void removeActivity(Activity activity) {
		scheduledActivities.remove(activity);
	}
	
	public ArrayList<Activity> getScheduledActivities() {
		return scheduledActivities;
	}
	
	public float getAssignedHours() throws ActivityException {
		float hoursum = 0;
		for(Activity activity : scheduledActivities) {
			hoursum += activity.getWorkloadForWeek(this);
		}
		return hoursum;
	}
	
	public boolean isEmpty() {
		return scheduledActivities.isEmpty();
	}

	public int getNumberOfScheduledActivities() {
		return getScheduledActivities().size();
	}
}
