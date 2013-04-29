package com.nysus.sassquatch;

public class User {
	
	private static User obj;
	static int op_ID;
	static String name = null;
	static String email = null;
	
	private User() {}
	
	public static User getInstance() {
		
		if (obj == null) {
			obj = new User();
		}
		
		return obj;
		
	}
	
	public static void setName(String username) {
		User.name = username;
	}
	
	public static String getName() {
		return User.name;
	}
	
	public static void setID(int ID) {
		User.op_ID = ID;
	}
	
	public static int getID() {
		return User.op_ID;
	}
	
	public static void setEmail(String email) {
		User.email = email;
	}
	
	public static String getEmail() {
		return User.email;
	}
	
}
