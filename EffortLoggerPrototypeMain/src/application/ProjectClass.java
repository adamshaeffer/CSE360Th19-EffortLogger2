package application;

import java.util.ArrayList;

public class ProjectClass {
	private String name;
	private ArrayList<Integer> LifeCycleSteps;
	
	public ProjectClass(String n, ArrayList<Integer> l) {
		name = n;
		LifeCycleSteps = l;
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
}