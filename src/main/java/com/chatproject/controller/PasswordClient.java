package com.chatproject.controller;
import java.io.*;
import java.net.*;
import java.sql.CallableStatement;
import java.sql.Connection;
public class PasswordClient extends BaseController{
	public boolean AddUsers(UsersModel users) {
		String sqlAdd="Insert into users values (?,?)";
		try {
			Connection conn = getConnection();
			CallableStatement stmt=conn.prepareCall(sqlAdd);
			stmt.setString(1, users.getUsername());
			stmt.setString(2, users.getPassword());
			int row=stmt.executeUpdate();
			return row>0;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean UpdateUsers(UsersModel users ) {
		String sqlUpdate = "Update users set password=? where username=?";
		try {
			Connection conn= getConnection();
			CallableStatement stmt = conn.prepareCall(sqlUpdate);
			stmt.setString(1,users.getUsername());
			stmt.setString(2,users.getPassword());
			int row=stmt.executeUpdate();
			return row>0;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean DeleteUsers(UsersModel users) {
		String sqlDelete="Delete from users where username=? and password = ?";
		try {
			Connection conn = getConnection();
			CallableStatement stmt = conn.prepareCall(sqlDelete);
			stmt.setString(1, users.getUsername());
			stmt.setString(2, users.getPassword());
			int row=stmt.executeUpdate();
			return row>0;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void main(String[] args) throws IOException{
		Socket clientSocket=new Socket("localhost",3307);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter username:");
		String username=inFromUser.readLine();
		System.out.println("Enter password:");
		String password=inFromUser.readLine();
		outToServer.writeBytes(username+'\n');
		outToServer.writeBytes(password+'\n');
		clientSocket.close();
	}

}
