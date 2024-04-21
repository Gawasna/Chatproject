/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chatproject.view;
import java.io.*;
import java.net.*;
import java.util.Random;

public class Client {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Client(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connected to the server.");

            // Generate random username
            String username = generateRandomUsername();
            // Send login request with generated username and type
            output.println("LOGIN," + username + ",GUEST_LOGIN");

            // Start listening for server responses
            startListening();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startListening() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response;
                    while ((response = input.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private String generateRandomUsername() {
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000; // Generate a random number between 10000 and 99999
        return "Guest@" + randomNumber;
    }
    
    public static void main(String[] args) {
        String serverAddress = "192.168.11.109"; // Change this to your server address
        int port = 12345; // Change this to your server port
        Client client = new Client(serverAddress, port);
    }
}
