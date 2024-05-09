package com.chatproject.controller;
import java.io.*;
import java.net.*;
public class RGClient {
	public static void main(String[] args) {
		try(Socket socket = new Socket("localhost",3307)){
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String serverResponse;
			while((serverResponse = in.readLine()) != null) {
				System.out.println(serverResponse);
			}
		}catch(IOException e) {
			System.out.println("Client excepiton: "+ e.getMessage());
			e.printStackTrace();
		}
	}

}
