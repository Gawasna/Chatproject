package com.chatproject.controller;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class PasswordServer extends BaseController{
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException{
		ServerSocket serverSocket = new ServerSocket(3307);
		System.out.println("Server is waiting for client...");
		Socket connectionSocket = serverSocket.accept();
		BufferedReader inFromClient=new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		String username = inFromClient.readLine();
		String password = inFromClient.readLine();
		MessageDigest md= MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());
		byte[] byteData=md.digest();
		StringBuffer sb= new StringBuffer();
		for(int i=0;i<byteData.length;i++) {
			sb.append(Integer.toString((byteData[i] & 0xff)+0x100, 16).substring(1));
		}
		String hashedPassword = sb.toString();
		System.out.println("Username: "+username);
		System.out.println("Hashed Password:" +hashedPassword);
		serverSocket.close();
		
		
	}

}
