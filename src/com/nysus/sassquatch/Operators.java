package com.nysus.sassquatch;

public class Operators {

	private int op_ID;
	private String username;
	private String email;
	
	public Operators(int o, String u, String e) {
		this.op_ID = o;
		this.username = u;
		this.email = e;
	}
	
	public int getID() {
		return this.op_ID;
	}
	
	public String getUser() {
		return this.username;
	}
	
	public String getEmail() {
		return this.email;
	}
	
}
