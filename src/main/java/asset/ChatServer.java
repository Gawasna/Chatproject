/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asset;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Map<String, PrintWriter> clientWriters = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running...");
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter writer;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                writer.println("Enter your username:");
                username = reader.readLine();

                synchronized (clientWriters) {
                    clientWriters.put(username, writer);
                    broadcastUserList();
                }

                broadcast(username + " has joined the chat");

                String message;
                while ((message = reader.readLine()) != null) {
                    broadcast(username + ": " + message);
                }
            } catch (IOException e) {
                System.out.println(username + " has left the chat");
            } finally {
                if (username != null) {
                    clientWriters.remove(username);
                    broadcastUserList();
                    broadcast(username + " has left the chat");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcast(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters.values()) {
                    writer.println(message);
                }
            }
        }

        private void broadcastUserList() {
            StringBuilder userList = new StringBuilder("USERLIST:");
            synchronized (clientWriters) {
                for (String user : clientWriters.keySet()) {
                    userList.append(user).append(",");
                }
            }
            broadcast(userList.toString());
        }
    }
}
