package com.nysus.sassquatch;

public class Customer {
	
	private String ID;
	private String name;
	
	public Customer(String i, String n) {
		this.ID = i;
		this.name = n;
	}
	
	public String getID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.name;
	}
	
}
