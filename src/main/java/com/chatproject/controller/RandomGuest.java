package com.chatproject.controller;
import java.io.*;
import java.net.*;
import java.util.Random; 
class RandomGuest extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STRING_LENGTH=8;
	public RandomGuest(Socket socket) {
		this.socket=socket;
	}
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out= new PrintWriter(socket.getOutputStream(),true);
			String username = generateRandomString();
			String password = generateRandomString();
			out.println("Username: "+username);
			out.println("Password: "+password);
			socket.close();
			
			
		} catch(IOException e) {
			System.out.println("Server exception: "+e.getMessage());
			e.printStackTrace();
		}
	}
	private String generateRandomString() {
		StringBuffer randStr = new StringBuffer();
		for(int i=0;i<RANDOM_STRING_LENGTH;i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
			
		}
		return randStr.toString();
	}
	private int getRandomNumber() {
		int randomInt =0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if(randomInt -1 == -1) {
			return randomInt;
		}else {
			return randomInt -1;
		}
	}
    
}

