package application;

public class LifeCycleStep {

	/*
	 * LifeCycleStep class
	 * Class for life cycle steps in the modify projects page
	 * Author: Adam Shaeffer
	 * 
	 */
	private String name;
	private int effort;
	private int deliverable;
	
	public LifeCycleStep(String n, int e, int d) {
		name = n;
		effort = e;
		deliverable = d;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setEffort(int e) {
		effort = e;
	}
	
	public int getEffort() {
		return effort;
	}
	
	public void setDeliverable(int d) {
		deliverable = d;
	}
	
	public int getDeliverable() {
		return deliverable;
	}
}