package application;

import java.util.ArrayList;

public class ProjectLog {
	/*
	 * ProjectLog class
	 * Class for organizing the logs found in ActivityLog.txt
	 * Author: Adam Shaeffer
	 * 
	 */
	
	private String name;
	private ArrayList<String> activities;
	
	public ProjectLog(String n) {
		name = n;
		activities = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public ArrayList<String> getActivities() {
		return activities;
	}
	
	public void addActivity(String a) {
		activities.add(a);
	}
}