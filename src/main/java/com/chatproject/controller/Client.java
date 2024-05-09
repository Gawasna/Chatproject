package com.chatproject.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String hostname, int port) {
        try {
            socket = new Socket(hostname, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error creating client: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        out.println(message); 
    }

    public String receiveMessage() throws IOException {
        return in.readLine(); 
    }

    public void close() {
        try {
            socket.close(); 
        } catch (IOException e) {
            System.err.println("Error closing client: " + e.getMessage());
        }
    }
    
}
