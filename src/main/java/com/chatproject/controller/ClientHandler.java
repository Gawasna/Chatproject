/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chatproject.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hungl
 */
    
    public class ClientHandler implements Runnable {
    private Socket socket;
    private ServerManager server;
    private PrintWriter out;
    private String username;
    private Set<ClientHandler> conversationPartners = new HashSet<>();

    public ClientHandler(Socket socket, ServerManager server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out = new PrintWriter(socket.getOutputStream(), true);

            // Get the username from the client
            out.println(username);
            username = in.readLine();

            System.out.println(username + " has joined the chat.");
            server.addClient(this);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received message from " + username + ": " + message);
                handleMessage(message);
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void addConversationPartner(ClientHandler partner) {
        conversationPartners.add(partner);
    }

    public void removeConversationPartner(ClientHandler partner) {
        conversationPartners.remove(partner);
    }

    private void handleMessage(String message) {
        if (message.startsWith("TO:")) {
            String[] parts = message.split(":");
            String partnerUsername = parts[1];
            String partnerMessage = parts[2];

            ClientHandler partner = server.getClientByUsername(partnerUsername);
            if (partner != null) {
                partner.sendMessage(username + ": " + partnerMessage);
            }
        } else {
            for (ClientHandler partner : conversationPartners) {
                partner.sendMessage(username + ": " + message);
            }
        }
    }

    private void disconnect() {
        if (out != null) {
            out.close();
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
        server.removeClient(this);
        System.out.println(username + " has left the chat.");
    }

    public String getUsername() {
        return username;
    }

    public void setConversationPartners(Set<ClientHandler> conversationPartners) {
        this.conversationPartners = conversationPartners;
    }
}