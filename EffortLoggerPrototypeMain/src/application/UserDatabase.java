package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/*
 * Stand in for SQL Database to be added later
 * Author: Jacob Lusenhop
 */
public class UserDatabase {
	
	/*
	 * Adds user to text file database
	 */
	public static void addUser(String username, String password, String role, String firstName, String lastName) {
		try {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("userdatabase.txt", true));
		bufferedWriter.write(username + "," + password + "," + role + "," + firstName + "," + lastName + "\n");
		bufferedWriter.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Returns an array list of all users
	 */
	public static ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();
		try {
			 BufferedReader bufferedReader = new BufferedReader(new FileReader("userdatabase.txt"));
			 
			 String line = bufferedReader.readLine();
			 while(line != null) {
				 String[] tokens = line.split(",", 5);
				 
				 String username = tokens[0];
				 String password = tokens[1];
				 String role = tokens[2];
				 String firstName = tokens[3];
				 String lastName = tokens[4];
				 
				 users.add(new User(username, password, role, firstName, lastName));
				 
				 line = bufferedReader.readLine();
			 }
			 bufferedReader.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return users;
	}
}
