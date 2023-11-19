package application;

/*
 * User class to use until SQL Database is added
 * Author: Jacob Lusenhop
 */
public class User {
	private String username;
	private String password;
	private String role;
	
	/*
	 * Constructor for user class
	 */
	public User(String username, String password, String role) {
		this.username = username;
	    this.password = password;
	    this.role = role;
	}
	
	/*
	 * Returns the username of the user
	 */
	public String getUsername() {
		return this.username;
	}
	
	/*
	 * Returns the role of the user
	 */
	public String getRole() {
		return role;
	}
	
	/*
	 * Returns true if the user is a supervisor, false otherwise
	 */
	public boolean isSupervisor() {
		return this.role.equals("Supervisor");
	}
	
	/*
	 * Returns true if the given password matches the users password, false otherwise
	 */
	public boolean verifyPassword(String password) {
		return this.password.equals(password);
	}
}
