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
 * public class ClientHandler implements Runnable {
    private Socket socket;
    private Server server;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isLoggedIn;
    private String username;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.isLoggedIn = false;
    }

    @Override
    public void run() {
        // Xử lý việc đăng nhập và giao tiếp với client

    }

    // Thêm các phương thức khác cần thiết cho việc xử lý kết nối từ client đến server
}**/

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler extends Thread {
    private String username;
    private BufferedReader in;
    private PrintWriter out;
    private Server server;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.server = server;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            // Đăng nhập khách và tạo tên người dùng ngẫu nhiên
            LoginHandler loginHandler = new LoginHandler();
            username = loginHandler.generateGuestUsername();
            server.addClient(username, this);

            // Gửi thông báo đến client về việc đăng nhập thành công và tên người dùng của họ
            out.println("Welcome to the chat, " + username + "!");

            String input;
            while ((input = in.readLine()) != null) {
                // Xử lý tin nhắn từ client
                server.broadcastMessage(input, username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Xóa client khi họ ngắt kết nối
            server.removeClient(username);
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String sender, String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = now.format(formatter);
        out.println(sender + ": " + message + " (" + formattedTime + ")");
    }
}
