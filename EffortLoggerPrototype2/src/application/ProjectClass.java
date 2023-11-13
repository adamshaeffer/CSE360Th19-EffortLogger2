package application;

import java.util.ArrayList;

public class ProjectClass {
	public String name;
	public ArrayList<Integer> LifeCycleSteps;
	
	public ProjectClass(String n, ArrayList<Integer> l) {
		name = n;
		LifeCycleSteps = l;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setSteps(ArrayList<Integer> l) {
		LifeCycleSteps = l;
	}
}