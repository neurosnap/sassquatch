package com.nysus.sassquatch;

public class User {
	
	private static User obj;
	static String name = null;
	
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
	
}
