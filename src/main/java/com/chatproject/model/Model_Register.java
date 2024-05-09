package com.chatproject.model;

public class Model_Register {
	   private String username;
	   private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Model_Register(String username, String password) {
		this.username=username;
		this.password=password;
		
	}
	public Model_Register() {
		
	}
}
