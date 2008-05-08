package projectplanner;

import java.util.ArrayList;

public class Project {
	private String name;
	private ArrayList<Activity> activities;
	private Employee leader;
	boolean frozen;
	int startweek;
	int endweek;
	
	public Project(String name) {
		startweek = 0;
		endweek = Integer.MAX_VALUE;
		frozen = false;
		leader = null;
		activities = new ArrayList<Activity>();
		this.name = name;
	}

	public Project(String name, Employee leader) {
		startweek = 0;
		endweek = Integer.MAX_VALUE;
		frozen = false;
		activities = new ArrayList<Activity>();
		this.name = name;
		this.leader = leader;
	}

	public void freeze() throws FrozenException {
		checkFreeze();
		frozen = true;
	}

	public void unfreeze() {
		frozen = false;
	}

	private void checkFreeze() throws FrozenException {
		if (frozen)
			throw new FrozenException(this);
	}	
	
	public void setName(String name) throws FrozenException {
		checkFreeze();
		this.name = name;
	}
	
	public void setStartWeek(int v) throws FrozenException {
		checkFreeze();
		startweek = v;
	}
	
	public void setEndWeek(int v) throws FrozenException {
		checkFreeze();
		endweek = v;
	}
	
	public void assignLeader(Employee projectLeader) throws FrozenException  {
		checkFreeze();
		this.leader = projectLeader;
	}
	
	public void addActivity(Activity activity) throws FrozenException {
		checkFreeze();
		activity.setParent(this);
		activities.add(activity);
	}

	public void freezeActivity(Activity activity) throws FrozenException {
		checkFreeze();
		activity.freeze();
	}
	
//	public int getRemainingWork() {
//		int load = 0;
//		for(Activity activity : activities){
//			//load += activity.workload-activity.progress;
//		}
//		return load;
//	}
	
	public ArrayList<Employee> getEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		for(Activity a : activities) {
			for(Employee e : a.getAssignedEmployees()) {
				employees.add(e);
			}
		}
		return employees;
	}
	
	public String toString() {
		String out = name;
		out += ". Start week: " + startweek + ", end week: " + endweek + ".";
		if(frozen) out += " [FROZEN]";
		out += "\n* Leader: ";
		if(leader==null) out += "none";
		else out += leader;
		out += "\n* Activities (" + activities.size() +"):\n";
		for(Activity a : activities) {
			out += "\t" + a + "\n";
		}
		return out;
	}
}
