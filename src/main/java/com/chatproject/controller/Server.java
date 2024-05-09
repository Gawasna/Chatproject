package com.chatproject.controller;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class Server {
    private static final int PORT = 12345;
    private final List<ClientHandler> clients = new ArrayList<>();
    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName("0.0.0.0"));
            System.out.println("Server is running on port "+PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e.getMessage());
        }
    }
    public synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}
    	
    
