/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chatproject.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author hungl
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private HashMap<String, ClientHandler> clients = new HashMap<>();

    public Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(8088);
            System.out.println("Server started");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                try {
                    ClientHandler clientHandler = new ClientHandler(socket, this);
                    clientHandler.start();
                } catch (IOException e) {
                    socket.close();
                }
            }
        } catch (IOException e) {
        }
    }

    public synchronized void broadcastMessage(String message, String sender) {
        for (ClientHandler clientHandler : clients.values()) {
            clientHandler.sendMessage(sender, message);
        }
    }

    public synchronized void addClient(String username, ClientHandler clientHandler) {
        clients.put(username, clientHandler);
    }

    public synchronized void removeClient(String username) {
        clients.remove(username);
    }
    
}



