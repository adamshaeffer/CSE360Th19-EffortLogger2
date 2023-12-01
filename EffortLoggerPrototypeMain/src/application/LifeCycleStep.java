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
	private String description;
	private String keywords;
	private int estimate;
	
	public LifeCycleStep(String n, int e, int d, String ds, String k, int es) {
		name = n;
		effort = e;
		deliverable = d;
		description = ds;
		keywords = k;
		estimate = es;
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

	public void setDescription(String d) {
		description = d;
	}
	
	public String getDescription() {
		return description;
	}

	public void setKeywords(String k) {
		keywords = k;
	}
	
	public String getKeywords() {
		return keywords;
	}

	public void setEstimate(int e) {
		estimate = e;
	}

	public int getEstimate() {
		return estimate;
	}
}