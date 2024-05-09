package com.chatproject.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

	public class RGServer{
		public static void main(String[] args) {
			try (ServerSocket serverSocket = new ServerSocket(3307)){
				System.out.println("Server is listening on port 3307");
				while(true) {
					Socket socket = serverSocket.accept();
					System.out.println("New client connected");
					RandomGuest newClient = new RandomGuest(socket);
					newClient.start();
				}
			} catch(IOException ex) {
				System.out.println("Server exception: "+ ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

