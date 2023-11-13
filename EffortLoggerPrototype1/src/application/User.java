package application;

/*
 * User class to use until SQL Database is added
 * Author: Jacob Lusenhop
 */
public class User {
	private String username;
	private String password;
	private boolean isSupervisor;
	
	/*
	 * Constructor for user class
	 */
	public User(String username, String password, boolean isSupervisor) {
		this.username = username;
	    this.password = password;
	    this.isSupervisor = isSupervisor;
	}
	
	/*
	 * Returns the username of the user
	 */
	public String getUsername() {
		return this.username;
	}
	
	/*
	 * Returns true if the user is a supervisor, false otherwise
	 */
	public boolean isSupervisor() {
		return this.isSupervisor;
	}
	
	/*
	 * Returns true if the given password matches the users password, false otherwise
	 */
	public boolean verifyPassword(String password) {
		return this.password.equals(password);
	}
}
