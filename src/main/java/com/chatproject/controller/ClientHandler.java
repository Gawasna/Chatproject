package com.chatproject.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error creating client handler: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
    
}
