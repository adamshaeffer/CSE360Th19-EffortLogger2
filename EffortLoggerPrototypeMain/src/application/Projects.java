package application;


public class Projects {
	
	Integer weights;
    String projects;
    String descriptions;
    String keywords;
	


	public Projects(Integer weights, String projects, String descriptions, String keywords) {
		this.weights = weights;
		this.projects = projects;
		this.descriptions = descriptions;
		this.keywords = keywords;
	}



	public Integer getWeights() {
		return weights;
	}
	public void setWeights(Integer weights) {
		this.weights = weights;
	}

	public String getProjects() {
		return projects;
	}
	public void setProjects(String projects) {
		this.projects = projects;
	}

	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	@Override
    public String toString() {
        return 	"	Backlog item:  " + projects + ";		" +
                "	Description:  " + descriptions + ";		" +
                "	Keywords:  " + keywords + ";		" +
                "	Weight:  " + weights;
    }
	
	
}
