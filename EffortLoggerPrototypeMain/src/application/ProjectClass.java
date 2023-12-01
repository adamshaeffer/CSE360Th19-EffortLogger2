package application;

import java.util.ArrayList;

public class ProjectClass {
	
	/*
	 * ProjectClass class
	 * Class for projects in the modify projects page
	 * Author: Adam Shaeffer
	 * 
	 */

	private String name;
	private ArrayList<Integer> LifeCycleSteps;
	private double averageWeights;
	
	public ProjectClass(String n, ArrayList<Integer> l) {
		name = n;
		LifeCycleSteps = l;
		averageWeights = 0;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSteps(ArrayList<Integer> l) {
		LifeCycleSteps = l;
	}
	
	public ArrayList<Integer> getSteps() {
		return LifeCycleSteps;
	}

	public void addEffort(double w) {
		averageWeights = w;
	}

	public double getEffort() {
		return averageWeights;
	}
}