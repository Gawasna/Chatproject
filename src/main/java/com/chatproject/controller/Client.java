/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chatproject.controller;

/**
 *
 * @author hungl
 */
/**
 * 
 * public class Client {
    private String username;
    private boolean isLoggedIn;

    public Client(String username) {
        this.username = username;
        this.isLoggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    // Thêm các phương thức khác cần thiết cho việc gửi và nhận tin nhắn
}

**/

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        new Client();
    }

    public Client() throws Exception {
        Socket socket = new Socket("localhost", 8088);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (true) {
                try {
                    String message = in.readLine();
                    if (message != null) {
                        System.out.println(message);
                    } else {
                        System.out.println("Server disconnected");
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        out.writeBytes(username + "\n");
        while (true) {
            System.out.println("Enter username to send");
            String usernameToSend = scanner.nextLine();
            System.out.println("Enter message to send");
            String message = scanner.nextLine();
            out.writeBytes(usernameToSend + "\n");
            out.writeBytes(username+": "+message + "\n");
            out.flush();
        }
    }
}